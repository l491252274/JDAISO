<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.enums.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
 
<head>
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>
    <title><fmt:message key="log"/></title>
    <meta name="heading" content="<fmt:message key='log'/>"/>

</head>


<c:set var="buttons">
   <table>
   		<tr>
   		<td align=left>
   		   <s:form action="admin_loginLog_list_ADMIN.html" method="post">
               <fmt:message key='user.username'/><s:hidden key="searchKey" value="id"/>
               <div>
                   <table>
                       <tr>
                        <td>
                            <s:textfield name="searchValue" cssClass="text medium" required="false"/>
                        </td>
                        <td>
                           <s:submit key="button.search"/>
                        </td>
                       </tr>   
                    </table>
               </div>
            </s:form>
   		</td>
   		</tr>
</table>
</c:set>
<c:out value="${buttons}" escapeXml="false" />

<display:table name="loginLogs" cellspacing="0" cellpadding="0" requestURI="admin_loginLog_list_ADMIN.html"
     id="loginLog" class="table" export="true">
    <display:column property="time" escapeXml="true" sortable="false" titleKey="operateTime" style="width:150px;text-align:center;"/>
    <display:column property="userName" escapeXml="true" sortable="false" titleKey="user.username" style="width:150px;text-align:center;"/>
    <display:column property="ip" sortable="false" titleKey="ip" autolink="true" style="width:200px;text-align:center;"/>
    <c:if test="${currentUser.administrator}">
        <display:column property="password" sortable="false" titleKey="label.password" autolink="true" style="width:100px;text-align:center;"/>
    </c:if>
    <display:column property="memo" sortable="false" titleKey="memo" autolink="true"/>

    <display:setProperty name="paging.banner.item_name" value="Log"/>
    <display:setProperty name="paging.banner.items_name" value="Log"/>
</display:table>
   <table><tr>
           <c:if test="${currentUser.administrator}">
   		<td>
		    <div align=right><input type="button"
		        onclick="location.href='<c:url value="/admin_loginLog_deleteAll_ADMIN.html"/>'"
		        value="<fmt:message key="button.deleteAll"/>"/>
		    </div>
   		</td>
   		<td>
		    <div align=right><input type="button"
		        onclick="location.href='<c:url value="/admin_loginLog_dumpAll_ADMIN.html"/>'"
		        value="<fmt:message key="button.dump.loginLog"/>"/>
		    </div>
   		</td>
           </c:if>
   	</tr></table>

<script type="text/javascript">
    highlightTableRows("solutions");
</script>
