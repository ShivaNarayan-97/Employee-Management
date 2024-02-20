package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	List<Employee> findAllByName(String name);
	
	Employee findByName(String name);
	
	List<Employee> findByEmail(String email);
	
	List<Employee> findByAgeBetween(int start, int end);
	
	Employee deleteByName(String name);
}
