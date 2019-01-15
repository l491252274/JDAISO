<%@ include file="/common/taglibs.jsp"%>
 
<body>
Update Problem Infomation
<c:set var="buttons">
   <table><tr>
   		<td>
		    <div align=right>
                <s:submit key="update problem status"/>
		    </div>
   		</td>
   	</tr></table>
</c:set>
<s:form method="get" action="updateProblemStatus">
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
        <hr>
	<div align="left">
            <c:out value="${buttons}" escapeXml="false" />
	</div>
</s:form>
</body>