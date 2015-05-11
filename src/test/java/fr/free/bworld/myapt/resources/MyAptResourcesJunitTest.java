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

}
