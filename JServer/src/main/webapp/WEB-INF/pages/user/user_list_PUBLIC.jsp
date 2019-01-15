<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
    <meta name="menu" content="${param.menu}"/>
    <meta name="submenu" content="${param.submenu}"/>
</head>
 
<display:table name="users" cellspacing="0" cellpadding="0" requestURI="user_user_list_PUBLIC.html"
    defaultsort="1" id="theUser" pagesize="50" class="table" export="true">
    <display:column title="<input type='checkbox' name='allbox' onClick='checkAll(pform);'/>"
            sortable="false" headerClass="sortable" style="width: 20px;text-align:center;" media="html">
        <input type="checkbox" name="heroBoardIDs" value="${user.id}"/>
    </display:column>
    <display:column property="username" escapeXml="true" sortable="true" titleKey="user.username" style="width:200px;text-align:center;"
        url="/user_user_view_PUBLIC.html?from=list&menu=${param.menu}&submenu=${param.submenu}" paramId="userId" paramProperty="id"/>

<c:if test="${currentUser.administrator}">
    <display:column property="className" sortable="false" titleKey="user.className" style="width: 120px; text-align:center;"/>
    <display:column property="fullName" sortable="false" titleKey="user.fullName" style="width: 80px; text-align:center;"/>
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
