package fr.free.bworld.myapt.resources;

import java.util.List;

/**
 * Lists the files of a given directory.
 * @author blerubrus
 * @version $Date$
 */
public interface Inspect {
	
	/**
	 * 
	 * @param recursive shows if the inspection must be recursive or not.
	 * @return 
	 */
	public List<Resource> list(boolean recursive);
	
	/**
	 * TODO: move this method in another interface...
	 * @return a representation in the APT Doxia format of the resources in APT Doxia Format.
	 */
	public String toApt();

	
}
