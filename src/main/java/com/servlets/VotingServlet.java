package com.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.database.VotingBin;
import com.domain.Vote;

@WebServlet("/VotingServlet")
public class VotingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VotingServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        Vote vt = new Vote();
        VotingBin vb = new VotingBin();
        int count = 0;

        vt.setUsername(request.getParameter("username"));
        vt.setVotes(request.getParameter("vote"));

        try {
            count = vb.voteCheck(vt);
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
            request.setAttribute("message", "Database error: " + e1.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("/styles/vote.jsp");
            rd.forward(request, response);
            return;
        }

        if (count > 0) {
            request.setAttribute("message", "Sorry! Your vote has already been registered!");
        } else {
            try {
                vb.voteRegistration(vt);
                request.setAttribute("message", "Your vote has been registered. Thank you :)");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "Database error: " + e.getMessage());
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/styles/vote.jsp");
        rd.forward(request, response);
    }
}
