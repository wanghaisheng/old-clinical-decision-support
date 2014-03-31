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

/**
 *
 * @author  Ken
 * @version
 *
 * Utility class for writing to a file
 *
 *  to get the instance: 
 *  WriteUtility.getInstance();
 */

import java.io.*;
import java.util.ArrayList;
import java.nio.channels.FileChannel;

import org.omg.dss.DSSRuntimeExceptionFault;

public class WriteUtility
{
    private static WriteUtility instance;  //singleton instance

    /**
     * Creates new WriteUtility
     */
    private WriteUtility()
    {
    }

    public static synchronized WriteUtility getInstance()
    {
        if (instance == null) instance = new WriteUtility();
        return instance;
    }

    /**
     * Adds newLines to outputFile.  If outputFile does not exist, creates new file.
     * If outputFile does exist, then appends newLines to end of file.
     *
     * Does nothing if newLines is null or outputFile is null.
     * @param newLines
     * @param outputFile
     * @throws DSSRuntimeExceptionFault 
     */
    public void addLinesToFile(ArrayList<String> newLines, File outputFile) throws DSSRuntimeExceptionFault
    {
        if ((newLines == null) || (outputFile == null))
        {
            // do nothing
        }
        else
        {
            if (! outputFile.exists())
            {
                saveText(newLines, outputFile);
            }
            else
            {
                ArrayList<String> existingLines = FileUtility.getInstance().getLinesFromFile(outputFile);
                existingLines.addAll(newLines);
                saveText(existingLines, outputFile);
            }
        }
    }

    // uses default encoding
    public void saveText(ArrayList<String> lines, File outputFile)
    {
        saveText(lines, outputFile, null);
    }

    /**
     * If encoding is null, uses default encoding.
     * @param lines
     * @param outputFile
     * @param encoding      e.g., "UTF8"
     */
    public void saveText(ArrayList<String> lines, File outputFile, String encoding)
            // debugging function; saves ArrayList of String lines to outputFile
    {
        // Compile the text lines into a single string.
        StringBuilder newTextBuilder = new StringBuilder();
        for (Object line : lines)
        {
            newTextBuilder.append(line);
            newTextBuilder.append("\r\n");
        }
        String newText = newTextBuilder.toString();

        // Check if outputFile exists and has contents.
        int curLen = (int) outputFile.length();
        if (curLen > 0)
        {
            try
            {
                // Read the output file contents into another string.
                char[] buf = new char[curLen];
                FileReader in = new FileReader(outputFile);
                in.read(buf);
                in.close();
                String curText = new String(buf);

                if (equalsIgnoreNewlineDifferences(newText, curText))
                {
                    // The new text is the same as the current contents, so no need to re-write this file.
                    // No error will be thrown if the file is read-only.
                    // This is faster and the file's timestamp won't be unnecessarily updated.
                    return;
                }
            }
            catch (Exception e)
            {
                // Do nothing.
            }
        }

        try
        {
            // Ensure the file is writable
            Runtime.getRuntime().exec(new String[] {"attrib", "-r", outputFile.getAbsolutePath()});

            //----- code prior to 7/11/07 below
            //FileWriter out = new FileWriter(outputFile);
            //out.write(newText);
            //out.close();
            //----- code prior to 7/11/07 above

            //----- code after 7/11/07 below
            if (encoding == null)
            {
                FileWriter out = new FileWriter(outputFile);
                out.write(newText);
                out.close();
            }
            else
            {
                OutputStream fout = new FileOutputStream(outputFile);
                OutputStream bout = new BufferedOutputStream(fout);
                OutputStreamWriter out = new OutputStreamWriter(bout, encoding);
                out.write(newText);
                out.close();
            }
            //----- code after 7/11/07 above
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static boolean equalsIgnoreNewlineDifferences(String s1, String s2)
    {
        if (s1 == s2)
        {
            // Both are null or are the same string instance.
            return true;
        }
        if (s1 == null || s2 == null)
        {
            // One is null and the other is not.
            return false;
        }

        int len1 = s1.length();
        int len2 = s2.length();
        int i1 = 0, i2 = 0;

        for (;;)
        {
            if (i1 == len1 && i2 == len2)
            {
                // Got to the end of both strings.
                return true;
            }
            if (i1 == len1 || i2 == len2)
            {
                // Got to the end of one of the strings but not the other.
                return false;
            }

            char c1 = s1.charAt(i1);
            char c2 = s2.charAt(i2);

            if (c1 == c2)
            {
                i1++;
                i2++;
            }
            else if (c1 == '\r' && c2 == '\n')
            {
                // String 1 may have an additional CR before the LF character, so skip past it.
                i1++;
            }
            else if (c2 == '\r' && c1 == '\n')
            {
                // String 2 may have an additional CR before the LF character, so skip past it.
                i2++;
            }
            else
            {
                // Found a difference.
                return false;
            }
        }
    }

    /**
     * Copy function; from http://www.experts-exchange.com/Programming/Programming_Languages/Java/Q_10245809.html
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public void copy(File source, File dest) throws IOException
    {
        FileChannel in = null, out = null;

        if (! dest.exists())
        {
            dest.createNewFile();
        }

        try
        {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(dest).getChannel();

            in.transferTo(0, in.size(), out);
        } finally
        {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

    public static void main(String[] args)
    {
        String s1 = "\r\n\n";
        String s2 = "\n\r\n";
        equalsIgnoreNewlineDifferences(s1, s2);
    }
}

