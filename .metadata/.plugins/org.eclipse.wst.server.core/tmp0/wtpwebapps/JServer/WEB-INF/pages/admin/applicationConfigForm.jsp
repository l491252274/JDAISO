<%@ include file="/common/taglibs.jsp"%>

<script>
function init(){
	setSize();
}
 
function setSize() { // check all the checkboxes in the list
  for (var i=0;i<document.forms[0].elements.length;i++) {
    var e = document.forms[0].elements[i];
        var eName = e.name;
        if ((e.type.indexOf("textarea") == 0)) {
        	e.style.width="95%";
        }
    }
}
window.onresize = setSize;
</script>

<head>
    <title><fmt:message key="applicationConfig.title"/></title>
    <meta name="heading" content="<fmt:message key='applicationConfig.heading'/>"/>
</head>


<body onload="init();">
	<p align="center">
	<table width="100%" border="0" align="center">
		<tr><td>
		<div id="divsource" style="display:block;">
			<s:form id="form1" name="form1" action="updateApplicationConfig" method="post">
                                <textarea cols="" rows="20" name="applicationConfig" id="applicationConfig">${applicationConfig}</textarea>
				<p align=center><input type=submit name=submit value=<fmt:message key="button.submit"/>></p>
			</s:form>
		</div>
		</td></tr>
	</table>
</body>