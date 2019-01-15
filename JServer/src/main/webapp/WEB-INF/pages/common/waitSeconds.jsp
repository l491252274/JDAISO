<%@ include file="/common/taglibs.jsp"%>

<html> 
<head>
    <meta name="heading" content=""/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/layout-1col.css'/>" />
</head>
<script type="text/javascript" language="javascript">
    function init()
    {
        var i=parseInt(document.getElementById('second').value);
        countdown(i);
    }
    function countdown(n)
    {
        if(n==0)
            goToUrl();
        else
        {
            document.getElementById('secondLabel').innerHTML=(n--);
            setTimeout("countdown('" + n + "');", 1010);
        }
    }
    function goToUrl()
    {
        var url = document.getElementById('url').value;
        window.location=url;
    }
    if(a)
    setTimeout("init();", 2000);
</script>
<%
    pageContext.setAttribute("url", session.getAttribute("_url"));
    pageContext.setAttribute("second", session.getAttribute("_waitForSeconds"));
%>
<body>
        <input name="url" value="${url}" id="url" type="hidden">
        <input name="second" value="${second}" id="second" type="hidden">
    <h2><fmt:message key="waitForSecond" />&nbsp;<span id="secondLabel" style="font-size: 24pt;color:blue;"></span>&nbsp;<fmt:message key="second"/>, <fmt:message key="autoRedirect"/></h2>
</body>
</html>
