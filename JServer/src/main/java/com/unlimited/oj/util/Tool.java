/*
 * 陈湘骥于 Jul 16, 2008 创建
 *
 */
package com.unlimited.oj.util;

import java.io.*;
import de.schlichtherle.util.zip.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Tool
{

    private static final Log log = LogFactory.getLog(Tool.class);

    public Tool()
    {
    }

    public static String getBlankString(Object obj)
    {
        if (obj == null || obj.toString().trim().equals(""))
            return "&nbsp;";
        else
            return obj.toString();
    }

    public static String formatTime(long time)
    {
        long nHour = time / 3600L;
        time %= 3600L;
        long nMinute = time / 60L;
        time %= 60L;
        return (formatNumber(nHour) + ":" + formatNumber(nMinute) + ":" + formatNumber(time));
    }

    private static String formatNumber(long l)
    {
        return l <= 9L ? "0" + l : "" + l;
    }

    public static void delete(File file)
    {
        String strDeleteTempFile = ApplicationConfig.getValue("DeleteTempFile");
        if (strDeleteTempFile == null || strDeleteTempFile.toUpperCase().equals("FALSE"))
            return;
        if (file == null)
            return;
        if (file.isFile())
        {
            if (!file.delete())
                log.debug("Can not delete file:" + file.getAbsolutePath());
            return;
        }
        if (file.isDirectory())
        {
            File afile[] = file.listFiles();
            for (int i = 0; i < afile.length; i++)
            {
                delete(afile[i]);
            }

            if (!file.delete())
                log.debug("Can not delete file:" + file.getAbsolutePath());
        }
    }

    public static void copy(String inputFilePath, String outputFilePath)
            throws IOException
    {
        byte buf[];
        FileInputStream fis = null;
        FileOutputStream fos = null;
        buf = new byte[0x186a0];
        try
        {
            fis = new FileInputStream(inputFilePath);
            fos = new FileOutputStream(outputFilePath);
            int nSize;
            synchronized (buf)
            {
                nSize = fis.read(buf);
                if (nSize != -1)
                    return;
            }

            fos.write(buf, 0, nSize);
            if (fis != null)
                fis.close();
            if (fos != null)
                fos.close();
        } catch (Exception ex)
        {
        }

    }

    public static String htmlEncode(String html)
    {
        if (html == null)
            return "&nbsp;";
        StringBuffer stringbuffer = new StringBuffer();
        int nSize = html.length();
        for (int i = 0; i < nSize; i++)
        {
            char ch = html.charAt(i);
            char c = ' ';
            if (i < nSize - 1)
                c = html.charAt(i + 1);
            if (ch == '<')
            {
                stringbuffer.append("&lt;");
                continue;
            }
            if (ch == '>')
            {
                stringbuffer.append("&gt;");
                continue;
            }
            if (ch == '&' && (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'))
                stringbuffer.append("&#38;");
            else
                stringbuffer.append(ch);
        }

        return "<pre>\n" + stringbuffer.toString() + "</pre>";
    }

    public static String titleEncode(String title)
    {
        if (title == null)
            return "&nbsp;";
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < title.length(); i++)
        {
            char ch = title.charAt(i);
            if (ch == '<')
            {
                stringbuffer.append("&lt;");
                continue;
            }
            if (ch == '>')
            {
                stringbuffer.append("&gt;");
                continue;
            }
            if (ch == '&')
            {
                stringbuffer.append("&#38;");
                continue;
            }
            if (ch == '"')
                stringbuffer.append("&#34;");
            else
                stringbuffer.append(ch);
        }

        return stringbuffer.toString();
    }

    public static String dealCompileInfo(String info)
    {
        if (info == null)
            return "&nbsp;";
        StringBuffer stringbuffer = new StringBuffer();
        info = info.replaceAll("[cCdD]:\\\\[tT][eE][mM][pP]\\\\", "");
        info = info.replaceAll("[cCdD]:[\\\\/]judgeonline[\\\\/]", "");
        for (int i = 0; i < info.length(); i++)
        {
            char ch = info.charAt(i);
            if (ch == '\n')
            {
                stringbuffer.append("<br>");
                continue;
            }
            if (ch == '<')
            {
                stringbuffer.append("&lt;");
                continue;
            }
            if (ch == '>')
            {
                stringbuffer.append("&gt;");
                continue;
            }
            if (ch == '&')
            {
                stringbuffer.append("&amp;");
                continue;
            }
            if (ch == ' ')
            {
                stringbuffer.append("&nbsp;");
                continue;
            }
            if (ch == '\t')
                stringbuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
            else
                stringbuffer.append(ch);
        }

        return stringbuffer.toString();
    }

    public static String getReplyString(String input)
    {
        if (input == null || input.length() == 0)
            return "";
        BufferedReader bufferedreader = new BufferedReader(new StringReader(input));
        String strRet = "";
        try
        {
            String strLine = bufferedreader.readLine();
            while (strLine != null)
            {
                if (strLine.length() < 2 || !strLine.substring(0, 2).equals("> "))
                    strRet = strRet + "> " + strLine + "\n";
                strLine = bufferedreader.readLine();
            }
        } catch (IOException ex)
        {
        }
        return strRet;
    }

    public static String gethtmlPreFormattedString(String html)
    {
        if (html == null || html.equals(""))
            return "";
        int nPos = html.indexOf("<pre>");
        if (nPos < 0)
            nPos = html.indexOf("<PRE>");
        if (nPos < 0)
            nPos = html.indexOf("</");
        if (nPos < 0)
            nPos = html.indexOf("<p>");
        if (nPos < 0)
            nPos = html.indexOf("<P>");
        if (nPos < 0)
            return htmlEncode(html);
        else
            return html;
    }

    public static String gethtmlFormattedString(String html)
    {
        if (html == null)
            return "&nbsp;";
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < html.length(); i++)
        {
            char ch = html.charAt(i);
            if (ch == '\n')
                stringbuffer.append("<br>");
            else if (ch != '\r')
                stringbuffer.append(ch);
        }

        return stringbuffer.toString();
    }

    public static String formatInputOutput(String input)
    {
        if (input == null)
            return "&nbsp;";
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < input.length(); i++)
        {
            char ch = input.charAt(i);
            if (ch == '\n')
                stringbuffer.append("<br>");
            if (ch == ' ')
                stringbuffer.append("");
            else
                stringbuffer.append(ch);
        }

        return stringbuffer.toString();
    }

    public static String encodeUrl(String url)
    {
        if (url == null)
            return null;
        else
            return url.replaceAll("&", "%26");
    }

    public static String fixPath(String s)
    {
        if (s == null)
            return File.separator;
        String fix;
        if (File.separator.equals("/"))
            fix = StringsReplace(s, "\\", File.separator);
        else
            fix = StringsReplace(s, "/", File.separator);
        return fix.endsWith(File.separator) ? fix : fix + File.separator;
    }

    public static String StringsReplace(String s, String s1, String s2)
    {
        String s3 = "";
        int nPos1 = 0;
        int nPos2 = s.indexOf(s1);
        if (nPos2 == -1)
            return s;
        for (; nPos2 != -1; nPos2 = s.indexOf(s1, nPos1))
        {
            s3 = s3 + s.substring(nPos1, nPos2) + s2;
            nPos1 = nPos2 + s1.length();
        }

        return s3 + s.substring(nPos1);
    }

    /**
     *
     * @param s
     * @return
     */
    public static String md5Convert(String s)
    {
        char hexChars[] =
        {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try
        {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++)
            {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 检测用户提交的email格式是否正确
     *
     * @param email
     *            用户提交的email值
     * @return boolean 如用户输入格式正确,则返回true,否则返回false
     * @since 1.0
     */
    public static boolean checkEmail(String email)
    {
        if (email == null || "".equals(email))
            return false;
        StringBuffer invaldMessage = new StringBuffer("");
        String regex = "^[a-zA-Z0-9_]+@([a-zA-Z0-9][a-zA-Z0-9]+.([a-zA-Z0-9.]*)[a-zA-Z]{2,5})$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);

        if (!m.find())
            return false;
        return true;
    }

    public static String getFileName(String filePath)
    {
        if (filePath == null)
            return null;
        filePath = Tool.StringsReplace(Tool.StringsReplace(filePath, "/", File.separator), "\\", File.separator);
        int nPos = filePath.lastIndexOf(File.separator);
        if (nPos >= 0)
            return filePath.substring(nPos + 1);
        else
            return filePath;

    }

    public static void zip(String zipFileName, String inputFile) throws Exception
    {
        zip(zipFileName, new File(inputFile));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception
    {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                zipFileName), "GBK");
        zip(out, inputFile, "");//递归压缩方法
        out.close();
    }

    /**
     * 递归压缩方法
     * @param out   压缩包输出流
     * @param f     需要压缩的文件
     * @param base 压缩的路径
     * @throws Exception
     */
    private static void zip(ZipOutputStream out, File f, String base) throws Exception
    {
        if (f.isDirectory())
        {   // 如果是文件夹，则获取下面的所有文件
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++)
                zip(out, fl[i], base + fl[i].getName());
        } else
        {   // 如果是文件，则压缩
            out.putNextEntry(new ZipEntry(base)); // 生成下一个压缩节点
            FileInputStream in = new FileInputStream(f);   // 读取文件内容
            int b;
            while ((b = in.read()) != -1)
                out.write(b);   // 写入到压缩包
            in.close();
        }
    }
}
