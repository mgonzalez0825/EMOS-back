package com.EMOS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.EMOS.domain.Jobs;
import org.springframework.stereotype.Repository;

public interface JobsRepository extends JpaRepository <Jobs, Long> {

}
