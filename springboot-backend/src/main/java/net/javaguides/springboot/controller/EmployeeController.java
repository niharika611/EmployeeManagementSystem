package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRespository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRespository employeeRespository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRespository.findAll();
	}
	
	//create employee rest api
	@PostMapping("/employees")
	public Employee createEmployees(@RequestBody Employee employee) {
		return employeeRespository.save(employee);
	}
	
	//get employee by id reset api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeId(@PathVariable Long id) {
		Employee employee=employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists id: "+ id));
		
		return ResponseEntity.ok(employee);
	}

	//update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
		Employee employee=employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists id: "+ id));
		
		employee.setFirstname(employeeDetails.getFirstname());
		employee.setLastname(employeeDetails.getLastname());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee= employeeRespository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	//delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee=employeeRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exists id: "+ id));
		
		employeeRespository.delete(employee);
		Map<String, Boolean> response= new HashMap<>();
		response.put("deleted", true);
		return ResponseEntity.ok(response);
	}
}
