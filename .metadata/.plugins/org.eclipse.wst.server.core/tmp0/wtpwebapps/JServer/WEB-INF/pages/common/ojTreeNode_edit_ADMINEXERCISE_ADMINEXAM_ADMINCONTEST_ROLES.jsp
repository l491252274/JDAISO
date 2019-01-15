<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='ojTreeNode.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content="ModifyOjTreeNode"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<s:form name="oForm" action="common_ojTreeNode_save_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES" method="post" validate="true">
    <s:hidden key="ojTreeNode.id"/>
    <c:set var="buttons">
       <table><tr>
            <td>
                <div align=right>
                    <s:submit key="button.save"/>
                </div>
            </td>
        </tr></table>
    </c:set>
    <c:out value="${buttons}" escapeXml="false"/>
   <table width=380 style="margin: 0pt -2pt"><tr>
   			<td colspan=2>
			    
			</td>
	   	</tr><tr>
   		<td width=50%>
    		<s:textfield key="ojTreeNode.id" cssClass="text medium" required="true" disabled="true" cssStyle="background:#E0E0E0;"/>
   		</td>
   		<td width=50%>
    		<s:textfield key="ojTreeNode.pid" cssClass="text medium" required="false" cssStyle="background:#E0E0E0;"/>
   		</td>
   	</tr><tr>
   		<td width=50%>
   		</td>
   		<td width=50%>
	    	<span style="color: blue"></span>
   		</td>
   	</tr></table>

    <c:if test="${cookieLogin != 'true'}">
    <table width=380 style="margin: 0pt -2pt">
    	<tr>
	   		<td width=50%>
	           <s:textfield key="ojTreeNode.name" theme="xhtml" required="true" cssClass="text medium"/>
	   		</td>
	   		<td width=50%>
               <s:select list="ojTreeNodeTypeList" listKey="key" listValue="value" emptyOption="false" name="ojTreeNode.type" key="ojTreeNode.type"/>    
	   		</td>
   		</tr><tr>
   			<td colspan=2>
	           <s:textfield key="ojTreeNode.url" theme="xhtml" required="true" cssClass="text large"/><br>
			</td>
	   	</tr><tr>
   			<td colspan=2>
			    <font color="blue">Example: common_ojTreeNode_listProblemNodes_PUBLIC.html?pid=......</font>
			</td>
	   	</tr>
   	</table>
    </c:if>

    <table width=380 style="margin: 0pt -2pt;background-color:#E0E0E0;">
                <tr>
	   		<td width=50%>
	             <s:textfield key="ojTreeNode.orderNum" theme="xhtml" required="false" cssClass="text medium"/>
	   		</td>
	   		<td width=50%>
   	 			<s:textfield key="ojTreeNode.icon" required="false" cssClass="text medium"/>
                        </td>
   		</tr><tr>
	   		<td width=50%>
	           <s:textfield key="ojTreeNode.connectPointer" theme="xhtml" required="true" cssClass="text medium"/>
	   		</td>
	   		<td width=50%>
	           <s:textfield key="ojTreeNode.problemId" theme="xhtml" required="true" cssClass="text medium"/>
	   		</td>
   		</tr><tr>
   			<td colspan=2>
			    <s:textfield key="ojTreeNode.doors" required="false" cssClass="text large"/>
			</td>
   		</tr><tr>
   			<td colspan=2>
			    <s:textfield key="ojTreeNode.description" required="false" cssClass="text large"/>
			</td>
	   	</tr><tr>
	   		<td width=50%>
	   			<s:checkbox tooltip="Is visible?" label="Is visible?" name="ojTreeNode.visible" />
	   		</td>
	   		<td width=50%>
	   		</td>
   		</tr><tr>
   			<td colspan=2>
   			</td>
	   	</tr>
   	</table>
</s:form>

<s:form name="oForm2" action="common_ojTreeNode_delete_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES" method="post" validate="true">
    <s:hidden key="ojTreeNode.id"/>
       <table><tr>
            <td>
                <s:submit key="button.delete" onclick="return confirmDelete('node')"/>
            </td>
        </tr></table>
</s:form>