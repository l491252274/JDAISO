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

<script>
	var bWidth=0;
	//alert(getCookie("leftState"));
	function init(){
	    document.getElementById("leftside").style.width="165px";
	    document.getElementById("rightside").style.width="10px";
	    document.getElementById("content").style.margin="0px 20px 0px 190px";
	    document.getElementById("constrictionIMG").src="<c:url value='/styles/${appConfig["csstheme"]}/images/constriction_open.jpg'/>"
	    document.getElementById("profile").style.display="none";
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
</script>

<%
        LocalizationContext locCtxt = BundleSupport.getLocalizationContext(pageContext);
        ResourceBundle bundle = locCtxt.getResourceBundle();
%>
<c:set var="currentUser" value="${currentUser}"/>
<c:set var="project_name" value="${appConfig['project.name']}"/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
	<BASE HREF="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/${appConfig['project.name']}/"/>
        <BASE target="main"/>
        <%@ include file="/common/meta.jsp" %>
        <title><fmt:message key="webapp.name"/></title>

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

        <script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/swfobject.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/flash/chart/js/chart.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/dtree/dtree.js'/>"></script>
        <style type="text/css">
        body{font-size:12px;
        }
        #divTreeView{
        float:left;
        width:165px;
        height:300px;
        overflow:auto;
        border-style:solid;
        border-color:#999999;
        border-width:1px;
        }
        .nodeInfo{
        float:left;
        margin-left:20px;
        }
        </style>
	</head>

    <%
        pageContext.setAttribute("badgeImage", "");
    %>
   	<body onload="init();" style="background:#f0f0f0; background-image: url(/${appConfig['project.name']}/styles/${appConfig['csstheme']}/images/bodybg.jpg);background-repeat:repeat-x;">
		<div id="leftcontainer">
           <%
            String color = "#606060";
            if(pageContext.getAttribute("currentUser")!=null)
            {
                User u = (User)pageContext.getAttribute("currentUser");
                color = RankColor.getRankColor(0);
            }
            pageContext.setAttribute("color", color);
            %>
                    <b><font color="${color}"><fmt:message key="user.status"/><br>${pageContext.request.remoteUser}
                            <c:if test="${currentUser.nick!=null && currentUser.nick!=''}">
                            (${currentUser.htmlNick})
                            </c:if>
                            </b><br/>

                            <br/>${badgeImage}
                            </font>
                            <font color="#00CCFF"><br/>${UserRankInGrade}</fornt>
                    <c:set var="currentMenu" value="${oj_menu}"/>
                    <c:set var="currentSubmenu" value="${oj_submenu}"/>
                    <c:if test="${currentMenu!=null and currentMenu!=''}">
                    <p>
                                <menu:useMenuDisplayer name="Velocity" config="cssLeftsideMenu.vm" permissions="rolesAdapter">
                                    <menu:displayMenu name="${currentMenu}"/>
                                </menu:useMenuDisplayer>
                    </p>
                    </c:if>
                    <hr style="border-style:dashed;color:#666666;}"/>
                    <%
                            List<OjTreeNode> currentOjTreeNodes = null;
                            ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getSession().getServletContext());
                            OjTreeNodeManager ojTreeNodeManager = (OjTreeNodeManager) ctx.getBean("ojTreeNodeManager");
                            String connectPointString = "";
                            Object tmpObj = pageContext.getAttribute("currentMenu");
                            if(tmpObj!=null)
                                connectPointString = (String)tmpObj;
                            else
                                connectPointString = "";
                            tmpObj = pageContext.getAttribute("currentSubmenu");
                            if(tmpObj!=null)
                                connectPointString = connectPointString + "-" + (String)tmpObj;
                            else
                                connectPointString = connectPointString + "-" ;
                            connectPointString = connectPointString.toLowerCase();
                            OjTreeNode obj2 = ojTreeNodeManager.getOjTreeNodeByConnectPoint(connectPointString);
                            if(obj2 != null)
                                {
                                currentOjTreeNodes = ojTreeNodeManager.getOffspringOjTreeNodeByPid(obj2.getId());
                                User u = (User)pageContext.getAttribute("currentUser");
                                for(OjTreeNode item:currentOjTreeNodes)
                                	if(item.getDoors()!=null && !item.getDoors().equals("")&& !u.haveKeys(item.getDoors())) item.setOpened(0);
                                	else item.setOpened(1);
                                	
                                }

                            pageContext.setAttribute("currentTreeNodes", currentOjTreeNodes);
                    %>
                    <c:if test="${currentTreeNodes!=null}">
                    <div id="divTreeView" class="dtree">
                        <p style="margin-left:0px; margin-bottom:6px;"><a href="javascript: tree.openAll();">[<font color="#026D84"><fmt:message key="ojTreeNode.openAll"/></font>]</a> | <a href="javascript: tree.closeAll();">[<font color="#026D84"><fmt:message key="ojTreeNode.closeAll"/></font>]</a></p>
                         <script type="text/javascript">
                            <!--
                               tree = new dTree('tree');
                             <%
                               Object obj = pageContext.getAttribute("currentTreeNodes");
                               if (obj!=null)
                               {
                                       List<OjTreeNode> treeNodes = (List<OjTreeNode>)obj;
                                       int i=1;
		                               long minPid=99999;
		                               for(OjTreeNode item: treeNodes)
		                               {
		                                   if(item.getPid().longValue()<minPid)
		                                       minPid = item.getPid().longValue();
		                               }
		                               for(OjTreeNode item: treeNodes)
		                               {
		                                   if(item.getType()<100)
		                                   {
			                                   	if(item.getOpened()==0)
			                                   	{
				                                   out.print("tree.add(" + (item.getId().longValue()-minPid-1) +
				                                       "," + (item.getPid().longValue()-minPid-1) +
				                                       ",'" + item.getName() +
				                                       "','" + item.getUrl() + "',null,null,tree.icon.lock);\n");
			                                   	}else
			                                   	{
		                                           out.print("tree.add(" + (item.getId().longValue()-minPid-1) +
		                                                   "," + (item.getPid().longValue()-minPid-1) +
		                                                   ",'" + item.getName() +
		                                                   "','" + item.getUrl() + "');\n");
			                                   	}
		                                    }
		                                }
                               }
                             %>
                             
                            document.write(tree);
                            tree.openAll();
                            //-->
                            </script>
                    </div>
                    </c:if>

                    <img src="/${appConfig['project.name']}/images/colors.jpg" height="104" width="145" class="thumbnail" alt="Included colors" style="margin: 5px;"/>
                </div>             
        </body>

