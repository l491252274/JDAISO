<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
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
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>

        <%@ include file="/common/meta.jsp" %>
        <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

        <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/swfobject.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/chart.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/dtree/dtree.js'/>"></script>
        <decorator:head/>
	</head>

	<body onload="init();">
		<div id="container">
		
			<div id="sitename">
            	<jsp:include page="/common/header.jsp"/>
			</div>
			
            <%
                Object obj = session.getAttribute("oj_menu");
                pageContext.setAttribute("oj_menu", obj);
                obj = session.getAttribute("oj_submenu");
                pageContext.setAttribute("oj_submenu", obj);
             %>
            <c:set var="currentMenu" scope="request">${oj_menu}</c:set>
            <c:set var="currentSubmenu" scope="request">${oj_submenu}</c:set>
		    <div id="mainmenu">
	            <jsp:include page="/common/menu.jsp"/>
		    </div>
			 
			<div id="wrap">
				<div id="leftside">
	            	<jsp:include page="/common/lefter.jsp"/>
				</div>
			
				<div id="rightside">
	            	<jsp:include page="/common/righter.jsp"/>
				</div>

				<div id="content">
					<table height="100%" border="0" style="width: 98%;">
					    <tbody><tr>
					        <td valign="top">	
				                <%@ include file="/common/messages.jsp" %>
			       	        	<h1><decorator:getProperty property="meta.heading"/></h1>
				    	        <decorator:body/>
						    </td>
					        <td valign="middle" class="right_constriction"><a href="javascript:leftState();">
					            <img style="margin:0px;padding:0px;" border="0" align="right"  src="/${appConfig['project.name']}/styles/andreas09/images/constriction_close.jpg" id="constrictionIMG"/></a>
					        </td>
				   	    </tr>
					</tbody></table>
				</div>
				<div class="clearingdiv">&nbsp;</div>
			</div>
		</div>
		
        <div id="footer" class="clearfix">
            <jsp:include page="/common/footer.jsp"/>
        </div>
	</body>
</html>