<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="signup.title" />
</title>
<meta name="heading" content="<fmt:message key='signup.heading'/>" />
<meta name="menu" content="UserMenu" />
<meta name="submenu" content="" />
</head>

<body id="signup" />

<div class="separator"></div>

<s:form name="signupForm" action="signup" method="post" validate="true">
	<li style="display: none"><s:hidden key="user.id" /> <s:hidden
			key="user.version" /> <input type="hidden" name="from"
		value="${param.from}" /> <c:if test="${cookieLogin == 'true'}">
			<s:hidden key="user.password" />
			<s:hidden key="user.confirmPassword" />
		</c:if> <s:if test="user.version == null">
			<input type="hidden" name="encryptPass" value="true" />
		</s:if></li>
	<li class="info"><c:choose>
			<c:when test="${param.from == 'list'}">
				<p>
					<fmt:message key="userProfile.admin.message" />
				</p>
			</c:when>
			<c:otherwise>
				<p>
					<fmt:message key="userProfile.message" />
				</p>
			</c:otherwise>
		</c:choose></li>

	<table width=380 style="margin: 0pt -2pt">
		<tr>
			<td width=50%><s:textfield key="user.username"
					cssClass="text medium" required="true" /></td>
			<td width=50%><s:textfield key="user.pkuAccount"
					cssClass="text medium" required="false" /></td>
		</tr>
	</table>

	<c:if test="${cookieLogin != 'true'}">
		<table width=380 style="margin: 0pt -2pt">
			<tr>
				<td width=50%><s:password key="user.password"
						showPassword="true" theme="xhtml" required="true"
						cssClass="text medium" onchange="passwordChanged(this)" /></td>
				<td width=50%><s:password key="user.confirmPassword"
						theme="xhtml" required="true" showPassword="true"
						cssClass="text medium" onchange="passwordChanged(this)" /></td>
			</tr>
		</table>
	</c:if>

	<s:textfield key="user.passwordHint" required="false"
		cssClass="text large" />
	<s:textfield key="user.email" theme="xhtml" required="true"
		cssClass="text large" />

	<table width=360 style="margin: 0pt -2pt; background-color: #E0E0E0;">
		<tr>
			<td colspan=2></td>
		</tr>
		<tr>
			<td width=50%><s:textfield key="user.school" required="false"
					cssClass="text medium" /></td>
			<td width=50%><s:textfield key="user.className" theme="xhtml"
					required="false" cssClass="text medium" /></td>
		</tr>
		<tr>
			<td width=50%><s:textfield key="user.studentNumber"
					theme="xhtml" required="false" cssClass="text medium" /></td>
			<td width=50%><s:textfield key="user.phoneNumber" theme="xhtml"
					cssClass="text medium" /></td>
		</tr>
		<tr>
			<td width=50%><s:textfield key="user.lastName" theme="xhtml"
					required="false" cssClass="text medium" /></td>
			<td width=50%><s:textfield key="user.firstName" theme="xhtml"
					required="false" cssClass="text medium" /></td>
		</tr>
		<tr>
			<td colspan=2><s:textfield key="user.website" required="false"
					cssClass="text large" /></td>
		</tr>
	</table>
	<li class="buttonBar bottom"><s:submit key="button.addlab"
			cssClass="button" /> <s:submit key="button.cancel" name="cancel"
			cssClass="button" /></li>
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
<c:if test="${param.from == 'list'}">
    selectAll('userRoles');
</c:if>
}
</script>
