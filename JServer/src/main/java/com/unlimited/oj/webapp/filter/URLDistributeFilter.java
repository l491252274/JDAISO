/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unlimited.oj.webapp.filter;

import com.unlimited.oj.util.ApplicationConfig;
import com.unlimited.oj.util.Tool;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class Distribute_Rule
{
	public String key;
	public String IP;

	public Distribute_Rule(String key, String IP)
	{
		this.key = key;
		this.IP = IP;
	}
}

class Distribute_Range
{
	public long low;
	public long high;
	public String IP;

	public Distribute_Range(long low, long high, String iP)
	{
		this.low = low;
		this.high = high;
		IP = iP;
	}
}

public class URLDistributeFilter implements Filter
{
	private static final Log log = LogFactory.getLog(URLDistributeFilter.class);
	private static HashMap<String, String> mapHashURL = new HashMap<String, String>();
	private static List<Distribute_Rule> rules = new LinkedList<Distribute_Rule>();
	private static List<Distribute_Range> ranges = new LinkedList<Distribute_Range>();

	private static long IPtoLong(String IP)
	{
		String[] tmp = IP.split("\\.");
		long ret = 0;
		try
		{
			int i;
			for (i = 0; i < tmp.length; i++)
				ret = ret *256 + Long.parseLong(tmp[i]);
			for (i = tmp.length; i < 4; i++)
				ret = ret *256;
		}catch(Exception e){}
		return ret;
	}

	public static void initFilter()
	{
		mapHashURL.clear();
		rules.clear();
		ranges.clear();
		try
		{
			String configeFileName = "";
			Properties properties = ApplicationConfig.getProperties();
			Enumeration<String> item = (Enumeration<String>) properties
					.propertyNames();
			while (item.hasMoreElements())
			{
				String name = item.nextElement();
				if (name.startsWith("DRule_"))
				{
					String from = name.substring(6);
					String to = properties.getProperty(name);
					rules.add(new Distribute_Rule(from, to));
				} else if (name.startsWith("DMap_"))
				{
					String from = name.substring(5);
					String to = properties.getProperty(name);
					mapHashURL.put(from, to);
				} else if (name.startsWith("DRange_"))
				{
					String[] tmp = name.substring(7).split("\\|");
					if (tmp.length > 1)
					{
						long low = IPtoLong(tmp[0]);
						long high = IPtoLong(tmp[1]);
						String to = properties.getProperty(name);
						log.info("DRange:" + tmp[0]+" to "+ low + "," + tmp[1] + " to " + high + " IP:" + to);
						ranges.add(new Distribute_Range(low, high, to));
					}
				}
			}

		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public String getDestinationIp(ServletRequest request)
	{
		String localIp = request.getLocalAddr();
		String remoteIp = request.getRemoteAddr();
		//System.out.println(remoteIp);
		// match maps
		if (mapHashURL.containsKey(remoteIp))
			return mapHashURL.get(remoteIp);
		// match ranges
		long longip = IPtoLong(remoteIp);

		for (Distribute_Range item : ranges)
		{
			if (longip >= item.low && longip <= item.high)
				return item.IP;
		}
		// match rules
		for (Distribute_Rule item : rules)
		{
			// System.out.println(item.key);
			String key = item.key;
			if (remoteIp.startsWith(key))
				return item.IP;
		}
		return localIp;
	}

	// 重写invoke()方法
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
	{
		Object lhbt = com.unlimited.oj.util.ApplicationConfig
				.getValue("useURLDistributeFilter");
		if (lhbt == null || !((String) lhbt).equalsIgnoreCase("true"))
		{
			try
			{
				chain.doFilter(request, response);
			} catch (Exception e)
			{
			}
			return;
		}
		String url = ((HttpServletRequest) request).getRequestURI(); // 获得调用URL
		try
		{
			if (url.indexOf("login") < 0)
				chain.doFilter(request, response);
			else
			{// 只允许在指定的服务器上login
				long procTime = System.currentTimeMillis();
				int port = ((HttpServletRequest) request).getLocalPort();
				String localIp = request.getLocalAddr();
				// logger.log(Level.INFO, url + " 开始执行");
				String destIp = getDestinationIp(request);
				if (destIp.equals(localIp))
				{
					chain.doFilter(request, response);
				}
				else
				{
					log.info("Transfer " + localIp +" to " + destIp);
					if (destIp.contains("/"))
						((HttpServletResponse) response).sendRedirect("http://"
								+ destIp);
					else if (port == 80 || destIp.contains(":"))
						((HttpServletResponse) response).sendRedirect("http://"
								+ destIp + url);
					else
						((HttpServletResponse) response).sendRedirect("http://"
								+ destIp + ":" + port + url);
				}
			}
		} catch (Exception e)
		{
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		initFilter();
	}

	public void destroy()
	{
	}
}
