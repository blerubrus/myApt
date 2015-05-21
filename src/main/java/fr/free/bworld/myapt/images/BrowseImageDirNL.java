package fr.free.bworld.myapt.images;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An implementation of the apt generator for images in a directory designed for "bworld NL" module.
 *
 * @author blerubrus
 * @version $Date$
 *
 */
public class BrowseImageDirNL extends BrowseImageDir {


	   
	public BrowseImageDirNL() {
		super();
	}
	
	/**
	 * @param path
	 */
	public BrowseImageDirNL(String path) {
		super(path);
	}

	/**
	 * @see fr.free.bworld.myapt.images.BrowseImageDirAbstract#generate()
	 */
	@Override
	public boolean generate() {
		StringBuffer bufMenu = new StringBuffer();//stores a link for the "site.xml", one per created apt file

	      StringBuffer bufGal = new StringBuffer();//stores the html code for a gallery
	      
	      String relativePathToDir = extractRelativePath();
	      if (! relativePathToDir.endsWith("/")){
	    	  relativePathToDir += "/";
	      }
	      
	      boolean result = false;
	      for (String currImage : files){
	         String imageDate = extractDate(currImage);
	         String imageKeyword = BrowseImageDirNL.extractKeyword(currImage);
	         
	         MyAptImage mai = new MyAptImage(relativePathToDir, currImage, imageDate + " " + imageKeyword);
	         if (mai.generate()){
	            List<String> genList = mai.getGeneratedFilenames();
	            String aptGenFilename = genList.get(0);
	            generatedFilenames.add(aptGenFilename);

	            /*generates the "<item name='' href=''/> for the menu (site.xml)
	             * the label (name attribute value) is composed of the date in yyyy-MM-dd then the keyword then the "_DSCNxxx.jp" (sometimes). */
	            bufMenu.append("<item name=\"");
	            bufMenu.append(imageKeyword);
	            bufMenu.append("\" href=\"");
	            bufMenu.append(aptGenFilename.substring(0, aptGenFilename.length() - 3));//remove the "apt" extension
	            bufMenu.append("html\"/>");//adds the html extension to reach the compiled apt
	            bufMenu.append(System.getProperty("line.separator"));
	            
	            /*generates the "<div class=gal...><a href=><img ><desc>*/
	            bufGal.append(getGalleryDiv(currImage, relativePathToDir));
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
	 * @see fr.free.bworld.myapt.images.BrowseImageDirAbstract#getGalleryDiv(java.lang.String, String)
	 */
	@Override
	protected String getGalleryDiv(String imageBaseName, String resRelPath) {
		//String dateDescription = extractDate(imageBaseName);
		   String keywordForTooltip = BrowseImageDirNL.extractKeyword(imageBaseName);
		   
		   StringBuffer buf = new StringBuffer();
		   buf.append("<div class=\"imgGal\"><a href=\"./");// link to apt transformed file to html
		   buf.append(imageBaseName);
		   buf.append(".html\" title=\"");
		   buf.append(keywordForTooltip);
		   buf.append("\"><img src=\"");
		   buf.append(resRelPath);
		   buf.append(imageBaseName);
		   buf.append("_thumb.png\" alt=\"");
		   buf.append(imageBaseName);
		   buf.append("\" width=\"100\" height=\"100\"/></a><div class=\"desc\">");
		   buf.append(keywordForTooltip);
		   buf.append("</div></div>");
		   buf.append(System.getProperty("line.separator"));
		   return buf.toString();
	}
	
	protected static String extractKeyword(String currImage){
		String imageKeyword = BrowseImageDir.extractKeyword(currImage);
        if (NO_DESC_AVAILABLE.equals(imageKeyword)){
       	   imageKeyword = currImage;
        }
        return imageKeyword;
	}

	
	/**
	 * Returns the filename of the generated menu extract.
	 * @see fr.free.bworld.myapt.GenerateApt#getTitles()
	 */
	@Override
	public String getMenuAdd() {
		return "Please consult *" + BrowseImageDir.FILENAME_MENU_ITEMS_SITE_XML;
	}
	
	/**
	 * Does nothing.
	 */
	@Override
	public void setToc(Object toc) {
		// TODO Auto-generated method stub
	}


}
