package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.Account;

public class AccountDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/bank_system";
    private String jdbcUserName = "root";
    private String jdbcPassword = "12345678";

    private static final String INSERT_ACCOUNT_SQL = "INSERT INTO account (accountNumber, userId, accountType, balance, pin) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM account WHERE accountNumber = ?;";
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account;";
    private static final String DELETE_ACCOUNT_SQL = "DELETE FROM account WHERE accountNumber = ?;";
    private static final String UPDATE_ACCOUNT_SQL = "UPDATE account SET userId = ?, accountType = ?, balance = ?, pin = ? WHERE accountNumber = ?;";

    public AccountDAO() {
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

    public void insertAccount(Account account) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL);
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setLong(2, account.getUserId());
            preparedStatement.setString(3, account.getAccountType());
            preparedStatement.setDouble(4, account.getBalance());
            preparedStatement.setString(5, account.getPin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account selectAccount(String accountNumber) {
        Account account = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("userId");
                String accountType = resultSet.getString("accountType");
                Double balance = resultSet.getDouble("balance");
                String pin = resultSet.getString("pin");
                account = new Account(accountNumber, userId, accountType, balance, pin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> selectAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ACCOUNTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String accountNumber = resultSet.getString("accountNumber");
                Long userId = resultSet.getLong("userId");
                String accountType = resultSet.getString("accountType");
                Double balance = resultSet.getDouble("balance");
                String pin = resultSet.getString("pin");
                accounts.add(new Account(accountNumber, userId, accountType, balance, pin));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean deleteAccount(String accountNumber) {
        boolean rowDeleted = false;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT_SQL);
            preparedStatement.setString(1, accountNumber);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateAccount(Account account) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL);
            preparedStatement.setLong(1, account.getUserId());
            preparedStatement.setString(2, account.getAccountType());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setString(4, account.getPin());
            preparedStatement.setString(5, account.getAccountNumber());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
}