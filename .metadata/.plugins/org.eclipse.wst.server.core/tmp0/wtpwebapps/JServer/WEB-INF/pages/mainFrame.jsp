<%@ include file="/common/taglibs.jsp" %>
<%@page import="java.util.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.service.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<link href="style/authority/main_css.css" rel="stylesheet" type="text/css" />
	<link href="style/authority/zTreeStyle.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="scripts/zTree/jquery.ztree.core-3.2.js"></script>
	<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
	
	<script type="text/javascript">
		/**退出系统**/
		function logout(){
			if(confirm("Quit?")){
				window.location.href = "/${appConfig['project.name']}/logout.jsp";
			}
		}
		
		/**获得当前日期**/
		function  getDate01(){
			var time = new Date();
			var myYear = time.getFullYear();
			var myMonth = time.getMonth()+1;
			var myDay = time.getDate();
			if(myMonth < 10){
				myMonth = "0" + myMonth;
			}
			if(myDay < 10){
				myDay = "0" + myDay;
			}
			//document.getElementById("yue_fen").innerHTML =  myYear + "." + myMonth;
			document.getElementById("day_day").innerHTML =  myYear + "." + myMonth + "." + myDay;
		}
	</script>
	<script type="text/javascript">
		/* zTree插件加载目录的处理  */
		var zTree;
		
		var setting = {
				view: {
					dblClickExpand: false,
					showLine: false,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				data: {
					key: {
						name: "resourceName"
					},
					simpleData: {
						enable:true,
						idKey: "resourceID",
						pIdKey: "parentID",
						rootPId: ""
					}
				},
				callback: {
	// 				beforeExpand: beforeExpand,
	// 				onExpand: onExpand,
					onClick: zTreeOnClick			
				}
		};
		 
		var curExpandNode = null;
		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
				if (treeNode !== treeNodeP.children[i]) {
					zTree.expandNode(treeNodeP.children[i], false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}
	
		}
		function singlePath(newNode) {
			if (newNode === curExpandNode) return;
			if (curExpandNode && curExpandNode.open==true) {
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							for (var i = Math.min(newParents.length, oldParents.length)-1; i>=0; i--) {
								if (newParents[i] !== oldParents[i]) {
									zTree.expandNode(oldParents[i], false);
									break;
								}
							}
						}else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		}
	
		function onExpand(event, treeId, treeNode) {
			curExpandNode = treeNode;
		}
		
		/** 用于捕获节点被点击的事件回调函数  **/
		function zTreeOnClick(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("dleft_tab1");
			zTree.expandNode(treeNode, null, null, null, true);
	// 		zTree.expandNode(treeNode);
			// 规定：如果是父类节点，不允许单击操作
			if(treeNode.isParent){
	// 			alert("父类节点无法点击哦...");
				return false;
			}
			// 如果节点路径为空或者为"#"，不允许单击操作
			if(treeNode.accessPath=="" || treeNode.accessPath=="#"){
				//alert("节点路径为空或者为'#'哦...");
				return false;
			}
		    // 跳到该节点下对应的路径, 把当前资源ID(resourceID)传到后台，写进Session
		    rightMain(treeNode.accessPath);
		    
		    if( !treeNode.isParent ){
			    $('#here_area').html('<fmt:message key="label.currentposition"/>:&nbsp;<span style="color:#1A5CC6">'+treeNode.getParentNode().resourceName+'&nbsp;>&nbsp;'+treeNode.resourceName+'</span>');
		    }else{
			    $('#here_area').html('<fmt:message key="label.currentposition"/>:&nbsp;<span style="color:#1A5CC6">'+treeNode.resourceName+'</span>');
		    }
		};
		
		/* 上方菜单 */
		function switchTab(tabpage,tabid){
		var oItem = document.getElementById(tabpage).getElementsByTagName("li"); 
		    for(var i=0; i<oItem.length; i++){
		        var x = oItem[i];    
		        x.className = "";
			}
			if('left_tab1' == tabid){
				$(document).ajaxStart(onStart).ajaxSuccess(onStop);
				// 异步加载"业务模块"下的菜单
			  	loadMenu('application', 'dleft_tab1');
			}else  if('left_tab2' == tabid){
				$(document).ajaxStart(onStart).ajaxSuccess(onStop);
				// 异步加载"其他"下的菜单
				loadMenu('config', 'dleft_tab1');
			}
		}
		
		
		$(document).ready(function(){
			$(document).ajaxStart(onStart).ajaxSuccess(onStop);
			/** 默认异步加载"业务模块"目录  **/
			loadMenu('application', "dleft_tab1");
			// 默认展开所有节点
			if( zTree ){
				// 默认展开所有节点
				zTree.expandAll(true);
			}
		});
		
		function loadMenu(resourceType, treeObj){
			$.ajax({
				type:"GET",
				url:"${dynamicURL}/${appConfig['project.name']}/common_menu_get_PUBLIC.html?resourceType=" + resourceType,
				dataType : "json",
				success:function(data){
					// 如果返回数据不为空，加载"业务模块"目录
					//alert(data)
		            if(data != null){
		                // 将返回的数据赋给zTree
						$.fn.zTree.init($("#"+treeObj), setting, data);
						zTree = $.fn.zTree.getZTreeObj(treeObj);
		                if( zTree ){
		                    // 默认展开所有节点
		                    zTree.expandAll(true);
		                }
		            }
				}
			});
		}
		
		//ajax start function
		function onStart(){
			$("#ajaxDialog").show();
		}
		
		//ajax stop function
		function onStop(){
	// 		$("#ajaxDialog").dialog("close");
			$("#ajaxDialog").hide();
		}
	</script>
</head>
<c:set var="currentUser" value="${currentUser}"/>
<c:set var="project_name" value="${appConfig['project.name']}"/>
<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();

        pageContext.setAttribute("badgeImage", "");
 %>
    
<script type="text/javascript">
		function show() {    
		    var objDiv = $("#mydiv");
		    $(objDiv).css("display","block");
		}
		function hide() {
		    var objDiv = $("#mydiv");
		    $(objDiv).css("display", "none");
		} 
		setTimeout(show,3000);
		setTimeout(hide,10000);
</script>
<style>
   .touming .font{ font-size:14px; color:#FF0}
  .tm11{position:relative; width:483; height:568;}
 .tm22{   position:absolute;  left:0px;   top:0px; width: 483px;  height: 568px;  }
  .alpha{filter:alpha(opacity=50);width:483px;} 
</style>
<%
        pageContext.setAttribute("logo", ApplicationConfig.getValue("LoginLogo"));
        pageContext.setAttribute("mainPage", ApplicationConfig.getValue("mainPage"));
%>
<body onload="getDate01()">
    <div id="top">
		<div id="top_logo" style="padding-left: 1px;">
			<img alt="logo" src="images/common/${logo}" width="544" height="49" style="vertical-align:middle;">
		</div>
		<div id="top_links">
			<div id="top_op">
				<ul>
					<li style="margin:20px;">
						<a onMouseOver="javascript:show();" onMouseOut=hide(); style="cursor:hand">
							<img alt="User" src="images/common/user.jpg">
						</a>
           <%
            String color = "#606060";

            pageContext.setAttribute("loginName", "");
            pageContext.setAttribute("color", color);
            %>
                    <b><font color="${color}">${badgeImage}${loginName}</b>
                        <c:if test="${appConfig['PlatformSecurityLevel']>0}">
                           
                        </c:if>
                            </font>                                                         
					</li>
					<li>
						<img alt="Today is" src="images/common/date.jpg">
						<span id="day_day"></span>
					</li>
                <c:if test="${appConfig['PlatformSecurityLevel']==0}">
                  	<li>
                      <font color=red><fmt:message key="label.level.0"/></font>
                    </li>
                </c:if>
                <c:if test="${appConfig['PlatformSecurityLevel']==1}">
                  	<li>
                      <font color=orange><fmt:message key="label.level.1"/></font>
                    </li>
                </c:if>
                <c:if test="${appConfig['PlatformSecurityLevel']==2}">
                  	<li>
                      <font color=blue><fmt:message key="label.level.2"/></font>
                    </li>
                </c:if>
				<c:if test="${appConfig['PlatformSecurityLevel']>0}">
					<div id="mydiv" style=" padding:1px 10px 1px 10px;  z-index:9999;width:200px;height:80px;position:absolute;display:none;border:1px;background-color:${color};">
						
					</div>
				</c:if>
				
				</ul> 
			</div>
			<div id="top_close">
				<a href="javascript:void(0);" onclick="logout();" target="_parent">
					<img alt="QUIT" title="QUIT" src="images/common/close.jpg" style="position: relative; top: 10px; left: 25px;">
				</a>
			</div>
		</div>
	</div>
    <!-- side menu start -->
	<div id="side">
		<div id="left_menu">
		 	<ul id="TabPage2" style="height:200px; margin-top:50px;">
				<li id="left_tab1" class="selected" onClick="javascript:switchTab('TabPage2','left_tab1');" title="application">
					<img alt="application" title="application" src="images/common/1_hover.jpg" width="33" height="31">
				</li>
				<li id="left_tab2" onClick="javascript:switchTab('TabPage2','left_tab2');" title="config">
					<img alt="config" title="config" src="images/common/2.jpg" width="33" height="31">
				</li>		
			</ul>
			
			
			<div id="nav_show" style="position:absolute; bottom:0px; padding:10px;">
				<a href="javascript:;" id="show_hide_btn">
					<img alt="Show/Hide" title="Show/Hide" src="images/common/nav_hide.png" width="35" height="35">
				</a>
			</div>
		 </div>
		 <div id="left_menu_cnt">
		 	<div id="nav_module">
		 		<img src="images/common/module_1.png" width="210" height="58"/>
		 	</div>
		 	<div id="nav_resource">
		 		<ul id="dleft_tab1" class="ztree"></ul>
		 	</div>
		 </div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#TabPage2 li').click(function(){
				var index = $(this).index();
				$(this).find('img').attr('src', 'images/common/'+ (index+1) +'_hover.jpg');
				$(this).css({background:'#fff'});
				$('#nav_module').find('img').attr('src', 'images/common/module_'+ (index+1) +'.png');
				$('#TabPage2 li').each(function(i, ele){
					if( i!=index ){
						$(ele).find('img').attr('src', 'images/common/'+ (i+1) +'.jpg');
						$(ele).css({background:'#303030'});
					}
				});
				// 显示侧边栏
				switchSysBar(true);
			});
			
			// 显示隐藏侧边栏
			$("#show_hide_btn").click(function() {
		        switchSysBar();
		    });
		});
		
		/**隐藏或者显示侧边栏**/
		function switchSysBar(flag){
			var side = $('#side');
	        var left_menu_cnt = $('#left_menu_cnt');
			if( flag==true ){	// flag==true
				left_menu_cnt.show(500, 'linear');
				side.css({width:'280px'});
				$('#top_nav').css({width:'77%', left:'304px'});
	        	$('#main').css({left:'280px'});
			}else{
		        if ( left_menu_cnt.is(":visible") ) {
					left_menu_cnt.hide(10, 'linear');
					side.css({width:'60px'});
		        	$('#top_nav').css({width:'100%', left:'60px', 'padding-left':'28px'});
		        	$('#main').css({left:'60px'});
		        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
		        } else {
					left_menu_cnt.show(500, 'linear');
					side.css({width:'280px'});
					$('#top_nav').css({width:'77%', left:'304px', 'padding-left':'0px'});
		        	$('#main').css({left:'280px'});
		        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
		        }
			}
		}
	</script>

    <!-- side menu start -->
    <div id="top_nav">
    <span id="here_area"><fmt:message key="label.currentposition"/>:&nbsp;<fmt:message key="mainMenu.title"/></span>
	</div>
    <div id="main">
      	<iframe name="right" id="rightMain" src="/${appConfig['project.name']}/${mainPage}.html" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true"/>
    </div>
</body>
</html>
   
 