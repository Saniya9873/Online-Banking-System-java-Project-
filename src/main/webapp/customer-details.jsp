<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%
String driver = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://localhost:3306/";
String database = "bank_system"; 
String userid = "root";    // MySQL username
String password = "12345678";      // MySQL password (if any)

Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;

try {
    Class.forName(driver);  // Load the MySQL JDBC driver
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
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
    <header class="text-center py-3 bg-dark text-white">
        <h1>Account Details</h1>
    </header>
    <div class="container mt-5">

    
    <!-- Table to display data -->
    <div class="card shadow-lg">
        <div class="card-body">
            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>S.NO</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Account Number</th>
                        <th>Account Balance</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    try {
                        // Connect to the database
                        connection = DriverManager.getConnection(connectionUrl + database, userid, password);
                        statement = connection.createStatement();
                        String sql = "SELECT u.id, u.name, u.email, u.phone, a.account_number,a.balance " +
                                "FROM user u " +
                                "JOIN account a ON u.id = a.user_id";
                        resultSet = statement.executeQuery(sql);
                        int serialNo = 1;
                        // Loop through the result set
                        while (resultSet.next()) {
                %>
                <tr>
                    <td><%= serialNo++ %></td> 
                    <td><%= resultSet.getString("name") %></td>
                    <td><%= resultSet.getString("email") %></td>
                    <td><%= resultSet.getString("phone") %></td>
                    <td><%= resultSet.getString("account_number") %></td>
                    <td><%= resultSet.getDouble("balance") %></td>
                     <td>
                            <!-- Transit Button to Redirect to Transaction Page -->
                            <a href="transaction.jsp?account_number=<%= resultSet.getString("account_number") %>" class="btn btn-primary btn-sm">
                                Transit
                            </a>
                        </td>
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
