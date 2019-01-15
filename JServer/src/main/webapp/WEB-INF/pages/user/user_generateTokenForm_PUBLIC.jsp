<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <meta name="heading" content="<fmt:message key='userProfile.title'/>"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/layout-1col.css'/>" />
</head>
<body id="login"/>

<s:form method="post" action="user_user_generateTokenForm_Submit_PUBLIC">
<s:hidden key="user.id"/>
<center>
<fieldset style="width:300pt;padding-bottom: 0;text-align:left;">
<ul>
<c:if test="${param.error != null}">
    <li class="error">
        <img src="${ctx}/images/iconWarning.gif" alt="<fmt:message key='icon.warning'/>" class="icon"/>
        <fmt:message key="errors.password.mismatch"/>
        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION_KEY.message}--%>
    </li>
</c:if>
<h2><p align="center"><fmt:message key="user.username"/>: ${user.username}</h2>
    <li>
        <b>Generate A New Token</font></b>
    </li>

    <li>
        <label for="password" class="required desc">
            <fmt:message key="registerForm.account"/><fmt:message key="label.password"/> <span class="req">*</span>
        </label>
        <input type="password" class="text medium" name="password" id="password" tabindex="2" />
    </li>

    <li>
        <input type="submit" class="button" name="login" value="<fmt:message key='button.submit'/>" tabindex="4" />
    </li>

</ul>
</fieldset>
</center>
</s:form>

