package fr.free.bworld.myapt.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.free.bworld.myapt.GenerateApt;

/**
 * Abstract class that aims at browsing a directory to generate a file with the 
 * apt code to display links and images in the given directory.
 * 
 * @author blerubrus
 * @version 2015-10
 *
 */
public abstract class BrowseImageDir implements GenerateApt {

   /**In the image filename, eventual suffix of the original photo (nikon) that shwos the end of the keyword.*/
   protected static final String KEYWORD_DELIMITER_DSCN = "_DSCN";

   /**Text when no description is available.*/
   protected static final String NO_DESC_AVAILABLE = "no desc";

   /**
	 * Suffix of the generated file (xdoc format) for the overview with thumbnails. The final
	 * generated filename will be composed of a date timestamp as prefix then this suffix.
	 */
	protected static final String FILENAME_OVERVIEW_XML = "_overview.xml";
	
	/**
	 * Suffix of the generated file (xml format) with the items to be added to the menu site.xml. The final
	 * generated filename will be composed of a date timestamp as prefix then this suffix.
	 */
	protected static final String FILENAME_MENU_ITEMS_SITE_XML = "_menu_items_site.xml";

	
   /**The directory containing images to browse.*/
   private String browsedDirectory;

   /**The list of files in the directory.*/
   protected final List<String> files;

   /**The list of generated apt filenames.*/
   protected List<String> generatedFilenames;
   
	/**Depending on the value (git or svn), injects rcs keywords in the apt file to generate.*/
	private String scm;

    /**
	 * Getter.
	 * @return the scm
	 */
	public String getScm() {
		return scm;
	}


   /**List of image file supported extensions.*/
   public static String[] SUPPORTED_FORMATS = {".JPG", ".jpg", ".JPEG", ".jpeg", ".png", ".PNG", ".SVG", ".svg", ".GIF", ".gif"};

   /**
    * The constructor with no arguments just sets an empty list of files.
    */
   public BrowseImageDir(){
	   files = new ArrayList<String>();
   }
   
   /**
    * Invokes {@link #listFiles()} for the directory whose path is given as parameter
    * @param path is the path to a directory
    */
   public BrowseImageDir(String path){
      if (path == null || path.isEmpty()){
         throw new InvalidParameterException("the path can neither be null nor empty");
      }
      this.browsedDirectory = path;
      files = new ArrayList<String>();
      generatedFilenames = new ArrayList<String>();
      listFiles();
   }

   /**
    * 
    * @return the number of files in the directory to browse.
    */
   private int listFiles() {
      int result = -1;

      File dir = new File(browsedDirectory);
      if (! dir.exists()){
         throw new InvalidParameterException("the directory '" + browsedDirectory + "' does not exist...");
      }
      else if (! dir.isDirectory()){
         throw new InvalidParameterException("the given parameter '" + browsedDirectory + "' is not a directory...");
      }
      else if (! dir.canRead()){
         throw new InvalidParameterException("the directory '" + browsedDirectory + "' can not be read...");
      }
      else{
         // filters supported images files but not "_thumb.png" files
         String[] myImageFiles = dir.list(new FilenameFilter() {
            public boolean accept(File directory, String filename) {
               /*remove filenames that end with "_thumb.png"*/
               if (filename.endsWith("_thumb.png")){
            	   return false;
               }
               else{
            	  for (String extension : SUPPORTED_FORMATS){
                       if (filename.endsWith(extension)){
                          return true;
                       }
                    }
                    return false;
               }
            }
         });

         for (String currFile : myImageFiles){
            files.add(currFile);
         }
      }
      // sorts!
      Collections.sort(files);
      result = files.size();
      return result;
   }

   /**
    * Generates an apt image file for each file in the directory, and an extract of the "site.xml" with links to these
    * generated files.
    * 
    * @return shows if all generations of images files in the given directory could be successfully achieved.
    */
   @Override
   public abstract boolean generate();


   /**
    * If the browsed directory includes the "resources/" string, replaces its full content start until "resources" by "./".
    * 
    * @return a relative path from the src/site/resources/ directory.
    */
   protected String extractRelativePath() {
      int resIndex = browsedDirectory.indexOf("resources/");
      if (resIndex == -1){//not found
         return browsedDirectory;
      }
      else{
         return "./" + browsedDirectory.substring(resIndex + "resources/".length());
      }
   }



