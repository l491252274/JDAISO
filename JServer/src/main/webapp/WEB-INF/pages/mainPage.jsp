<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.enums.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.*"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.unlimited.oj.dao.support.Page"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content="<fmt:message key='BBS'/>"/>
    <meta name="menu" content="MainMenu"/>
</head>
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>


    <table width="95%" style="margin: 0pt -2pt">
    <tr>
        <td>

        </td>
        <td width=20px>
        </td>
    </tr>
    </table>


<div class="separator"></div>
