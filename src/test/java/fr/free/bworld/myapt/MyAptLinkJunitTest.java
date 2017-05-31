/**
 * 
 */
package fr.free.bworld.myapt;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: not real tests, just exe
 * 
 * @author blerubrus
 *
 */
public class MyAptLinkJunitTest {
	
	@Test
	public void testFile(){
		MyAptLink mal = new MyAptLink("src/site/resources/myApt_20150512.jar", "test");
		Assert.assertTrue("bad generate...", mal.generate());
	}
	
	@Test
	public void testDir(){
		MyAptLink mal = new MyAptLink("src/site/resources/myAptImageDirTest", "test");
		Assert.assertTrue("bad generate...", mal.generate());
	}
	
	@Test
	public void testAptIndex(){
		MyAptLink mal = new MyAptLink("src/site/apt/index.apt", null);
		Assert.assertTrue("bad generate...", mal.generate());
	}
	
	@Test
	public void testAptIndexLabel(){
		MyAptLink mal = new MyAptLink("src/site/apt/index.apt", "test");
		Assert.assertTrue("bad generate...", mal.generate());
	}

}
