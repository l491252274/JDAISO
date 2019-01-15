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
    <meta name="menu" content="${param.menu}"/>
    <meta name="submenu" content="${param.submenu}"/>
</head>
<body onload="init();">
	<h1><p align="center">Add Game Attends</p></h1>
	<p align="center">
	<table width="100%" border="0" align="center">
		<tr><td>
		<div id="divsource" style="display:block;">
			<s:form id="form1" name="form1" action="addGameAttends" method="post">
				<s:hidden key="gameProblem.id" />
				<h1><p align="center"><c:out value="${gameProblem.id}"/>&nbsp;<c:out value="${gameProblem.title}"/></p></h1>
				<s:textarea tooltip="Enter Data" label="Source" name="addGameAttendsData" rows="12" />
				<p align=center><font color=red>per user name per line</font></p>
				<p align=center><input type=submit name=submit value=submit><INPUT type=reset value=Reset name=reset></p>
			</s:form>
		</div>
		</td></tr>
	</table>
</body>

