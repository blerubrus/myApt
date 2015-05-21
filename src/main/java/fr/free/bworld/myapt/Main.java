package fr.free.bworld.myapt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Entry point of the myApt program.
 * 
 * @author blerubrus
 * @version since 2015-05-21
 */
public class Main {
	
   /**Possible argument to include a toc in the generated simple apt file.*/	
   protected static final String ARGUMENT_TOC = "-Dtoc";

   /**Possible argument to display the version of the software.*/
   private static final String ARGUMENT_VERSION_3 = "-v";

   /**Possible argument to display the version of the software.*/
   private static final String ARGUMENT_VERSION_2 = "-version";
   
   /**Possible argument to display the version of the software.*/
   private static final String ARGUMENT_VERSION_1 = "--version";

   /** The key in the resource properties file to get the build date. */
   private static final String VERSION_KEY_BUILD_DATE = "build.date";

   /** The key in the resource properties file to get the version of the project. */
   private static final String VERSION_KEY_PROJECT_VERSION = "project.version";

	
   /**Classpath to the properties file that is updated by maven build process and that contains the build date and project version.*/
   private static final String CLASSPATH_TO_VERSION_PROPERTIES_FILE = "/bworld-myApt_version.properties";

	/**Expected "property key value" argument on the command line when the user wants to generate an apt file for an image file.*/
   protected static final String ARGUMENT_IMAGE_FILE = "-DimageFile=";

   /**Expected "property key value" argument on the command line when the user wants to generate an apt file for each image in a directory.*/
   protected static final String ARGUMENT_IMAGE_DIR = "-DimageDir=";

   /**Expected "property key value" optional argument on the command line for the user to choose the style of the generated apt files for images in a directory. BrowseImageDirStyleEnum expected.*/
   protected static final String ARGUMENT_IMAGE_DIR_STYLE = "-Dstyle=";
   
   /**Expected "key value property" argument prefix for specifying the path and name of the apt file to be generated.*/
   protected static final String ARGUMENT_TARGET = "-Dtarget=";

   /**Expected "key value property" argument prefix for specifying the title of the document.*/
   protected static final String ARGUMENT_TITLE = "-Dtitle=";

   /**Expected "key value property" argument prefix for specifying the type of the apt document.*/
   protected static final String ARGUMENT_TYPE = "-Dtype=";

   /**Expected value for the argument prefix {@link #ARGUMENT_TYPE}, in order to generate a "month done" apt document.*/
   protected static final String ARGUMENT_TYPE_VALUE_DONE = "done";
   
   /**Expected value for the argument prefix {@link #ARGUMENT_TYPE}, in order to generate a listing or resources apt document.*/
   protected static final String ARGUMENT_TYPE_VALUE_RESOURCES = "resources";

   /**Expected argument prefix for specifying the year of the "month done" apt file.*/
   protected static final String ARGUMENT_DONE_YEAR = "-Dyear=";

   /**Expected argument prefix for specifying the month of the "month done" apt file.*/
   public static final String ARGUMENT_DONE_MONTH = "-Dmonth=";

   /**Expected argument prefix for specifying the locale to use for the content of the apt file (in the case of a "done" type, for the calendar).*/
   public static final String ARGUMENT_LOCALE = "-Dlocale=";
   
   /**Expected argument prefix for specifying the directory to browse when the type of apt file to generate is "resources". Expects the absolute path.*/
   public static final String ARGUMENT_RESOURCES_DIR = "-DresourcesDir=";
   
   /**Expected argument prefix for specifying if the apt will contain rcs keywords, depending on the value ({@link #ARGUMENT_SCM_GIT} or {@link #ARGUMENT_SCM_GIT}.*/
   public static final String ARGUMENT_SCM = "-Dscm=";
   
   /**Possible value for the argument {@link #ARGUMENT_SCM}. If set so, the apt files will contain rcs Id URL Author and Date keywords.*/
   public static final String ARGUMENT_SCM_SVN = "svn";

   /**
    * Invokes the factory to handle input parameters and generates the apt file, then displays the build result
    * to the console.
    * @param args is the list of input parameters on the command line.
    */
   public static void main(String[] args) {
      // checks if the user wants to display the version. 3 possible arguments
	  if (args != null){
		  for (String arg : args){
			  if (ARGUMENT_VERSION_1.equals(arg)
		    			||
		          ARGUMENT_VERSION_2.equals(arg)
		     		   	||
		   		  ARGUMENT_VERSION_3.equals(arg)){
		     	  System.out.println(new Main().getVersion());
		     	  return;
		      }
		  }
	  }
    		  
      // not "-v" as argument: let's create a file
	  GenerateApt ma = MyAptFactory.getMyApt(args);
      if (ma.generate()){
         System.out.println("Build succeeded!");         
         for (String currGeneratedFilename : ma.getGeneratedFilenames()){
            System.out.println("   Generated apt file " + currGeneratedFilename);
            System.out.println("You may wish to add the following line in the site.xml file:");
            System.out.println("   " + ma.getMenuAdd());
         }
      }
      else{
         System.out.println("Build failed: could not generate an apt file :(");
      }
      
      
   }
   
   /**
	 * Loads the properties resource file updated by maven build and reads keys.
	 * 
	 * @return a custom message providing the version and build date.
	 */
	private String getVersion() {
		String result = "Unknown version...";
		try {
			Properties props = new Properties();
			InputStream is = this.getClass().getResourceAsStream(CLASSPATH_TO_VERSION_PROPERTIES_FILE);
			if (is == null) {
				System.out.println("Could not find the version properties resource...");
			}
			else {
				props.load(is);

				StringBuffer buf = new StringBuffer("bworld-myApt");
				buf.append(" Version ");
				buf.append(props.get(VERSION_KEY_PROJECT_VERSION));
				buf.append(" Build ");
				buf.append(props.get(VERSION_KEY_BUILD_DATE));
				
				result = buf.toString();
			}
		}
		catch (IOException e) {
			System.out.println("Could not find the version properties resource... Exception: " + e.getMessage());
		}
		return result;

	}
   
   

}
