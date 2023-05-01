package com.EMOS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.EMOS.domain.Scheduler;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends JpaRepository <Scheduler, Long> {

}
