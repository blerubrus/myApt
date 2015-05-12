package fr.free.bworld.myapt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Usefull to generate a new "b2do month todo" apt (Doxia) file.
 * <br/>
 * Usage, example for July 2011:
 * <ol>
 * <li>MonthDone md = new MonthDone(2011, 7);</li>
 * <li>md.create();</li>
 * </ol>
 * The "2011-07_done.apt" file has been generated.
 * <br/>
 * The <code>setOutputFilePath</code> method allows to set a custom output file path and name.
 * <br/>
 * See <code>MonthDoneJunitTest</code> for examples and usage.
 * 
 *
 * @author blerubrus
 * @version $Date$
 *
 */
public class MonthDone implements GenerateApt {

   /**The default directory where the done file is generated to.*/
   private static final String DEFAULT_APT_DIRECTORY = "./";

   /**An APT comment line that separates each date of the generated file.*/
   protected static final String APT_DECO_SEPARATION = "~~===============================================================================";

   /**The default suffix for the generated file.*/
   public static final String FILE_SUFFIX = "_done.apt";

   /**A message when the input parameter of month is invalid (must be between 1 and 12).*/
   public static final String INVALID_MONTH_PARAM_MESSAGE = "The month parameter must be an integer between 1 and 12";


   /**Useful for the new line.*/
   static final String LINE_SEPARATOR = System.getProperty("line.separator");

   /**Shows the year.*/
   private int year;

   /**Shows the month (January: 1).*/
   private int month;

   /**The path to the generated file. The default value is set by the constructor, but can be overriden by the user on using the setter.*/
   private String outputFilePath;
   
   /**Depending on the value (svn), injects rcs keywords in the apt file to generate.*/
   private String scm;
   
   /**The title of the document.*/
   private String title;
   
   /**
    * Sets the attributes: year, month and default output filename.
    * 
    * 
    * @param year is the year to consider.
    * @param month is the (human) month to consider (example: January - 1).
    * @throws Exception if the month is not valid (between 1 and 12).
    */
   public MonthDone(int year, int month) throws Exception{
      if (month < 1 || month > 12){
         throw new Exception(INVALID_MONTH_PARAM_MESSAGE);
      }		
      setYear(year);
      setMonth(month);

      StringBuffer output = new StringBuffer();
      output.append(year);
      output.append("-");
      if (month < 10){
         output.append("0");
      }
      output.append(month);
      output.append(FILE_SUFFIX);
      setOutputFilePath(output.toString());		
   }

   /**
    * Invokes the {@link #generateHeader(String)} and {@link #generateCalendar()} then
    * prints to the file.
    * 
    * @return shows if the creation succeeded.
    */
   @Override
   public boolean generate(){

      StringBuffer buf = new StringBuffer();
      buf.append(generateHeader(scm));
      buf.append(generateCalendar());
      buf.append(MyApt.generateFooter(scm));

      File file = new File(getOutputFilePath());
      try{
         FileOutputStream fos = new FileOutputStream(file);
         PrintWriter pw = new PrintWriter(fos);
         pw.write(buf.toString());
         pw.flush();
         fos.close();
         pw.close();
         return true;
      }
      catch(Exception e){
         e.printStackTrace();
         return false;
      }
   }



   /**
    * 
    * @param scm if the value is "svn", adds 2 comment lines with Id and URL
    * @return the start of the generated file.
    */
   protected StringBuffer generateHeader(String scm){
      //title of the page
      StringBuffer titleBuf = new StringBuffer("Done ");		

      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, getYear());
      cal.set(Calendar.MONTH, getMonth() - 1);

      titleBuf.append(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));		
      titleBuf.append(" ");		
      titleBuf.append(getYear());
      this.title = titleBuf.toString();

      StringBuffer buf = MyApt.generateHeader(titleBuf.toString(), null, scm);

      buf.append(" This page lists the things I did in ");
      buf.append(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
      buf.append(" ");
      buf.append(getYear());

      buf.append(LINE_SEPARATOR);
      buf.append(LINE_SEPARATOR);

      return buf;
   }

   /**
    * Builds the list of dates in the month in an APT format style.
    * @return the dates and names of days with APT format decoration.
    */
   protected StringBuffer generateCalendar(){
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, getYear());
      cal.set(Calendar.MONTH, getMonth() - 1);

      StringBuffer buf = new StringBuffer();

      // 1 to 31: for (int i = cal.getActualMinimum(Calendar.DAY_OF_MONTH); i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
      // 31 to 1: for (int i = cal.getActualMaximum(Calendar.DAY_OF_MONTH); i >= cal.getActualMinimum(Calendar.DAY_OF_MONTH); i--){

      for (int i = cal.getActualMinimum(Calendar.DAY_OF_MONTH); i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++){
         cal.set(Calendar.DAY_OF_MONTH, i);

         //deco
         buf.append(LINE_SEPARATOR);
         buf.append(APT_DECO_SEPARATION);
         buf.append(LINE_SEPARATOR);
         // APT comments if week end
         if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            buf.append("~~");
         }

         buf.append("* ");
         buf.append(getYear());
         buf.append("-");

         if (getMonth() < 10){
            buf.append("0");
         }

         buf.append(getMonth());
         buf.append("-");

         if (i < 10){
            buf.append("0");
         }

         buf.append(i);
         buf.append(" (");
         buf.append(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH));
         buf.append(")");

         buf.append(LINE_SEPARATOR);

      }

      return buf;
   }



   /**
    * Getter.
    * @return the outputFilePath
    */
   public String getOutputFilePath() {
      return outputFilePath;
   }



   /**
    * Setter.
    * @param outputFilePath the outputFilePath to set
    */
   public void setOutputFilePath(String outputFilePath) {
      this.outputFilePath = outputFilePath;
   }



   /**
    * Getter.
    * @return the year
    */
   private int getYear() {
      return year;
   }

   /**
    * Setter.
    * @param year the year to set
    */
   private void setYear(int year) {
      this.year = year;
   }

   /**
    * Getter.
    * @return the month
    */
   private int getMonth() {
      return month;
   }

   /**
    * Setter.
    * @param month the month to set
    */
   private void setMonth(int month) {
      this.month = month;
   }



   /**
    * @see fr.free.bworld.myapt.GenerateApt#getGeneratedFilenames()
    */
   @Override
   public List<String> getGeneratedFilenames() {
      List<String> result = new ArrayList<String>();
      result.add(getOutputFilePath());
      return result;
   }

	/**
	 * @see fr.free.bworld.myapt.GenerateApt#setScm(java.lang.String)
	 */
	@Override
	public void setScm(String scm) {
		this.scm = scm;
	}
	
	/**
	 * Generates an xml instruction to be added to the site.xml maven site descriptor.
	 * @return the {@link #title} and generated filename in a menu item xml instruction.
	 */
	@Override
	public String getMenuAdd() {
		StringBuffer buf = new StringBuffer("<item name=\"");
		buf.append(title);
		buf.append("\" href=\"");
		String targetFilename = getOutputFilePath();
		//get the file wihout path
		File f = new File(targetFilename);
		String filename = f.getName();
		// replace apt by html
		buf.append(filename.substring(0, filename.length() - 3) + "html");
		buf.append("\"/>");
		return buf.toString();
	}


}
