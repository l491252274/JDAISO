<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="activeUsers.title"/></title>
    <meta name="heading" content="<fmt:message key='activeUsers.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content="ActiveUsers"/>
</head>
<body id="activeUsers"/>
 
<p><fmt:message key="activeUsers.message"/></p>

<div class="separator"></div>

<display:table name="applicationScope.userNames" id="user" cellspacing="0" cellpadding="0"
    defaultsort="1" class="table" pagesize="50" requestURI="admin/activeUserList.html">
  
     <display:column property="username" escapeXml="true" style="width: 30%" titleKey="user.username" sortable="true"/>
     <display:column titleKey="activeUsers.fullName" style="width: 30%" sortable="true" property="fullName"/>
     <display:column titleKey="icon.email">
        <c:if test="${not empty user.email}">
	        <a href="mailto:<c:out value="${user.email}"/>">${user.email}</a>
        </c:if>
    </display:column>
       
    <display:setProperty name="paging.banner.item_name" value="user" />
    <display:setProperty name="paging.banner.items_name" value="users" />
</display:table>
