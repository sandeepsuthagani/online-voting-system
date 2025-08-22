<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/style.css">
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
    %>
</head>
<body>
    <div id="page">

        <!-- Title Section -->
        <div class="header-title">
            <h1>Online Voting System</h1>
            <h1>Welcome! Voter</h1>
        </div>

        <!-- Navigation Header -->
        <div id="header">
            <ul>
                

                <%
                    if (userName == null) {
                %>
                 <li class="current"><a href="<%= request.getContextPath() %>/styles/index.jsp">Project Description</a></li>
                <li><a href="<%= request.getContextPath() %>/styles/contact.jsp">Contact us</a></li>
                <li><a href="<%= request.getContextPath() %>/styles/login.jsp">Login</a></li>
                 <li><a href="<%= request.getContextPath() %>/styles/register.jsp">Register</a></li>

                    
                <%
                    } else {
                %>
                    <li><a href="#">Welcome <%= userName %></a></li>
                    <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
                    <li><a href="<%= request.getContextPath() %>/styles/vote.jsp">Vote Here</a></li>
                    <li><a href="<%= request.getContextPath() %>/styles/vote-stats.jsp">Voting Statistics</a></li>
                  
                <%
                    }
                %>
            </ul>
        </div>

        <!-- Tagline / Description -->
        <div id="tagline">
            <div>
                "Online Voting System" is a simple web-based online voting system that helps our college discover students' most preferred language for software development. Online voting (also known as e-voting) uses electronic systems to aid casting and counting votes. <br><br>
                Remote e-Voting is where voting is performed within the voter's sole influence, and is not physically supervised by representatives (e.g. voting from one's personal computer, mobile phone, or television via the internet—also called i-voting). Electronic voting technology can speed the counting of ballots and provide efficiency in statistics. <br><br>
                In this project, students must register first by clicking on the "Sign Up" button to vote for their preferred development language. Once registered, they can sign in through the "Student Login" page with their respective username and password. After successful sign-in, they can go to the "Vote Here" tab and register their vote by selecting any one language of their choice and hitting the "Submit" button. One vote per student is allowed. In the "Voting Statistics" tab, students can see the total number of votes received for each development language. Thank you!
            </div>
        </div>

        <!-- Footer -->
        <div id="footer">
            <div class="section">
                <p style="text-align: center;"></p>
            </div>
        </div>

    </div>
</body>
</html>
