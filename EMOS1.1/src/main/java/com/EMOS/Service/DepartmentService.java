package com.EMOS.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.EMOS.Repository.DepartmentRepository;
import com.EMOS.domain.Department;
import com.EMOS.domain.User;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;
	

	public Department save(Department dept) {
		return departmentRepo.save(dept);
	}

	public Optional<Department> findbyId(Long deptNo) {
		return departmentRepo.findById(deptNo);
	}

	public ArrayList<Department> findAll() {
        return (ArrayList<Department>) departmentRepo.findAll();
	}
	
    public ResponseEntity<Map<String,Boolean>> delete(Department dept){
    	
        departmentRepo.delete(dept);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
