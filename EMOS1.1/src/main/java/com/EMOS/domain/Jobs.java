package com.EMOS.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "jobs")

public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId ;

    @Column(name="job_title")
    private String jobTitle;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "job_pay_range")
    private String jobPayRange;

    @Column(name = "job_description")
    private String jobDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "dept_no")
    private Department department;


    //access deptName in job json response
    @Transient
    private String deptName;

    public String getDeptName() {
        return this.department.getDeptName();
    }




    public Jobs (){

    }
    public Jobs(String jobTitle, String jobType, String jobPayRange, String jobDescription) {
        this.jobTitle = jobTitle;
        this.jobType = jobType;
        this.jobPayRange = jobPayRange;
        this.jobDescription = jobDescription;
    }


    //getters and setters
    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobPayRange() {
        return jobPayRange;
    }

    public void setJobPayRange(String jobPayRange) {
        this.jobPayRange = jobPayRange;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
