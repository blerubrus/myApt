package fr.free.bworld.myapt.resources;

import java.io.File;

import org.junit.Test;

import fr.free.bworld.myapt.resources.MyAptResources;
import junit.framework.Assert;

// $URL: svn+ssh://blerubrus@scm.forge.imag.fr/var/lib/gforge/chroot/scmrepos/svn/bworld/trunk/module/dev/module/myApt/src/test/java/fr/free/bworld/apt/resources/MyAptResourcesJunitTest.java $
/**
 * Tests the eponymic class.
 * @author blerubrus
 * @version $Id: MyAptResourcesJunitTest.java 149 2014-03-02 13:52:13Z blerubrus $
 */
public class MyAptResourcesJunitTest {
	
	/**
	 * Checks the constructor with null arg returns an exception.
	 */
	@Test
	public void testMyAptResourcesNullArg(){
		try{
			new MyAptResources(null);
			Assert.fail("expected exception for null arg");
		}
		catch(Exception e){
			Assert.assertTrue("expected exception message for null arg", e.getMessage() != null && e.getMessage().length() > 1);
		}
	}
	
	/**
	 * Executes the creation of an apt file for this directory given as parameter, checks the generation
	 * succeeded then deletes the file (does not check the content).
	 */
	@Test
	public void testThisProjectSite(){
		String userDir = System.getProperty("user.dir");
		MyAptResources mar = new MyAptResources(userDir);
		boolean generation = mar.generate();
		Assert.assertTrue("could not generate for userDir " + userDir, generation);
		
		String createdAptFilename = mar.getAptFilename();
		System.out.println("generated by test (will now delete it): " + createdAptFilename);
		File generatedFile = new File(createdAptFilename);
		Assert.assertTrue("the expected generated apt file '" + createdAptFilename + "' does not exist", generatedFile.exists());
		Assert.assertTrue("the expected generated apt file '" + createdAptFilename + "' is not a file", generatedFile.isFile());
		Assert.assertTrue("could not delete the generated apt file '" + createdAptFilename + "' does not exist", generatedFile.delete());
		
	}
	
	/**
	 * Simply tests the "replaceAptByHtml" for a test file named index.apt.
	 */
	@Test
	public void testReplaceAptByHtml_index() {
		ResourceImpl ri = new ResourceImpl("src/site/apt", "index.apt");
		String actual = ri.replaceAptByHtml();
		Assert.assertEquals("apt not transformed to html", "index.html", actual);
	}
	
	/**
	 * Simply tests the "replaceAptByHtml" for a test file named index.apt.
	 */
	@Test
	public void testReplaceAptByHtml_indexNotApt() {
		ResourceImpl ri = new ResourceImpl("src/site/apt", "index.ap");
		String actual = ri.replaceAptByHtml();
		Assert.assertNull("ap file name should be null", actual);
	}
	
	@Test
	public void testExtractRelativePath_aptIndex(){
		ResourceImpl ri = new ResourceImpl("src/site/apt", "toto");
		String actual = ri.extractRelativePathFromResourcesDirName("src/site/apt");
		Assert.assertEquals("no good extract", "./", actual);
	}
	
	@Test
	public void testExtractRelativePath_aptSlash(){
		ResourceImpl ri = new ResourceImpl("src/site/apt/", "toto");
		String actual = ri.extractRelativePathFromResourcesDirName("src/site/apt/");
		Assert.assertEquals("no good extract", "./", actual);
	}
	
	@Test
	public void testExtractRelativePath_aptSubfolder(){
		ResourceImpl ri = new ResourceImpl("src/site/apt/toto", "titi");
		String actual = ri.extractRelativePathFromResourcesDirName("src/site/apt/toto");
		Assert.assertEquals("no good extract", "./toto", actual);
	}
	

}
