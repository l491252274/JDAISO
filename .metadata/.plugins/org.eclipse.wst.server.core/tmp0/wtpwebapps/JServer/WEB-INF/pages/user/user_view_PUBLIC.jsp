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
   			<b><font color=${color}>${user.username}</font></b>
   		</td>
   		<td width=20%>
   			<fmt:message key="user.htmlNick"/>:
   		</td>
   		<td width=20%>
   			<b><font color=${color}>${user.htmlNick}</font></b>
   		</td>
   	</tr>   	
    <c:if test="${currentUser.administrator}">
   <tr>
   		<td colspan=1>
   			<fmt:message key="user.className"/>:
   		</td>
   		<td colspan=3>
   			<b><font color=${color}>${user.className}</font></b>
   		</td>
   	</tr>
   	<tr>
   		<td>
   			<fmt:message key="user.fullName"/>: 
   		</td>
   		<td >
   			<b><font color=${color}>${user.lastName }${user.firstName }</font></b>
   		</td>
   		<td>
   			ID: 
   		</td>
   		<td >
   			<b><font color=${color}>${user.id}</font></b>
   		</td>
   	</tr>
   	</c:if>
   	
   	<tr>
   		<td>
   			<fmt:message key="user.grade"/>: 
   		</td>
   		<td>
   			<b><font color=${color}>${user.grade }</font></b>
   		</td>
   		<td>
   			<fmt:message key="user.money"/>: 
   		</td>
   		<td>
   			<b><font color=${color}>${user.money }</font></b>
   		</td>
   	</tr>

   	<c:if test="${currentUser.administrator}">
   	<tr>
   		<td></td>
   		<td colspan=3>
   			<b><font color=${color}>${userRank}</font></b>
   		</td>
   	</tr>
   	<tr>
   		<td>
   			<fmt:message key="user.email"/>: 
   		</td>
   		<td colspan=3>
   			<b><font color=${color}>${user.email }</font></b>
   		</td>
   	</tr>
   	</c:if>
   	
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
    <!-- begin -->
	<table id="showChart" style="visibility:visible;">
		<tr>
			<td>
		      <a href="javascript:initLine('Chart',680,200,'${dataChartGlobal.title}','${dataChartGlobal.xlabel}','${dataChartGlobal.ylabel}','${dataChartGlobal.label}','${dataChartGlobal.value}','${dataChartGlobal.unit}');">
		         [Grobal] 
		      </a>
		      &nbsp;&nbsp;&nbsp;
		      <a href="javascript:initBar('Chart',680,200,'${dataChartRecent.title}','${dataChartRecent.xlabel}','${dataChartRecent.ylabel}','${dataChartRecent.label}','${dataChartRecent.value}','${dataChartRecent.unit}');">
		        [Recent]
		      </a>
		      &nbsp;&nbsp;&nbsp;
		      <a href="/${appConfig['project.name']}/user_userActionLog_list_PUBLIC.html?userId=${user.id}">
		        [Log]
		      </a>
			</td>
		</tr>
		<tr>
			<td id="Chart">
			</td>
		</tr>
	</table>
	<script>
		initLine('Chart',680,200,'${dataChartGlobal.title}','${dataChartGlobal.xlabel}','${dataChartGlobal.ylabel}','${dataChartGlobal.label}','${dataChartGlobal.value}','${dataChartGlobal.unit}');	</script>
    <c:if test="${user.accountValid}">
    	<a href="/${appConfig['project.name']}/user_user_upadateAccountAcProblem_PUBLIC.html?userId=${user.id}">Refresh, get your accepts on other OJs</a>
    </c:if>
	<!-- end -->
<c:if test="${badgeMsg!=null}">
    <hr>
    <b><h1>Honor:</h1></b>
	${badgeMsg}
</c:if>  
<c:if test="${currentUser.administrator}">
    <hr>
	<display:table name="userProfiles" cellspacing="0" cellpadding="0" requestURI="user_userActionLog_list_PUBLIC.html"
	    id="userProfile" pagesize="50" class="table" export="true">
	    <display:column property="description" sortable="false" titleKey="news.content" style="text-align:left;"/>
	    <display:column property="inDate" sortable="false" titleKey="genericBoard.inDate" style="width:200px; text-align:center;"/>
	
	    <display:setProperty name="paging.banner.item_name" value="User Profile"/>
	    <display:setProperty name="paging.banner.items_name" value="User Profiles"/>
	
	    <display:setProperty name="export.excel.filename" value="userProfile List.xls"/>
	    <display:setProperty name="export.csv.filename" value="userProfile List.csv"/>
	    <display:setProperty name="export.pdf.filename" value="userProfile List.pdf"/>
	</display:table>  
</c:if> 
<br/><input type=button onclick="javascript:window.print();" value="Print"> 
</body>