   /**
    * @see fr.free.bworld.myapt.GenerateApt#getGeneratedFilenames()
    */
   @Override
   public List<String> getGeneratedFilenames() {
      return generatedFilenames;
   }

   /**
    * For a given image, generates a link to the apt/html file with the link
    * content made of the thumbnail and date.
    *  
    * @param imageBaseName is the basename of the image (without extension?)
    * @param resRelPath is the relative path from the "resources" directory.
    * @return a code based on the template: &lt;div class="imgGal"&gt;&lt;a href="./imageBaseName.html"&gt;&lt;img src="./images/imageBaseName_thumb.png" alt="todo" width="100" height="100"&gt;&lt;/img&gt;&lt;/a&gt;&lt;div class="desc"&gt;2013-MM-dd&lt;/div&gt;&lt;/div&gt;&lt;
    */
   protected abstract String getGalleryDiv(String imageBaseName, String resRelPath);


	/**
	 * @param imageBaseName may include a date, example: yyyyMMdd_myFilename.png
	 * @return a readable human date yyyy-MM-dd
	 */
	protected static String extractDate(String imageBaseName) {
		if (imageBaseName.length() > 8){
			String date = imageBaseName.substring(0, 8);
			try{
				Integer.parseInt(date);
				StringBuffer dateDesc = new StringBuffer();
				dateDesc.append(imageBaseName.substring(0, 4));
				
				dateDesc.append("-");
				
				imageBaseName = imageBaseName.substring(4);
				dateDesc.append(imageBaseName.substring(0, 2));
				
				dateDesc.append("-");
				
				imageBaseName = imageBaseName.substring(2);
				dateDesc.append(imageBaseName.substring(0, 2));
				
				return dateDesc.toString();
				
			}
			catch(NumberFormatException e){
				System.err.println("WARNING: the given filename " + imageBaseName + " does not include a date prefix in yyyyMMdd format, using default empty string.");
			}	
		}
		return "";
	}
	/**
	 * Tries to extract the keyword in the given filename that respects the following "gardi" pattern:
	 * <ol>
	 * 	<li>date in "yyyyMMdd" format</li>
	 *  <li>underscore</li>
	 *  <li>keyword</li>
	 *  <li>underscore</li>
	 *  <li>eventually, "DSCN0xyz" (original filename when extracted from camera"</li>
	 *  <li>.jpg</li>
	 * </ol>
	 * Example: "20130705_Tomate_DSCN0323.jpg" as input returns "Tomate".
	 * @param imageBaseName is the filename of the image.
	 * @return the content of the filename that is expected to be respect the "gardi" pattern. 
	 */
	protected static String extractKeyword(String imageBaseName){
		if (imageBaseName.length() > 8){
			String noDate = imageBaseName.substring(9);//removes "yyyyMMdd_"
			int dscnIndex = noDate.indexOf(KEYWORD_DELIMITER_DSCN);
			if (dscnIndex == -1){
				int lastDotIndex = noDate.lastIndexOf('.');
				if (lastDotIndex == -1){
					return imageBaseName;
				}
				else{
					return noDate.substring(0, lastDotIndex);
				}
			}
			else{
				return noDate.substring(0, dscnIndex);
			}
		}
		return NO_DESC_AVAILABLE;
	}
	
	
	protected boolean createFile(File file, StringBuffer content, String description){
		boolean result = false;
		try{
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(content.toString());
			pw.flush();
			fos.close();
			pw.close();
			System.out.println("Successfully created the " + description + " file '" + file.getAbsolutePath() + "'.");
			result = true;
		}
		catch(Exception e){
			System.err.println("Failed while trying to create the " + description + " file '" + file.getAbsolutePath() + "'.");
			e.printStackTrace();
			return false;
		}
		return result;
   }


	/**
	 * @see fr.free.bworld.myapt.GenerateApt#setScm(java.lang.String)
	 */
	@Override
	public void setScm(String scm) {
		this.scm = scm;
	}

}
