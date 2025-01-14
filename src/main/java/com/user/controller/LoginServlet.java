package com.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import com.user.dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

     

        Connection conn = null;

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            UserDAO dao = new UserDAO();
            String userId = dao.getUserIdByEmail(email);

            
            if (authenticateUser(request, conn, email, password, "user")) {
            	
                logger.info("Admin login successful for username: " + email);

                
                createSessionAndRedirect(request, response, email, "sub-home.html");
                return;
            }

      

            request.setAttribute("errorMessage", "Invalid username or password.");
            
            request.getRequestDispatcher("login.html").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error occurred during login: " + e.getMessage());
            request.setAttribute("errorMessage", "An error occurred. Please try again later.");
            request.getRequestDispatcher("login.html").forward(request, response);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean authenticateUser(HttpServletRequest request, Connection conn, String email, String password, String tableName) throws SQLException {
        String idColumn = "id";  
        

        String sql = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    
                    int userId = result.getInt(idColumn); 
                    HttpSession session = request.getSession();
                    session.setAttribute(tableName + "Id", userId);  
                    session.setAttribute("email", email);    
                    
                    logger.info("User authenticated in table: " + tableName);
                    return true;
                }
            }
        }
        
        logger.warning("Authentication failed for user: " + email + " in table: " + tableName);
        return false;
    }

    private void createSessionAndRedirect(HttpServletRequest request, HttpServletResponse response, String name ,String redirectPage) throws IOException {
        
       

        
        HttpSession session = request.getSession();
        session.setAttribute("Name", name);  
        

        
        logger.info("Session created for Name: " + name);
        
       
        response.sendRedirect(request.getContextPath() + "/" + redirectPage);
    }
}