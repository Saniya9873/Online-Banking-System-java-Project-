<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%
    String driver = "com.mysql.cj.jdbc.Driver"; // Updated MySQL Driver
    String connectionUrl = "jdbc:mysql://localhost:3306/";
    String database = "bank_system"; // Database name
    String userid = "root";    // MySQL username
    String password = "12345678";      // MySQL password (if any)

    String accountNumber = request.getParameter("account_number");
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    String senderName = "";
    double senderBalance = 0;

    try {
        // Load MySQL JDBC driver
        Class.forName(driver);
        // Connect to the database
        connection = DriverManager.getConnection(connectionUrl + database, userid, password);
        statement = connection.createStatement();

        // Fetch sender's details based on account number
        String sql = "SELECT  balance FROM account WHERE account_number = '" + accountNumber + "'";
        resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
           
            senderBalance = resultSet.getDouble("balance");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Page</title>
    <!-- Bootstrap CSS (CDN) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
    <header class="text-center py-3 bg-dark text-white">
        <h1>Transaction for <%= senderName %></h1>
    </header>

    <div class="container mt-5">
        <!-- Transaction Form -->
        <div class="card shadow-lg">
            <div class="card-body">
                <form action="processTransaction.jsp" method="post">
                    <input type="hidden" name="sender_account" value="<%= accountNumber %>">
                    <input type="hidden" name="sender_balance" value="<%= senderBalance %>">

                    <div class="form-group">
                        <label for="receiver_account">Receiver Account Number</label>
                        <input type="text" id="receiver_account" name="receiver_account" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="amount">Amount to Transfer</label>
                        <input type="number" id="amount" name="amount" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Submit Transaction</button>
                    </div>
                </form>

                <p><strong>Current Balance:</strong> <%= senderBalance %> </p>
            </div>
        </div>
    </div>
</body>
</html>
