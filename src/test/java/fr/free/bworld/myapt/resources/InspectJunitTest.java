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

/* $URL: svn+ssh://blerubrus@scm.forge.imag.fr/var/lib/gforge/chroot/scmrepos/svn/bworld/trunk/module/dev/module/myApt/src/test/java/fr/free/bworld/apt/resources/InspectJunitTest.java $ */
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
	 * Checks the user.dir directory: 3 expected resources: .classpath, .project and pom.xml 
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
		Assert.assertEquals("unexpected nb of res", 3, resources.size());
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
	
	@Test
	@Ignore("hardcoded path, but worked as expected")
	public void testListM4dVilnius(){
		Inspect inspect = new InspectImpl("/Users/blerubrus/workspace/m4d/src/site/resources/20131204_vilnius");
		System.out.println(inspect.toApt());
	}
	
	@Test
	@Ignore("hardcoded path, but worked as expected")
	public void testListM5dDemoVilnius(){
		Inspect inspect = new InspectImpl("/Users/blerubrus/workspace/m5d/src/site/resources/20131202_demoVilnius");
		System.out.println(inspect.toApt());
	}
	
	@Test
	@Ignore("hardcoded path, but worked as expected on moon")
	public void testMoonWiki(){
		Inspect inspect = new InspectImpl("/Users/blerubrus/workspace/bworld/module/moon/src/site/resources/images/wiki");
		System.out.println(inspect.toApt());
	}
	
	@Test
    @Ignore("hardcoded path, but worked as expected on cuttysark")
    public void testB2do(){
        Inspect inspect = new InspectImpl("/home/blerubrus/workspace/b2do/src/site/resources");
        System.out.println(inspect.toApt());
    }
	
	
   /**Counts the number of checked resources in espon-m4d that could be found in m4d.*/
   private static int FOUND_NB = 0;
	   
   /**Counts the number of checked resources in espon-m4d that could not be found in m4d.*/
   private static int NOT_FOUND_NB = 0;

   /**
    * Gets the list of all files in "espon-m4d/src/site/resources/" project, then, for the resources which are not in particular directories
    * (images, delivery, docbook, css), tries to find the filename in the "m4d" project directory.
    * This method invokes the {@link #find(String, String)} method for each filename, then outputs a summary to the console.
    * Checks the consistency of the number of resources.
    * <br/>
    * FIXME: hardcoded path, uses static variables...
    */
   @Test
   @Ignore("one shot hardcoded test to compare the resources in m4d and espon-m4d on cuttysark")
   public void testEsponM4d() {
      Inspect inspect = new InspectImpl("/home/blerubrus/workspace/espon-m4d/src/site/resources");
      List<Resource> resources = inspect.list(true);

      int excludedNb = 0;// counts the nb of res in espon-m4d that are not checked (i.e. trying to find them in m4d)
      int includedNb = 0;// counts the nb of res in espon-m4d that are checked (i.e. trying to find them in m4d)

      for (Resource r : resources) {
         String resPath = r.getPath();
         String resFilename = r.getFilename();

         // excludes some directories: counts the nb of res in these directories
         if (resPath.startsWith("/home/blerubrus/workspace/espon-m4d/src/site/resources/delivery")
               || resPath.startsWith("/home/blerubrus/workspace/espon-m4d/src/site/resources/images")
               || resPath.startsWith("/home/blerubrus/workspace/espon-m4d/src/site/resources/css")) {
            excludedNb++;
         }
         else {
            includedNb++;
            find(resPath, resFilename);
         }
      }
      System.out.println();
      System.out.println("Nb of res in espon-m4d: " + resources.size());
      System.out.println("Nb of res in espon-m4d that are checked: " + includedNb);
      System.out.println("Nb of res in espon-m4d that are not checked (images, css, delivery directories): " + excludedNb);
      System.out.println("Nb of cheched res in espon-m4d found in m4d: " + FOUND_NB);
      System.out.println("Nb of checked res in espon-m4d not found in m4d: " + NOT_FOUND_NB);

      Assert.assertEquals("the sum of found and of not found does not match the nb of  included res", includedNb, FOUND_NB + NOT_FOUND_NB);
      Assert.assertEquals("the sum of excluded and of included does not match the total nb of res", resources.size(), excludedNb + includedNb);
   }

   
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
   
   /**
    * Shows how to handle the output and error messages of an external process: the command options must be given in a table.
    * Hardcoded command example: "find /home/blerubrus/workspace/m4d -name src".
    * <br/>
    * Source: http://www.rgagnon.com/javadetails/java-0014.html
    */
   @Test
   @Ignore("hardcoded example showing how to handle the output and error messages of an external process")
   public void testCmd(){
      try {
        String[] cmdTab = { "find", "/home/blerubrus/workspace/m4d", "-name", "src" };
        StringBuffer buf = new StringBuffer("* Executing the following command: ");
        for (String c : cmdTab){
           buf.append(c);
           buf.append(" ");
        }
        System.out.println(buf);
        Process p = Runtime.getRuntime().exec(cmdTab);
        String line;
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        System.out.println("* Output:");
        while ((line = bri.readLine()) != null) {
           System.out.println(line);
        }
        bri.close();
        StringBuffer errBuf = new StringBuffer();
        while ((line = bre.readLine()) != null) {
           errBuf.append(line);
           errBuf.append(System.getProperty("line.separator"));
        }
        bre.close();
        if (! errBuf.toString().isEmpty()){
           System.out.println("* Error:");
           System.out.println(errBuf);
        }
        p.waitFor();
        System.out.println("Done.");
     }
     catch (Exception err) {
        err.printStackTrace();
     }
  }

}
