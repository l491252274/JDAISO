<%@ include file="/common/taglibs.jsp"%>
 
<html>
<head>
    <meta name="heading" content=""/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/layout-1col.css'/>" />
</head>

<body>
    Download: <a href="${downloadPath}">Click here and download</a>
    <s:form id="form2" name="form" action="deleteDownloadFiles" method="post">
        <s:hidden key="downloadFolder"/>
        <input value="<fmt:message key='button.delete'/>" name="submit" type="submit">
    </s:form>
</body>
</html>
