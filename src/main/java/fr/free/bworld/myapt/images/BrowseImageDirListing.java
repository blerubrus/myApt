/**
 * 
 */
package fr.free.bworld.myapt.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import fr.free.bworld.myapt.MyApt;

/**
 * @author blerubrus
 *
 */
public class BrowseImageDirListing extends BrowseImageDir {

	/**
	 * Invokes super constructor.
	 * @param path is the path to the image file.
	 */
	public BrowseImageDirListing(String path) {
		super(path);
	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.GenerateApt#getMenuAdd()
	 */
	@Override
	public String getMenuAdd() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.GenerateApt#setToc(java.lang.Object)
	 */
	@Override
	public void setToc(Object toc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.images.BrowseImageDir#generate()
	 */
	@Override
	public boolean generate() {		
		
		StringBuffer bufContent = new StringBuffer();//stores the content of the generated apt file
		
		String relativePathToDir = extractRelativePath();//FIXME
		StringBuffer bufContentIntro = new StringBuffer();
		bufContentIntro.append(" This page proposes a link and a display for images in the directory <<<");
		bufContentIntro.append(relativePathToDir);
		bufContentIntro.append(">>> (generated on the ");
		bufContentIntro.append(new Date());
		bufContentIntro.append(").");
		bufContentIntro.append(MyAptImage.NEW_LINE);
		
		for (String currImage : files){	       
	         
	       MyAptImage mai = new MyAptImage(relativePathToDir, currImage, currImage);
	       StringBuffer currImageFileBody = mai.generateBody();
	         
	       bufContent.append(currImageFileBody);
	       bufContent.append(MyAptImage.NEW_LINE);
	    }
		
		//displays to console the body core content of the file that is going to be generated
		System.out.println("=== Preview ===");
		System.out.println(bufContent);
		System.out.println("===============");
		
		
		StringBuffer buf = new StringBuffer();//the full content
		buf.append(MyApt.generateHeader("Directory Images Listing", null, null));
		buf.append(bufContentIntro);
		buf.append(bufContent);
		buf.append(MyApt.generateFooter(null));
		
		File file = new File(MyAptImage.DEFAULT_IMAGE_APT_TARGET_FILENAME);
		
		getGeneratedFilenames().add(MyAptImage.DEFAULT_IMAGE_APT_TARGET_FILENAME);
		
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

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.images.BrowseImageDir#getGalleryDiv(java.lang.String, java.lang.String)
	 */
	@Override
	protected String getGalleryDiv(String imageBaseName, String resRelPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
