package com.sms.model;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String department;
    private int year;
    private double marks;

    public Student() {
    }

    public Student(String name, String email, String department, int year, double marks) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
        this.marks = marks;
    }

    public Student(int studentId, String name, String email, String department, int year, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
        this.marks = marks;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", year=" + year +
                ", marks=" + marks +
                '}';
    }
}
