package fr.free.bworld.myapt.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import fr.free.bworld.myapt.resources.Inspect;
import fr.free.bworld.myapt.resources.InspectImpl;
import fr.free.bworld.myapt.resources.Resource;


/**
 * Used to generate the apt file listing resources in the specified directory.
 * 
 * @author blerubrus
 * @version $Id: InspectJunitTest.java 231 2014-07-02 12:48:25Z blerubrus $
 */
public class InspectJunitTest {
	/**
	 * Gives the constructor a null path, an exception with a non null message is expected.
	 */
	@Test
	public void testListNullPath(){
		try{
			new InspectImpl(null);
			Assert.assertFalse("should throw an exception", true);
		}
		catch(IllegalArgumentException e){
			Assert.assertNotNull("the exception message should not be null", e.getMessage());
		}
	}
	/**
	 * Gives the constructor a non existing path ("/toto"), an exception with a non null message is expected.
	 */
	@Test
	public void testListTotoPath(){
		try{
			new InspectImpl("/toto");
			Assert.assertFalse("should throw an exception", true);
		}
		catch(IllegalArgumentException e){
			Assert.assertNotNull("the exception message should not be null", e.getMessage());
		}
	}
	
	/**
	 * Checks the user.dir directory: 4 expected resources: .classpath, .project, .gitignore, LICENSE, README.md and pom.xml 
	 * 
	 */
	@Test
	public void testListUserDirNonRecursive(){
		String path = System.getProperty("user.dir");
		System.out.println("Inspect: " + path);
		Inspect inspect = new InspectImpl(path);
		List<Resource> resources = inspect.list(false);
		System.out.println("Res nb: " + resources.size());
		
		for (Resource r : resources){
			System.out.println(r.toString());
		}
		Assert.assertEquals("unexpected nb of res", 6, resources.size());
	}
	
	@Test
	public void testListUserDirRecursive(){
		String path = System.getProperty("user.dir");
		System.out.println("Inspect: " + path);
		Inspect inspect = new InspectImpl(path);
		List<Resource> resources = inspect.list(true);
		System.out.println("Res nb: " + resources.size());
		//Assert.assertEquals("unexpected nb of res", 3, resources.size());
		for (Resource r : resources){
			System.out.println(r.toString());
		}
	}
	
	
	
	
   /**Counts the number of checked resources in espon-m4d that could be found in m4d.*/
   private static int FOUND_NB = 0;
	   
   /**Counts the number of checked resources in espon-m4d that could not be found in m4d.*/
   private static int NOT_FOUND_NB = 0;

   
   
   /**
    * Executes the following external process: "find /home/blerubrus/workspace/m4d -name filename".
    * <br/>
    * Displays the result of this process (found or not).
    * 
    * @param path is the absolute path to the filename, used for the output message.
    * @param filename is the filename to find.
    */
   public static void find(String path, String filename){
      try {
         String newLine = System.getProperty("line.separator");
         String searchDir = "/home/blerubrus/workspace/m4d";
         String[] cmdTab = { "find", searchDir, "-name", filename};
         
         Process p = Runtime.getRuntime().exec(cmdTab);
         String line;

         BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
         BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
         
         // output messages
         StringBuffer outBuf = new StringBuffer();
         while ((line = bri.readLine()) != null) {
            if (line.startsWith(searchDir)){
               outBuf.append(line.substring(searchDir.length() - 3));
            }
            else{
               outBuf.append(line);
            }
            
            outBuf.append(newLine);
         }
         bri.close();
         
         //errors output for the cmd
         StringBuffer errBuf = new StringBuffer();
         while ((line = bre.readLine()) != null) {
            errBuf.append(line);
            errBuf.append(newLine);
         }
         bre.close();
         
         
         if (! outBuf.toString().isEmpty()){
            FOUND_NB++;
            System.out.println("The resource '" + path.substring(26) + "/" + filename + "' has been found in: " + outBuf);// there are 26 characters in "/home/blerubrus/workspace/"
         }
         else{
            NOT_FOUND_NB++;
            System.out.println("The resource '" + path.substring(26) + "/" + filename + "' could not be found in m4d" + newLine);// there are 26 characters in "/home/blerubrus/workspace/"
         }
         //eventual erros
         if (! errBuf.toString().isEmpty()){
            System.err.println("* Error:");
            System.err.println(errBuf);
         }
         p.waitFor();
      }
      catch (Exception err) {
         err.printStackTrace();
      }
   }
   
   

}
