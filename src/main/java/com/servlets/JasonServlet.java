package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.database.VotingBin;
import com.domain.Vote;

@WebServlet("/JasonServlet")
public class JasonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
try {
VotingBin vb = new VotingBin();
List<Vote> list = new ArrayList<>();
Vote counts = new Vote(); // for totals

try {
    // ✅ Get all individual votes
    List<Vote> dbList = vb.getAllVotes();
    if (dbList != null) {
        list = dbList;
    }

    // ✅ Get vote counts
    counts = vb.getVoteCounts();

} catch (ClassNotFoundException | SQLException e) {
    e.printStackTrace();
    request.setAttribute("jsondata", "{\"Votes\":[],\"Counts\":{}}");
    RequestDispatcher dispatch = getServletContext()
            .getRequestDispatcher("/jason-data.jsp");
    dispatch.forward(request, response);
    return;
}

int size = list.size();
int count = 1;

response.setContentType("application/json");
PrintWriter out = response.getWriter();

StringBuilder js = new StringBuilder();
js.append("{");

// ✅ Add all individual votes
js.append("\"Votes\": [");
for (Vote v : list) {
    js.append("{")
      .append("\"Id\": \"").append(v.getId()).append("\",")
      .append("\"Username\": \"").append(v.getUsername()).append("\",")
      .append("\"Vote\": \"").append(v.getVotes()).append("\"")
      .append("}");
    if (count != size) {
        js.append(",");
    }
    count++;
}
js.append("],");

// ✅ Add total counts
js.append("\"Counts\": {")
  .append("\"Java\": ").append(counts.getJavaCount()).append(",")
  .append("\"DotNet\": ").append(counts.getDotnetCount()).append(",")
  .append("\"Python\": ").append(counts.getPythonCount())
  .append("}");

js.append("}");

out.print(js.toString());

// ✅ Forward to JSP
request.setAttribute("jsondata", js.toString());
RequestDispatcher dispatch = getServletContext()
        .getRequestDispatcher("/jason-data.jsp");
dispatch.forward(request, response);

} catch (IOException e) {
e.printStackTrace();
}
}


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}
