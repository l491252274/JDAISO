<%@ include file="/common/taglibs.jsp"%>
<html>
<body>

cache refreshed<p>

<cache:flush key="testcache" scope="application"/>

<a href="cache1.jsp">back</a>

</body>
</html>