<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>

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
	<script type="text/javascript" src="<c:url value='/flash/chart/js/swfobject.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/flash/chart/js/chart.js'/>"></script>
        <decorator:head/>
	</head>

	<body>
		<div id="container"> 
			<div id="wrap">
				<div id="content" style="margin:100px 0px;">
					<table height="100%" border="0" style="width: 98%;">
					    <tbody><tr>
					        <td valign="top">	
				                <%@ include file="/common/messages.jsp" %>
			       	        	<h1><decorator:getProperty property="meta.heading"/></h1>
				    	        <decorator:body/>
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