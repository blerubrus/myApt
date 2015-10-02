/**
 * 
 */
package fr.free.bworld.myapt.images;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Test;

/**
 * JUnit tests for the "my apt image dir listing" tool.
 * 
 * 
 * @author blerubrus
 *
 */
public class BrowseImageDirListingJunitTest {

   /**
    * tries to generate for the relative path to this project maven web site resources directory.
	*/
	@Test
	public void testMarsMyAptSrcSite(){
		testGenerate("src/site/resources/");
	}
	
	/**
	 * Tests that the file has been created, then removes it.
	 * @param path is the path to a directory.
	 */
	public void testGenerate(String path){
		File f = new File(path);
		if (! f.exists()){
			System.err.println("invalid path: " + path);
		}
		else if (! f.isDirectory()){
			System.err.println("not a directory: " + path);
		}
		Assume.assumeTrue(f.exists());
		Assume.assumeTrue(f.isDirectory());
		
		BrowseImageDir bid = new BrowseImageDirListing(path);
		Assert.assertTrue("could not generate the apt files for the directory: " + path, bid.generate());
		
		List<String> files = bid.getGeneratedFilenames();
		for (String genFileName : files){
			File genFile = new File(genFileName);
			Assert.assertTrue("could not remove the generated file " + genFileName, genFile.delete());
		}
	}
}
