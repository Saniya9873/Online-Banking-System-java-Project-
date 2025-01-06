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

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;

try {
    // Load MySQL JDBC driver
    Class.forName(driver);
} catch (ClassNotFoundException e) {
    e.printStackTrace();
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction List</title>
    <!-- Bootstrap CSS (CDN) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

<header class="text-center py-3 bg-dark text-white">
    <h1>Transaction Details</h1>
</header>

<div class="container mt-5">
    <!-- Table to display data -->
    <div class="card shadow-lg">
        <div class="card-body">
            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>S.NO</th>
                        <th>Sender Account</th>
                        <th>Receiver Account</th>
                        <th>Amount</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    try {
                        // Connect to the database
                        connection = DriverManager.getConnection(connectionUrl + database, userid, password);
                        statement = connection.createStatement();
                        
                        // SQL query to fetch transaction data
                        String sql = "SELECT sender_account, receiver_account, amount, date_time FROM transcation";
                        resultSet = statement.executeQuery(sql);
                        
                        int serialNo = 1; // Serial number for each row
                        
                        // Loop through the result set
                        while (resultSet.next()) {
                %>
                    <tr>
                        <td><%= serialNo++ %></td>  <!-- Display serial number -->
                        <td><%= resultSet.getString("sender_account") %></td>
                        <td><%= resultSet.getString("receiver_account") %></td>
                        <td><%= resultSet.getDouble("amount") %></td>
                        <td><%= resultSet.getTimestamp("date_time") %></td>  <!-- Display transaction date -->
                    </tr>
                <%
                        }
                        connection.close();  // Close the connection after retrieving data
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
