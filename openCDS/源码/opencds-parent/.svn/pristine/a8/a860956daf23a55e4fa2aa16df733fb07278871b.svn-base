/**
 * Copyright 2011 OpenCDS.org
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 *	
 */

package org.opencds.common.utilities;

/*
 * FileUtility is a utility for File operations.
 *
 * @author Kensaku Kawamoto
 * @version 1.00
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.omg.dss.DSSRuntimeExceptionFault;

public class FileUtility
{
    private static FileUtility instance;  //singleton instance

    private FileUtility()
    {
    }

    public static synchronized FileUtility getInstance()
    {
        if (instance == null) instance = new FileUtility();
        return instance;
    }

    /**
     * Returns an ArrayList containing File objects in the specified file path, with the file name
     * starting with and ending with the strings specified.
     * Sample filePath: "C:\Temp\Folder1" or "C:\Temp\Folder1\". (both types ok --> tested).
     *
     * @param fullDirectoryPath
     * @param startsWith
     * @param endsWith
     * @return
     */
    public List<File> getFileListInDirectory(String fullDirectoryPath, String startsWith, String endsWith)
    {
    	List<File> arrayToReturn = new ArrayList<File>();
        File parentDirectory = new File(fullDirectoryPath);

        File[] files = parentDirectory.listFiles();

        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                boolean startOkay = false;
                boolean endOkay = false;

                File file = files[i];
                String fileName = file.getName();

                if ((startsWith == null) || (startsWith.equals("")))
                {
                    startOkay = true;
                }
                else
                {
                    startOkay = fileName.startsWith(startsWith);
                }

                if ((endsWith == null) || (endsWith.equals("")))
                {
                    endOkay = true;
                }
                else
                {
                    endOkay = fileName.endsWith(endsWith);
                }

                if (startOkay && endOkay)
                {
                    arrayToReturn.add(file);
                }
            }
        }
        return arrayToReturn;
    }

    /**
     * Returns an ArrayList containing fileName String objects in the specified file path, with the file name
     * starting with and ending with the strings specified.
     * Sample filePath: "C:\Temp\Folder1" or "C:\Temp\Folder1\". (both types ok --> tested).
     *
     * @param fullDirectoryPath
     * @param startsWith
     * @param endsWith
     * @return
     */
    public List<String> getFileNameListInDirectory(String fullDirectoryPath, String startsWith, String endsWith)
    {
    	List<String> fileNameList = new ArrayList<String>();
    	List<File> fileList = getFileListInDirectory(fullDirectoryPath, startsWith, endsWith);
    	for ( File file : fileList) {
    		fileNameList.add( file.getName());
    	}
    	return fileNameList;
    }
 
    /**
     * Returns an ArrayList containing File objects in the specified file path, with the file name
     * starting with and ending with the strings specified.
     * Sample filePath: "\Folder1" or "\Folder1\". (both types ok --> tested).
     *
     * @param clazz - Class whose classLoader is to be used to find the file list
     * @param relativeDirectoryPath - origin is the classLoader for the submitted Class
     * @param startsWith
     * @param endsWith
     * @return
     * @throws DSSRuntimeExceptionFault 
     */
//    public ArrayList<File> getFileListInResourceDirectory(	Class<?>  clazz, 
//    														String relativeDirectoryPath, 
//    														String startsWith, 
//    														String endsWith
//    														) 
//    throws DSSRuntimeExceptionFault
//    {
//    	URL dirURL = clazz.getClassLoader().getResource(relativeDirectoryPath);
//        File fullPath = null;
//        if (dirURL != null && dirURL.getProtocol().equals("file")) 
//        {
//        	/* File path */
//        	try {
////			return new File(dirURL.toURI());
//        		fullPath = new File(dirURL.toURI());
//			} catch (URISyntaxException e) {
//				e.printStackTrace();
//				throw new DSSRuntimeExceptionFault("error in getFileListInResourceDirectory: " + e.getMessage());
//			}
//        } else {          
//        	//throw new UnsupportedOperationException("Cannot find file in URL "+dirURL);
//        	return new ArrayList<File>();
//        }
//        String absolutePath = fullPath.getAbsolutePath();
//        return getFileListInDirectory(absolutePath, startsWith, endsWith);
//    }

    /**
     * Returns an ArrayList containing fileName String objects in the specified file path, with the file name
     * starting with and ending with the strings specified.
     * Sample filePath: "C:\Temp\Folder1" or "C:\Temp\Folder1\". (both types ok --> tested).
     *
     * @param fullDirectoryPath
     * @param startsWith
     * @param endsWith
     * @return
     */
