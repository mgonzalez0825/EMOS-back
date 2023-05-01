package com.EMOS.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


@Entity
@Table(name="events")

public class Scheduler {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId ;

    @Column(name="event_job")
    private String eventJob;

    @Column(name = "event_employee")
    private String eventEmployee;
    

    @Column(name = "event_start_date")
    private String eventStartDate;

    @Column(name = "event_end_Date")
    private String eventEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("jobsReference")
    @JoinColumn(name = "job_id")
    private Jobs jobs;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference("employeeReference") 
    @JoinColumn(name = "employee_id")
    private Employee employee;

//    @Transient
//    private long jobId;

    @Transient
    private String jobTitle;
    
    @Transient
    private String firstName;
    
    @Transient
    private String lastName;
    
//    public String getJobTitle() {
//    	return this.jobs.getJobTitle();
//    }

    public Scheduler (){

    }
    
    public Scheduler(String eventJob, String eventEmployee, String eventStartDate, String eventEndDate) {
        this.eventJob = eventJob;
        this.eventEmployee = eventEmployee;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate; 
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getEventJob() {
        return eventJob;
    }

    public void setEventJob(String eventJob) {
        this.eventJob = eventJob;
    }

    public String getEventEmployee() {
        return eventEmployee;
    }

    public void setEventEmployee(String eventEmployee) {
        this.eventEmployee = eventEmployee;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }
    
    public Jobs getJobs() {
    	return jobs;
    }
    
    public void setJobs(Jobs jobs) {
    	this.jobs = jobs;
    }
    
    public Employee getEmployee() {
    	return employee;
}
    
    public void setEmployee(Employee employee) {
    	this.employee = employee;
    }
}