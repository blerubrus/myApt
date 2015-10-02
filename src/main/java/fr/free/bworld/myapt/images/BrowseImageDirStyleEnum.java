package fr.free.bworld.myapt.images;

/**
 * This enumeration provides the possible values for the styles
 * to be applied when generating apt files for images in a browsed directory.
 *
 * @author blerubrus
 * @version 2015
 *
 */
public enum BrowseImageDirStyleEnum {
	
	/**Default behavior style, no treatment of image filename for the title.*/
	LISTING,
	
	/**"bworld gardi" style, for images filenames including a date and keyword.*/
	GARDI,
	
	/**"bworld nl" style, for images filenames including a date and keyword (different from GARDI, the date and keyword are not used in the same places).*/
	NL;

}
