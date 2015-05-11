package fr.free.bworld.myapt.images;

import java.io.File;
import java.security.InvalidParameterException;

import junit.framework.Assert;

import org.junit.Test;

import fr.free.bworld.myapt.images.MyAptImage;

/* $URL: svn+ssh://blerubrus@scm.forge.imag.fr/var/lib/gforge/chroot/scmrepos/svn/bworld/trunk/module/dev/module/myApt/src/test/java/fr/free/bworld/apt/images/MyAptImageJunitTest.java $ */
/**
 * JUnit test for the aponyme class.
 *
 * @author blerubrus
 * @version $Id: MyAptImageJunitTest.java 79 2013-04-29 16:41:48Z blerubrus $
 *
 */
public class MyAptImageJunitTest {
	
	/**
	 * Tests the behaviour for null params passed to constructor.
	 */
	@Test
	public void testConstructorNullParams() {
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage(null, "toto");
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage("toto", null);
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage(null, null);
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for null params in constructor", true);
		}
	}
	
	/**
	 * Tests the behaviour for empty params passed to constructor.
	 */
	@Test
	public void testConstructorEmptyParams() {
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage("", "toto");
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage("toto", "");
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
		try{
			//MyAptImage mai = new MyAptImage(null, null);
			new MyAptImage("", "");
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
		catch(InvalidParameterException e){
			Assert.assertTrue("an exception should be thrown for empty params in constructor", true);
		}
	}
	
	/**
	 * Generates a custom "my apt image" file and deletes it.
	 */
	@Test
	public void testCreate(){
		MyAptImage mai = new MyAptImage("path/", "myImage.png");
		Assert.assertTrue("could not create", mai.generate());
		
		//System.out.println(mai.getAptFilename());
		File f = new File(mai.getAptFilename());
		Assert.assertTrue("the file '" + mai.getAptFilename() + "' does not exist", f.exists());
		Assert.assertTrue("the file '" + mai.getAptFilename() + "' could not be deleted", f.delete());
	}

}
