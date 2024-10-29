package com.student.management;

public class Student {
	    private int id;
	    private String name;
	    private int age;
	    private String department;
	    private String email;
	    
	    // Constructor, Getters, and Setters
	    public Student(int id, String name, int age, String department, String email) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	        this.department = department;
	        this.email = email;
	    }

	    // toString method for displaying student details
	    public String toString() {
	        return "Student [ID=" + id + ", Name=" + name + ", Age=" + age + 
	                ", Department=" + department + ", Email=" + email + "]";
	    }
	}