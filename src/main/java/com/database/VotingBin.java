package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.domain.Register;
import com.domain.Vote;

public class VotingBin {

    // ✅ Database connection method
	private Connection getConnection() throws SQLException, ClassNotFoundException {
	    // Try to get environment variables (used in cloud deployment)
	    String dbUrl = System.getenv("DB_URL");
	    if (dbUrl == null) {
	        // Fallback to Railway's public connection URL
	        dbUrl = "jdbc:mysql://shortline.proxy.rlwy.net:53877/railway?useSSL=true";
	    }

	    String dbUsername = System.getenv("DB_USERNAME");
	    if (dbUsername == null) {
	        dbUsername = "root"; // Railway uses 'root' by default
	    }

	    String dbPassword = System.getenv("DB_PASSWORD");
	    if (dbPassword == null) {
	        dbPassword = "FdifNMDTCQZZLWRfpEjtyVpBDbkvkEBO"; // Replace with real password or set in Render
	    }

	    // Load MySQL JDBC driver
	    Class.forName("com.mysql.cj.jdbc.Driver");

	    // Connect and return
	    return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	}


    // ✅ Aadhaar validation (moved here from RegistrationServlet)
    public String validateAadhaar(String aadhaar) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT age FROM aadhaar_data WHERE aadhaar_number = ?")) {

            ps.setString(1, aadhaar);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return "Aadhaar number not found!";
            } else if (rs.getInt("age") < 18) {
                return "You must be above 18 to register!";
            }
            return null; // ✅ valid
        }
    }

    // ✅ Register new user
    public void loginRegistration(Register reg) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO studentdata (fname, lname, username, password) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, reg.getfName());
            ps.setString(2, reg.getlName());
            ps.setString(3, reg.getUsername());
            ps.setString(4, reg.getPassword());

            ps.executeUpdate();
        }
    }

    // ✅ Check if user already exists
    public int loginCheck(Register reg) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM studentdata WHERE username = ?")) {

            ps.setString(1, reg.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    // ✅ Check if a user has already voted
    public int voteCheck(Vote vt) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM devlang WHERE username = ?")) {

            ps.setString(1, vt.getUsername());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    // ✅ Register a new vote
    public void voteRegistration(Vote vt) throws SQLException, ClassNotFoundException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO devlang (username, votes) VALUES (?, ?)")) {

            ps.setString(1, vt.getUsername());
            ps.setString(2, vt.getVotes());

            ps.executeUpdate();
        }
    }

    // ✅ Get all votes from the database
 // ✅ Keep this for JasonServlet (list of all votes)
    public List<Vote> getAllVotes() throws SQLException, ClassNotFoundException {
        List<Vote> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT id, username, votes FROM devlang");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vote v = new Vote();
                v.setId(rs.getInt("id"));
                v.setUsername(rs.getString("username"));
                v.setVotes(rs.getString("votes"));
                list.add(v);
            }
        }
        return list;
    }

    // ✅ New method for counting votes
    public Vote getVoteCounts() throws SQLException, ClassNotFoundException {
        Vote voteCount = new Vote();

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT votes, COUNT(*) as total FROM devlang GROUP BY votes");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String choice = rs.getString("votes");
                int total = rs.getInt("total");

                switch (choice.toLowerCase()) {
                    case "java":
                        voteCount.setJavaCount(total);
                        break;
                    case "dot net":
                        voteCount.setDotnetCount(total);
                        break;
                    case "python":
                        voteCount.setPythonCount(total);
                        break;
                }
            }
        }
        return voteCount;
    }
}