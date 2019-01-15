<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="com.unlimited.oj.enums.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>

<head>
    <title><fmt:message key="problemList.title"/></title>
    <meta name="heading" content="<fmt:message key='ojTreeNode.heading'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content="ModifyOjTreeNode"/>
</head>

<s:form name="oform" action="ojTreeNodeAction" method="get" validate="true">
<c:set var="buttons">
   <table><tr>
   		<td>
		    <div align=right><input type="button"
		        onclick="location.href='<c:url value="/common_ojTreeNode_edit_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES.html?pid=${ojTreeNodeParent.id}&method=Add&from=list"/>'"
		        value="<fmt:message key="button.add"/>"/>
		    </div>
   		</td>
   	</tr></table>
</c:set>
<c:out value="${buttons}" escapeXml="false" />
<%
	LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
	ResourceBundle bundle = locCtxt.getResourceBundle();
%>
Path:&nbsp;&nbsp;<c:out value="${currentPath}" escapeXml="false" />
<display:table name="ojTreeNodes" cellspacing="0" cellpadding="0" requestURI="common_ojTreeNode_list_PUBLIC"
    defaultsort="1" id="item" pagesize="50" class="table" export="true">
    <display:column title="<input type='checkbox' name='allbox' onClick='checkAll(pform);'/>"
            sortable="false" headerClass="sortable" style="width: 5%;word-break:break-all;text-align:center;">
        <input type="checkbox" name="problemIDs" value="${ojTreeNode.id}"/>
    </display:column>
    <display:column property="id" escapeXml="true" sortable="false" titleKey="ojTreeNode.id" paramId="pid" paramProperty="id" autolink="true" style="width: 60px;text-align:center;"/>
    <display:column property="pid" sortable="false" titleKey="ojTreeNode.pid" media="html" style="width: 60px;text-align:center;"/>
    <display:column property="orderNum" sortable="false" titleKey="ojTreeNode.orderNum" media="html" style="width: 60px;text-align:center;"/>
    <c:choose>
        <c:when test="${item.type<100}">
            <display:column property="name" sortable="false" titleKey="ojTreeNode.name" url="/common_ojTreeNode_list_PUBLIC.html?from=list" paramId="pid" paramProperty="id" autolink="true" style="width: 120px;text-align:center;"/>
        </c:when>
        <c:otherwise>
            <display:column property="name" sortable="false" titleKey="ojTreeNode.name" url="/common_ojTreeNode_list_PUBLIC.html" paramId="ojTreeNodeId" paramProperty="id" autolink="true" style="width: 120px;text-align:center;"/>
        </c:otherwise>
    </c:choose>
    <%
    	int type=0;
    	Object obj = pageContext.getAttribute("item");
    	if(obj!=null)
    	{
	        OjTreeNode node = (OjTreeNode)obj;
	        type = node.getType();
	    }
        pageContext.setAttribute("type", bundle.getString(OjTreeNodeType.getOjTreeNodeType(type).getName()));
     %>
	<display:column value="${type}" sortable="false" titleKey="ojTreeNode.type" style="width: 80px;text-align:center;"/>
    <display:column property="url" sortable="false" titleKey="ojTreeNode.url" autolink="false" style="width: 120px;text-align:center;"/>
    <display:column property="description" sortable="false" titleKey="ojTreeNode.description"/>
    <display:column property="visible" sortable="false" titleKey="ojTreeNode.visible"/>
    <display:column titleKey="list.operate" sortable="false" headerClass="sortable" style="width:150px; word-break:break-all;">
    	<table style="width:100%; border:0; margin:0; padding:0;"><tr style="border:0; margin:0; padding:0;">
    		<td style="text-align:center; border:0; margin:0; padding:0;">
    		<input type="button" onclick="location.href='/${appConfig['project.name']}/common_ojTreeNode_edit_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES.html?ojTreeNodeId=${item.id}'" value="<fmt:message key="button.edit"/><fmt:message key="ojTreeNode.heading"/>"/>
    		</td>
         <c:if test="${item.problemId!=null && item.problemId!=''}">
    		<td style="text-align:center; border:0; margin:0; padding:0;">
    		<input type="button" onclick="location.href='/${appConfig['project.name']}/common_problem_edit_ADMINEXERCISE_ADMINEXAM_ADMINCONTEST_ROLES.html?problemId=${item.problemId}'" value="<fmt:message key="button.edit"/><fmt:message key="problem"/>"/>
    		</td>
         </c:if>
    	</tr></table>
    </display:column>

    <display:setProperty name="paging.banner.item_name" value="ojTreeNode"/>
    <display:setProperty name="paging.banner.items_name" value="ojTreeNodes"/>

    <display:setProperty name="export.excel.filename" value="OjTreeNode List.xls"/>
    <display:setProperty name="export.csv.filename" value="OjTreeNode List.csv"/>
    <display:setProperty name="export.pdf.filename" value="OjTreeNode List.pdf"/>
</display:table>
<div style="margin:0% 0% 0% 70%"></div>
</s:form>

<script type="text/javascript">
    highlightTableRows("ojTreeNodes");
</script>
