import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/*
 * 陈湘骥于 Jun 26, 2008 创建
 *
 */

public class ExportDataFileCheck
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // Check parameters
        if (args.length < 1)
        {
            System.err.println("usage: java ExportDataFileCheck <ExportDataFileName>");
            return;
        }
        //        // Get parameters
        String fileName = args[0];
        File file = new File(fileName);
        if (!file.exists())
        {
            System.err.println("File:" + file.getAbsolutePath() + " not exists.");
            return;
        }
        File file2 = new File("c:/temp/a_.log");

        // scan the file, log something for the next step.
        boolean needHandle = false;
        try
        {
            Hashtable columns = new Hashtable();
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF8");
            BufferedReader reader = new BufferedReader(read);
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file2), "UTF8");
            PrintWriter writer = new PrintWriter(write);
            String line, line2;
            String tableName = "", curTableName = "", column = "";
            int nPos = -1, nPos2 = -1;
            int start = 0, change = 0;
            String changeLine = "";
            Integer counter = 0;

            while ((line = reader.readLine()) != null)
            {
                counter++;
                if (line.charAt(0) != ' ')
                    continue;
                if (line.trim().equals(""))
                    continue;
                nPos = line.indexOf('<');
                if (nPos < 0)
                {
                    System.err.println("Format error: " + line);
                    return;
                }
                nPos2 = line.indexOf(' ', nPos + 1);
                if (nPos2 < 0)
                {
                    System.err.println("Format error: " + line);
                    return;
                }
                tableName = line.substring(nPos + 1, nPos2);
                if (!curTableName.equals(tableName))
                {//表名不同,表示出现同类表的第一项
                    //先处理上一表
                    if (start != change)
                    {
                        System.out.println(curTableName);
                        System.out.println(start);
                        System.out.println(change);
                        System.out.println("");
                        System.out.println("");

                        writer.println(start);
                        writer.println(change);
                        writer.println(changeLine);

                        needHandle = true; //设置需要处理标志
                    }
                    start = change = counter;
                    columns.clear();
                    curTableName = tableName;
                    while (nPos2 > 0)
                    {
                        nPos = line.indexOf('=', nPos2 + 1);
                        column = line.substring(nPos2 + 1, nPos);
                        columns.put(column, counter);
                        //System.out.println("       " + column);
                        nPos2 = line.indexOf('"', nPos + 1);
                        if (nPos2 < 0)
                        {
                            System.err.println("Format error: " + line);
                            return;
                        }
                        nPos2 = line.indexOf('"', nPos2 + 1);
                        if (nPos2 < 0)
                        {
                            System.err.println("Format error: " + line);
                            return;
                        }
                        nPos2 = line.indexOf(' ', nPos2 + 1);
                    }
                }
                else
                {//表名相同,比较
                    boolean flagChange = false;
                    while (nPos2 > 0)
                    {
                        nPos = line.indexOf('=', nPos2 + 1);
                        column = line.substring(nPos2 + 1, nPos);
                        if (columns.containsKey(column))
                            columns.put(column, counter);
                        else
                        {
                            flagChange = true;
                            columns.put(column, counter);
                        }
                        nPos2 = line.indexOf('"', nPos + 1);
                        if (nPos2 < 0)
                        {
                            System.err.println("Format error: " + line);
                            return;
                        }
                        nPos2 = line.indexOf('"', nPos2 + 1);
                        if (nPos2 < 0)
                        {
                            System.err.println("Format error: " + line);
                            return;
                        }
                        nPos2 = line.indexOf(' ', nPos2 + 1);
                    }
                    if (flagChange)
                    {
                        Iterator it = columns.keySet().iterator();
                        while (it.hasNext())
                        {
                            String key = (String) it.next();
                            Integer value = (Integer) columns.get(key);
                            if (!value.equals(counter))
                            {
                                System.out.println("Line " + counter + " was");
                                System.out.println(line);

                                line = line.substring(0, line.length() - 2) + " " + key + "=\"\"/>";

                                System.out.println("Now is changed to");
                                System.out.println(line);
                                System.out.println();
                            }
                        }
                        change = counter; // 设定需要交换的行
                        changeLine = line;
                    }
                }
            }
            if (start != change)
            {
                writer.println(start);
                writer.println(change);
                writer.println(changeLine);
            }
            writer.close();
            write.close();

            reader.close();
            read.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (!needHandle)
        {
            System.out.println("文档不需要处理!");
            return;
        }

        File file3 = new File("c:/temp/out_.xml");
        // 第二遍处理文档
        try
        {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF8");
            BufferedReader reader = new BufferedReader(read);
            InputStreamReader readLog = new InputStreamReader(new FileInputStream(file2), "UTF8");
            BufferedReader readerLog = new BufferedReader(readLog);
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file3), "UTF8");
            PrintWriter writer = new PrintWriter(write);
            String line, line2;
            String tableName = "", curTableName = "", column = "";
            int nPos = -1, nPos2 = -1;
            int nCounter = 0;

            line = readerLog.readLine();
            nPos = Integer.parseInt(line);
            line = readerLog.readLine();
            nPos2 = Integer.parseInt(line);
            line2 = readerLog.readLine();
            while ((line = reader.readLine()) != null)
            {
                nCounter++;
                if (nCounter == nPos)
                {
                    writer.println(line2);
                    writer.println(line);
                    while ((line = reader.readLine()) != null)
                    {
                        nCounter++;
                        if (nCounter == nPos2)
                            break;
                        writer.println(line);
                    }
                    System.out.println("Hande " + nPos + " vs " + nPos2);
                    System.out.println();
                    line = readerLog.readLine();
                    if (line == null)
                        break;
                    nPos = Integer.parseInt(line);
                    line = readerLog.readLine();
                    nPos2 = Integer.parseInt(line);
                    line2 = readerLog.readLine();
                }
                else
                    writer.println(line);
            }
            while ((line = reader.readLine()) != null)
                writer.println(line);
            writer.close();
            write.close();

            reader.close();
            read.close();

            readerLog.close();
            readLog.close();

            String temp = file.getAbsolutePath();
            file.renameTo(new File(file.getAbsolutePath() + ".bak"));
            file3.renameTo(new File(temp));
            file2.delete();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
