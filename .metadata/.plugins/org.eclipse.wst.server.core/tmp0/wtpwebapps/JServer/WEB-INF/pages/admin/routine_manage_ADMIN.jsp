<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.Exam"%>
<%@page import="com.unlimited.oj.enums.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>

<head>
    <title><fmt:message key="menu.admin.adminRoutine"/></title>
    <meta name="heading" content="<fmt:message key='menu.admin.adminRoutine'/>"/>
    <meta name="menu" content="AdminMenu"/>
    <meta name="submenu" content="AdminRoutine"/>
</head>
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<table>
<tr>
            <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/admin/updateUserAcProblemsStatus.html"/>'"
                    value="Update Ac Problem"/>
                </div>
            </td>
            <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/admin_starProblem_setBatch_ADMIN.html"/>'"
                    value="Update Star Problem Status"/>
                </div>
            </td>
            <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/admin_acProblem_correctTime_ADMIN.html"/>'"
                    value="Correct Ac Problem Time"/>
                </div>
            </td>
</tr>
<tr>
            <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/admin_heroBoard_checkHeros_ADMIN.html"/>'"
                    value="Check Hero Valid"/>
                </div>
            </td>
            
             <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/common_keyToProblem_list_ADMIN.html"/>'"
                    value="Key To Problem"/>
                </div>
             </td>
            
             <td>
                <div align=center><input type="button"
                    onclick="location.href='<c:url value="/user_user_batchUpdateBadges_ADMIN.html"/>'"
                    value="Update Users' Badges"/>
                </div>
             </td>
             <td>
            </td>
       </tr></table>

<script type="text/javascript">
    highlightTableRows("exams");
</script>
