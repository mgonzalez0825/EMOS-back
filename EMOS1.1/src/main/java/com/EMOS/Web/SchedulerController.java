package com.EMOS.Web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EMOS.Exception.ResourceNotFoundException;
import com.EMOS.Repository.EmployeeRepository;
import com.EMOS.Repository.JobsRepository;
import com.EMOS.Repository.SchedulerRepository;
import com.EMOS.domain.Employee;
import com.EMOS.domain.Jobs;
import com.EMOS.domain.Scheduler;
import com.EMOS.domain.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
public class SchedulerController {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private JobsRepository jobsRepository;
    
    @GetMapping("/scheduler")
    public ArrayList<Scheduler> getEvents(){
        return (ArrayList<Scheduler>) schedulerRepository.findAll();
    }

    @PostMapping("/scheduler")
    public ResponseEntity<Scheduler> createEvent(@AuthenticationPrincipal User User, @RequestBody Scheduler eventRequest){
        
    	Jobs jobs = jobsRepository.findById(eventRequest.getJobs().getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + eventRequest.getJobs().getJobId()));

    	Employee employee = employeeRepository.findById(eventRequest.getEmployee().getEmpId())
    			.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + eventRequest.getEmployee().getEmpId()));
    	
    	
        Scheduler event = new Scheduler(); 
        event.setJobs(jobs);
        event.setEmployee(employee);
        event.setEventJob(eventRequest.getEventJob());
        event.setEventEmployee(eventRequest.getEventEmployee());
        event.setEventStartDate(eventRequest.getEventStartDate());
        event.setEventEndDate(eventRequest.getEventEndDate());
        
        Scheduler createdEvent = schedulerRepository.save(event);
    
        
//        jobs.getEvents().add(createdEvent);
        jobsRepository.save(jobs);
        
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/scheduler/{id}")
    public ResponseEntity<Scheduler> getEventById(@AuthenticationPrincipal User User, @PathVariable Long id){

        Scheduler scheduler = schedulerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + id + " does not exist"));
        return ResponseEntity.ok(scheduler);
    }

    @PutMapping("/scheduler/{id}")
    public ResponseEntity<Scheduler> updateEvent(@AuthenticationPrincipal User User, @PathVariable Long id,@RequestBody Scheduler eventRequest){

        Scheduler scheduler = schedulerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + id + " does not exist."));

//        Jobs jobs = jobsRepository.findById(eventRequest.getJobs().getJobId())
//        		.orElseThrow(() -> new ResourceNotFoundException("Job with id: " + eventRequest.getJobs().getJobId() + " does not exist."));
//        
//        scheduler.setJobs(jobs);
        scheduler.setEventJob(eventRequest.getEventJob());
        scheduler.setEventEmployee(eventRequest.getEventEmployee());
        scheduler.setEventStartDate(eventRequest.getEventStartDate());
        scheduler.setEventEndDate(eventRequest.getEventEndDate());

        Scheduler updatedEvent =  schedulerRepository.save(scheduler);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/scheduler/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEventById(@PathVariable Long id){

        Scheduler event = schedulerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The event with id: " + id + " does not exist."));


        schedulerRepository.delete(event);
        
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}