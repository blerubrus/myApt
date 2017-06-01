/**
 * 
 */
package fr.free.bworld.myapt;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Note that many of the tests here are just for the display of the result...
 * 
 * @author blerubrus
 *
 */
public class MyAptLinkJunitTest {
	
	/**Computes the current date for the myAptLink tests.*/
	private static final String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	
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
	
	/**
	 * Example of a linkTo that does not start with http: null expected.
	 */
	@Test
	public void testGetAptHref_noHttp(){
		MyAptLink mal = new MyAptLink("src/site/apt/index.apt", "test");
		String actual = mal.getAptHref();
		Assert.assertNull("should be null for no start with http", actual);
	}
	
	/**
	 * Nominal case with a label and http link.
	 */
	@Test
	public void testGetAptHref_http(){
		MyAptLink mal = new MyAptLink("http://coucou", "test");
		String actual = mal.getAptHref();
		String expected = " {{{http://coucou}test}} (" + CURRENT_DATE + ")";
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Case for an http link with a null label .
	 */
	@Test
	public void testGetAptHref_http_nullLabel(){
		MyAptLink mal = new MyAptLink("http://coucou", null);
		String actual = mal.getAptHref();
		String expected = " {{http://coucou}} (" + CURRENT_DATE + ")";
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Case for an http link with an empty label .
	 */
	@Test
	public void testGetAptHref_http_emptyLabel(){
		MyAptLink mal = new MyAptLink("http://coucou", "  ");
		String actual = mal.getAptHref();
		String expected = " {{http://coucou}} (" + CURRENT_DATE + ")";
		Assert.assertEquals(expected, actual);		
	}
	
	/**
	 * Tests the case of an https link.
	 */
	@Test
	public void testGetAptHref_https(){
		MyAptLink mal = new MyAptLink("https://titi", "");
		String actual = mal.getAptHref();
		String expected = " {{https://titi}} (" + CURRENT_DATE + ")";
		Assert.assertEquals(expected, actual);
	}

}
