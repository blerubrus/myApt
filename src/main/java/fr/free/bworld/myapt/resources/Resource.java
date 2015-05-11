package fr.free.bworld.myapt.resources;

/**
 * @author blerubrus
 * @version $Date$
 */
public interface Resource {
	
	/**
	 * 
	 * @return a pretty name for this resource.
	 */
	public String getLabel();
	
	/**
	 * 
	 * @return the leaf filename of the resource.
	 */
	public String getFilename();
	
	/**
	 * 
	 * @return the relative path starting from the Maven convention of "src/site/resources" directory.
	 */
	public String getPath();

}
