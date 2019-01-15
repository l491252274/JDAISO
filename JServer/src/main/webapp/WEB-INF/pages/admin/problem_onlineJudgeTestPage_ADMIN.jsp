<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content=""/>
</head>
<body>
Test Online Judge
<hr/>
   <table><tr>
   		<td>
		    <div align=right>
			<s:form method="post" action="admin_problem_TestPku_ADMIN">
                <s:submit key="Test Pku"/>
			</s:form>
		    </div>
   		</td>
   		<td>
		    <div align=right>
			<s:form method="post" action="admin_problem_TestZju_ADMIN">
                <s:submit key="Test Zju"/>
			</s:form>
		    </div>
   		</td>
   		<td>
		    <div align=right>
			<s:form method="post" action="admin_problem_TestTimus_ADMIN">
                <s:submit key="Test Timus"/>
			</s:form>
		    </div>
   		</td>
   		<td>
		    <div align=right>
			<s:form method="post" action="admin_problem_TestHdu_ADMIN">
                <s:submit key="Test Hdu"/>
			</s:form>
		    </div>
   		</td>
   	</tr></table>
</body>