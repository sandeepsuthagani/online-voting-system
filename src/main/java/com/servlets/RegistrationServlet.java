package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.database.VotingBin;
import com.domain.Register;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegistrationServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("regpwd");
        String aadhaar = request.getParameter("aadhaar");

        // Validate required fields
        if (isEmpty(fname) || isEmpty(lname) || isEmpty(uname) || isEmpty(pwd) || isEmpty(aadhaar)) {
            forwardWithAlert(request, response, "All fields are required!", "/styles/register.jsp");
            return;
        }

        VotingBin vb = new VotingBin();

        // Aadhaar validation (using VotingBin method)
        try {
            String aadhaarError = vb.validateAadhaar(aadhaar);
            if (aadhaarError != null) { // if any error found
                forwardWithAlert(request, response, aadhaarError, "/styles/register.jsp");
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            forwardWithAlert(request, response, "Error checking Aadhaar: " + e.getMessage(), "/styles/register.jsp");
            return;
        }

        // Continue registration
        Register reg = new Register();
        reg.setfName(fname);
        reg.setlName(lname);
        reg.setUsername(uname);
        reg.setPassword(pwd);

        try {
            vb.loginRegistration(reg);
            int count = vb.loginCheck(reg);

            if (count > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("alertMessage", "Registration Successful!");
                response.sendRedirect(request.getContextPath() + "/styles/login.jsp");
            } else {
                forwardWithAlert(request, response, "Registration Failed - Username may already exist", "/styles/register.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            forwardWithAlert(request, response, "Database error: " + e.getMessage(), "/styles/register.jsp");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void forwardWithAlert(HttpServletRequest request, HttpServletResponse response, String message, String page)
            throws ServletException, IOException {
        request.setAttribute("alertMessage", message);
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }
}
