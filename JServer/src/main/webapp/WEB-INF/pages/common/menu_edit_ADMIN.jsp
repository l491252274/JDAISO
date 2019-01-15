<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='menu.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content="ModifyOjTreeNode"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<c:if test="${menu.id!=null }">
<s:form name="oForm2" action="common_menu_delete_ADMIN" method="post" validate="true">
    <s:hidden key="menu.id"/>
       <table><tr>
	       <td>
				<input type="button" style="margin-right: 5px"
				                        onclick="location.href='<c:url value="/common_menu_edit_ADMIN.html?parentId=${menu.id}"/>'"
				                        value="<fmt:message key="button.createSubmenu"/>"/>
	       
	       </td>
	       <td>
	                <s:submit key="button.delete" onclick="return confirmDelete('node')"/>
	       </td>
        </tr></table>
</s:form>
</c:if>                   

<s:form name="oForm" action="common_menu_save_ADMIN" method="post" validate="true">
    <s:hidden key="menu.id"/>
   <table width=380 style="margin: 0pt -2pt"><tr>
   			<td colspan=2>
			    
			</td>
	   	</tr><tr>
   		<td width=50%>
    		<s:textfield key="menu.id" cssClass="text medium" required="true" disabled="true" cssStyle="background:#E0E0E0;"/>
   		</td>
   		<td width=50%>
    		<s:textfield key="menu.parentID" cssClass="text medium" required="false" cssStyle="background:#E0E0E0;"/>
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
	           <s:textfield key="menu.resourceName" theme="xhtml" required="true" cssClass="text medium"/>
	   		</td>
	   		<td width=50%>
	           <s:textfield key="menu.resourceType" theme="xhtml" required="true" cssClass="text medium"/>
	   		</td>
   		</tr><tr>
   			<td colspan=2>
	           <s:textfield key="menu.accessPath" theme="xhtml" required="true" cssClass="text large"/><br>
			</td>
	   	</tr>
   	</table>
    </c:if>

    <table width=380 style="margin: 0pt -2pt;background-color:#E0E0E0;">
                <tr>
	   		<td width=50%>
	             <s:textfield key="menu.resourceOrder" theme="xhtml" required="false" cssClass="text medium"/>
	   		</td>
	   		<td width=50%>
	           <s:textfield key="menu.resourceCode" theme="xhtml" required="true" cssClass="text medium"/>
                        </td>
   		</tr><tr>
   			<td colspan=2>
			    <s:textfield key="menu.resourceDesc" required="false" cssClass="text large"/>
			</td>
   		</tr><tr>
   			<td colspan=2>
			    <s:textfield key="menu.authority" required="false" cssClass="text large"/>
			</td>
   		</tr><tr>
   			<td colspan=2>
			    <s:textfield key="menu.author" required="false" cssClass="text medium"/>
			</td>
	   	</tr><tr>
	   		<td width=50%>
	   			<s:checkbox tooltip="Is visible?" label="Is visible?" name="menu.visible" />
	   		</td>
	   		<td width=50%>
	   		</td>
   		</tr><tr>
   			<td colspan=2>
   			</td>
	   	</tr>
   	</table>
	<br/>
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
</s:form>
