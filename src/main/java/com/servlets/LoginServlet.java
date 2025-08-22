package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.database.VotingBin;
import com.domain.Register;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/styles/login.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        Register reg = new Register();
        reg.setUsername(request.getParameter("username"));
        reg.setPassword(request.getParameter("password"));

        VotingBin vb = new VotingBin();
        int count = 0;

        try {
            count = vb.loginCheck(reg);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("alertMessage", "Database error: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/styles/login.jsp");
            rd.forward(request, response);
            return;
        }

        if (count == 1) {
            Cookie loginCookie = new Cookie("username", reg.getUsername());
            loginCookie.setMaxAge(30 * 60); // 30 minutes
            response.addCookie(loginCookie);
            response.sendRedirect(request.getContextPath() + "/styles/index.jsp");
        } else {
            request.setAttribute("alertMessage", "Please check your Username or Password!");
            RequestDispatcher rd = request.getRequestDispatcher("/styles/login.jsp");
            rd.forward(request, response);
        }
    }
}
