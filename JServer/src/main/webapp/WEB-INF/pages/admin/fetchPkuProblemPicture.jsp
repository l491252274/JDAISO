<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content=""/>
</head>
<body>
<s:form method="get" action="fetchPkuProblemPicture">
   <table width=100% style="margin: 0pt -2pt"><tr>
   		<td align=left>
		    <div align=left>
		   		<s:textfield key="from" theme="xhtml" required="true" cssClass="text autosize" size="30"/>
		    </div>
   		</td>
   		<td align=left>
		    <div align=left>
		    	<s:textfield key="to" theme="xhtml" required="true" cssClass="text autosize" size="30"/>
		    </div>
   		</td>
   	</tr></table>
	<div align="center">
		<p><input value="<fmt:message key='button.submit'/>" name="submit" type="submit"><input value="<fmt:message key='button.reset'/>" name="reset" type="reset"></p>
	</div>
</s:form>
</body>