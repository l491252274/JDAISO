<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="forget.password"/></title>
    <meta name="heading" content="<fmt:message key='forget.password'/>"/>
    <meta name="menu" content="ExamMenu"/>
</head>
 
<body>
<s:form method="post" action="user_user_sendNewPassword_PUBLIC">
<center>
    <fieldset style="width:300pt;padding-bottom: 0;text-align:left;">
    <ul>
        <li>
            <label for="username" class="required desc">
                <fmt:message key="label.username"/> <span class="req">*</span>
            </label>
            <input class="text medium" name="forgetUsername" id="forgetUsername" tabindex="2" />
        </li>
        <li>
            <label for="email" class="required desc">
                <fmt:message key="user.email"/> <span class="req">*</span>
            </label>
            <input class="text medium" name="forgetEmail" id="email" tabindex="2" />
        </li>

        <li>
            <input type="submit" class="button" name="submit" value="<fmt:message key='button.submit'/>" tabindex="4" />
        </li>
    </ul>
    </fieldset>
</center>
</s:form>

