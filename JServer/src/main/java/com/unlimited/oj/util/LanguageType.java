/*
 * Develop by Checkie on 2006.07.18
 */

package com.unlimited.oj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LanguageType
{
    private static boolean m_isInit = false;
    private static int m_nLangCount = 0;
    private static int m_arrLanguageExtMemory[] = null;
    private static String arrDesc[] = getLanguageDescs();
    private static String m_arrLanguageExts[] = null;
    private static String m_arrCompileCmd[] = null;
    private static String m_arrRunCmd[] = null;
    private static String m_arrLanguageExes[] = null;
    private static int m_arrCompileStreamOrder[] = null;
    private static int m_arrLanguageTimeFactor[] = null;
    private static int m_arrLanguageExtTime[] = null;
    private static final Log log = LogFactory.getLog(LanguageType.class);

    public LanguageType()
    {
    }

    public static int getOrder(int i)
    {
        init();
        if (i < m_nLangCount)
            return m_arrCompileStreamOrder[i];
        else
            return -1;
    }

    public static int getTimeFactor(int i)
    {
        init();
        return m_arrLanguageTimeFactor[i];
    }

    public static int getExtMemory(int i)
    {
        init();
        if (i < m_nLangCount)
            return m_arrLanguageExtMemory[i];
        else
            return -1;
    }

    public static int getExtTime(int i)
    {
        return m_arrLanguageExtTime[i];
    }

    public static String getExe(int i)
    {
        return m_arrLanguageExes[i];
    }

    public static String[] getDescriptions()
    {
        init();
        return arrDesc;
    }

    public static int getLangs()
    {
        init();
        return m_nLangCount;
    }

    public static void init(boolean force)
    {
        if(force)
            m_isInit=false;
        init();
    }

    private static void init()
    {
        if (!m_isInit)
        {
            log.debug("LanguageType.init() Start.");/*����*/
            m_nLangCount = getLangCount();
            m_arrLanguageExts = getLanguageExts();
            m_arrLanguageExtMemory = getLanguageExtMemory();
            m_arrCompileStreamOrder = getCompileStreamOrder();
            m_arrCompileCmd = getCompileCmd();
            m_arrRunCmd = getRunCmd();
            m_arrLanguageExes = getLanguageExes();
            m_arrLanguageTimeFactor = getLanguageTimeFactor();
            m_arrLanguageExtTime = getLanguageExtTime();
            m_isInit = true;
            log.debug("LanguageType.init() Done.");/*����*/
        }
    }

    private static int getLangCount()
    {
        return Integer.parseInt(ApplicationConfig.getValue("LangCount"));
    }

    private static String[] getCompileCmd()
    {
        String as[] = new String[m_nLangCount];
        for (int i = 0; i < m_nLangCount; i++)
            as[i] = ApplicationConfig.getValue(arrDesc[i] + "CompileCmd");

        return as;
    }

    private static String[] getRunCmd()
    {
        String as[] = new String[m_nLangCount];
        for (int i = 0; i < m_nLangCount; i++)
            as[i] = ApplicationConfig.getValue(arrDesc[i] + "RunCmd");

        return as;
    }

    private static int[] getLanguageTimeFactor()
    {
        String s = ApplicationConfig.getValue("LanguageTimeFactor");
        String as[] = s.split(",");
        int ai[] = new int[as.length];
        for (int i = 0; i < as.length; i++)
            ai[i] = Integer.parseInt(as[i]);

        return ai;
    }

    /*用于界面中列表选择*/
    public static List<Map<String, String>> getLanguageListMap()
    {
        String[] sDes = getLanguageDescs();
        List<Map<String, String>> languageListMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < sDes.length; i++)
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", new Integer(i).toString());
            map.put("value", sDes[i]);
            languageListMap.add(map);
        }
        return languageListMap;
    }

    private static int[] getLanguageExtTime()
    {
        String s = ApplicationConfig.getValue("LanguageExtTime");
        String as[] = s.split(",");
        int ai[] = new int[as.length];
        for (int i = 0; i < as.length; i++)
            ai[i] = Integer.parseInt(as[i]);

        return ai;
    }

    private static int[] getCompileStreamOrder()
    {
        String s = ApplicationConfig.getValue("CompileStreamOrder");
        String as[] = s.split(",");
        int ai[] = new int[as.length];
        for (int i = 0; i < as.length; i++)
            ai[i] = Integer.parseInt(as[i]);

        return ai;
    }

    private static String[] getLanguageExes()
    {
        String s = ApplicationConfig.getValue("LanguageExes");
        String as[] = s.split(",");
        return as;
    }

    private static int[] getLanguageExtMemory()
    {
        String s = ApplicationConfig.getValue("LanguageExtMemory");
        String as[] = s.split(",");
        int ai[] = new int[as.length];
        for (int i = 0; i < as.length; i++)
            ai[i] = Integer.parseInt(as[i]);

        return ai;
    }

    private static String[] getLanguageDescs()
    {
        String s = ApplicationConfig.getValue("LanguageDescs");
        String as[] = s.split(",");
        return as;
    }

    private static String[] getLanguageExts()
    {
        String s = ApplicationConfig.getValue("LanguageExts");
        String as[] = s.split(",");
        return as;
    }

    public static boolean isLanguage(int i)
    {
        init();
        return i >= 0 && i < m_nLangCount;
    }

    public static String getDesc(int i)
    {
        if(0<=i && i<getLangCount())
            return arrDesc[i];
        return "";
    }

    public static String getExt(int i)
    {
        init();
        return m_arrLanguageExts[i];
    }

    public static String getRunCmd(String path, String name, int i)
    {
        init();
        String strCmd = m_arrRunCmd[i];
        path = TransPathName(path);
        if (strCmd == null)
        {
            strCmd = path + name + ".exe";
        }
        else
        {
            strCmd = Tool.StringsReplace(strCmd, "%PATH%", path);
            strCmd = Tool.StringsReplace(strCmd, "%NAME%", name);
            strCmd = Tool.StringsReplace(strCmd, "%EXT%", m_arrLanguageExts[i]);
            strCmd = Tool.StringsReplace(strCmd, "%EXE%", m_arrLanguageExes[i]);
        }
        return strCmd;
    }

    public static String getCompileCmd(String path, String name, int nExe)
    {
        init();
        path = TransPathName(path);
        String strCmd = m_arrCompileCmd[nExe];
        strCmd = Tool.StringsReplace(strCmd, "%PATH%", path);
        strCmd = Tool.StringsReplace(strCmd, "%NAME%", name);
        strCmd = Tool.StringsReplace(strCmd, "%EXT%", m_arrLanguageExts[nExe]);
        strCmd = Tool.StringsReplace(strCmd, "%EXE%", m_arrLanguageExes[nExe]);
        return strCmd;
    }

    private static String TransPathName(String s)
    {
        return s.endsWith(java.io.File.separator) ? s : s + java.io.File.separator;
    }

}