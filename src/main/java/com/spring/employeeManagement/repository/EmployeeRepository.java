package com.spring.employeeManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.employeeManagement.entity.Employee;

@Repository
public interface EmployeeRepository  extends MongoRepository<Employee,Integer>{

	

	

}
