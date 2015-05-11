package fr.free.bworld.myapt;

/**
 * Entry point of the myApt program.
 * 
 * @author blerubrus
 * @version $Date$
 */
public class Main {

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
   
   /**
    * Expected argument prefix for specifying if the apt will contain rcs keywords, depending on the value ({@link #ARGUMENT_SCM_GIT} or {@link #ARGUMENT_SCM_GIT}.*/
   public static final String ARGUMENT_SCM = "-Dscm=";
   
   /**Possible value for the argument {@link #ARGUMENT_SCM}. If set so, the apt files will contain rcs Id URL Author and Date keywords.*/
   public static final String ARGUMENT_SCM_SVN = "svn";
   
   /**Possible value for the argument {@link #ARGUMENT_SCM}. If set so, the apt files will contain rcs Date keyword in the footer.*/
   public static final String ARGUMENT_SCM_GIT = "git";

   /**
    * Invokes the factory to handle input parameters and generates the apt file, then displays the build result
    * to the console.
    * @param args is the list of input parameters on the command line.
    */
   public static void main(String[] args) {
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
         System.out.println("Build failed: could not generate apt file :(");
      }
   }

}
