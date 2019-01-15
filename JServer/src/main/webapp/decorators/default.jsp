<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
<script type="text/javascript">
	$(document).ready(function(){
		/** add   **/
	    $("#addBtn").fancybox({
	    	'href'  : 'house_edit.html',
	    	'width' : 733,
	        'height' : 530,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	window.location.href = 'borrow_closed.html';
	        }
	    });
		
	    /** import  **/
	    $("#importBtn").fancybox({
	    	'href'  : '/xngzf/archives/importFangyuan.action',
	    	'width' : 633,
	        'height' : 260,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	window.location.href = 'borrow_closed.html';
	        }
	    });
		
	    /** view   **/
	    $("a.edit").fancybox({
	    	'width' : 733,
	        'height' : 503,
	        'type' : 'iframe',
	        'hideOnOverlayClick' : false,
	        'showCloseButton' : false,
	        'onClosed' : function() { 
	        	window.location.href = 'borrow_closed.html';
	        }
	    });
	});

	var userRole = '';


	function search(){
		$("#submitForm").attr("action", "borrow_closed.html?page=" + 1).submit();
	}

	/** add   **/
	function add(){
		$("#submitForm").attr("action", "/xngzf/archives/luruFangyuan.action").submit();	
	}
	 
	/** Excel import  **/
	function exportExcel(){
		if( confirm('sure?') ){
			var fyXqCode = $("#fyXq").val();
			var fyXqName = $('#fyXq option:selected').text();
//	 		alert(fyXqCode);
			if(fyXqCode=="" || fyXqCode==null){
				$("#fyXqName").val("");
			}else{
//	 			alert(fyXqCode);
				$("#fyXqName").val(fyXqName);
			}
			$("#submitForm").attr("action", "/xngzf/archives/exportExcelFangyuan.action").submit();	
		}
	}
	
	/** delete **/
	function del(fyID){
		if(fyID == '') return;
		if(confirm("sure?")){
			$("#submitForm").attr("action", "/xngzf/archives/delFangyuan.action?fyID=" + fyID).submit();			
		}
	}
	
	/** batch delete **/
	function batchDel(){
		if($("input[name='IDCheck']:checked").size()<=0){
			art.dialog({icon:'error', title:'Tip', drag:false, resize:false, content:'at least one', ok:true,});
			return;
		}

		var allIDCheck = "";
		$("input[name='IDCheck']:checked").each(function(index, domEle){
			bjText = $(domEle).parent("td").parent("tr").last().children("td").last().prev().text();
			if($.trim(bjText)=="closed"){
// 				$(domEle).removeAttr("checked");
				$(domEle).parent("td").parent("tr").css({color:"red"});
				$("#resultInfo").html("closed, invalid");
			}else{
				allIDCheck += $(domEle).val() + ",";
			}
		});
		if(allIDCheck.length>0) {
			allIDCheck = allIDCheck.substring(0, allIDCheck.length-1);
			$("#allIDCheck").val(allIDCheck);
			if(confirm("sure?")){
				// submit form
				$("#submitForm").attr("action", "/xngzf/archives/batchDelFangyuan.action").submit();
			}
		}
	}

	/** jump **/
	function jumpNormalPage(page){
		$("#submitForm").attr("action", "borrow_closed.html?page=" + page).submit();
	}
	
	/** inpur page number **/
	function jumpInputPage(totalPage){
		if($("#jumpNumTxt").val() != ''){
			var pageNum = parseInt($("#jumpNumTxt").val());
			if(pageNum<1 | pageNum>totalPage){
				art.dialog({icon:'error', title:'Tip', drag:false, resize:false, content:'invalid', ok:true,});
				pageNum = 1;
			}
			$("#submitForm").attr("action", "borrow_closed.html?page=" + pageNum).submit();
		}else{
			art.dialog({icon:'error', title:'Tip', drag:false, resize:false, content:'invalid', ok:true,});
			$("#submitForm").attr("action", "borrow_closed.html?page=" + 1).submit();
		}
	}
</script>
<script>
	var bWidth=0;
	//alert(getCookie("leftState"));
	function init(){ 
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="10px";
	    document.getElementById("content").style.margin="0px 20px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_open.jpg'/>"
	    document.getElementById("profile").style.display="none";

		time();
	}

	function leftState(){
	  var bWidth=parseInt(document.documentElement.clientWidth);
	  if(document.getElementById("constrictionIMG").src.indexOf("constriction_open.jpg")==-1)
	  {
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="10px";
	    document.getElementById("content").style.margin="0px 20px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_open.jpg'/>"
	    document.getElementById("profile").style.display="none";
	    deleteCookie("leftState");
	    setCookie("leftState", "0");
	  }
	  else
	  {   
	  	document.getElementById("leftside").style.width="165px";
	  	document.getElementById("rightside").style.width="190px";
	    document.getElementById("content").style.margin="0px 190px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_close.jpg'/>"
	    document.getElementById("profile").style.display="";
	    deleteCookie("leftState");
	    setCookie("leftState", "1");
	  }
	}
	
    function time() 
    {
        t_div = document.getElementById("showtime");
        var now = new Date();
        t_div.innerHTML = now.getFullYear();
    }
</script>
	
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>

        <%@ include file="/common/meta.jsp" %>
        <title><decorator:title/> | <fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

        <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/swfobject.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/chart.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/dtree/dtree.js'/>"></script>
        <decorator:head/>
	</head>

	<body onload="init();" style="background:#FFFFFF;">
		<div id="container">
			<div id="wrap">

				<div id="content">
					<table height="100%" border="0" style="width: 98%;">
					    <tbody><tr>
					        <td valign="top">
				                <%@ include file="/common/messages.jsp" %>
			       	        	<h1><decorator:getProperty property="meta.heading"/></h1>
				    	        <decorator:body/>
						    </td>
				   	    </tr>
					</tbody></table>
				</div>
				<div class="clearingdiv">&nbsp;</div>
			</div>
		</div>
		<div id="footer">
             <span class="left"><fmt:message key="webapp.version"/> |
                 <span id="validators">
                     <a href="mailto:checkie@vip.qq.com"><fmt:message key="designer"/></a> |
                 </span>
             </span>
             <span class="right">
                 &copy; 2009-${svrYear} <a href="<fmt:message key="company.url"/>"><fmt:message key="company.name"/></a>
	     </div>
	     <div id="showtime"></div>
	</body>
</html>