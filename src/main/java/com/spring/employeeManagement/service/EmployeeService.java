package com.spring.employeeManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.spring.employeeManagement.entity.Attendance;
import com.spring.employeeManagement.entity.Employee;
import com.spring.employeeManagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	SequenceGenerator sequenceGenerator;
	
	@Autowired
	MongoTemplate mongoTemplate;
	public void addEmployee(Employee e)
	{
		e.setId(sequenceGenerator.generateSequence("Employee_Sequence"));
		employeeRepo.save(e);
	}
	
	public List<Employee> getAllEmployee()
	{
		return employeeRepo.findAll();
	}

	public Employee editEmployee(int id) {
		Optional<Employee> emp= employeeRepo.findById(id);
		 if(emp.isPresent())
			 return emp.get();
		 
		return null;
		
	}

	public void updateEmployee(Employee e) {
		Query query=new Query(Criteria.where("id").is(e.getId()));
		Update update=new Update()
				.set("name",e.getName())
				.set("dateOfBirth",e.getDateOfBirth())
				.set("phoneNo", e.getPhoneNo())
				.set("salary", e.getSalary());
		FindAndModifyOptions options=FindAndModifyOptions.options().upsert(true).returnNew(true);
		mongoTemplate.findAndModify(query, update, options,Employee.class);
		
	}

	public void deleteEmployee(int id) {
		employeeRepo.deleteById(id);
		
	}

	public void saveAttendance(Integer employeeId, String attendance, LocalDate attendanceDate) {

		Employee employee = employeeRepo.findById(employeeId).orElse(null);
		if (employee.getAttendance() != null) {
			List<Attendance> attendances = employee.getAttendance();

			Optional<Attendance> existingAttendance = attendances.stream().filter(
					attend -> attend.getAttendanceDate() != null && attend.getAttendanceDate().equals(attendanceDate))
					.findFirst();

			if (existingAttendance.isPresent()) {
				// Update existing attendance
				Attendance existing = existingAttendance.get();
				existing.setAttendanceStatus(attendance);
			} else {
				// Create new attendance
				Attendance newAttendance = new Attendance();
				newAttendance.setAttendanceDate(attendanceDate);
				newAttendance.setAttendanceStatus(attendance);
				attendances.add(newAttendance);
			}
			employeeRepo.save(employee);
		} else {

			Attendance attend = new Attendance();
			ArrayList<Attendance> attendanceList = new ArrayList<>();
			attend.setAttendanceStatus(attendance);
			attend.setAttendanceDate(attendanceDate);
			attendanceList.add(attend);
			employee.setAttendance(attendanceList);
			employeeRepo.save(employee);
		}
	}

	public Integer calculateSalary(Integer employeeId, LocalDate fromDate, LocalDate toDate, Integer advancedSalary) {
		Employee employee = employeeRepo.findById(employeeId).orElse(null);
		if(employee.getAttendance()!=null)
		{
			long presentDays = employee.getAttendance().stream()
			.filter(attend->attend.getAttendanceDate()!=null  && !attend.getAttendanceDate().isBefore(fromDate)
			&& !attend.getAttendanceDate().isAfter(toDate)
			&& "present".equalsIgnoreCase(attend.getAttendanceStatus())).count();
			
			Integer salary=calculateSalary(presentDays,advancedSalary,employee.getSalary());
			return salary;
		}
		return null;
	}
		
		public Integer calculateSalary(long presentDays,Integer advancedSalary,Integer salaryDecided)
		{
			return (int) ((presentDays*salaryDecided)-advancedSalary);
		}
}
