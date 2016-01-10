package fr.free.bworld.myapt.resources;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.free.bworld.myapt.images.BrowseImageDir;

/**
 * Simple implementation that lists the files in a sub directory of a Maven Web Site "site/resources" folder
 * and generates to the console a list in apt format of links to these files.
 * @author blerubrus
 */
public class InspectImpl implements Inspect {
	
	/**Filenames that are excluded by default when listing directories.*/
	public static String[] DEFAULT_IGNORED_FILENAMES = {".svn", ".DS_Store"};
	
	/**Allows to accept or not some filenames.*/
	public FilenameFilter filenameFilter;
	
	/**The path to the directory we want to inspect.*/
	private final String path;
	
	
	/**
	 * Sets the path and filenames filter.
	 * @param path is the path to the directory we want to inspect.
	 */
	public InspectImpl(String path) {
		if (path == null){
			throw new IllegalArgumentException("the given path must not be null...");
		}
		
		this.path = path;
		if (! checkPath()){
			throw new IllegalArgumentException("the given path '" + path + "' is not an existing readable directory...");
		}
		
		filenameFilter = new FilenameFilter() {
			/**
			 * checks that the given file name does not end with one of the items in the default ignored filenames list.
			 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
			 */
			@Override
			public boolean accept(File arg0, String arg1) {
				boolean foundInIgnores = false;
				for (String ignore : DEFAULT_IGNORED_FILENAMES){
					if (arg0.getName().endsWith(ignore)){
						foundInIgnores = true;
						break;
					}
				}
				if (foundInIgnores){
					return false;
				}
				else{
					return true;
				}
			}
		};
	}
	
	
	
	/**
	 * Getter.
	 * @return the filenameFilter
	 */
	public FilenameFilter getFilenameFilter() {
		return filenameFilter;
	}



	/**
	 * Setter.
	 * @param filenameFilter the filenameFilter to set
	 */
	public void setFilenameFilter(FilenameFilter filenameFilter) {
		this.filenameFilter = filenameFilter;
	}



	/**
	 * If non recursive, returns the files in the current directory.
	 * For recursive option, returns the files in the hierarchy.
	 * @see fr.free.bworld.myapt.resources.Inspect#list(boolean)
	 */
	public List<Resource> list(List<Resource> listing, String path, boolean recursive) {
		File pathFile = new File(path);
		File[] files = pathFile.listFiles(filenameFilter);
		for (File f : files){
			if (f.isFile()){
				Resource r = new ResourceImpl(path, f.getName());
				listing.add(r);
			}
			else if (recursive && f.isDirectory() && f.canRead()){
				File subdir = new File(pathFile, f.getName());
				listing.addAll(list(new ArrayList<Resource>(), subdir.getPath(), recursive));
			}
		}
		return listing;
	}
	
	

	/**
	 * 
	 * @return shows if the path exists and is a readable directory.
	 */
	public boolean checkPath(){
		File f = new File(path);
		return f.exists() && f.isDirectory() && f.canRead();
	}


	/**
	 * Getter.
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @see fr.free.bworld.myapt.resources.Inspect#list(boolean)
	 */
	@Override
	public List<Resource> list(boolean recursive) {
		return list(new ArrayList<Resource>(), getPath(), recursive);
	}



	/**
	 * TODO refactor this method, change its signature to provide the list of resources as argument?
	 * 
	 * Generates a list of links in the apt format to the available resources files given by path.
	 * @see fr.free.bworld.myapt.resources.Inspect#toApt()
	 */
	@Override
	public String toApt() {
		List<Resource> resources = list(true);
		
		//sort the list on file path
		Collections.sort(resources, new Comparator<Resource>() {
			@Override
			public int compare(Resource r1, Resource r2) {
				return (r1.getPath() + r1.getFilename()).compareTo(r2.getPath() + r2.getFilename());
			}
		});
		
		StringBuffer buf = new StringBuffer();
		String newLine = System.getProperty("line.separator");
		
		String curFolderPath = "./";//
		
		for (Resource res : resources){
			
			String respath = res.getPath();
			//detects "resources/" to start the link at this path
			respath = extractRelativePathFromResourcesDirName(path);
			
			if (! curFolderPath.equals(respath)){
				curFolderPath = respath;
				buf.append(" Folder {{{");
				buf.append(respath);
				buf.append("}");
				buf.append("<<<");
				buf.append(respath);
				buf.append(">>>");
				buf.append("}}");
				buf.append(newLine);
				buf.append(newLine);
			}
			buf.append("   *");
			
			buf.append(res.getAptLink());
			
			
			buf.append(newLine);
			buf.append(newLine);
			
			// adds code to display the image, if the filename ends with one of the supported image file formats
			for (String extension : BrowseImageDir.SUPPORTED_FORMATS){
				if (res.getFilename().endsWith(extension)){
					buf.append(res.getAptImageDisplay());
					buf.append(newLine);
				}
			}
		}
		return buf.toString();
	}
	
   /**
    * If the browsed directory includes the "resources/" string, replaces its full content start until "resources" by "./".
    * 
    * @return a relative path from the src/site/resources/ directory.
    */
   protected String extractRelativePathFromResourcesDirName(String path) {
	  if (path.endsWith("resources")){
		  return "./";
	  }
      int resIndex = path.indexOf("resources/");
      if (resIndex == -1){//not found
         return path;
      }
      else{
         return "./" + path.substring(resIndex + "resources/".length());
      }
   }

}
