<%@ include file="/common/taglibs.jsp"%>

<%@page import="java.util.*"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="com.unlimited.oj.model.*"%>
<%@page import="com.unlimited.oj.service.*"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.apache.taglibs.standard.tag.common.fmt.BundleSupport"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="javax.servlet.jsp.jstl.fmt.LocalizationContext"%>

<html>
<head>
<base target="main1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<link href="style/authority/zTreeStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="scripts/zTree/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"/></link>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>


<script type="text/javascript" src="<c:url value='/scripts/dtree/dtree.js'/>"></script>

<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<c:set var="currentUser" value="${currentUser}"/>
<c:set var="project_name" value="${appConfig['project.name']}"/>
<c:set var="connectpoint" value="${connectpoint}"/>
<style type="text/css">
   div,h1,ul,li,p{
	   margin: 8;
	   padding: 0;
    }

	#sider{
		position: absolute;
		top: 0;
		left: 25px;
		bottom: 0px;
		width: 660px;
		border: 1px solid #DEDFDF;
	}
	
	#main{
		position: absolute;
		top: 0;
		left: 685px;
		right: 0px;
		bottom: 0px;
		border: 1px solid #DEDFDF;
		overflow: auto;
	}
	#box_border {
		border: 1px solid #EDEDED;
		height: 50px;
		line-height: 50px;
		text-align: center;
	}
	#fang_type {
		list-style-type: none;
	}
	#fang_type li{
		width: 80px;
		height: 22px;
		line-height: 22px;
		color: #FFF;
		display: inline-block;
	}
	
	.fy_table_td{
		color: #fff;
	}
	
	.fang_th{
		background: #044599 no-repeat;
		text-align: center;
		border-left: 1px solid #02397F;
		border-right: 1px solid #02397F;
		border-bottom: 1px solid #02397F;
		border-top: 1px solid #02397F;
		letter-spacing: 2px;
		text-transform: uppercase;
		font-size: 14px;
		color: #fff;
		height: 37px;
	}
</style>	

</head>
<body>
		<div id="container">
			<div id="sider">
                  <c:set var="currentTreeNodes" value="${menuList}"/>
                    <c:if test="${currentTreeNodes!=null}">
                        <p style="margin-left:0px; margin-bottom:6px;"><a href="javascript: tree.openAll();">[<font color="#026D84"><fmt:message key="ojTreeNode.openAll"/></font>]</a> | <a href="javascript: tree.closeAll();">[<font color="#026D84"><fmt:message key="ojTreeNode.closeAll"/></font>]</a></p>
                         <script type="text/javascript">
                            <!--
                               tree = new dTree('tree');
                               <%
                               Object obj = pageContext.getAttribute("currentTreeNodes");
                               if (obj!=null)
                               {
                                       List<Menu> treeNodes = (List<Menu>)obj;
                                       int i=1;
		                               long minPid=99999;
		                               for(Menu item: treeNodes)
		                               {
		                                   if(item.getParentID().longValue()<minPid)
		                                       minPid = item.getParentID().longValue();
		                               }
		                               for(Menu item: treeNodes)
		                               {
		                                   	if(!item.getVisible())
		                                   	{
			                                   out.print("tree.add(" + (item.getId().longValue()-minPid-1) +
			                                       "," + (item.getParentID().longValue()-minPid-1) +
			                                       ",'" + item.getResourceName() + "&nbsp;&nbsp;&nbsp;&nbsp;(" + item.getAuthority() + ")" +
			                                       "','common_menu_edit_ADMIN.html?menuId=" + item.getId() + "',null,null,tree.icon.lock,tree.icon.lock);\n");
		                                   	}else
		                                   	{
                                           		out.print("tree.add(" + (item.getId().longValue()-minPid-1) +
                                                        "," + (item.getParentID().longValue()-minPid-1) +
                                                        ",'" + item.getResourceName() + "&nbsp;&nbsp;&nbsp;&nbsp;(" + item.getAuthority() + ")" +
                                                        "','common_menu_edit_ADMIN.html?menuId=" + item.getId() + "');\n");
		                                   	}
		                                }
                               }
                             %>
                             
                            document.write(tree);
                            tree.closeAll();
                            //-->
                            </script>
                    </c:if>				
				
			</div>
            
			<div id="main">
				<iframe name="main1" width=100% height=100% frameborder=0 scrolling=auto src=></iframe>
			</div>
		</div>

<div style="display:none"><script src='' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>