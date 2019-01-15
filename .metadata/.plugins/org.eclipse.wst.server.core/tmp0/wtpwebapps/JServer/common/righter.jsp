<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.io.*"%>

<c:set var="username" value="${currentUser.username}"/>
<%
    String name = (String)pageContext.getAttribute("username");

	File file = new File(pageContext.getServletContext().getRealPath("/") + "/WEB-INF/pages/profile/" + name + "/profile.htm");
	pageContext.setAttribute("exist", file.exists());
	
%>
	<table height="100%" border="0" width="100%">
	    <tbody><tr>
	        <td id="profile" valign="top">
			   <c:if test="${cookieLogin != 'true' && currentUser!=null}">
				<div  style="margin-left:10pt;">

				<c:if test="${exist}">
	            	<jsp:include page="/WEB-INF/pages/profile/${currentUser.username}/profile.htm"/>
	            </c:if>
				</div>
	   		   </c:if>
		    </td>
   	    </tr>
	</tbody></table>