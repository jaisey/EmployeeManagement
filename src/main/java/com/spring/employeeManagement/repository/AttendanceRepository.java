package com.spring.employeeManagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.employeeManagement.entity.Attendance;
@Repository
public interface AttendanceRepository  extends MongoRepository<Attendance,Integer>{

}
