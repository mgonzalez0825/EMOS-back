
package com.EMOS.Web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import com.EMOS.Service.DepartmentService;
import com.EMOS.domain.Department;
import com.EMOS.domain.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;


    // getAllDepts API

    @GetMapping("/departments")
    public ArrayList<Department> getDepartments(){
        return (ArrayList<Department>) departmentService.findAll();
    }

    //create department API


    @PostMapping("/departments")
    public ResponseEntity<?> createDepartment(@AuthenticationPrincipal User user,@RequestBody Department department){
        Department newDepartment = new Department();
        newDepartment.setDeptName(department.getDeptName());
        newDepartment.setParentDept(department.getParentDept());
        newDepartment.setLocation(department.getLocation());
        newDepartment.setManagerNo(department.getManagerNo());
        newDepartment.setEmployees(department.getEmployees());

        Department savedDepartment = departmentService.save(newDepartment);
        return ResponseEntity.ok(savedDepartment);
    }

    // get departmentById API

    @GetMapping("/departments/{deptNo}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long deptNo, @AuthenticationPrincipal User user){
    	Optional<Department> departmentOpt = departmentService.findbyId(deptNo);
        return ResponseEntity.ok(departmentOpt.orElse(new Department()));
    }
    //
//    //update department  API
//
    @PutMapping("/departments/{deptNo}")
    public ResponseEntity<?> updateDepartment(@AuthenticationPrincipal User user,@PathVariable Long deptNo, @RequestBody Department departmentAttributes){
    	Department updatedDepartment = departmentService.findbyId(deptNo)
    			.orElseThrow(() -> new ResourceNotFoundException("Department with id: " + deptNo + " does not exists"));

        updatedDepartment.setDeptName(departmentAttributes.getDeptName());
        updatedDepartment.setParentDept(departmentAttributes.getParentDept());
        updatedDepartment.setLocation(departmentAttributes.getLocation());
        updatedDepartment.setManagerNo(departmentAttributes.getManagerNo());
        updatedDepartment.setEmployees(departmentAttributes.getEmployees());

        Department savedDepartment = departmentService.save(updatedDepartment);
        return ResponseEntity.ok(savedDepartment);
        
    }
    //
    //delete department API
    @DeleteMapping("/departments/{deptNo}")
    public ResponseEntity<Map<String,Boolean>> deleteDepartmentById(@PathVariable Long deptNo){

    	Department department = departmentService.findbyId(deptNo)
                .orElseThrow(() -> new ResourceNotFoundException("Department with id: " + deptNo + " does not exists"));


        departmentService.delete(department);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
