package com.user.model;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    // Default constructor
    public User() {
    }

    // Constructor with fields
    public User(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    // Constructor with all fields including id
    public User(Long id, String name, String email, String password, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public User(Long id, String name, String email,  String phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
  
        this.phone = phone;
    }

    public User(String name, String email) {
    	this.name = name;
    	this.email = email;
		
	}

	// Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone + "]";
    }
}