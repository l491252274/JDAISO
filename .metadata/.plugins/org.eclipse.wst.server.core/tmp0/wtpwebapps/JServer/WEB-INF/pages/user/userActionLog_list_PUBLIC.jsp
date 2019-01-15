<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <meta name="menu" content="${param.menu}"/>
    <meta name="submenu" content="${param.submenu}"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>
 
<body>
<c:set var="user" value="${user}"/>
    <%
        String color = "#606060;";
        if(pageContext.getAttribute("user")!=null)
        {
            User u = (User)pageContext.getAttribute("user");
            if(u!=null)
                color = RankColor.getRankColor(u.getGrade());
        }
        pageContext.setAttribute("color", color);

    %>
   <table width=380 style="margin: 0pt -2pt">
   <tr>
   		<td width=20%>
   			<fmt:message key="user.username"/>:
   		</td>
   		<td width=30%>
   			<b>${user.username}</b>
   		</td>	
   		<td width=20%>
   			<fmt:message key="user.htmlNick"/>:
   		</td>
   		</td>
   		<td width=20%>
   			<b>${user.htmlNick}</b>
   		</td>
   	</tr>
   	
    <c:if test="${currentUser.administrator}">
   	<tr>
   		<td>
   			<fmt:message key="user.fullName"/>: 
   		</td>
   		<td colspan=3>
   			<b>${user.lastName }${user.firstName }</b>
   		</td>
   	</tr>
   	</c:if>
   	
   	<tr>
   		<td>
   			<fmt:message key="user.grade"/>: 
   		</td>
   		<td colspan=3>
   			<b><font color=${color}>${user.grade }</font></b>
   		</td>
   	</tr>

   	<tr>
   		<td>
   			<fmt:message key="user.email"/>: 
   		</td>
   		<td colspan=3>
   			<b>${user.email }</b>
   		</td>
   	</tr>
   	
</table>
	<br>
    <c:if test="${currentUser.administrator}">
    <li>
        <strong><fmt:message key="user.roles"/>:</strong>
        <s:iterator value="user.roleList" status="status">
          <s:property value="name"/><s:if test="!#status.last">,</s:if>
          <input type="hidden" name="userRoles" value="<s:property value="name"/>"/>
        </s:iterator>
    </li>
    </c:if>
    <hr>
<display:table name="userActionLogs" cellspacing="0" cellpadding="0" requestURI="user_userActionLog_list_PUBLIC.html"
    defaultsort="1" id="userActionLog" pagesize="50" class="table" export="true">
    <display:column property="action" sortable="false" titleKey="news.content" style="text-align:left;"/>
    <display:column property="inDate" sortable="false" titleKey="genericBoard.inDate" style="width:150px; text-align:center;"/>

    <display:setProperty name="paging.banner.item_name" value="userLog"/>
    <display:setProperty name="paging.banner.items_name" value="userLogs"/>

    <display:setProperty name="export.excel.filename" value="Problem List.xls"/>
    <display:setProperty name="export.csv.filename" value="Problem List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Problem List.pdf"/>
</display:table>
</body>