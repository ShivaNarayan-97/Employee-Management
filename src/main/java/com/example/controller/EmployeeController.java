package com.example.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Employee;
import com.example.helper.ResponseStructure;
import com.example.repository.EmployeeRepository;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository er;
	
	//Save the data
	@PostMapping("/saveemp")
	public ResponseStructure<Employee> saveEmp(@RequestBody Employee employee){
		er.save(employee);
		ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setData(employee);
		rs.setMessage("Data Saved Successfully");
		return rs;
	}
	
	//Get the data
	@GetMapping("/fetchdatabyid")
	public ResponseStructure<Employee> fetchDataById(@RequestParam("id") int id){
		try {
			Optional<Employee> option = er.findById(id);
			Employee employee = option.get();
			ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
			rs.setStatuscode(HttpStatus.FOUND.value());
			rs.setData(employee);
			rs.setMessage("Data Found");
			return rs;
		} catch (NoSuchElementException e) {
			ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
			rs.setStatuscode(HttpStatus.FOUND.value());
			rs.setData(null);
			rs.setMessage("Data Found");
			return rs;
		}
	}
	
	@GetMapping("/fetchDataByName")
	public ResponseStructure<List<Employee>> fetchDataByName(@RequestParam("name") String name){
		List<Employee> employees = er.findAllByName(name);
		if (employees.size() > 0) {
			ResponseStructure<List<Employee>> rs = new ResponseStructure<List<Employee>>();
			rs.setStatuscode(HttpStatus.FOUND.value());
			rs.setData(employees);
			rs.setMessage("Data Found");
			return rs;
		}else {
			ResponseStructure<List<Employee>> rs = new ResponseStructure<List<Employee>>();
			rs.setStatuscode(HttpStatus.NOT_FOUND.value());
			rs.setData(null);
			rs.setMessage("Data Not Found");
			return rs;
		}
	}
	
	@GetMapping("/fetchdatabetween")
	public ResponseStructure<List<Employee>> fetchByAgeBetween(@RequestParam("start") int start, @RequestParam("end") int end){
		List<Employee> employees = er.findByAgeBetween(start, end);
		ResponseStructure<List<Employee>> rs = new ResponseStructure<List<Employee>>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setData(employees);
		rs.setMessage("Data fetched");
		return rs;
	}
	
	//Update the Data
	@PutMapping("/updatedata")
	public ResponseStructure<Employee> updateEmployee(@RequestBody Employee employee){
		er.save(employee);
		try {
			ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
			rs.setStatuscode(HttpStatus.ACCEPTED.value());
			rs.setData(employee);
			rs.setMessage("Data updated");
			return rs;
		} catch (Exception e) {
			ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
			rs.setStatuscode(HttpStatus.NOT_ACCEPTABLE.value());
			rs.setData(null);
			rs.setMessage("Data not updated");
			return rs;
		}
	}
	
	//Delete the data
	@DeleteMapping("/deletedata")
	public ResponseStructure<Employee> deleteEmployee(@RequestParam("id") int id){
		Optional<Employee> option = er.findById(id);
		Employee employee = option.get();
		er.deleteById(id);
		
		ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setData(employee);
		rs.setMessage("Data deleted");
		return rs;
	}
	
	@DeleteMapping("/deletebyname")
	public ResponseStructure<Employee> deleteByName(@RequestParam("name") String name){
//		Employee employee = er.deleteByName(name);
		Employee employee = er.findByName(name);
		
		if (employee == null) {
			ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
			rs.setStatuscode(HttpStatus.NOT_FOUND.value());
			rs.setData(null);
			rs.setMessage("Employee with name " + name + " not found");
			return rs;
		}
		er.delete(employee);
		
		ResponseStructure<Employee> rs = new ResponseStructure<Employee>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setData(employee);
        rs.setMessage("Data deleted");
        return rs;
	}
}
