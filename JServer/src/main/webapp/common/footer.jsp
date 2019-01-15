<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.*"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.service.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>
        <BASE target="_top"/>
        <%@ include file="/common/meta.jsp" %>
        <title><fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

        <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/swfobject.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/chart.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/dtree/dtree.js'/>"></script>
	    <script language=Javascript>
	    function time() {
	        t_div = document.getElementById('showtime');
	        var now = new Date()
	        t_div.innerHTML = now.getFullYear();
	    }
	    </script>
   	</head>

	<body onload="time();" style="background:#f0f0f0; background-image: url(/${appConfig['project.name']}/styles/${appConfig['csstheme']}/images/footerbg.jpg);background-repeat:repeat-x;">
		<div id="footer">
                    <span class="left"><fmt:message key="webapp.version"/> |
                        <span id="validators">
                            <a href="mailto:checkie_chen@scau.edu.cn"><fmt:message key="designer"/></a> |
                        </span>
                    </span>
                    <span class="right">
                	 &copy; 2009-${svrYear} <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
                    </span>
                </div>
        </body>
</html>