package com.sms.util;

import com.sms.database.DBConnection;
import com.sms.model.Student;
import com.sms.model.User;
import com.sms.service.AuthService;
import com.sms.service.StudentService;
import com.sms.service.StudentService.DashboardStats;

import java.util.List;

public class SystemVerifier {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  Student Management System - System Verification ");
        System.out.println("==================================================");

        int passed = 0;
        int failed = 0;

        System.out.print("[Test 1] Testing MySQL Connection... ");
        if (DBConnection.testConnection()) {
            System.out.println("PASSED");
            passed++;
        } else {
            System.out.println("FAILED");
            failed++;
        }

        System.out.print("[Test 2] Ensuring Database Schema Exists... ");
        try {
            DBConnection.initializeDatabase();
            System.out.println("PASSED");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        AuthService authService = new AuthService();
        StudentService studentService = new StudentService();

        String testUser = "testuser_" + System.currentTimeMillis();
        String testPass = "SecurePass123!";
        System.out.print("[Test 3] Testing User Registration with BCrypt... ");
        try {
            User registered = authService.register(testUser, testPass, testPass);
            if (registered != null && registered.getId() > 0) {
                System.out.println("PASSED (User ID: " + registered.getId() + ")");
                passed++;
            } else {
                System.out.println("FAILED (No ID returned)");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 4] Testing Duplicate Username Prevention... ");
        try {
            authService.register(testUser, testPass, testPass);
            System.out.println("FAILED (Should have thrown exception)");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED (" + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Unexpected exception: " + e.getMessage() + ")");
            failed++;
        }

        System.out.print("[Test 5] Testing Login with Valid Credentials... ");
        try {
            User loggedIn = authService.login(testUser, testPass);
            if (loggedIn != null && testUser.equals(loggedIn.getUsername())) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 6] Testing Login with Invalid Password... ");
        try {
            authService.login(testUser, "WrongPassword");
            System.out.println("FAILED (Should have thrown exception)");
            failed++;
        } catch (IllegalArgumentException e) {
            System.out.println("PASSED (" + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("FAILED (Unexpected exception: " + e.getMessage() + ")");
            failed++;
        }

        String testEmail = "test_" + System.currentTimeMillis() + "@university.edu";
        Student newStudent = new Student("Rohit Sharma", testEmail, "CSE", 3, 94.50);
        System.out.print("[Test 7] Testing Student Addition (CRUD - Create)... ");
        try {
            boolean added = studentService.addStudent(newStudent);
            if (added && newStudent.getStudentId() > 0) {
                System.out.println("PASSED (New Student ID: " + newStudent.getStudentId() + ")");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 8] Testing Student Search by Name and ID (CRUD - Read)... ");
        try {
            List<Student> searchName = studentService.searchStudents("Rohit");
            List<Student> searchId = studentService.searchStudents(String.valueOf(newStudent.getStudentId()));
            if (!searchName.isEmpty() && !searchId.isEmpty()) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 9] Testing Student Update (CRUD - Update)... ");
        try {
            newStudent.setMarks(98.00);
            newStudent.setDepartment("AI & DS");
            boolean updated = studentService.updateStudent(newStudent);
            Student retrieved = studentService.getStudentById(newStudent.getStudentId());
            if (updated && retrieved != null && Math.abs(retrieved.getMarks() - 98.00) < 0.001) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 10] Testing Student Deletion (CRUD - Delete)... ");
        try {
            boolean deleted = studentService.deleteStudent(newStudent.getStudentId());
            Student checkDeleted = studentService.getStudentById(newStudent.getStudentId());
            if (deleted && checkDeleted == null) {
                System.out.println("PASSED");
                passed++;
            } else {
                System.out.println("FAILED");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.print("[Test 11] Testing Dashboard Statistics Calculation... ");
        try {
            DashboardStats stats = studentService.getDashboardStats();
            if (stats.totalStudents() >= 0 && stats.departmentCount() >= 0 && stats.averageMarks() >= 0.0) {
                System.out.printf("PASSED (Total: %d, Departments: %d, Avg Marks: %.2f)%n",
                        stats.totalStudents(), stats.departmentCount(), stats.averageMarks());
                passed++;
            } else {
                System.out.println("FAILED (Invalid values)");
                failed++;
            }
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            failed++;
        }

        System.out.println("==================================================");
        System.out.printf("Summary: %d Passed, %d Failed (Total: %d)%n", passed, failed, passed + failed);
        System.out.println("==================================================");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
