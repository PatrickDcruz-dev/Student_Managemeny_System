package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class StudentService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public record DashboardStats(int totalStudents, int departmentCount, double averageMarks) {}

    public void validateStudent(Student student, boolean isUpdate) throws SQLException {
        if (student == null) {
            throw new IllegalArgumentException("Student information cannot be null.");
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
        if (student.getName().trim().length() < 2) {
            throw new IllegalArgumentException("Student name must be at least 2 characters.");
        }

        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email address is required.");
        }
        String trimmedEmail = student.getEmail().trim();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Please enter a valid email address (e.g., student@example.com).");
        }

        int excludeId = isUpdate ? student.getStudentId() : 0;
        if (studentDAO.existsByEmail(trimmedEmail, excludeId)) {
            throw new IllegalArgumentException("A student with email '" + trimmedEmail + "' already exists.");
        }

        if (student.getDepartment() == null || student.getDepartment().trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required.");
        }

        if (student.getYear() < 1 || student.getYear() > 6) {
            throw new IllegalArgumentException("Academic year must be between 1 and 6.");
        }

        if (student.getMarks() < 0.0 || student.getMarks() > 100.0) {
            throw new IllegalArgumentException("Marks must be between 0.0 and 100.0.");
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public List<Student> getRecentStudents(int limit) throws SQLException {
        return studentDAO.getRecentStudents(limit);
    }

    public Student getStudentById(int studentId) throws SQLException {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID.");
        }
        return studentDAO.getStudentById(studentId);
    }

    public List<Student> searchStudents(String keyword) throws SQLException {
        return studentDAO.searchStudents(keyword);
    }

    public boolean addStudent(Student student) throws SQLException {
        validateStudent(student, false);
        return studentDAO.addStudent(student);
    }

    public boolean updateStudent(Student student) throws SQLException {
        if (student.getStudentId() <= 0) {
            throw new IllegalArgumentException("Cannot update: Invalid student ID.");
        }
        validateStudent(student, true);
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int studentId) throws SQLException {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Please select a valid student to delete.");
        }
        return studentDAO.deleteStudent(studentId);
    }

    public DashboardStats getDashboardStats() throws SQLException {
        int total = studentDAO.getTotalStudentCount();
        int depts = studentDAO.getDistinctDepartmentCount();
        double avg = studentDAO.getAverageMarks();
        return new DashboardStats(total, depts, avg);
    }
}
