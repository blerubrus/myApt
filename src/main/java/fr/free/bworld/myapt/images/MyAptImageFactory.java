package fr.free.bworld.myapt.images;

import java.io.File;

import fr.free.bworld.myapt.GenerateApt;

/**
 * Handles the arguments given to the main method of MyAptImage to return an adhoc apt generator.
 *
 * @author blerubrus
 * @version 2015-10
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
            generator = new MyAptImage(f.getParent(), f.getName());
         }
         else if (f.isDirectory()){
        	 
        	 // handles the possible output styles of apt
        	 
        	 if (imageAptStyle == BrowseImageDirStyleEnum.NL){
        		 generator = new BrowseImageDirNL(argument);
        	 }
        	 else if(imageAptStyle == BrowseImageDirStyleEnum.GARDI){
        		 generator = new BrowseImageDirGardi(argument);
        	 }
        	 else{//default is LISTING
        		 generator = new BrowseImageDirListing(argument);
        	 }
         }
         else{
            throw new IllegalArgumentException("The given argument '" + argument + "' is neither a file nor a directory...");
         }
         return generator;
      }
   }
}
