package fr.free.bworld.myapt.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.free.bworld.myapt.GenerateApt;
import fr.free.bworld.myapt.MyApt;

/**
 * Aims at generating an apt file containing a list of links to the files that are contained in a directory whose
 * path is given as argument.
 * 
 * @author blerubrus
 * @version $Date$
 */
public class MyAptResources extends MyApt implements GenerateApt {
	
	/**The filename to generate, commposed of the date yyyyMMdd then "_myAptResources.apt" suffix.*/
	protected static final String DEFAULT_RESOURCES_APT_TARGET_FILENAME = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_myAptResources.apt";
	
	/**The title of the apt page to generate.*/
	private static final String DEFAULT_RESOURCES_APT_PAGE_TITLE = "Resources Listing";
	
	/**The path to the directory we want to browse to generate the listing.*/
	private String dir;

	/**
	 * Sets the directory to browse and a custom target apt filename.
	 * @param dir is the directory to browse.
	 */
	public MyAptResources(String dir) {
		//check the dir exists
		if (dir == null){
			throw new IllegalArgumentException("The path to browsed directory must not been null...");
		}
		else{
			File f = new File(dir);
			if (! f.exists() || ! f.isDirectory()){
				throw new IllegalArgumentException("The given path argument '" + dir + "' must be an existing directory...");
			}
			else{
				this.dir = dir;
				this.setAptFilename(DEFAULT_RESOURCES_APT_TARGET_FILENAME);
			}
		}
	}

	/**
	 * Getter.
	 * @return {@link #dir}
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * Invokes Inspect to generate the body of the apt page (listing of links to resources), inserts
	 * the result in the standard apt page then writes the file.
	 */
	@Override
	public boolean generate() {
		Inspect inspector = new InspectImpl(dir);
		String listing = inspector.toApt();

		StringBuffer buf = new StringBuffer();
		buf.append(generateHeader(DEFAULT_RESOURCES_APT_PAGE_TITLE, null, null));
		buf.append(LINE_SEPARATOR);
		buf.append(LINE_SEPARATOR);
		buf.append(listing);
		buf.append(LINE_SEPARATOR);
		buf.append(LINE_SEPARATOR);
		buf.append(generateFooter(null));

		File file = new File(getAptFilename());
		try{
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString());
			pw.flush();
			fos.close();
			pw.close();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
