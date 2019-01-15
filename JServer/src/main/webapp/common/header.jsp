<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.util.*"%>
<c:set var="config" value="${configuration}"/>
<%
    String v="-1";
	if(ApplicationConfig.getValue("TopMessageRefreshTime")!=null)
		v = ApplicationConfig.getValue("TopMessageRefreshTime");
	pageContext.setAttribute("refreshTime", v);
%>

<META http-equiv="refresh" content="${refreshTime};"> 


<script>
	var bWidth=0;
	//alert(getCookie("leftState"));
	function init(){
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="10px";
	    document.getElementById("content").style.margin="0px 20px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_open.jpg'/>"
	    document.getElementById("profile").style.display="none";
	}

	function leftState(){
	  var bWidth=parseInt(document.documentElement.clientWidth);
	  if(document.getElementById("constrictionIMG").src.indexOf("constriction_open.jpg")==-1)
	  {
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="10px";
	    document.getElementById("content").style.margin="0px 20px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_open.jpg'/>"
	    document.getElementById("profile").style.display="none";
	    deleteCookie("leftState");
	    setCookie("leftState", "0");
	  }
	  else
	  {
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="190px";
	    document.getElementById("content").style.margin="0px 190px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_close.jpg'/>"
	    document.getElementById("profile").style.display="";
	    deleteCookie("leftState");
	    setCookie("leftState", "1");
	  }
	}
</script>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
        <head>
        <base target="left"/>
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>

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
	</head>
        
    <%
    	String msg = ApplicationConfig.getValue("TopMessage");
    	if(msg==null) msg="";
    	else
    		msg=new String(msg.getBytes("ISO-8859-1"),"gb2312");
    	String nowIP = pageContext.getRequest().getRemoteAddr();
    	if(nowIP.startsWith("172.26."))
    	{
    		String msg2 = ApplicationConfig.getValue("TopMessageLocal");
    		if(msg2!=null) msg+=new String(msg2.getBytes("ISO-8859-1"),"gb2312");
    	}
    	pageContext.setAttribute("msg", msg);    	
    %>
	<body onload="init();">
		<div id="topcontainer">
					<div id="sitename">
                            <c:choose>
                               <c:when test="${pageContext.request.locale.language != 'en'}">
                                <div id="switchLocale"><a href="<c:url value='/?locale=en'/>" target="_top">ENGLISH</a></div>
                               </c:when>
                               <c:otherwise>
                                <div id="switchLocale" style="padding-top:-10px;"><a href="<c:url value='/?locale=zh'/>" target="_top">CHINESE</a></div>
                               </c:otherwise>
                            </c:choose>
                            <div id="branding" style="position:absolute ; top:-10px;">
                                    <table width="100%">
                                    <tr>
                                        <td style="letter-spacing: .5em; width:1px;">
                                        	<h1><font color=white><fmt:message key="webapp.logo"/></font></h1>
                                        </td>
                                    	<td width="800px" valign="bottom">
                                    		<h2 style="padding-top:10px;"><font color=white><fmt:message key="webapp.name"/></font></h2>
                                    	</td>
                                    	<td width="400px" valign="bottom">
                                    		<font color=white style="font-weight:bold;" size=2><fmt:message key="webapp.tagline"/></font>
                                    	</td>
                                    </tr>
                                    </table>
                                </div>
                            <div style="position:absolute ; top:50px;">
								<c:if test="${msg!=''}">                                
									<marquee scrollamount="3" direction="left" onmouseover="this.stop()" onmouseout="this.start()">
									${msg}
									</marquee > 
								</c:if>
							</div> 
                    </div>
                    <div id="mainmenu">
                            <jsp:include page="/common/menu.jsp"/>
                    </div>
                <%-- Put constants into request scope --%>
                <appfuse:constants scope="request"/>
                    <div id="wrap"></div>
            </div>
        </body>
</html>