//    public ArrayList<String> getFileNameListInResourceDirectory(Class<?>  clazz, 
//																String relativeDirectoryPath, 
//																String startsWith, 
//																String endsWith
//																) 
//	throws DSSRuntimeExceptionFault
//    {
//    	ArrayList<File> fileList = getFileListInResourceDirectory(clazz, relativeDirectoryPath, startsWith, endsWith);
//    	ArrayList<String> fileNameList = new ArrayList<String>();
//    	for ( File file : fileList) {
//    		fileNameList.add( file.getName());
//    	}
//    	return fileNameList;
//    }
 
    
    /**
     * Returns a List containing Strings that represent individual lines in sourceFile.
     *
     * @param sourceFile
     * @return
     * @throws DSSRuntimeExceptionFault 
     */
    public ArrayList<String> getLinesFromFile(File sourceFile) throws DSSRuntimeExceptionFault
    {
    	ArrayList<String> arrayToReturn = new ArrayList<String>();
        try
        {
            String line = null;
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));

            while ((line = reader.readLine()) != null)
            {
                arrayToReturn.add(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DSSRuntimeExceptionFault("unable to getLinesFromFile '" + sourceFile.getPath() + "' because: " + e.getMessage());
        }

        return arrayToReturn;
    }

//    /**
//     * Returns a String[] containing Strings that represent individual lines in sourceFile.
//     *
//     * @param sourceFile
//     * @return
//     * @throws DSSRuntimeExceptionFault 
//     */
//    public String[] getLinesFromFile(File sourceFile) throws DSSRuntimeExceptionFault
//    {
//    	String[]  arrayToReturn = null; //new String[0];
//        try
//        {
//            String line = null;
//            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
//
////            while ((line = reader.readLine()) != null)
//            for (int i=0; i < reader.)
//            {
//                arrayToReturn[] = line;
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            throw new DSSRuntimeExceptionFault();
//        }
//
//        return arrayToReturn;
//    }

    /**
     * Returns contents of file as a single String.
     *
     * @param sourceFile
     * @return
     * @throws DSSRuntimeExceptionFault 
     */
    public String getFileContentsAsString(File sourceFile) throws DSSRuntimeExceptionFault
    {
    	List<String> fileLines = getLinesFromFile(sourceFile);
        StringBuffer buffer = new StringBuffer(5000);

        for (int k = 0; k < fileLines.size(); k++)
        {
            buffer.append(fileLines.get(k));
        }
        return buffer.toString();

    }


    /**
     * Returns contents of file as a single String, with imbedded \n where \n, \r or \r\n were present in original.
     *
     * @param sourceFile
     * @return
     * @throws DSSRuntimeExceptionFault 
     */
    public String getFileContentsAsStringWithNewlines(File sourceFile) throws DSSRuntimeExceptionFault
    {
    	List<String> fileLines = getLinesFromFile(sourceFile);
        StringBuffer buffer = new StringBuffer(5000);

        for (int k = 0; k < fileLines.size(); k++)
        {
            buffer.append(fileLines.get(k) + "\\n");
        }
        return buffer.toString();

    }


    /**
     * Deletes all files and subdirectories under dir.  Deletes parent directory if so specified.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     * @param dir
     * @param depth 	starts at 1, increments up
     * @return
     */
    private static boolean deleteDir(File dir, int depth, boolean deleteRootDirectory)
    {
    	int originalDepth = depth;
    	depth++;
    	if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]), depth, deleteRootDirectory);
                if (!success)
                {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
    	if ((originalDepth == 1) && (! deleteRootDirectory))
    	{
    		return true;
    	}
    	else
    	{
    		return dir.delete();
    	}
    }
    
    /**
     * Deletes all files and subdirectories under dir.  Deletes parent directory itself if so specified.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir, boolean deleteRootDirectory)
    {
        return deleteDir(dir, 1, deleteRootDirectory);
    }

    
    /**
     * Copies file.  From http://www.rgagnon.com/javadetails/java-0064.html
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyFile(File in, File out)
            throws IOException
    {
        FileChannel inChannel = new
                FileInputStream(in).getChannel();
        FileChannel outChannel = new
                FileOutputStream(out).getChannel();
        try
        {
            // magic number for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size)
            {
                position +=
                        inChannel.transferTo(position, maxCount, outChannel);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }        
    }
    
//    /**
//     * Adapted from public code posting at http://www.uofr.net/~greg/java/get-resource-listing.html.
//     * 
//     * Lists directory contents for a resource folder. Not recursive.
//     * This is basically a brute-force implementation.
//     * Works for regular files and also JARs.
//     * 
//     * @param clazz 	Any java class that lives in the same place as the desired resources.
//     * @param path 		Should end with "/", but not start with one.
//     * @return 			Name of each member item, not the full paths.
//     * @throws URISyntaxException 
//     * @throws IOException 
//     * @throws DSSRuntimeExceptionFault 
//     */
//    public String[] getKRResourceListing(Class<?> clazz, String path) throws DSSRuntimeExceptionFault 
//    {
//    	File baseFile = getResourceFile(clazz, "KnowledgeRepositoryConfig.cfg");
////    	URL baseURL = clazz.getClassLoader().getResource("KnowledgeRepositoryConfig.cfg");
//    	String contents = getFileContentsAsString(baseFile);
//    	String basePath = null;
//    	if ( "KRPath=".equals(contents.substring(0, 6)) ) {
//    		basePath = contents.substring(7, contents.indexOf(" "));
//    	}
//    	
////    	String basePathWithFullFileName = baseFile.getPath();
////    	String basePath = basePathWithFullFileName.substring(0, basePathWithFullFileName.indexOf("KnowledgeRepositoryConfig.cfg")) + path;
//    	List<String> result = getFileNameListInDirectory(basePath, "", "");
//    	return result.toArray(new String[result.size()]);
//    }
    
	//NOTE  this attempt to traverse a jar file doesn't work...
