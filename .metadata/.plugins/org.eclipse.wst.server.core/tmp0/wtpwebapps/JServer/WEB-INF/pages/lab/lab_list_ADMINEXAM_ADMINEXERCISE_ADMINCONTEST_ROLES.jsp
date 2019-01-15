<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="com.unlimited.oj.enums.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>

</head>
 <%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<body>
<c:set var="buttons">
   <table><tr>
           <td>
              <!--<s:form action="user_user_listBySearchUsername_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES" method="post">
                   <s:hidden key="searchKey" value="username"/>
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
             <td valign="top">
                    <div style="margin-top:3px;">
                    <input type="button" style="margin-right: 5px"
                        onclick="location.href='<c:url value="/user_user_edit_PUBLIC.html?method=Add&from=list&menu=${param.menu}&submenu=${param.submenu}"/>'"
                        value="<fmt:message key="button.add"/>"/>
                    </div>
   		</td>
   	</tr></table>-->
</c:set>
<c:out value="${buttons}" escapeXml="false" />
<display:table name="users" cellspacing="0" cellpadding="0" requestURI="/lab_lab_list_ADMINEXAM_ADMINEXERCISE_ADMINCONTEST_ROLES.html"
    defaultsort="1" id="theUser" pagesize="50" class="table" export="true">
    <%
        String color = "#606060;";
        pageContext.setAttribute("color", color);

    %>

    
   	<display:column property="id" sortable="false" titleKey="user.id" style="width: 50px; text-align:center;"/>
   	<display:column property="username" sortable="false" titleKey="user.username" style="width: 200px; text-align:center;"/>
    <display:column sortable="false" titleKey="user.enabled" style="width: 40px; text-align:center;" url="/user_user_view_PUBLIC.html?from=list" paramId="id" paramProperty="id">
        
     
	</display:column>

    
</display:table>

 
