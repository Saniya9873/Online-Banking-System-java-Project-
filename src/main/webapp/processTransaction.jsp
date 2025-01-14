<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
    String driver = "com.mysql.cj.jdbc.Driver"; // Updated MySQL Driver
    String connectionUrl = "jdbc:mysql://localhost:3306/";
    String database = "bank_system"; // Database name
    String userid = "root";    // MySQL username
    String password = "12345678";      // MySQL password (if any)

    // Initialize variables
    String senderAccount = "";
    double senderBalance = 0;
    String receiverAccount = "";
    double amount = 0;
    String message = "";
    boolean transactionSuccessful = false;

    // Get form parameters, with null checks
    try {
        senderAccount = request.getParameter("sender_account");
        String senderBalanceParam = request.getParameter("balance");
        receiverAccount = request.getParameter("receiver_account");
        String amountParam = request.getParameter("amount");

        // Parse balance and amount only if they're valid
        if (senderBalanceParam != null && !senderBalanceParam.isEmpty()) {
            senderBalance = Double.parseDouble(senderBalanceParam);
        }

        if (amountParam != null && !amountParam.isEmpty()) {
            amount = Double.parseDouble(amountParam);
        }
    } catch (NumberFormatException e) {
        message = "Invalid number format for balance or amount.";
    }

    // Get current timestamp
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String transactionTime = sdf.format(new Date());

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        // Load MySQL JDBC driver
        Class.forName(driver);
        // Connect to the database
        connection = DriverManager.getConnection(connectionUrl + database, userid, password);
        statement = connection.createStatement();

        // Check if the sender has enough balance
        if (senderBalance <= amount) {
            // Deduct amount from sender's account
            String deductSQL = "UPDATE account SET balance = balance - " + amount + " WHERE account_number = '" + senderAccount + "'";
            int rowsAffected = statement.executeUpdate(deductSQL);

            // Add amount to receiver's account
            String addSQL = "UPDATE account SET balance = balance + " + amount + " WHERE account_number = '" + receiverAccount + "'";
            rowsAffected += statement.executeUpdate(addSQL);

            if (rowsAffected == 2) {
                // Insert the transaction into the transaction table
                String transactionSQL = "INSERT INTO transcation (sender_account, receiver_account, amount, date_time) VALUES ('" 
                                        + senderAccount + "', '" + receiverAccount + "', " + amount + ", '" + transactionTime + "')";
                statement.executeUpdate(transactionSQL);

                transactionSuccessful = true;
                message = "Transaction Successful!";
            } else {
                message = "Transaction failed. Please check the receiver's account number.";
            }
        } else {
            message = "Insufficient balance for the transaction.";
        }
    } catch (Exception e) {
        e.printStackTrace();
        message = "An error occurred during the transaction.";
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Status</title>
    <!-- Bootstrap CSS (CDN) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <div class="card shadow-lg">
        <div class="card-body">
            <h3 class="text-center"><%= message %></h3>
            <% if (transactionSuccessful) { %>
                <a href="customer-details.jsp" class="btn btn-success btn-block">Go to Home</a>
            <% } else { %>
                <a href="transaction.jsp?account_number=<%= senderAccount %>" class="btn btn-danger btn-block">Retry</a>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
