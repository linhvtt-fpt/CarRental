<%-- 
    Document   : VerifyEmail
    Created on : Feb 28, 2021, 9:06:47 PM
    Author     : Thuy Linh
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <font size="+4"> Verify </font>
        <font color="red"> ${requestScope.ErrorVerify}</font></br>
        <form action="verify" method="POST">
            <input type="text" name="txtOTP" value="" />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
