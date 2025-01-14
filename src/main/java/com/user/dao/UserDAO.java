package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDAO {
    
    private String jdbcURL="jdbc:mysql://localhost:3306/bank_system";
    private String jdbcUserName="root";
    private String jdbcPassword="12345678";
    
    private static final String INSERT_USER_SQL="INSERT INTO user (name, email, password, phone) VALUES (?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID="SELECT * FROM user WHERE id=?;";
    private static final String SELECT_ALL_USERS="SELECT * FROM user;";
    private static final String SELECT_USERID_BY_EMAIL="SELECT id from user where email=? ";
    private static final String DELETE_USER_SQL="DELETE FROM user WHERE id=?;";
    private static final String UPDATE_USER_SQL="UPDATE user SET name=?, email=?, password=?, phone=? WHERE id=?;";
    
    public UserDAO() {
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
    
    public void insertUser(User user) {
        UserDAO dao = new UserDAO();
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getUserIdByEmail( String email) throws SQLException {
	    String userId = null;
	    UserDAO dao = new UserDAO(); 
    	    try ( Connection connection=dao.getConnection()){
	    	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERID_BY_EMAIL);
	    	preparedStatement.setString(1, email);

	       
	        try (ResultSet rs = preparedStatement.executeQuery()) {
	            
	            if (rs.next()) {
	                userId = rs.getString("id"); 
	            }
	        }
	    }

	    return userId;
	}
    
    public User selectUser(int id) {
        User user = new User();
        UserDAO dao = new UserDAO();
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        UserDAO dao = new UserDAO();
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                
                users.add(new User(id, name, email, password, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean deleteUser(int id) {
        boolean status = false;
        UserDAO dao = new UserDAO();
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, id);
            
            status = preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public boolean updateUser(User user) {
        boolean status = false;
        UserDAO dao = new UserDAO();
        try (Connection connection = dao.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setLong(5, user.getId());
            
            status = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public User selectUserByEmailAndPassword(String email, String password) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("phone")
                    );
                }
            }
        }
        return user;
    }

}