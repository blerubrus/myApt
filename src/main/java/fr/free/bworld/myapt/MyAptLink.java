/**
 * 
 */
package fr.free.bworld.myapt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.free.bworld.myapt.images.BrowseImageDir;
import fr.free.bworld.myapt.resources.Inspect;
import fr.free.bworld.myapt.resources.InspectImpl;
import fr.free.bworld.myapt.resources.Resource;
import fr.free.bworld.myapt.resources.ResourceImpl;

/**
 * Handles the creation of a string output representing the apt code to generate a link to a file (display image).
 * 
 * @author blerubrus
 *
 */
public class MyAptLink implements GenerateApt {
	
	/**The input  path to the resource file or directory. Since 20170601, can also be an http(s).*/
	private String linkTo;
	
	/**The label requested for the link.*/
	private String linkLabel;
	
	
	/**
	 * 
	 * @param linkTo is the path to the resource.
	 * @param linkLabel is the label for the link.
	 */
	public MyAptLink(String linkTo, String linkLabel) {
		if (linkTo == null){
			throw new IllegalArgumentException("myAptLink expects at least one argument: the path to a file or directory...");
		}
		else if (linkTo.startsWith("http")){
			this.linkTo = linkTo;
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

	/**
	 * Getter
	 * @return {@link #linkTo}
	 */
	public String getLinkTo() {
		return linkTo;
	}

	/**
	 * Setter.
	 * @param linkTo is the pointed file.
	 */
	public void setLinkTo(String linkTo) {
		this.linkTo = linkTo;
	}

	/**
	 * Getter.
	 * @return the link label.
	 */
	public String getLinkLabel() {
		return linkLabel;
	}

	/**
	 * Setter.
	 * @param linkLabel is a label for the link to create.
	 */
	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}

	/**
	 * Checks if the input is a file or directory then invokes inspector or resource impl to generate the link(s).
	 * @see fr.free.bworld.myapt.GenerateApt#generate()
	 */
	@Override
	public boolean generate() {
		if (linkTo.startsWith("http")){
			System.out.println(getAptHref());
		}
		else{
			File f = new File(linkTo);
			if (f.isDirectory()){
				Inspect inspector = new InspectImpl(linkTo);
				String listing = inspector.toApt();
				System.out.println(listing);
			}
			else{// file
				Resource res = new ResourceImpl(f.getParent(), f.getName(), linkLabel);
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

	/**
	 * Checks that the "linkTo" starts with "http", else returns null. Else, builds the apt code for this link,
	 * if the label is null or empty, only the link. Then adds the date in parenthesis.
	 * then adds the date between parenthesis. Example: 
	 * @return null if the linkTo does not start with http, or the APT code for a link to this hrefa 
	 */
	public String getAptHref(){
		String result = null;
		if (linkTo.startsWith("http")){
			StringBuffer buf = new StringBuffer();
			buf.append(" {{");
			if (linkLabel == null || linkLabel.trim().isEmpty()){
				buf.append(linkTo);
				buf.append("}}");
			}
			else{
				buf.append("{");
				buf.append(linkTo);
				buf.append("}");
				buf.append(linkLabel);
				buf.append("}}");
			}
			// displays the current date in yyyy-MM-dd between parenthesis
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			buf.append(" (");
			buf.append(sdf.format(date));
			buf.append(")");
			result = buf.toString();
		}
		return result;
	}

}
