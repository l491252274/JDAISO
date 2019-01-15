<%@ include file="/common/taglibs.jsp"%>
<head>
    <meta name="menu" content="${param.menu}"/>
    <meta name="submenu" content="${param.submenu}"/>
</head>
<c:set var="buttons">
   <table><tr>
   		<td>
		    <div align=right><input type="button" 
		        onclick="location.href='javascript:history.go(-1)'"
		        value="<fmt:message key="button.history.goback"/>"/>
		    </div>
   		</td>
   		<td>
		    <div align=right><input type="button" 
		        onclick="location.href='<c:url value="/common/editProblem.html?id=${problem.id}&menu=${param.menu}&submenu=${param.submenu}"/>'"
		        value="<fmt:message key="button.editProblem"/>"/>
		    </div>
   		</td>
   	</tr></table>
</c:set>

<center><h1><s:property value="problem.id"/>&nbsp;<s:property value="problem.title"/></h1></center>
<p align="center">Time Limit:<s:property value="problem.timeLimit"/>MS&nbsp; Memory Limit:<s:property value="problem.memoryLimit"/>K<br>
Total Submit:<s:property value="problem.submit"/> Accepted:<s:property value="problem.accepted"/>
</p>
<p align="center">Language: not limited<br>
</p><p><h1>Description</h1>
<p><pre><s:property value="problem.description"/></pre>

<c:if test="${problem.input != ''}">
	<p align="left"><h1>Input</h1>
	<p><s:property value="problem.input"/></p>
</c:if>
<c:if test="${problem.output != ''}">
	<p align="left"><h1>Output</h1>
	<p><s:property value="problem.output"/></p>
</c:if>
<c:if test="${problem.sampleInput != ''}">
	<p align="left"><h1>Sample Input</h1>
	<pre><s:property value="problem.sampleInput"/></pre>
</c:if>
<c:if test="${problem.sampleOutput != ''}">
	<p align="left"><h1>Sample Output</h1>
	<pre><s:property value="problem.sampleOutput"/></pre>
</c:if>
<c:if test="${problem.hint != ''}">
	<p align="left"><h1>Hint</h1>
	<s:property value="problem.hint"/>
</c:if>
<c:if test="${problem.source != ''}">
	<p align="left"><h1>Source</h1>
	<s:property value="problem.source"/>
</c:if>
<c:if test="${problem.author != ''}">
	<p align="left"><h1>Provider</h1>
	<s:property value="problem.author"/>
</c:if>
<hr/>
<c:out value="${buttons}" escapeXml="false" />