//    	URL dirURL = clazz.getClassLoader().getResource(path + "//");
//        if (dirURL != null && dirURL.getProtocol().equals("file")) {
//        	/* File path */
//        	try {
////        		String p1 = dirURL.toExternalForm();
////        		String p2 = dirURL.toString();
////        		String p3 = dirURL.toURI().toString();
////        		String p4 = dirURL.getFile();
////        		String p5 = dirURL.getPath();
////        		Object p6 = dirURL.getContent();
////        		System.out.println(p6.toString());
//        		File p7 = new File(dirURL.toURI());
//        		String[] p8 = p7.list();
//        		return (new File(dirURL.toURI())).list();
//        	} catch (URISyntaxException e) {
//        		e.printStackTrace();
//        		throw new DSSRuntimeExceptionFault("FileUtility.getResourceListing failed with URISyntaxException: " + e.getMessage());
////        	} catch (IOException e) {
////				e.printStackTrace();
////				throw new DSSRuntimeExceptionFault("FileUtility.getResourceListing failed with IOException: " + e.getMessage());
//        	}
//        } 

//        if (dirURL == null) {
//
//			 /* 
//			  * In case of a jar file, we can't actually find a directory.
//			  * Have to assume the same jar as clazz.
//			  */
//        	
//			 String me = clazz.getName().replace(".", "/")+".class";
//			 dirURL = clazz.getClassLoader().getResource(me);
//        }
//        
//        if (dirURL.getProtocol().equals("jar")) {
//        	/* A JAR path */
//        	String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
//        	JarFile jar;
//			try {
//				jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//        		throw new DSSRuntimeExceptionFault("FileUtility.getResourceListing failed with UnsupportedEncodingException: " + e.getMessage());
//			} catch (IOException e) {
//				e.printStackTrace();
//        		throw new DSSRuntimeExceptionFault("FileUtility.getResourceListing failed with IOException: " + e.getMessage());
//			}
//	        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
//	        Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
//	        while(entries.hasMoreElements()) {
//	        	String name = entries.nextElement().getName();
//	        	if (name.startsWith(path)) { //filter according to the path
//	        		String entry = name.substring(path.length());
//	        		int checkSubdir = entry.indexOf("/");
//	        		if (checkSubdir >= 0) {
//	        			// if it is a subdirectory, we just return the directory name
//	        			entry = entry.substring(0, checkSubdir);
//	        		}
//	        		result.add(entry);
//	        	}
//	        }
//	        return result.toArray(new String[result.size()]);
//        } 
//          
//        throw new DSSRuntimeExceptionFault("FileUtility.getResourceListing failed to list files in path='" + path + "' for URL " + dirURL);
//	}

    
    /**
     * @param fullPath
     * @return
     * @throws DSSRuntimeExceptionFault
     */
    public File getFile(String fullPath) throws DSSRuntimeExceptionFault
    {
    	try {
    		File thisFile = new File(fullPath);
//    		if ( !thisFile.isFile() ) {
//    			throw new DSSRuntimeExceptionFault("FileUtility.getFile(" + fullPath + ") returned a non-file (possibly a directory?).");
//    		}
//    		if ( thisFile.length() == 0 ) {
//    			throw new DSSRuntimeExceptionFault("FileUtility.getFile(" + fullPath + ") returned a file of length: '" + thisFile.length() + "'.");
//    		}
    		if ( !thisFile.exists() ) {
			throw new DSSRuntimeExceptionFault("FileUtility.getFile(" + fullPath + ") returned a non-file (possibly a directory?).");
		}
    		return thisFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DSSRuntimeExceptionFault("FileUtility.getFile failed with unknown Exception: " + e.getMessage());
		}
    }
    

    /**
     * @param fullPath
     * @return
     */
    public File getFileWithoutException(String fullPath) 
    {
    	File thisFile = new File(fullPath);
    	if ( !thisFile.exists() ) {
			return null;
		} else {
    		return thisFile;
		}
    }
    

    /**
     * @param clazz
     * @param path
     * @return
     * @throws DSSRuntimeExceptionFault
     */
    public File getResourceFile(Class<?> clazz, String path) throws DSSRuntimeExceptionFault  
    {
        URL fileURL = clazz.getClassLoader().getResource(path);
        if (fileURL != null && fileURL.getProtocol().equals("file")) {
          /* File path */
        	try {
        		return new File(fileURL.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
				throw new DSSRuntimeExceptionFault("FileUtility.getResourceFile failed with URISyntaxException: " + e.getMessage());
			}
        }    	
        
        if (fileURL == null) {
			 /* 
			  * In case of a jar file, we can't actually find a directory.
			  * Have to assume the same jar as clazz.
			  */
			 String me = clazz.getName().replace(".", "/")+".class";
			 fileURL = clazz.getClassLoader().getResource(me);
        }
        
        if (fileURL.getProtocol().equals("jar")) {
        	/* A JAR path */
        	String jarPath = fileURL.getPath().substring(5, fileURL.getPath().indexOf("!")); //strip out only the JAR file
        	JarFile jar;
			try {
				jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
        		throw new DSSRuntimeExceptionFault("FileUtility.getResourceFile failed with UnsupportedEncodingException: " + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
        		throw new DSSRuntimeExceptionFault("FileUtility.getResourceList failed with IOException: " + e.getMessage());
			}
	        Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
	        while(entries.hasMoreElements()) {
	        	String name = entries.nextElement().getName();
	        	if (name.equals(path)) { //filter according to the path
	        		return new File(path);
	        	}
	        }	        
        } 
          
        throw new DSSRuntimeExceptionFault("FileUtility.getResourceFile failed to find file in path='" + path + "' for URL " + fileURL);
        
    }


    /**
     * 
     * @param str = String to convert into an InputStream
     * @return
     */
	public InputStream getInputStreamFromString(String str)
    {
        try
        {
            byte[] bytes = str.getBytes("UTF8");
            return new ByteArrayInputStream(bytes);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  
			throw new RuntimeException( "getInputStreamFromString had errors: " + e.getMessage() );
        }
    }
	
	/**
	 * 
	 * @param str = XML String to convert to an InputStream
	 * @return
	 */
	public InputStream getInputStreamFromXML(String str)
    {
        try
        {
            byte[] bytes = str.getBytes("UTF8");
            return new ByteArrayInputStream(bytes);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();  
			throw new RuntimeException( "getInputStreamFromXML had errors: " + e.getMessage() );
        }
    }

	public static void main(String args[]) throws DSSRuntimeExceptionFault
	{
//		AddressElement myFU = new AddressElement();
//		String[]  fileNameList =	myFU.getResourceListing(myFU.getClass(), "..\\");//org\\opencds\\vmr\\v1_0\\internal\\"); /*"opencds-vmr-1_0-internal.jar".getClass(),*/
//		for (String eachResource : fileNameList) {
//			System.out.println(eachResource);
//		}
		
		
////		String resourceString = getResourceAsString("knowledgeModules", "org.opencds^NQF_0031_v1^1.0.0.drl");
//		File resource	= new File("C:\\OpenCDS\\opencds-parent\\opencds-knowledge-repository\\src\\main\\resources\\knowledgeModules" + "\\" + "org.opencds^NQF_0031_v1^1.0.0.drl");
////		File resource 	= new File("C\\\\OpenCDS\\opencds-parent\\opencds-knowledge-repository\\src\\main\\resources\\" + "knowledgeModules" + "\\" + "org.opencds^NQF_0031_v1^1.0.0.drl");
////		File resource 	= new File(".\\src\\main\\resources\\" + "knowledgeModules" + "\\" + "org.opencds^NQF_0031_v1^1.0.0.drl");
//		String resourceString =	FileUtility.getInstance().getFileContentsAsString(resource);
//		if (resourceString == null) {
//			System.out.println("resourceString is null.");
//		} else {
//			System.out.println(resourceString);
//		}
		
		
		List<File> fileList = new ArrayList<File>();
//		String filePath = ".\\src\\main\\resources\\" + "schema" + "\\";
//		String filePath = ".\\";
//		String filePath = "c:\\OpenCDS\\opencds-parent\\opencds-vmr-1_0\\opencds-vmr-1_0-internal\\target\\org\\opencds\\vmr\\v1_0\\internal\\";//getResourceListing("opencds-vmr-1_0-internal.jar".getClass(), ".\\org\\opencds\\vmr\\v1_0\\internal\\");
//		String filePath = "c:\\OpenCDS\\opencds-parent\\opencds-vmr-1_0\\opencds-vmr-1_0-internal\\target\\classes\\org\\opencds\\vmr\\v1_0\\internal\\";//getResourceListing("opencds-vmr-1_0-internal.jar".getClass(), ".\\org\\opencds\\vmr\\v1_0\\internal\\");
		String filePath = "..\\opencds-vmr-1_0\\opencds-vmr-1_0-internal\\target\\classes\\org\\opencds\\vmr\\v1_0\\internal\\";//getResourceListing("opencds-vmr-1_0-internal.jar".getClass(), ".\\org\\opencds\\vmr\\v1_0\\internal\\");
		fileList = FileUtility.getInstance().getFileListInDirectory(filePath, "", "");
//		ArrayList<String> resourceList = listResourcesByType("schema");
		for (File eachResource : fileList) {
			System.out.println(eachResource.getName());
		}
	
	}
	

}