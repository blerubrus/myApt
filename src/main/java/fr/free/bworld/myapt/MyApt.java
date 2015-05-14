package fr.free.bworld.myapt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Allows to generate a custom apt file.
 *
 * @author blerubrus
 * @version $Date$
 *
 */
public class MyApt implements GenerateApt {

	/**Expected "property key value" argument on the command line when the user wants to set the title of the apt file to generate.*/
	public static final String ARGUMENT_TITLE = "-Dtitle=";
	
	/**Meta info.*/
	protected static final String PROGRAM_MENTION = " (with bworld-myApt)";

	/**Uses the system user account.*/
	protected static final String DEFAULT_AUTHOR = System.getProperty("user.name");

	/**Useful for the new line.*/
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/**Default suffix for filenames when no argument is given.*/
	private static final String DEFAULT_FILENAME_SUFFIX = "_myApt.apt"; 

	/**When no argument is given on the command line, here is the default target filename, composed of the current date yyyyMMdd then a "_myApt.apt" suffix.*/
	public static final String DEFAULT_TARGET_FILE_PATH = new SimpleDateFormat("yyyyMMdd").format(new Date()) + DEFAULT_FILENAME_SUFFIX;
	
	/**Default title composed of the date in yyyy-MM-dd format*/
	public static final String DEFAULT_TITLE = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + DEFAULT_FILENAME_SUFFIX;

	/**Default path to the directory where the apt file is generated to.*/
	protected static final String DEFAULT_DIR_PATH = ".";
	
	/**The filename to the apt file that has to be generated.*/
	private String aptFilename;
	
	/**The title of the document.*/
	private String title;
	
	/**Depending on the value (git or svn), injects rcs keywords in the apt file to generate.*/
	private String scm;
	
	
	/**
	 * Invokes {@link #MyApt(String, String)} with default file path and default title.
	 */
	public MyApt(){
		this(DEFAULT_TARGET_FILE_PATH, DEFAULT_TITLE);
	}
	
	/**
	 * Invokes {@link #MyApt(String, String)} with given file path and default title.
	 * @param targetFile is the file (path and name) to generate.
	 */
	public MyApt(String targetFile){
		this(targetFile, DEFAULT_TITLE);
	}
	
