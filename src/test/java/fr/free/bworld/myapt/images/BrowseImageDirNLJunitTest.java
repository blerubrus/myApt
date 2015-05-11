package fr.free.bworld.myapt.images;

import java.io.File;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import fr.free.bworld.myapt.images.BrowseImageDir;
import fr.free.bworld.myapt.images.BrowseImageDirNL;

/* $URL: svn+ssh://blerubrus@scm.forge.imag.fr/var/lib/gforge/chroot/scmrepos/svn/bworld/trunk/module/dev/module/myApt/src/test/java/fr/free/bworld/apt/images/BrowseImageDirNLJunitTest.java $ */
/**
 *
 *
 * @author blerubrus
 * @version $Id: BrowseImageDirNLJunitTest.java 165 2014-04-01 08:51:56Z blerubrus $
 *
 */
public class BrowseImageDirNLJunitTest {

	
	 /**
     * Generates the apt files for images in the given directory.
     */
    @Test
    @Ignore("des-ignore when changes happen in the directory")
    public void testbNl(){
        testGenerate("/Users/blerubrus/workspace/bworld/module/20140310_nl/src/site/resources/images/nl");
    }
    
    /**
     * Generates the apt files for images in the given directory.
     */
    @Test
    @Ignore("des-ignore when changes happen in the directory")
    public void testIpics(){
        testGenerate("/Users/blerubrus/workspace/bworld/module/20140310_nl/src/site/resources/images/picTheNetherlands");
    }
    
    /**
     * Generates the apt files for images in the given directory.
     */
    @Test
    //@Ignore("des-ignore when changes happen in the gardi images directory")
    public void testallb(){
        testGenerate("/Users/blerubrus/workspace/bworld/module/20140310_nl/src/site/resources/images/raw");
    }
	
	/**
	 * 
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
		
		BrowseImageDir bid = new BrowseImageDirNL(path);
		Assert.assertTrue("could not generate the apt files for the directory: " + path, bid.generate());
	}
}
