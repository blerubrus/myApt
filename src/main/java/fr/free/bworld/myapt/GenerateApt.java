package fr.free.bworld.myapt;

import java.util.List;

/**
 * Provides the methods that an apt generator must implement. 
 * 
 * @author blerubrus
 */
public interface GenerateApt {

	/**
	 * Creates the apt file.
	 * @return shows if the creation of the file has succeeded.
	 */
	public boolean generate();
	
	/**
	 * An xml instruction that can be added to the Maven site descriptor site.xml file.
	 * @return a line with the xml instruction that may be added to site.xml maven site descriptor file.
	 */
	public String getMenuAdd();
	
	/**
	 * Useful for an output message
	 * @return the list of generated filenames
	 */
	public List<String> getGeneratedFilenames();

	/**
	 * The apt file will contain svn or git rcs keyword.
	 * @param scm should be one of "git" or "svn"
	 */
	public void setScm(String scm);
	
	/**
	 * If set, the apt file will contain a table of contents.
	 * @param toc if not null, adds a toc.
	 */
	public void setToc(Object toc);
	
	
}
