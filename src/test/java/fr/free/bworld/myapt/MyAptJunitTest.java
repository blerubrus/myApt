package fr.free.bworld.myapt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import fr.free.bworld.myapt.Main;
import fr.free.bworld.myapt.MyApt;

/**
 * Tests the <code>MyApt</code> class.
 *
 * @author blerubrus
 * @version $Id: MyAptJunitTest.java 95 2013-06-27 13:35:51Z blerubrus $
 *
 */
public class MyAptJunitTest {

	/**
	 * 
	 */
	private static final String JUNIT_APT_FILE = "junit.apt";
	/**
	 * 
	 */
	private static final String JUNIT_AUTHOR = "junit author";
	/**
	 * 
	 */
	private static final String JUNIT_TITLE = "junit title";

	/**
	 * Tests the generation of the header with null parameters.
	 */
	@Test
	public void testGenerateHeaderNullParams(){
		StringBuffer actual = MyApt.generateHeader(null, null, null);
		
		
		//System.out.println(actual.toString());
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String creationDate = sdf.format(cal.getTime());
		
		SimpleDateFormat sdfTitle = new SimpleDateFormat("yyyyMMdd");
		String title = sdfTitle.format(cal.getTime()) + "_myApt.apt";
		
		StringBuffer expected = new StringBuffer(" ---");
		expected.append(System.getProperty("line.separator"));
		expected.append(" ");
		expected.append(title);
		expected.append(System.getProperty("line.separator"));
		expected.append(" ---");
		expected.append(System.getProperty("line.separator"));
		expected.append(" ");
		expected.append(System.getProperty("user.name"));
		expected.append(MyApt.PROGRAM_MENTION);
		expected.append(System.getProperty("line.separator"));
		expected.append(" ---");
		expected.append(System.getProperty("line.separator"));
		expected.append(" ");
		
		expected.append(creationDate);		
		expected.append(System.getProperty("line.separator"));
		expected.append(System.getProperty("line.separator"));
		expected.append(title);
		expected.append(System.getProperty("line.separator"));
		expected.append(System.getProperty("line.separator"));
		
		
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	
	
	/**
	 * Tests the generation of the footer for null param.
	 */
	@Test
	public void testGenerateFooterNullParam(){
		StringBuffer actual = MyApt.generateFooter(null);
		StringBuffer expected = new StringBuffer("");
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	/**
	 * Tests the generation of the footer for svn param.
	 */
	@Test
	public void testGenerateFooterSvn(){
		StringBuffer actual = MyApt.generateFooter(Main.ARGUMENT_SCM_SVN);
		
		//System.out.println(actual.toString());
		
		StringBuffer expected = new StringBuffer(System.getProperty("line.separator"));
		expected.append(System.getProperty("line.separator"));
		expected.append("===");
		expected.append(System.getProperty("line.separator"));
		expected.append(System.getProperty("line.separator"));
		expected.append(" <$Revision");
		expected.append("$ on $Date");
		expected.append("$ by $Author");
		expected.append("$>");
		expected.append(System.getProperty("line.separator"));
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	
	
	/**
	 * Tests the generation of the footer for empty param.
	 */
	@Test
	public void testGenerateFooterToto(){
		StringBuffer actual = MyApt.generateFooter("toto");
		StringBuffer expected = new StringBuffer("");
		Assert.assertEquals(expected.toString(), actual.toString());
	}
	
	/**
	 * Tests the generation of the header when a title and an author are given. 
	 * Checks the indexes of the two occurrences of this title, and the index of the occurrence of the author. 
	 */
	@Test
	public void testGenerateHeaderNullScm(){
		StringBuffer actual = MyApt.generateHeader(JUNIT_TITLE, JUNIT_AUTHOR, null);
		// first occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE));
		Assert.assertEquals(6, actual.indexOf(JUNIT_TITLE));
		// occurrence of the given author
		//System.out.println(actual.indexOf(JUNIT_AUTHOR));
		Assert.assertEquals(24, actual.indexOf(JUNIT_AUTHOR));
		// second occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE, 30));
		Assert.assertEquals(75, actual.indexOf(JUNIT_TITLE, 30));
	}
	
	/**
	 * Tests the generation of the header when a title and an author are given. 
	 * Checks the indexes of the two occurrences of this title, and the index of the occurrence of the author. 
	 */
	@Test
	public void testGenerateHeaderToto(){
		StringBuffer actual = MyApt.generateHeader(JUNIT_TITLE, JUNIT_AUTHOR, "toto");
		// first occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE));
		Assert.assertEquals(6, actual.indexOf(JUNIT_TITLE));
		// occurrence of the given author
		//System.out.println(actual.indexOf(JUNIT_AUTHOR));
		Assert.assertEquals(24, actual.indexOf(JUNIT_AUTHOR));
		// second occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE, 30));
		Assert.assertEquals(75, actual.indexOf(JUNIT_TITLE, 30));
	}
	
	/**
	 * Tests the generation of the header when a title and an author are given. 
	 * Checks the indexes of the two occurrences of this title, and the index of the occurrence of the author. 
	 */
	@Test
	public void testGenerateHeaderSvn(){
		StringBuffer actual = MyApt.generateHeader(JUNIT_TITLE, JUNIT_AUTHOR, Main.ARGUMENT_SCM_SVN);
		// first occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE));
		Assert.assertEquals(6, actual.indexOf(JUNIT_TITLE));
		// occurrence of the given author
		//System.out.println(actual.indexOf(JUNIT_AUTHOR));
		Assert.assertEquals(24, actual.indexOf(JUNIT_AUTHOR));
		// second occurrence of the given title
		//System.out.println(actual.indexOf(JUNIT_TITLE, 30));
		Assert.assertEquals(93, actual.indexOf(JUNIT_TITLE, 30));
	}
	
	/**
	 * Creates a file with a target file and a title then deletes it.
	 */
	@Test
	public void testCreate(){
		MyApt actual = new MyApt(JUNIT_APT_FILE, JUNIT_TITLE);
		Assert.assertTrue(actual.generate());
		File f = new File(JUNIT_APT_FILE);
		Assert.assertTrue("the file " + JUNIT_APT_FILE + " does not exist", f.exists());
		Assert.assertTrue("the file " + JUNIT_APT_FILE + " could not be deleted", f.delete());
	}
	
	/**
	 * Creates a file with a null target file and a title then deletes it.
	 * The created filename has the default name.
	 */
	@Test
	public void testCreateNullTarget(){
		MyApt actual = new MyApt(null, JUNIT_TITLE);
		Assert.assertTrue(actual.generate());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdfTitle = new SimpleDateFormat("yyyyMMdd");
		String filename = sdfTitle.format(cal.getTime()) + "_myApt.apt";
		File f = new File(filename);
		Assert.assertTrue("the file " + filename + " does not exist", f.exists());
		Assert.assertTrue("the file " + filename + " could not be deleted", f.delete());
	}
}
