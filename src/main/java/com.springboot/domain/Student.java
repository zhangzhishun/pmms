package com.springboot.domain;

/**
 * @author eternalSy
 * @version 1.0.0
 */
public class Student {
    private String stuId;
    private String password;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student() {
    }

    public Student(String stuId, String password) {
        this.stuId = stuId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
