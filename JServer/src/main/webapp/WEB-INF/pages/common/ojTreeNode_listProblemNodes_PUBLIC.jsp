<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.service.*"%>
<%@page import="com.unlimited.oj.service.*"%>
<%@page import="java.util.*"%>
<%@page import="com.unlimited.oj.pojo.*"%>
 <%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
 
<head>
    <title><fmt:message key="experimentProblemList.title"/></title>
    <meta name="heading" content="<fmt:message key='experimentProblemList.heading'/>"/>
</head>
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
        pageContext.setAttribute("unpublished", bundle.getString("label.unpublished"));
%>
<table><tr>
            <td>
                <div align=right><input type="button"
                    onclick="location.href='<c:url value="/common_solution_listByUserRecent_PUBLIC_DELAY4.html"/>'"
                    value="<fmt:message key="solutionList.viewListRecent"/>"/>
                </div>
            </td>
            <td>
                <div align=right><input type="button"
                    onclick="location.href='<c:url value="/common_solution_listByUser_PUBLIC_DELAY8.html"/>'"
                    value="<fmt:message key="solutionList.viewListHistory"/>"/>
                </div>
            </td>
    </tr></table>

<s:form name="pform" action="problemAction" method="get" validate="true">
<center><h1>${ojTreeNodeParent.name}</h1></center>
<c:set var="project_name" value="${appConfig['project.name']}"/>
<display:table name="problemNodes" cellspacing="0" cellpadding="0" requestURI=""
     id="node" class="table" export="true">
    <%
    	String tmp = "";
		String hasKey="";
		int i;
        String pn = (String)pageContext.getAttribute("project_name");
        DataProblem dp = (DataProblem)pageContext.getAttribute("node");
        Problem prob = dp.problem;
        if(prob!=null)
        {
        	for(i=0; i<prob.getStar(); i++)
        		tmp+="<img src=\"/"+pn+"/images/star.gif\" width=11 height=10 BORDER=0 align=bottom/>";
        		
        	List<Resource> list = prob.getResourceList();
        	if(list!=null)
        	{
        		for(Resource item: list)
        			if(item instanceof KeyToProblem)
        			{
        				KeyToProblem ktp = (KeyToProblem)item;
        				hasKey = "<a href=\"common_keyToProblem_list_PUBLIC.html?problemId="+ ktp.getProblemList().get(0).getId() + "\">" + bundle.getString("button.view") + "</a>";
        				 break;
        			}
        	}
        }
        pageContext.setAttribute("hasKey", hasKey);
        pageContext.setAttribute("starImage", tmp);
    %>
    <display:column sortable="false" title="" style="width: 5%;text-align:center;">
        <c:choose>
            <c:when test="${node.doneStatus==0}"><img src="/${appConfig['project.name']}/styles/${appConfig['csstheme']}/images/accepted.gif"/></c:when>
            <c:when test="${node.doneStatus==1}"><img src="/${appConfig['project.name']}/styles/${appConfig['csstheme']}/images/wrong.gif"/></c:when>
        </c:choose>
    </display:column>
    <display:column property="id" escapeXml="true" sortable="false" titleKey="problemList.problem.id" style="width: 10%;text-align:center;" paramId="id" paramProperty="id"/>
    <c:if test="${!node.publishFlag}">
    	<display:column value="${node.title}&nbsp;${starImage}&nbsp;[${unpublished}]" sortable="false" titleKey="problemList.problem.title"  url="/common_problem_view_PUBLIC.html?identity=${node.md5Identity}" paramId="problemId" paramProperty="id" style="width: 35%" autolink="true"/>
    </c:if>
    <c:if test="${node.publishFlag}">
    	<display:column value="${node.title}&nbsp;${starImage}" sortable="false" titleKey="problemList.problem.title"  url="/common_problem_view_PUBLIC.html?identity=${node.md5Identity}" paramId="problemId" paramProperty="id" style="width: 35%" autolink="true"/>
    </c:if>
    <display:column value="${hasKey}" sortable="false" titleKey="keyToProblem" autolink="false" style="width: 5%;text-align:center;"/>
    <display:column property="tag" sortable="false" titleKey="tag.heading" style="width: 20%;text-align:center;" autolink="true"/>
    <display:column property="tagAuthor" sortable="false" titleKey="tag.author" style="width: 10%;text-align:center;" autolink="true"/>
    <display:column sortable="false" titleKey="answerCode" style="width:10%;text-align:center;">
        <c:choose>
            <c:when test="${currentUser.administrator && node.hasAnswer && !node.answerVisible}"><a href="/${appConfig['project.name']}/common_problem_showStandardCode_PUBLIC.html?problemId=${node.id}&identity=${node.md5Identity}"><img src="/${appConfig['project.name']}/images/unvisible.jpg" width=24 height=15 BORDER=0/></a></c:when>
            <c:when test="${appConfig['PlatformSecurityLevel']>2 && node.hasAnswer && node.answerVisible && node.doneStatus==0}"><a href="/${appConfig['project.name']}/common_problem_showStandardCode_PUBLIC.html?problemId=${node.id}&identity=${node.md5Identity}"><img src="/${appConfig['project.name']}/images/unlock.jpg" width=10 height=18 BORDER=0/></a></c:when>
            <c:when test="${currentUser.administrator && node.hasAnswer && node.answerVisible && node.doneStatus!=0}"><a href="/${appConfig['project.name']}/common_problem_showStandardCode_PUBLIC.html?problemId=${node.id}&identity=${node.md5Identity}"><img src="/${appConfig['project.name']}/images/lock.jpg" width=10 height=18 BORDER=0/></a></c:when>
            <c:when test="${appConfig['PlatformSecurityLevel']>2 && !currentUser.administrator && node.hasAnswer && node.answerVisible && node.doneStatus!=0}"><img src="/${appConfig['project.name']}/images/lock.jpg" width=10 height=18/></c:when>
            <c:otherwise>&nbsp;</c:otherwise>
        </c:choose>
    </display:column>
    <display:column sortable="false" titleKey="solutionList.heading" style="width:10%;text-align:center;">
        <c:choose>
            <c:when test="${appConfig['PlatformSecurityLevel']>2 && (node.doneStatus==0 || node.doneStatus==1)}">
		    		<input type="button" onclick="location.href='/${appConfig['project.name']}/common_solution_listByUserAndProblem_PUBLIC_DELAY6.html?userId=${currentUser.id}&problemId=${node.id}'" value="<fmt:message key="button.view"/>" style="color:grean"/>
            </c:when>
            <c:otherwise>
            &nbsp;
            </c:otherwise>
        </c:choose>
    </display:column>
    <display:setProperty name="paging.banner.item_name" value="problem"/>
    <display:setProperty name="paging.banner.items_name" value="problems"/>

    <display:setProperty name="export.excel.filename" value="Problem List.xls"/>
    <display:setProperty name="export.csv.filename" value="Problem List.csv"/>
    <display:setProperty name="export.pdf.filename" value="Problem List.pdf"/>
</display:table>
</s:form>

<script type="text/javascript">
    highlightTableRows("ojTreeNodes");
</script>
