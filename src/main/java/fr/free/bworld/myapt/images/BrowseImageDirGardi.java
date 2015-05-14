package fr.free.bworld.myapt.images;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An implementation of the generator for the files in the "bworld gardi" module. 
 * @author blerubrus
 * @version $Date$
 */
public class BrowseImageDirGardi extends BrowseImageDir {


	
	public BrowseImageDirGardi() {
		super();
	}
	
	
	public BrowseImageDirGardi(String path) {
		super(path);
	}
	
	/**
	    * Generates an apt image file for each file in the directory, and an extract of the "site.xml" with links to these
	    * generated files.
	    * 
	    * @return shows if all generations of images files in the given directory could be successfully achieved.
	    */
	   @Override
	   public boolean generate(){
	      StringBuffer bufMenu = new StringBuffer();//stores a link for the "site.xml", one per created apt file

	      StringBuffer bufGal = new StringBuffer();//stores the html code for a gallery
	      
	      String relativePathToDir = extractRelativePath();//FIXME

	      boolean result = false;
	      for (String currImage : files){
	         String imageDate = extractDate(currImage);
	         String imageKeyword = extractKeyword(currImage);
	         MyAptImage mai = new MyAptImage(relativePathToDir, currImage, imageDate + " " + imageKeyword);
	         if (mai.generate()){
	            List<String> genList = mai.getGeneratedFilenames();
	            String aptGenFilename = genList.get(0);
	            generatedFilenames.add(aptGenFilename);

	            /*generates the "<item name='' href=''/> for the menu (site.xml)
	             * the label (name attribute value) is composed of the date in yyyy-MM-dd then the keyword then the "_DSCNxxx.jp" (sometimes). */
	            bufMenu.append("<item name=\"");
	            
	            bufMenu.append(imageDate);
	            bufMenu.append(" ");
	            bufMenu.append(imageKeyword);
	            bufMenu.append("\" href=\"");
	            bufMenu.append(aptGenFilename.substring(0, aptGenFilename.length() - 3));//remove the "apt" extension
	            bufMenu.append("html\"/>");//adds the html
	            bufMenu.append(System.getProperty("line.separator"));
	            
	            /*generates the "<div class=gal...><a href=><img ><desc>*/
	            bufGal.append(getGalleryDiv(currImage, "./"));
	            result = true;
	         }
	         else{
	            System.err.println("Could not create the apt file for image file '" + currImage + "'");
	            return false;
	         }
	      }

	      String filesTimestampPrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	      
	      /*generates the extract of the site.xml with an item link to the set of generated apt file*/
	      File menuFile = new File(filesTimestampPrefix + FILENAME_MENU_ITEMS_SITE_XML);
	      boolean menuResult = createFile(menuFile, bufMenu, "menu items xml file");
	      if (! menuResult){
	    	  return false;
	      }
	      
	      /*generates the gallery div*/
	      File overviewFile = new File(filesTimestampPrefix + FILENAME_OVERVIEW_XML);
	      boolean overviewResult = createFile(overviewFile, bufGal, "overview in xdoc format");
	      if (! overviewResult){
	    	  return false;
	      }

	      return result;
	   }
	   
	   /**
	    * For a given image, generates a link to the apt/html file with the link
	    * content made of the thumbnail and date.
	    *  
	    * @param imageBaseName is the name of the image without path.
	    * @param resRelPath is the relative path from the "resources" directory.
	    * @return a code based on the template: <div class="imgGal"><a href="./imageBaseName.html"><img src="./images/imageBaseName_thumb.png" alt="todo" width="100" height="100"/></a><div class="desc">2013-MM-dd</div></div>
	    */
	   protected String getGalleryDiv(String imageBaseName, String resRelPath){
		   String dateDescription = extractDate(imageBaseName);
		   String keywordForTooltip = extractKeyword(imageBaseName);
		   
		   StringBuffer buf = new StringBuffer();
		   buf.append("<div class=\"imgGal\"><a href=\"./");// link to apt transformed file to html
		   buf.append(imageBaseName);
		   buf.append(".html\" title=\"");
		   buf.append(keywordForTooltip);
		   buf.append("\"><img src=\"");//FIXME: there should be the path to the image, subfolder of the "resources" directory
		   buf.append(resRelPath);
		   buf.append(imageBaseName);
		   buf.append("_thumb.png\" alt=\"");
		   buf.append(imageBaseName);
		   buf.append("\" width=\"100\" height=\"100\"/></a><div class=\"desc\">");
		   buf.append(dateDescription);
		   buf.append("</div></div>");
		   buf.append(System.getProperty("line.separator"));
		   return buf.toString();
	   }


	


	/**
	 * Returns the filename of the generated menu extract.
	 * @see fr.free.bworld.myapt.GenerateApt#getTitles()
	 */
	@Override
	public String getMenuAdd() {
		return "Please consult *" + BrowseImageDir.FILENAME_MENU_ITEMS_SITE_XML;
	}
	   

}
