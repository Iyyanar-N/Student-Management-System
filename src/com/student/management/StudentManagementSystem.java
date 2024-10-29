package com.student.management;

import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            // Ensure that nextInt() does not skip over the next input by using nextLine().
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Enter a number between 1-6.");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after int input

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAllStudents(); break;
                case 3: updateStudent(); break;
                case 4: deleteStudent(); break;
                case 5: searchStudent(); break;
                case 6: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice, try again.");
            }
        } while (choice != 6);
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine(); // nextLine() to read full name input
        System.out.print("Enter age: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input for age. Please enter an integer value.");
            scanner.next();
        }
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline after int input
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        String query = "INSERT INTO students (name, age, department, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, department);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewAllStudents() {
        String query = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("No students found.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("student_id") + ", Name: " + rs.getString("name") +
                                       ", Age: " + rs.getInt("age") + ", Dept: " + rs.getString("department") +
                                       ", Email: " + rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        System.out.print("Enter the Student ID to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new department: ");
        String department = scanner.nextLine();
        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        String query = "UPDATE students SET name = ?, age = ?, department = ?, email = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, department);
            pstmt.setString(4, email);
            pstmt.setInt(5, studentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter the Student ID to delete: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        System.out.println("Search by: ");
        System.out.println("1. Student ID");
        System.out.println("2. Student Name");
        int searchChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String query;
        if (searchChoice == 1) {
            System.out.print("Enter the Student ID: ");
            int studentId = scanner.nextInt();
            query = "SELECT * FROM students WHERE student_id = " + studentId;
        } else {
            System.out.print("Enter the Student Name: ");
            String name = scanner.nextLine();
            query = "SELECT * FROM students WHERE name LIKE '%" + name + "%'";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("No student found.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("student_id") + ", Name: " + rs.getString("name") +
                                       ", Age: " + rs.getInt("age") + ", Dept: " + rs.getString("department") +
                                       ", Email: " + rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching for student: " + e.getMessage());
        }
    }
}