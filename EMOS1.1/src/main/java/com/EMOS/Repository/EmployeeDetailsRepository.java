package com.EMOS.Repository;


import com.EMOS.domain.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
    }

