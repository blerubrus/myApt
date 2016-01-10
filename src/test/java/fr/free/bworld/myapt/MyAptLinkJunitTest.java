/**
 * 
 */
package fr.free.bworld.myapt;

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
		mal.generate();
	}
	
	@Test
	public void testDir(){
		MyAptLink mal = new MyAptLink("src/site/resources/myAptImageDirTest", "test");
		mal.generate();
	}

}
