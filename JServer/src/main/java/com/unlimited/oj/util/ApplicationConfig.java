/**
 * Modify by Checkie on 2008.07.28
 * Code by Checkie on 2006.06.05
 */
package com.unlimited.oj.util;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unlimited.oj.Constants;

public class ApplicationConfig
{

    private static Properties properties = null;
    private static String designerName = "Chen Xiang-ji";
    private static String designerEmail = "checkie_chen@scau.edu.cn";
    private static String developerName = "Chen Xiang-ji";
    private static String systemInfo = "";
    private static String defaultTitle = "ACM SCAU";
    private static String runShell = null;
    private static String comShell = null;
    private static String onlineShell = null;
    private static String clozeShell = null;
    private static String matchJudgerShell = null;
    private static String sameJudgerCmd = null;
    private static String playShell = null;
    private static String gameHostCmd = null;


    //
    private static boolean init = false;
    private static boolean ready = false;
    private static String configeFileName;
    private static final Log log = LogFactory.getLog(ApplicationConfig.class);
    private static String applicationRootPath;

    public ApplicationConfig()
    {
    }

    public static void init(String configRootPath)
    {
        ApplicationConfig.applicationRootPath = configRootPath;

        init = true;
        loadProperties();
        ready = true;

        log.debug("ServerConfig.loadProperties(): Properties is loaded.");
    }

    public static void init(String configRootPath, boolean force)
    {
        if(force)
            ready = false;
        init(configRootPath);
        if(force)
        {
            CacheType.init(true);
        }

        log.debug("ServerConfig.loadProperties(): Properties is loaded.");
    }

    private static synchronized void loadProperties()
    {
        if (ready)
            return;
        try
        {
            properties = new Properties();
            try
            {
                String sOS = System.getProperty("os.name").toLowerCase();
                configeFileName = Tool.fixPath(Tool.fixPath(applicationRootPath) + "WEB-INF/classes/parameters") + 
                        (sOS.indexOf("windows")>=0?"applicationConfig-windows.properties":"applicationConfig-linux.properties");
                properties.load(new FileInputStream(configeFileName));
                log.info("Load " + configeFileName + " Success.");
            } catch (Exception ex)
            {
                log.fatal("Load config-file(" + configeFileName + ") failure.");
            }

            runShell = properties.getProperty("RunShell");
            comShell = properties.getProperty("ComShell");
            onlineShell = properties.getProperty("OnlineShell");
            clozeShell = properties.getProperty("ClozeShell");
            playShell = properties.getProperty("PlayShell");
            sameJudgerCmd = properties.getProperty("SameJudgerCmd");
            gameHostCmd = properties.getProperty("GameHostCmd");
            defaultTitle = properties.getProperty("DefaultTitle");

            matchJudgerShell = properties.getProperty("MatchJudgerShell");
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static String getValue(String name)
    {
        if (ready)
        {
            return properties.getProperty(name);
        } else
        {
            log.debug("ServerConfig not ready. getValue(" + name + ") failure.");
            return null;
        }
    }

    public static Properties getProperties()
    {
        return properties;
    }

    public static String getRunShell()
    {
        return runShell;
    }

    public static String getComShell()
    {
        return comShell;
    }

    public static String getOnlineShell()
    {
        return onlineShell;
    }

    public static String getClozeShell()
    {
        return clozeShell;
    }

    public static Timestamp getSystemTime()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public static boolean isReady()
    {
        return ready;
    }

    public static String getDesignerName()
    {
        return designerName;
    }

    public static String getDesignerEmail()
    {
        return designerEmail;
    }

    public static String getDeveloperName()
    {
        return developerName;
    }

    public static String getSystemInfo()
    {
        return systemInfo;
    }

    public static void setSystemInfo(String systemInfo)
    {
        ApplicationConfig.systemInfo = systemInfo;
    }

    public static String getDefaultTitle()
    {
        return defaultTitle;
    }

    public static String getApplicationRootPath()
    {
        return applicationRootPath;
    }

    public static String getMatchJudgerShell()
    {
        return matchJudgerShell;
    }

    public static String getSameJudgerCmd()
    {
        return sameJudgerCmd;
    }

    public static String getPlayShell()
    {
        return playShell;
    }

    public static void setPlayShell(String playShell)
    {
        ApplicationConfig.playShell = playShell;
    }

    public static String getGameHostCmd()
    {
        return gameHostCmd;
    }

    public static void setGameHostCmd(String gameHostCmd)
    {
        ApplicationConfig.gameHostCmd = gameHostCmd;
    }
}