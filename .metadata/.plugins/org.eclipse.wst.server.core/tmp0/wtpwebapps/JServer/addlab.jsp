<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page  import="java.util.*" %>
<%@ page  import="java.sql.PreparedStatement" %>
<%@ page  import="java.sql.DriverManager" %>
<%@ page  import="java.sql.Connection" %>
<%@ page  import="com.unlimited.webserver.model.Lab" %>
<%@ page  import="com.unlimited.webserver.service.impl.LabManagerImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
 <body>
        <%
        	
            request.setCharacterEncoding("UTF-8");//设置统一字符编码
            
            String labid = request.getParameter("labid");
            String roomid = request.getParameter("roomid");
            String floor = request.getParameter("floor");
            Lab TargetLab = new Lab();
            LabManagerImpl handle = new LabManagerImpl();
            TargetLab.setFloor(floor);
            TargetLab.setId(Long.parseLong(labid));
            TargetLab.setRoomid(roomid);
          %>
          <h1><%=labid %></h1><br>
        <h1><%=roomid%></h1><br>
        <h1><%=floor%></h1><br>
        <% handle.saveLab(TargetLab);%>
             
            
        
            
	
         
         
    </body>
</html>