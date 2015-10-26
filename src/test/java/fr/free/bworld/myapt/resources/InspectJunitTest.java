package fr.free.bworld.myapt.resources;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;


/**
 * Used to generate the apt file listing resources in the specified directory.
 * 
 * TODO: For the given list of resources, checks the expected alphabetical sort of the "toApt" method.
 * Not easy, since the sort is computed in the "toApt" method that returns a string...
 * 
 * @author blerubrus
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
	 * Checks the user.dir directory: 6 expected resources: .classpath, .project, .gitignore, LICENSE, README.md and pom.xml 
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

}