	/**
	 * Getter.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets attributes.
	 * @param targetFile is the file (path and name) to generate.
	 * @param title is the title of the document.
	 */
	public MyApt(String targetFile, String title){
		this.aptFilename = targetFile;
		this.title = title;
	}
	
	
	

	
	/**
	 * Creates a buffer with the header (null title, null author) and footer.
	 * Generates the file.
	 * @return shows if the generation succeeded.
	 */
	@Override
	public boolean generate(){
		StringBuffer buf = new StringBuffer();
		buf.append(generateHeader(title, null, scm));
		buf.append(MyApt.generateFooter(scm));
		
		File file;
		if (getAptFilename() == null ){
			file = new File(DEFAULT_TARGET_FILE_PATH);
		}
		else{
			file = new File(getAptFilename());
		}
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
	
	
	/**
	 * Generates an xml instruction to be added to the site.xml maven site descriptor,
	 * with the title and "html" filename matching the name of the apt file.
	 * @return the {@link #title} and generated filename in a menu item xml instruction.
	 */
	@Override
	public String getMenuAdd() {
		StringBuffer buf = new StringBuffer("<item name=\"");
		buf.append(title);
		buf.append("\" href=\"");
		String targetFilename = aptFilename;
		if (aptFilename == null){
			targetFilename = DEFAULT_TARGET_FILE_PATH;
		}
		//get the file wihout path
		File f = new File(targetFilename);
		String filename = f.getName();
		// replace apt by html
		buf.append(filename.substring(0, filename.length() - 3) + "html");
		buf.append("\"/>");
		return buf.toString();
	}

	/**
	 * Builds the header of the apt file, with the title, author, date, and depending on the
	 * scm parameter, id and url ad comments, then the title as a main section.
	 * 
	 * @param title is the title of the document (the customized date if null)
	 * @param author is the author of the document + a mention
	 * @param scm indicates the rcs keywords to inject as comments (svn: id url, git: none)
	 * @return a custom header.
	 */
	protected static StringBuffer generateHeader(String title, String author, String scm){
		Calendar now = Calendar.getInstance();
		
		StringBuffer buf = new StringBuffer(" ---");
		buf.append(LINE_SEPARATOR);
		
		//title of the page
		buf.append(" ");
		if (title == null || title.trim().length() == 0){
			title = DEFAULT_TARGET_FILE_PATH;
		}
		buf.append(title);
		
		
		// author
		buf.append(LINE_SEPARATOR);
		buf.append(" ---");
		buf.append(LINE_SEPARATOR);
		
		buf.append(" ");
		if (author == null || author.trim().length() == 0){
			buf.append(DEFAULT_AUTHOR);
		}
		else{
			buf.append(author);
		}
		buf.append(PROGRAM_MENTION);
		
		
		buf.append(LINE_SEPARATOR);		
		buf.append(" ---");
		buf.append(LINE_SEPARATOR);
		
		// creation date of the file
		buf.append(" ");
		buf.append(now.get(Calendar.YEAR));
		buf.append("-");
		if (now.get(Calendar.MONTH) + 1 < 10){
			buf.append("0");
		}
		buf.append(now.get(Calendar.MONTH) + 1);
		buf.append("-");
		if (now.get(Calendar.DAY_OF_MONTH) < 10){
			buf.append("0");
		}
		buf.append(now.get(Calendar.DAY_OF_MONTH));
		buf.append(LINE_SEPARATOR);
		buf.append(LINE_SEPARATOR);
		
		// 2 comment lines with svn Id and URL rcs keywords
		if (scm != null && Main.ARGUMENT_SCM_SVN.equalsIgnoreCase(scm)){
			buf.append(getSvnIdUrlComments());
		}

		// title of the page
		buf.append(title);
		buf.append(LINE_SEPARATOR);
		buf.append(LINE_SEPARATOR);
		
		return buf;
	}
	
	/**
	 * Builds two lines of apt comments with the Id and URL svn rcs auto-completed keywords.
	 * @return a custom apt instruction with svn Id and URL keywords.
	 */
	public static StringBuffer getSvnIdUrlComments(){
		StringBuffer buf = new StringBuffer();
		buf.append("~~ $");
		buf.append("Id$");
		buf.append(LINE_SEPARATOR);
		buf.append("~~ $");
		buf.append("URL$");
		buf.append(LINE_SEPARATOR);
		buf.append(LINE_SEPARATOR);
		return buf;
	}
	
	/**
	 * Getter.
	 * @return the scm attribute value.
	 */
	public String getScm(){
		return this.scm;
	}

	
	/**
	 * If the given parameter is "svn", adds a line separator and rcs keywords in italics.
	 * @param scm if svn, includes revision date author.
	 * @return a custom footer including rcs keywords in italics that depend on the given parameter.
	 */
	protected static StringBuffer generateFooter(String scm) {
		StringBuffer buf = new StringBuffer("");
		if (scm != null && Main.ARGUMENT_SCM_SVN.equalsIgnoreCase(scm)){
			buf.append(LINE_SEPARATOR);
			buf.append(LINE_SEPARATOR);
			buf.append("===");// apt line
			buf.append(LINE_SEPARATOR);
			buf.append(LINE_SEPARATOR);
			buf.append(" <$Revision");
			buf.append("$ on $");
			buf.append("Date$ by $");
			buf.append("Author$>");
			buf.append(LINE_SEPARATOR);
		}
		return buf;
	}
	

	
	/**
	 * Getter.
	 * @return the aptFilename
	 */
	public String getAptFilename() {
		return aptFilename;
	}

	/**
	 * Setter.
	 * @param aptFilename the aptFilename to set
	 */
	public void setAptFilename(String aptFilename) {
		this.aptFilename = aptFilename;
	}

   /**
    * Creates a list and adds one item: {@link #aptFilename}
    * @see fr.free.bworld.myapt.GenerateApt#getGeneratedFilenames()
    */
   @Override
   public List<String> getGeneratedFilenames() {
      List<String> result = new ArrayList<String>();
      result.add(aptFilename);
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
