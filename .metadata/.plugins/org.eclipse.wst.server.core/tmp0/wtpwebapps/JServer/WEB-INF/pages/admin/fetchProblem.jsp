<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content=""/>
</head>
<body>
Fetch Problem from Other OJ
<c:set var="buttons">
   <table><tr>
   		<td>
		    <div align=right>
                <s:submit method="fetchPkuProblem" key="Fetch Pku"/>
		    </div>
   		</td>
   		<td>
		    <div align=right>
                <s:submit method="fetchZjuProblem" key="Fetch Zju"/>
		    </div>
   		</td>
   		<td>
		    <div align=right>
                <s:submit method="fetchTimusProblem" key="Fetch Timus"/>
		    </div>
   		</td>
   		<td>
		    <div align=right>
                <s:submit method="fetchHduProblem" key="Fetch Hdu"/>
		    </div>
   		</td>
   		<td>
		    <div align=right>
                <s:submit method="fetchUvaProblem" key="Fetch Uva"/>
		    </div>
   		</td>
   	</tr></table>
</c:set>
<s:form method="get" action="fetchProblem">
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