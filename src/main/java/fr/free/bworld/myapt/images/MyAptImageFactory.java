package fr.free.bworld.myapt.images;

import java.io.File;

import fr.free.bworld.myapt.GenerateApt;

/**
 * 
 *
 * @author blerubrus
 * @version $Date$
 *
 */
public class MyAptImageFactory {

   /**
    * Handles the return of a dir listing or single image file.
    * @param argument is the path to an image filename or to a directory.
    * @param imageAptStyle is the style to use for the generation of the apt files associated to images.
    * @return the generator to use.
    */
   public static GenerateApt getGenerator(String argument, BrowseImageDirStyleEnum imageAptStyle){
      GenerateApt generator = null;      
      if (argument == null || argument.isEmpty()){
         throw new IllegalArgumentException("Can not generate with neither null nor empty input argument");
      }
      else{
         File f = new File(argument);
         if (f.isFile()){
            generator = new MyAptImage("./images", f.getName());
         }
         else if (f.isDirectory()){
        	 if (imageAptStyle == BrowseImageDirStyleEnum.NL){
        		 generator = new BrowseImageDirNL(argument);
        	 }
        	 else{//default
        		 generator = new BrowseImageDirGardi(argument);
        	 }
         }
         else{
            throw new IllegalArgumentException("The given argument '" + argument + "' is neither a file nor a directory...");
         }
         return generator;
      }
   }
}
