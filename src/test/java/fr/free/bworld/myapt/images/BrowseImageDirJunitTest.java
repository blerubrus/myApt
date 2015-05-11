package fr.free.bworld.myapt.images;

import java.io.File;

import junit.framework.Assert;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import fr.free.bworld.myapt.images.BrowseImageDir;
import fr.free.bworld.myapt.images.BrowseImageDirGardi;

/* $URL: svn+ssh://blerubrus@scm.forge.imag.fr/var/lib/gforge/chroot/scmrepos/svn/bworld/trunk/module/dev/module/myApt/src/test/java/fr/free/bworld/apt/images/BrowseImageDirJunitTest.java $ */
/**
 * JUnit test for the eponymic class.
 *
 * @author blerubrus
 * @version $Id: BrowseImageDirJunitTest.java 161 2014-03-13 17:37:14Z blerubrus $
 *
 */
public class BrowseImageDirJunitTest {
	
	/**Shortcut to the home directory.*/
	private static final String USER_HOME = System.getProperty("user.home");
	
	/**
	 * Checks that the user home system property is a directory.
	 */
	@Test
	public void testUserHome(){
		Assert.assertNotNull("user home null", USER_HOME);
		File f = new File(USER_HOME);
		Assert.assertTrue("user home dir does not exist", f.exists());
		Assert.assertTrue("user home dir does not exist", f.isDirectory());
	}
	
	
	@Test
	@Ignore("test")
	public void testGenerateHomeSweetHomeDir(){
		String path = USER_HOME + "/workspace/bworld/module/arti/src/site/resources/images/20120911_homeSweetHome/";
		testGenerate(path);
	}
	
	/**
	 * Generates the apt files for images in the "gardi/.../images" directory.
	 */
	@Test
	@Ignore("des-ignore when changes happen in the gardi images directory")
	public void testGardi(){
		String path = USER_HOME + "/workspace/bworld/module/gardi/src/site/resources/images/";
		testGenerate(path);
	}
	
	/**
     * Generates the apt files for images in the "/home/blerubrus/Bureau/"20130429...gardi/images" directory.
     */
    @Test
    @Ignore("des-ignore when changes happen in the gardi images directory")
    public void testNikonGardi(){
        String path = USER_HOME + "/Bureau/20130429_nikonGardi/";
        testGenerate(path);
    }
    
    @Test
    @Ignore("just a test of the test, hardcoded path")
    public void testDublin(){
        String path = USER_HOME + "/Desktop/t/src/site/resources/20130613_dublin/photo/";
        testGenerate(path);
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
		
		BrowseImageDir bid = new BrowseImageDirGardi(path);
		Assert.assertTrue("could not generate the apt files for the directory: " + path, bid.generate());
	}
	
	
	@Test
	@Ignore("FIXME: find a valid path")
	public void testGetRelativePath(){
		BrowseImageDir bid = new BrowseImageDirGardi("/home/blerubrus/workspace/bworld/module/arti/src/site/resources/images/20120911_homeSweetHome/");
		
		Assert.assertEquals("./images/20120911_homeSweetHome/", bid.extractRelativePath());
		//System.out.println(bid.extractRelativePath());
	}
	
