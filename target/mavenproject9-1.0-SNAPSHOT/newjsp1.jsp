<%-- 
    Document   : newjsp1
    Created on : Jun 9, 2021, 2:48:25 AM
    Author     : LENOVO
--%>

<%@page import="java.util.Enumeration"%>
<%@page language = "java" contentType = "text/html"%>
<html xmlns = "http://www.w3c.org/1999/xhtml"
   xmlns:jsp = "http://java.sun.com/JSP/Page">


<jsp:useBean id ="demo" scope = "request" class="javabean.NhanVien">
    <jsp:setProperty name ="demo" property="name" value="Phan Van Minh"></jsp:setProperty>   
</jsp:useBean>    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p>get isSecure: <%= request.getInputStream()%></p>
    </body>
</html>
