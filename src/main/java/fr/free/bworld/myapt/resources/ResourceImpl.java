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
	 * Sets path and filename attributes.
	 * @param path is the path to the parent folder of this resource.
	 * @param filename is the name of the file.
	 */
	public ResourceImpl(String path, String filename){
		this.path = path;
		this.filename = filename;
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
	

}
