package fr.free.bworld.myapt.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.free.bworld.myapt.MyApt;

/**
 * Generates an APT file that displays a title and an image. The title is the image filename.
 *
 * TODO: add a boolean flag attribute to add "previous" and "next" links in generated apt files (for browsing all images)
 * 
 * TODO: add a boolean flag attribute to display or not the filename
 * 
 * @author blerubrus
 * @version $Date$
 *
 */
public class MyAptImage extends MyApt {//implements GenerateApt {
	
	/**The filename to generate, commposed of the date "yyyyMMddHHmm" then "_myAptImage.apt" suffix.*/
	protected static final String DEFAULT_IMAGE_APT_TARGET_FILENAME = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "_myAptImage.apt";
	
	
	/**The text before the link to the image.*/
	protected static final String LINK_TO_THIS_IMAGE_FILE = " Link to this image file: ";

	/**A shortcut to insert a new line in a string buffer.*/
	public static String NEW_LINE = System.getProperty("line.separator");
	
	/**The relative path from the Maven "resources/" directory to the location of the image. Exemple: "images/20120830_holidays/".*/
	private String resourcePath;
	
	/**The filename of the image to be displayed in the apt file. Example: IMG_666.jpg.*/
	private String imageFilename;
	
	/**The title of the generated apt file (keyword of the image for example, in the case of the gardi project).*/
	private String title;
	
	
	
	
	/**
	 * Invokes {@link #MyAptImage(String, String, String)} with the image filename as title.
	 * @param resourcePath expects a relative path from the Maven "src/resources/" directory to the image.
	 * @param imageFilename is the filename of the image that will be used for the filename of the generated apt.
	 */
	public MyAptImage(String resourcePath, String imageFilename){
		this(resourcePath, imageFilename, imageFilename);
	}
	
	
	
	/**
	 * 
	 * @param resourcePath expects a relative path from the Maven "src/site/resources/" directory to the image.
	 * @param imageFilename is the filename of the image that will be used for the filename of the generated apt.
	 * @param title is the title of the page.
	 */
	public MyAptImage(String resourcePath, String imageFilename, String title){
		
		if (resourcePath == null || resourcePath.isEmpty()){
			throw new InvalidParameterException("The resourcePath must neither be null nor empty!");
		}
		
		String relativePathToDir = extractRelativePath(resourcePath);//FIXME
		this.resourcePath = relativePathToDir;
		
		if (imageFilename == null || imageFilename.isEmpty()){
			throw new InvalidParameterException("The image filename must neither be null nor empty!");
		}
		this.imageFilename = imageFilename;
		
		if (title == null || title.isEmpty()){
			throw new InvalidParameterException("The title must neither be null nor empty!");
		}
		this.title = title;
		
		//this.setAptFilename(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + imageFilename + ".apt");
		this.setAptFilename(imageFilename + ".apt");
	}
	
	/**
	 * If the browsed directory includes the "resources/" string, replaces its
	 * full content start until "resources" by "./".
	 * @param path is the path to the file referenced by the user
	 * @return a relative path from the src/site/resources/ directory.
	 */
	protected String extractRelativePath(String path) {
		int resIndex = path.indexOf("resources");
		if (resIndex == -1) {// not found
			return path;
		}
		else {
			return "."	+ path.substring(resIndex + "resources".length());
		}
	}
	
	/**
	 * Generates the apt "image" tag and a text showing the filename of the image.
	 * @return the content of the body of the apt file to generate.
	 */
	public StringBuffer generateBody(){
		StringBuffer buf = new StringBuffer();
		buf.append(NEW_LINE);
		buf.append(NEW_LINE);
		//apt code for an image
		buf.append("[");
		buf.append(resourcePath);
		if (! resourcePath.endsWith("/")){
			buf.append("/");
		}
		buf.append(imageFilename);
		// apt code for end of image
		buf.append("]");
		buf.append(NEW_LINE);
		buf.append(NEW_LINE);
		
		/*link to to see the image only*/
		buf.append(LINK_TO_THIS_IMAGE_FILE);
		buf.append("{{{");
		buf.append(resourcePath);
		if (! resourcePath.endsWith("/")){
			buf.append("/");
		}
		buf.append(imageFilename);
		buf.append("}");
		buf.append(imageFilename);
		buf.append("}}");
		
		/*line*/
		buf.append(NEW_LINE);
		buf.append(NEW_LINE);
		buf.append("===");
		
		return buf;
	}
	
	
	
	/**
	 * Creates a buffer with the header (null title, null author), invokes the {@link #generateBody()} then adds the footer, and generates the file.
	 * @return shows if the generation succeeded.
	 */
	@Override
	public boolean generate(){
		StringBuffer buf = new StringBuffer();
		buf.append(generateHeader(title, null, null));
		
		StringBuffer body = generateBody();
		
		System.out.println("=== Preview ===");
		System.out.println(body);
		System.out.println("===============");
		
		
		buf.append(body);
		buf.append(generateFooter(null));
		
		File file = new File(getAptFilename());

		try{
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString());
			pw.flush();
			fos.close();
			pw.close();
			
			System.out.println("Successfully created the apt 'image' file: " + file.getAbsolutePath());
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			System.err.println("Failed at creating the apt 'image' file: " + file.getAbsolutePath());
			return false;
		}
	}   

}
