<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.heading'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>
 
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<c:set var="project_name" value="${appConfig['project.name']}"/>
<c:set var="currentUser" value="${currentUser}"/>
<c:set var="user" value="${user}"/>
    <%
    	// color
        String color = "#606060;";

        pageContext.setAttribute("color", color);
        
        // badge
        String pn = (String)pageContext.getAttribute("project_name");
        String sbadge = "";
        Boolean sameUser = true;
        User currentUser = (User)pageContext.getAttribute("currentUser");
        if(pageContext.getAttribute("user")!=null)
        {
            User u = (User)pageContext.getAttribute("user");
            if(u!=null)
            {
                sameUser = u.getId().equals(currentUser.getId());
            }
        }
        pageContext.setAttribute("badgeImage", "");
        pageContext.setAttribute("sameUser", sameUser);
    %>
    <s:form name="userForm" action="user_user_save_PUBLIC" method="post" validate="true">
    <li style="display: none">
        <s:hidden key="user.id"/>
        <s:hidden key="user.version"/>
        <s:hidden key="user.oldPassword"/>
        <s:hidden key="encodeUsername"/>
<!--
        <c:if test="${!currentUser.administrator}">
            <s:hidden key="user.enabled"/>
        </c:if>
 -->
         <input type="hidden" name="from" value="${param.from}"/>

        <c:if test="${cookieLogin == 'true'}">
            <s:hidden key="user.password"/>
            <s:hidden key="user.confirmPassword"/>
        </c:if>
    </li>
    <li class="buttonBar right">
        <c:set var="buttons">
            <div align="right"><input type=button value="<fmt:message key='log'/>" onclick="window.location.href('/${appConfig['project.name']}/user_userActionLog_list_PUBLIC.html?userId=${user.id}');"/></div>&nbsp;&nbsp;
            <s:submit key="button.save" method="user_save_PUBLIC" onclick="onFormSubmit(this.form)"/>&nbsp;&nbsp;
        </c:set>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="userProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="userProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>

   <table width=380 style="margin: 0pt -2pt"><tr>
   		<td width=50%>
   		<font color=${color}>
	    <c:if test="${currentUser.administrator}">
    		<s:textfield key="user.username" cssClass="text medium" required="true"/>
	    </c:if>
	    <c:if test="${!currentUser.administrator}">
    		<s:textfield key="user.username" cssClass="text medium" required="true" disabled="true" cssStyle="background:#E0E0E0;"/>
	    </c:if>
   		</td>
   		</font>
   		<td width=50%>
    		</td>
   	</tr>
   	</table>

    <c:if test="${cookieLogin != 'true'}">
    <table width=480 style="margin: 0pt -2pt">
    	<tr>
	   		<td width=50%>
	           <s:password key="user.password" showPassword="true" theme="xhtml" required="true" 
	               cssClass="text medium" onchange="passwordChanged(this)"/>
	   		</td>
	   		<td width=50%>
	           <s:password key="user.confirmPassword" theme="xhtml" required="true" 
	               showPassword="true" cssClass="text medium" onchange="passwordChanged(this)"/>
	   		</td>
   		</tr>
   		<tr>
   		<td>
                            <c:if test="${currentUser.administrator}">
                            <table><tr><td><font color="blue"><b>
                                       <fmt:message key="user.enabled"/>&nbsp;&nbsp;&nbsp;
                                    </b></font></td><td>
                            <s:checkbox tooltip="Enabled?" name="user.enabled"/>
                            </td></tr></table>
                            </c:if>
		</td>
	   	</tr>
   	</table>
    </c:if>

    <table width=480 style="margin: 0pt -2pt;background-color:#E0E0E0;">

	   	<tr>
	   		<td width=50% colspan=2>
	   		</td>
	   		<td width=50% colspan=2>
	                <s:textfield key="user.phoneNumber" theme="xhtml" cssClass="text medium"/>
	   		</td>
	   	</tr>
   	</table>

<c:choose>
    <c:when test="${currentUser.administrator}">
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.assignRoles"/></legend>
            <table class="pickList">
                <tr>
                    <th class="pickLabel">
                        <label class="required"><fmt:message key="user.availableRoles"/></label>
                    </th>
                    <td></td>
                    <th class="pickLabel">
                        <label class="required"><fmt:message key="user.roles"/></label>
                    </th>
                </tr>
                <c:set var="leftList" value="${availableRoles}" scope="request"/>
                <s:set name="rightList" value="user.roleList" scope="request"/>
                <c:import url="/WEB-INF/pages/pickList.jsp">
                    <c:param name="listCount" value="1"/>
                    <c:param name="leftId" value="availableRoles"/>
                    <c:param name="rightId" value="userRoles"/>
                </c:import>
            </table>
        </fieldset>
    </li>
    </c:when>
    <c:otherwise>
    <li>
        <strong><fmt:message key="user.roles"/>:</strong>
        <s:iterator value="user.roleList" status="status">
          <s:property value="name"/><s:if test="!#status.last">,</s:if>
          <!--  <input type="hidden" name="userRoles" value="<s:property value="name"/>"/> -->
        </s:iterator>
    </li>
    </c:otherwise>
</c:choose>
    <li class="buttonBar bottom">
    	<p align=center><c:out value="${buttons}" escapeXml="false"/></p>
    </li>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["userForm"]);
    highlightFormElements();

    function passwordChanged(passwordField) {
        if (passwordField.name == "user.password") {
            var origPassword = "<s:property value="user.password"/>";
        } else if (passwordField.name == "user.confirmPassword") {
            var origPassword = "<s:property value="user.confirmPassword"/>";
        }
        
        if (passwordField.value != origPassword) {
            createFormElement("input", "hidden",  "encryptPass", "encryptPass",
                              "true", passwordField.form);
        }
    }

<!-- This is here so we can exclude the selectAll call when roles is hidden -->
function onFormSubmit(theForm) {
    selectAll('userRoles');
}
</script>
