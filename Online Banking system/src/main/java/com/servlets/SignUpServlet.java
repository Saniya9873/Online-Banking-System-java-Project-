package com.servlets;

import com.utils.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (!password.equals(confirmPassword)) {
            out.println("Passwords do not match!");
            return;
        }

        try (Connection conn = DBConnection.openConnection()) {
            String checkUserQuery = "SELECT * FROM account WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                out.println("Username already exists!");
                return;
            }

            String insertQuery = "INSERT INTO account (username, password, name) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.setString(3, name);
            insertStmt.executeUpdate();

            out.println("Sign-up successful!");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error occurred: " + e.getMessage());
        }
    }
}
