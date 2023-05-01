package com.EMOS.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="Employee_details")
public class EmployeeDetails implements Serializable{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "employee_id")
    private Employee employee;

        @Column(name="salary")
        private int salary;
        @Column(name="position")
        private String position;
        @Column(name="employee_type")
        private String employeeType;

        @Column(name="pay_rate")
        private String payRate;

        @Column(name="hire_date")
        private String hireDate;

        @Column(name="phone_number")
        private String phoneNumber;

        @Column(name="supervisor")
        private boolean supervisor;

        @Column(name="ssn")
        private String ssn;





        public EmployeeDetails(){

        }
    public EmployeeDetails( int salary, String position, String employeeType, String payRate, String hireDate, String phoneNumber, boolean supervisor, String ssn) {
        this.id = id;
        this.salary = salary;
        this.position = position;
        this.employeeType = employeeType;
        this.payRate = payRate;
        this.hireDate = hireDate;
        this.phoneNumber = phoneNumber;
        this.supervisor = supervisor;
        this.ssn = ssn;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getPayRate() {
        return payRate;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
