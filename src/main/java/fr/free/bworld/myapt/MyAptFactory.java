package fr.free.bworld.myapt;

import java.util.Calendar;

import fr.free.bworld.myapt.images.BrowseImageDirStyleEnum;
import fr.free.bworld.myapt.images.MyAptImageFactory;
import fr.free.bworld.myapt.resources.MyAptResources;

/**
 * Based on the <i>Factory</i> design pattern, this class aims at parsing
 * the input arguments passed to the main method in order to generate the expected
 * APT document.
 * 
 * @author blerubrus
 * @version $Date$
 */
public class MyAptFactory {

   /**
    * Parses the arguments to instanciate the matching apt template.
    * 
    * @param args is the list of input parameters.
    * @return a type of apt template, depending on the given arguments.
    */
   public static GenerateApt getMyApt(String[] args) {
      GenerateApt result =  null;

      if (args == null || args.length == 0){//default behaviour
         result = new MyApt();
      }
      else{//there are input parameters
         // parses params
         String targetFile = null;
         String title = null;
         String year = null;
         String month = null;
         boolean aptForMonthDone = false;
         boolean aptForImage = false;//flag showing if the generation concerns an image
         String imageArg = null; // if the generation concerns an image, provides the path to the file or directory
         BrowseImageDirStyleEnum imageAptStyleEnum = BrowseImageDirStyleEnum.GARDI;//if the generation concerns apt for images in a directory, the main may provide an argument "style"
         String imageAptStyle = null;// eventual input value
         boolean aptForResources = false;//flag showing if the user wants to generate an apt file listing resources
         String resourcesArg = null;//if the generation concerns a "resources listing apt file", stores the expected argument value whose value is the directory to browse
         String scm = null;//eventual scm value
         
         for (String arg : args){//browses the input arguments and tries to match expected parameters
            if (arg.startsWith(Main.ARGUMENT_TARGET)){
               targetFile = arg.substring(Main.ARGUMENT_TARGET.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_TITLE)){
               title = arg.substring(Main.ARGUMENT_TITLE.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_TYPE + Main.ARGUMENT_TYPE_VALUE_DONE)){
               aptForMonthDone = true;
            }
            else if (arg.startsWith(Main.ARGUMENT_DONE_YEAR)){
               year = arg.substring(Main.ARGUMENT_DONE_YEAR.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_DONE_MONTH)){
               month = arg.substring(Main.ARGUMENT_DONE_MONTH.length());
            }            
            else if (arg.startsWith(Main.ARGUMENT_IMAGE_FILE)){
               aptForImage = true;
               imageArg = arg.substring(Main.ARGUMENT_IMAGE_FILE.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_IMAGE_DIR)){
               aptForImage = true;
               imageArg = arg.substring(Main.ARGUMENT_IMAGE_DIR.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_IMAGE_DIR_STYLE)){
            	imageAptStyle = arg.substring(Main.ARGUMENT_IMAGE_DIR_STYLE.length());
            	//checks the value is one of the enum
            	imageAptStyleEnum = BrowseImageDirStyleEnum.valueOf(imageAptStyle);
            	if (imageAptStyleEnum == null){
            		System.err.println("Invalid argument for the image apt style, using default");
            		imageAptStyleEnum = BrowseImageDirStyleEnum.GARDI;
            	}
            }
            else if (arg.startsWith(Main.ARGUMENT_TYPE + Main.ARGUMENT_TYPE_VALUE_RESOURCES)){
            	aptForResources = true;
            }
            else if (arg.startsWith(Main.ARGUMENT_RESOURCES_DIR)){
            	resourcesArg = arg.substring(Main.ARGUMENT_RESOURCES_DIR.length());
            }
            else if (arg.startsWith(Main.ARGUMENT_SCM)){
            	String givenScm = arg.substring(Main.ARGUMENT_SCM.length());
            	if (Main.ARGUMENT_SCM_SVN.equalsIgnoreCase(givenScm)){
            		scm = Main.ARGUMENT_SCM_SVN;
            	}
            	else{
            		System.out.println("The given scm parameter value is not handled: " + givenScm);
            		scm = null;
            	}
            }
         }//end browsing arg
         
         // now knows the type of apt to generate
         if (aptForMonthDone){
            try {
               int y = Calendar.getInstance().get(Calendar.YEAR);               
               if (year != null){
                  try{
                     y = Integer.parseInt(year);
                  }
                  catch(NumberFormatException e){
                     System.err.println("Could not convert the given year '" + year + "' to an integer, using current year");
                  }
               }
               
               int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
               if (month != null){
                  try {
                     m = Integer.parseInt(month);
                  }
                  catch(NumberFormatException e){
                     System.err.println("Could not convert the given month '" + month + "' to an integer, using current month");
                  }
               }
               result = new MonthDone(y, m);
            }
            catch (Exception e) {
               System.err.println("Error with the month done constructor...");
               e.printStackTrace();
            }
         }//end case "monthDone" type
         else if(aptForImage){
            result = MyAptImageFactory.getGenerator(imageArg, imageAptStyleEnum);
         }
         else if (aptForResources){
        	 result = new MyAptResources(resourcesArg);
         }
         else{
            result = new MyApt(targetFile, title);
         }
         // manages scm
         if (scm != null){
        	 result.setScm(scm);
         }
      }
      

      return result;
   }

}