	/**
	 * Tests the div for a simple case (do not forget the end of line)
	 */
	@Test
	public void testGetDivBasic(){
		BrowseImageDir bid = new BrowseImageDirGardi();
		String actual = bid.getGalleryDiv("toto.jpg", "./");
		String expected = "<div class=\"imgGal\"><a href=\"./toto.jpg.html\" title=\"no desc\"><img src=\"./toto.jpg_thumb.png\" alt=\"toto.jpg\" width=\"100\" height=\"100\"/></a><div class=\"desc\"></div></div>" + System.getProperty("line.separator");
				
		//System.out.println(actual);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetDivMultiple(){
		String[] baseName = {
				"20120919_calamondin1.jpg",
				"20120919_calamondin2.jpg",
				"20120919_menthe1.jpg",
				"20120919_menthe2.jpg",
				"20120919_verveine.jpg",
				"20120919_verveineMenthe.jpg",
				"20121015_papyrus.jpg",
				"20121106_papyrus.jpg",
				"20121227_calamodain.jpg",
				"20121227_calamondain_menthe.jpg",
				"20121227_jardin1.jpg",
				"20121227_menthe.jpg",
				"20130324_oeillet.jpg",
				"20130324_oeillet2.jpg",
				"20130324_oeillet_pistou.jpg",
				"20130330_calamondain.jpg",
				"20130330_jardin.jpg",
				"20130330_jardin2.jpg",
				"20130330_menthe.jpg",
				"20130330_oeillet.jpg",
				"20130330_pistou.jpg",
				"20130330_verveine.jpg",
				"20130401_persil.jpg",
				"20130401_potager.jpg",
				"20130401_radis.jpg",
				"20130401_salade.jpg",
				"20130413_calamondain.jpg",
				"20130413_radis.jpg",
				"20130413_verveine.jpg",
				"20130421_cactus.jpg",
				"20130421_calamondain.jpg",
				"20130421_ciboulette.jpg",
				"20130421_ciboulette2.jpg",
				"20130421_oeillet.jpg",
				"20130421_persil.jpg",
				"20130421_potager.jpg",
				"20130421_potager2.jpg",
				"20130421_radis.jpg",
				"20130421_radis2.jpg",
				"20130421_radis3.jpg",
				"20130421_salade.jpg",
				"20130428_bidens_petunia_DSCN0291.jpg",
				"20130428_bidens_petunia_DSCN0300.jpg",
				"20130428_bidens_petunia_DSCN0304.jpg",
				"20130428_cactus_DSCN0301.jpg",
				"20130428_calamondin_DSCN0281.jpg",
				"20130428_calamondin_DSCN0282.jpg",
				"20130428_calamondin_DSCN0283.jpg",
				"20130428_calamondin_DSCN0285.jpg",
				"20130428_calamondin_DSCN0286.jpg",
				"20130428_calamondin_DSCN0287.jpg",
				"20130428_calamondin_DSCN0288.jpg",
				"20130428_calamondin_DSCN0289.jpg",
				"20130428_calamondin_DSCN0290.jpg",
				"20130428_figuierBarbarie_DSCN0302.jpg",
				"20130428_lavande_thym_DSCN0292.jpg",
				"20130428_lavande_thym_DSCN0303.jpg",
				"20130428_menthe_DSCN0296.jpg",
				"20130428_persil_DSCN0295.jpg",
				"20130428_potager_DSCN0297.jpg",
				"20130428_salade_DSCN0298.jpg",
				"20130428_tomate_DSCN0299.jpg",
				"20130428_verveine_DSCN0294.jpg",
				"20130429_papyrus_DSCN0309.jpg"
				
		};
		
		StringBuffer buf = new StringBuffer();
		BrowseImageDir bid = new BrowseImageDirGardi();
		for (String t : baseName){
			buf.append(bid.getGalleryDiv(t, "./"));
		}
		System.out.println(buf.toString());
	}
	
	@Test
	public void testExtractDate(){
		String input = "20130429_papyrus_DSCN0309.jpg";
		String actual = BrowseImageDir.extractDate(input);
		String expected = "2013-04-29";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testExtractDateNull(){
		String input = "papyrus_DSCN0309.jpg";
		String actual = BrowseImageDir.extractDate(input);
		Assert.assertEquals("", actual);
	}
	
	/**
	 * Checks that the extract of keyword works for a filename with "yyyyMMdd_keyword_DSCNsomething" input.
	 */
	@Test
	public void testExtractKeywordWithDscn(){
		String input = "20130428_potager_DSCN0297.jpg";
		String actual = BrowseImageDir.extractKeyword(input);
		Assert.assertEquals("potager", actual);
	}
	
	/**
	 * Checks that the extract of keyword works for a filename with "yyyyMMdd_keyword.jpg" input.
	 */
	@Test
	public void testExtractKeywordWithoutDscn(){
		String input = "20130428_potager.jpg";
		String actual = BrowseImageDir.extractKeyword(input);
		Assert.assertEquals("potager", actual);
	}
}
