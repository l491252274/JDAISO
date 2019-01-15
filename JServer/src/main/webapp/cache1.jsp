<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*" %>

<html>
<body>

no cache date: <%= new Date() %><p>

<cache:cache time="30">
30 refresh cache date: <%= new Date() %>
</cache:cache>
<br>
<cache:cache key="testcache">
man refresh date: <%= new Date() %><p>
</cache:cache>
<a href="cache2.jsp">man refresh</a>
</body>
</html>