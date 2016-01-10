/**
 * 
 */
package fr.free.bworld.myapt;

import java.io.File;
import java.util.List;

import fr.free.bworld.myapt.images.BrowseImageDir;
import fr.free.bworld.myapt.resources.Inspect;
import fr.free.bworld.myapt.resources.InspectImpl;
import fr.free.bworld.myapt.resources.ResourceImpl;

/**
 * Handles the creation of a string output representing the apt code to generate a link to a file (display image).
 * 
 * @author blerubrus
 *
 */
public class MyAptLink implements GenerateApt {
	
	private String linkTo;
	
	private String linkLabel;
	
	
	/**
	 * 
	 * @param linkTo
	 * @param linkLabel
	 */
	public MyAptLink(String linkTo, String linkLabel) {
		if (linkTo == null){
			throw new IllegalArgumentException("myAptLink expects at least one argument: the path to a file or directory...");
		}
		else{//checks the dir or file exists
			File f = new File(linkTo);
			if (! f.exists()){// || ! f.isDirectory()){
				throw new IllegalArgumentException("myAptLinks expects an existing file or directory as parameter. The given input could not be found...");
			}
			else{
				this.linkTo = linkTo;
			}
			
		}
		
		this.linkLabel = linkLabel;
	}

	public String getLinkTo() {
		return linkTo;
	}

	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}

	public String getLinkLabel() {
		return linkLabel;
	}

	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.GenerateApt#generate()
	 */
	@Override
	public boolean generate() {
		File f = new File(linkTo);
		if (f.isDirectory()){
			Inspect inspector = new InspectImpl(linkTo);
			String listing = inspector.toApt();
			System.out.println(listing);
		}
		else{// file
			ResourceImpl res = new ResourceImpl(f.getParent(), f.getName(), linkLabel);
			System.out.println(res.getAptLink());
			// if image file
			for (String extension : BrowseImageDir.SUPPORTED_FORMATS){
				if (f.getName().endsWith(extension)){
					System.out.println();
					System.out.println();
					System.out.println(res.getAptImageDisplay());
				}
			}
		}
		return true;
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
	 * @see fr.free.bworld.myapt.GenerateApt#getGeneratedFilenames()
	 */
	@Override
	public List<String> getGeneratedFilenames() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.GenerateApt#setScm(java.lang.String)
	 */
	@Override
	public void setScm(String scm) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see fr.free.bworld.myapt.GenerateApt#setToc(java.lang.Object)
	 */
	@Override
	public void setToc(Object toc) {
		// TODO Auto-generated method stub

	}

	

}
