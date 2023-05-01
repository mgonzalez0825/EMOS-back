package com.EMOS;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.EMOS.Repository.DepartmentRepository;
import com.EMOS.Repository.JobsRepository;
import com.EMOS.Web.JobsController;
import com.EMOS.domain.Department;
import com.EMOS.domain.Jobs;
import com.EMOS.domain.User;

@SpringBootTest
class JobsControllerTests {

    @InjectMocks
    JobsController jobsController;

    @Mock
    JobsRepository jobsRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    User user;

    @Test
    void testCreateJob() {
        // create department
        Department department = new Department();
        department.setDeptNo(1L);
        department.setDeptName("IT");

        // create job
        Jobs jobRequest = new Jobs();
        jobRequest.setDepartment(department);
        jobRequest.setJobTitle("Java Developer");
        jobRequest.setJobType("Full-time");
        jobRequest.setJobPayRange("$50,000 - $70,000");
        jobRequest.setJobDescription("Java developer with 3+ years of experience");

        // mock the behavior of departmentRepository.findById
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // mock the behavior of jobsRepository.save
        when(jobsRepository.save(any(Jobs.class))).thenReturn(jobRequest);

        // invoke the createJob method
        ResponseEntity<Jobs> response = jobsController.createJob(user, jobRequest);

        // assert the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(jobRequest);
    }
}