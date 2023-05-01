package com.EMOS.Web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.EMOS.Repository.DepartmentRepository;
import com.EMOS.Repository.EmployeeDetailsRepository;
import com.EMOS.Repository.EmployeeRepository;
import com.EMOS.Repository.UserRepository;
import com.EMOS.Util.CustomPasswordEncoder;
import com.EMOS.domain.Department;
import com.EMOS.domain.Employee;
import com.EMOS.domain.EmployeeDetails;
import com.EMOS.domain.User;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    
    // getAllEmployees rest

    @GetMapping("/employees")
    public ArrayList<Employee> getAllEmployees(){
        return (ArrayList<Employee>) employeeRepository.findAll();
    }

    //create employee API


    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@AuthenticationPrincipal User User, @RequestBody Employee employeeRequest){
        Department department = departmentRepository.findById(employeeRequest.getDepartment().getDeptNo())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + employeeRequest.getDepartment().getDeptNo()));

        User user = new User();
        Employee employee = new Employee();
        
        user.setId(employee.getEmpId());
        employee.setDepartment(department); // Set the department on the employee object
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmailId(employeeRequest.getEmailId());
        employee.setPassword(employeeRequest.getPassword());
        

        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setSsn(employeeRequest.getEmployeeDetails().getSsn());
        employeeDetails.setEmployeeType(employeeRequest.getEmployeeDetails().getEmployeeType());
        employeeDetails.setPhoneNumber(employeeRequest.getEmployeeDetails().getPhoneNumber());
        employeeDetails.setSupervisor(employeeRequest.getEmployeeDetails().isSupervisor());
        employeeDetails.setSalary(employeeRequest.getEmployeeDetails().getSalary());
        employeeDetails.setPosition(employeeRequest.getEmployeeDetails().getPosition());
        employeeDetails.setPayRate(employeeRequest.getEmployeeDetails().getPayRate());
        employeeDetails.setHireDate(employeeRequest.getEmployeeDetails().getHireDate());
        employeeDetails.setEmployee(employee);
        
        
        user.setAdmin(employeeRequest.getEmployeeDetails().isSupervisor());
        user.setUsername(employeeRequest.getEmailId());
        user.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        
        User createdUser = userRepository.save(user);

        employee.setEmployeeDetails(employeeDetails);
        Employee createdEmployee = employeeRepository.save(employee);

        department.getEmployees().add(createdEmployee);
        departmentRepository.save(department);

        return ResponseEntity.ok(createdEmployee);
    }






    //get employeeById API

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@AuthenticationPrincipal User user,@PathVariable Long id ){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " does not exists"));
                return ResponseEntity.ok(employee);
    }




    //update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@AuthenticationPrincipal User User,@PathVariable Long id, @RequestBody Employee employeeRequest) {
    	
    User user = userRepository.findById(id+1)
            	.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + (id+1)));
    Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        Department department = departmentRepository.findById(employeeRequest.getDepartment().getDeptNo())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + employeeRequest.getDepartment().getDeptNo()));

        employee.setDepartment(department);
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmailId(employeeRequest.getEmailId());
        employee.setPassword(employeeRequest.getPassword());
        
        user.setUsername(employeeRequest.getEmailId());
        user.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));


        EmployeeDetails existingDetails = employeeRequest.getEmployeeDetails();
        existingDetails.setSsn(employeeRequest.getEmployeeDetails().getSsn());
        existingDetails.setPhoneNumber(employeeRequest.getEmployeeDetails().getPhoneNumber());
        existingDetails.setPosition(employeeRequest.getEmployeeDetails().getPosition());
        existingDetails.setSalary(employeeRequest.getEmployeeDetails().getSalary());
        existingDetails.setPayRate(employeeRequest.getEmployeeDetails().getPayRate());
        existingDetails.setSupervisor(employeeRequest.getEmployeeDetails().isSupervisor());
        existingDetails.setEmployeeType(employeeRequest.getEmployeeDetails().getEmployeeType());
        existingDetails.setHireDate(employeeRequest.getEmployeeDetails().getHireDate());
        existingDetails.setEmployee(employee);



        Employee updatedEmployee = employeeRepository.save(employee);
        User createdUser = userRepository.save(user);

        return ResponseEntity.ok(updatedEmployee);
    }


    //edit employee details
    @PutMapping("/employees/add-details/{id}")
    public ResponseEntity<?> updateEmployeeDetails(@AuthenticationPrincipal User User,@PathVariable Long id, @RequestBody EmployeeDetails employeeDetailsRequest) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        User user = userRepository.findById(id+1)
            	.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + (id+1)));
        
        
        EmployeeDetails existingDetails = employee.getEmployeeDetails();
        existingDetails.setSsn(employeeDetailsRequest.getSsn());
        existingDetails.setPhoneNumber(employeeDetailsRequest.getPhoneNumber());
        existingDetails.setPosition(employeeDetailsRequest.getPosition());
        existingDetails.setSalary(employeeDetailsRequest.getSalary());
        existingDetails.setPayRate(employeeDetailsRequest.getPayRate());
        existingDetails.setSupervisor(employeeDetailsRequest.isSupervisor());
        existingDetails.setEmployeeType(employeeDetailsRequest.getEmployeeType());
        existingDetails.setHireDate(employeeDetailsRequest.getHireDate());
        
        user.setAdmin(employeeDetailsRequest.isSupervisor());

        employee.setEmployeeDetails(existingDetails);
        Employee updatedEmployee = employeeRepository.save(employee);
        User createdUser = userRepository.save(user);

        return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee API
    @DeleteMapping("/employees/{id}")
    public ResponseEntity< Map<String,Boolean>> deleteEmployeeById(@PathVariable Long id){

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " does not exists"));
        
        User user = userRepository.findById(id+1)
            	.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + (id+1)));

        employeeRepository.delete(employee);
        userRepository.delete(user);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
