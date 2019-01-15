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
	<h1><p align="center">Set Batch Star Problems</p></h1>
	<p align="center">
	<table width="100%" border="0" align="center">
		<tr><td>
		<div id="divsource" style="display:block;">
			<s:form id="form1" name="form1" action="admin_starProblem_saveBatch_ADMIN" method="post">
				<h1><p align="center">Format:&nbsp;source,&nbsp;problem ID,&nbsp;star,&nbsp;publishFlag(1 Yes;0 No)</p></h1>
				<s:textarea tooltip="Enter Data" name="setBatchStarProblems" rows="12" />
				<p align=center><font color=red>per problem per line</font></p>
				<p align=center><input type=submit name=submit value=submit><INPUT type=reset value=Reset name=reset></p>
			</s:form>
		</div>
		</td></tr>
	</table>
</body>

