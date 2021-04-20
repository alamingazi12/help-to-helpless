package com.example.help2helpless.model;

public class Student {
    int id;

    public Student() {
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    int salary;

    public Student(int id, int salary) {
        this.id = id;
        this.salary = salary;
    }
}
