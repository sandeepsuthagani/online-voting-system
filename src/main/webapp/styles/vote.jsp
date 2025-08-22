<%@page import="com.domain.Vote"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/style.css" />
<title>Voting System</title>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username"))
                userName = cookie.getValue();
        }
    }
    if (userName == null)
        response.sendRedirect("login.jsp");
%>
</head>
<body>
    <div id="page">
        <div id="header">
            <div id="section">
                <div style="text-align: center; color: white; margin: 0px 0 0px 324px;">
                    <h1>Online Voting System</h1>
             
                </div>
            </div>
            <ul>
               <li><a href="<%= request.getContextPath() %>/styles/vote-stats.jsp">voting statistics</a></li>

       
                <li><a href="contact.jsp">Contact us</a></li>
                
                <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
                <li><a class="welcome"><b>Welcome! <%=userName%></b></a></li>
            </ul>
            <div id="tagline">
                <div>
                    <h2 style="margin: 0px 0px 0px 315px;">Please register your vote below</h2>
                    <div style="padding: 0px 0px 21px 0;">
                        <center>
                            <form method="POST" action="<%= request.getContextPath() %>/VotingServlet">
                                <input type="hidden" name="username" id="username" value="<%=userName%>">
                                <table cellspacing=0 cellpadding="10">
                                    <tr>
                                        <td align=left style="color: #295071">Click to Vote:</td>
                                        <td align=left><input type="radio" name="vote" value="Java"/>Java</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td align=left><input type="radio" name="vote" value="Dot Net"/>Dot Net</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td align=left><input type="radio" name="vote" value="Python"/>Python</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>
                                            <button name="submit" type="submit" id="submit"
                                                style="width: 96px; height: 36px; font-size: 16px; color: #295071; margin: 0 0 0 -56px;">Submit</button>
                                        </td>
                                    </tr>
                                </table>
                                <%
  String msg = (String) request.getAttribute("message");
  if (msg == null) msg = "";
%>
<p style="width: 500px; font-size: 16px; color: #c8085f;"><%=msg%></p>

                               
                            </form>
                        </center>
                    </div>
                </div>
            </div>

            <div id="footer">
                <div class="section">
                    <p style="text-align: center;">Empower Democracy.</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
