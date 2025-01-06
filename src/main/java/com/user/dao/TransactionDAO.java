package com.user.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.user.model.Transaction;

public class TransactionDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/bank_system";
    private String jdbcUserName = "root";
    private String jdbcPassword = "12345678";

    private static final String INSERT_TRANSACTION_SQL = "INSERT INTO transaction (senderAccount, receiverAccount, amount, date_time) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM transaction WHERE id = ?;";
    private static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transaction;";
    private static final String DELETE_TRANSACTION_SQL = "DELETE FROM transaction WHERE id = ?;";
    private static final String UPDATE_TRANSACTION_SQL = "UPDATE transaction SET senderAccount = ?, receiverAccount = ?, amount = ?, date_time = ? WHERE id = ?;";

    public TransactionDAO() {
    }

    // Database connection
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertTransaction(Transaction transaction) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_SQL);
            preparedStatement.setString(1, transaction.getSenderAccount());
            preparedStatement.setString(2, transaction.getReceiverAccount());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getDateTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction selectTransaction(Long id) {
        Transaction transaction = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRANSACTION_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String senderAccount = resultSet.getString("senderAccount");
                String receiverAccount = resultSet.getString("receiverAccount");
                Double amount = resultSet.getDouble("amount");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                transaction = new Transaction(id, senderAccount, receiverAccount, amount, dateTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public List<Transaction> selectAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TRANSACTIONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senderAccount = resultSet.getString("senderAccount");
                String receiverAccount = resultSet.getString("receiverAccount");
                Double amount = resultSet.getDouble("amount");
                Timestamp dateTime = resultSet.getTimestamp("date_time");
                transactions.add(new Transaction(id, senderAccount, receiverAccount, amount, dateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean deleteTransaction(Long id) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSACTION_SQL);
            preparedStatement.setLong(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateTransaction(Transaction transaction) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSACTION_SQL);
            preparedStatement.setString(1, transaction.getSenderAccount());
            preparedStatement.setString(2, transaction.getReceiverAccount());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getDateTime());
            preparedStatement.setLong(5, transaction.getId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
}