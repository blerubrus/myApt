package fr.free.bworld.myapt.resources;

/**
 * Simple bean representing a Maven Web Site resource file.
 * 
 * @author blerubrus
 * @version $Date$
 */
public class ResourceImpl implements Resource {
	
	/**The path to the resource file.*/
	private String path;
	
	/**The leaf filename of the resource.*/
	private String filename;
	
	/**A custom name for this resource.*/
	private String label;
	
	/**
	 * Invokes the other constructor with a null label.
	 * @param path is the path to the parent folder of this resource.
	 * @param filename is the name of the file.
	 */
	public ResourceImpl(String path, String filename){
		this(path, filename, null);
	}
	
	/**
	 * Sets path filename and label attributes.
	 * @param linkTo
	 * @param name
	 * @param linkLabel
	 */
	public ResourceImpl(String linkTo, String name, String linkLabel) {
		this.path = extractRelativePathFromResourcesDirName(linkTo);
		this.filename = name;
		this.label = linkLabel;
	}

	/**
	 * 
	 * @return a custom message showing the values of the attributes.
	 */
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append("Resource Path: ");
		buf.append(getPath());
		buf.append(" ; Filename: ");
		buf.append(getFilename());
		buf.append(" ; Label: ");
		buf.append(getLabel());
		return buf.toString();
	}
	
	
	
	/**
	 * 
	 * @return the apt code to display an image.
	 */
	public String getAptImageDisplay(){
		StringBuffer buf = new StringBuffer();
		//String linkpath = extractRelativePathFromResourcesDirName(path);
		
		buf.append("[");
		buf.append(path);
		if(! path.endsWith("/")){
			buf.append("/");
		}
		buf.append(getFilename());
		buf.append("]");
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}

	/**
	 * @see fr.free.bworld.myapt.resources.Resource#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.label;
	}

	/**
	 * @see fr.free.bworld.myapt.resources.Resource#getFilename()
	 */
	@Override
	public String getFilename() {
		return this.filename;
	}

	/**
	 * @see fr.free.bworld.myapt.resources.Resource#getPath()
	 */
	@Override
	public String getPath() {
		return this.path;
	}
	
	/**
	    * If the browsed directory includes the "resources/" string, replaces its full content start until "resources" by "./".
	    * 
	    * @return a relative path from the src/site/resources/ directory.
	    */
	   protected String extractRelativePathFromResourcesDirName(String path) {
		  if (path.endsWith("resources")){
			  return "./";
		  }
	      int resIndex = path.indexOf("resources/");
	      if (resIndex == -1){//not found
	         return path;
	      }
	      else{
	         return "./" + path.substring(resIndex + "resources/".length());
	      }
	   }

	@Override
	public String getAptLink() {
		StringBuffer buf = new StringBuffer();
		String linkpath = extractRelativePathFromResourcesDirName(path);
		
		buf.append(" {{{");
		buf.append(linkpath);
		if(! linkpath.endsWith("/")){
			buf.append("/");
		}
		buf.append(getFilename());
		buf.append("}");
		if (getLabel() == null){//I do not know how to automatically set a label...
			buf.append(getFilename());
		}
		else{
			buf.append(getLabel());
		}
		buf.append("}}");
		
		return buf.toString();
	}
	

}
