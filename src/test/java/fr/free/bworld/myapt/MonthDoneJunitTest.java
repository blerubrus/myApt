package fr.free.bworld.myapt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import fr.free.bworld.myapt.MonthDone;
import fr.free.bworld.myapt.MyApt;

/**
 * Tests the behaviour of the <code>MonthDone</code> class methods.
 *
 * @author blerubrus
 * @version $Id: MonthDoneJunitTest.java 149 2014-03-02 13:52:13Z blerubrus $
 *
 */
public class MonthDoneJunitTest {
	
	/**
	 * Checks the constructor with 0 and 13.
	 * @throws Exception
	 */
	@Test
	public void testConstructorInvalidMonth() throws Exception {
		try{
			new MonthDone(0, 0);
			Assert.assertFalse("should get an exception for month parameter = 0", true);
		}
		catch(Exception e){
			Assert.assertEquals(MonthDone.INVALID_MONTH_PARAM_MESSAGE, e.getMessage());
		}
		try{
			new MonthDone(0, 13);
			Assert.assertFalse("should get an exception for month parameter = 12", true);
		}
		catch(Exception e){
			Assert.assertEquals(MonthDone.INVALID_MONTH_PARAM_MESSAGE, e.getMessage());
		}
	}
	
	/**
	 * Checks the july 2011 expected calendar.
	 * @throws Exception
	 */
	@Test
	public void testGenerateCalendarJuly2011() throws Exception {
		MonthDone md = new MonthDone(2011, 07);
		StringBuffer actual = md.generateCalendar();
		
		//System.out.println(actual);

		StringBuffer expectedBuf = new StringBuffer();
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-01} (Friday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-02} (Saturday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-03} (Sunday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-04} (Monday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-05} (Tuesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-06} (Wednesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-07} (Thursday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-08} (Friday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-09} (Saturday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-10} (Sunday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-11} (Monday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-12} (Tuesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-13} (Wednesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-14} (Thursday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-15} (Friday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-16} (Saturday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-17} (Sunday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-18} (Monday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-19} (Tuesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-20} (Wednesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-21} (Thursday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-22} (Friday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-23} (Saturday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-24} (Sunday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-25} (Monday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-26} (Tuesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-27} (Wednesday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-28} (Thursday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("* {2011-07-29} (Friday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-30} (Saturday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append(MonthDone.APT_DECO_SEPARATION);
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		expectedBuf.append("~~* {2011-07-31} (Sunday)");
		expectedBuf.append(MonthDone.LINE_SEPARATOR);
		
		Assert.assertEquals(expectedBuf.toString(), actual.toString());
	}
	
	/**
	 * Checks the generated header for july 2011 without an scm specified.
	 * @throws Exception
	 */
	@Test
	public void testGenerateHeader()  {
		MonthDone md;
		try {
			md = new MonthDone(2011, 07);
		
			StringBuffer actual = md.generateHeader(null);
			
			StringBuffer expected = new StringBuffer(" ---");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(" Done July 2011");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(" ---");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(" ");
			expected.append(System.getProperty("user.name"));
			expected.append(MyApt.PROGRAM_MENTION);
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(" ---");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(" ");
			
			// adds the date when this file is generated
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			expected.append(sdf.format(c.getTime()));
			
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(MonthDone.LINE_SEPARATOR);
			//expected.append("~~ $");
			//expected.append("Id$");
			//expected.append(MonthDone.LINE_SEPARATOR);
			//expected.append("~~ $");
			//expected.append("URL$");
			//expected.append(MonthDone.LINE_SEPARATOR);
			//expected.append(MonthDone.LINE_SEPARATOR);
			expected.append("Done July 2011");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(MonthDone.LINE_SEPARATOR);
			
			expected.append(" This page lists the things I did in July 2011");
			expected.append(MonthDone.LINE_SEPARATOR);
			expected.append(MonthDone.LINE_SEPARATOR);
	
			
			//System.out.println(actual.toString());
			Assert.assertEquals(expected.toString(), actual.toString());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.assertFalse("unexpected exception", true);
			
		}
	}
	
	
	
	
	/**
	 * Tests the creation of the file, then deletes it.
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception {
		MonthDone md = new MonthDone(2011, 7);
		md.setOutputFilePath("junittest.apt");
		Assert.assertTrue(md.generate());
		File expected = new File("junittest.apt");
		Assert.assertTrue("does not exist", expected.exists());
		Assert.assertTrue("could not be deleted", expected.delete());		
	}
	
	
}