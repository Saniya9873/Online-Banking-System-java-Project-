package com.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Random;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

    public RegisterServlet() {
        super();
    }

    private String generateAccountNumber(int length){
       
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            accountNumber.append(index);
        }

        return accountNumber.toString();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String accountType = request.getParameter("account-type"); 

        String account_number = generateAccountNumber(10);

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            if (isAccountNumberExists(connection, account_number)) {  // Corrected line
                request.setAttribute("errorMessage", "Account Number or email already exists.");
                request.getRequestDispatcher("login.html").forward(request, response);
                return; 
            }
            
            if (isEmailExists(connection, email)) {
                request.setAttribute("errorMessage", "Account Number or email already exists.");
                request.getRequestDispatcher("login.html").forward(request, response);
                return; 
            }

            String sql = "INSERT INTO user (name, email, password, phone) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, phone);

                int rowsAffected = statement.executeUpdate();
                logger.info("Rows affected: " + rowsAffected); 

                if (rowsAffected > 0) {
                    logger.info("User signed up successfully ");
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("username", name);
                    
                    logger.info("Session set: name=" + session.getAttribute("name"));

                    ResultSet rs = statement.getGeneratedKeys();  
                    if (rs.next()) {
                        int userId = rs.getInt(1);

                        String insertAccountSql = "INSERT INTO account (account_number, user_id, account_type, balance) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement accountStatement = connection.prepareStatement(insertAccountSql)) {
                            accountStatement.setString(1, account_number);  
                            accountStatement.setInt(2, userId); 
                            accountStatement.setString(3, accountType); 
                            accountStatement.setDouble(4, 0); 
                            int rowsAffectedAccount = accountStatement.executeUpdate();
                            logger.info("Rows affected in account table: " + rowsAffectedAccount); 

                            if (rowsAffectedAccount > 0) {
                                logger.info("Account created successfully ");
                                response.sendRedirect("login.html?signup=success");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            response.getWriter().println("Error: Unable to create account.");
                        }
                    }
                } else {
                    logger.warning("Error: Unable to signup user.");
                    response.getWriter().println("Error: Unable to signup user.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred during signup.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isAccountNumberExists(Connection connection, String accountNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM account WHERE account_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; 
                }
            }
        }
        return false;
    }

    private boolean isEmailExists(Connection connection, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; 
                }
            }
        }
        return false;
    }
}
