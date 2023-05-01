package com.EMOS.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="department")

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deptNo;


    @Column(name="dept_name")
    private String deptName;
    @Column(name="mgr_no")
    private String managerNo;

    @Column(name="parent_dept")
    private String parentDept;

    @Column(name="location")
    private String location;

   //testing github


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
    @JsonManagedReference
//    @JsonIgnore
    private List<Employee> employees ;



    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
//    @JsonManagedReference
//    @JsonIgnore
    private List<Jobs> jobs ;


    //constructors
    public Department() {

    }

    public Department( String deptName, String managerNo, String parentDept, String location) {
        this.deptName = deptName;
        this.managerNo = managerNo;
        this.parentDept = parentDept;
        this.location = location;
    }



    //setter and getter


    public long getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(long deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getManagerNo() {
        return managerNo;
    }

    public void setManagerNo(String managerNo) {
        this.managerNo = managerNo;
    }

    public String getParentDept() {
        return parentDept;
    }

    public void setParentDept(String parentDept) {
        this.parentDept = parentDept;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
    }
}

