<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
    <meta name="menu" content="${param.menu}"/>
    <meta name="submenu" content="${param.submenu}"/>
</head>
 
<c:set var="buttons">
   <table><tr>
           <td>
              <s:form action="user_user_listBySearchUsername_PUBLIC" method="post">
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
   	</tr></table>
</c:set>
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<c:out value="${buttons}" escapeXml="false" />
<c:set var="project_name" value="${appConfig['project.name']}"/>
<display:table name="users" cellspacing="0" cellpadding="0" requestURI="user_user_listBySearchUsername_PUBLIC.html"
    defaultsort="1" id="theUser" pagesize="50" class="table" export="true">
    <%
    	// color
        String color = "#606060;";
        if(pageContext.getAttribute("theUser")!=null)
        {
            User u = (User)pageContext.getAttribute("theUser");
            if(u!=null)
                color = RankColor.getRankColor(u.getGrade());
        }
        pageContext.setAttribute("color", color);
        
        pageContext.setAttribute("badgeImage", "");
    %>
    <display:column title="<input type='checkbox' name='allbox' onClick='checkAll(pform);'/>"
            sortable="false" headerClass="sortable" style="width: 20px;text-align:center;" media="html">
        <input type="checkbox" name="heroBoardIDs" value="${user.id}"/>
    </display:column>
    <display:column value="<a href='/${appConfig['project.name']}/user_user_view_PUBLIC.html?userId=${theUser.id}'>${theUser.username}</a>" sortable="false" titleKey="user.username" autolink="true" style="width:100px;text-align:center;font-weight: bold;color:${color};"/>
    <c:if test="${currentUser.administrator}">
    	<display:column titleKey="button.edit" value="<a href='/${appConfig['project.name']}/user_user_edit_ADMIN.html?userId=${theUser.id}&from=list&menu=${param.menu}&submenu=${param.submenu}' style='color=${color}'>EDIT</a>" escapeXml="false" sortable="false" style="width:50px;text-align:center;"/>
    </c:if>

    <display:setProperty name="paging.banner.item_name" value="user"/>
    <display:setProperty name="paging.banner.items_name" value="users"/>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
</display:table>

<script type="text/javascript">
    highlightTableRows("users");
</script>
