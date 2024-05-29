package com.spring.employeeManagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.employeeManagement.entity.Employee;
import com.spring.employeeManagement.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/")
	public String home(Model m) {
		List<Employee> allEmployee = employeeService.getAllEmployee();
		m.addAttribute("allEmployee", allEmployee);
		return "index";
	}

	@GetMapping("/addEmp")
	public String addEmployee() {
		return "addEmp";
	}

	@PostMapping("/register")
	public String employeeRegister(@ModelAttribute Employee employee, HttpSession session) {
		System.out.println(employee);
		employeeService.addEmployee(employee);
		session.setAttribute("message", "Employee Added succesfully");

		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String editEmployee(@PathVariable int id, Model m) {
		Employee e = employeeService.editEmployee(id);
		m.addAttribute("emp", e);
		return "editEmp";
	}

	@PostMapping("/update")
	public String updateEmployee(@ModelAttribute Employee e, HttpSession session) {
		employeeService.updateEmployee(e);
		session.setAttribute("message", "Employee Updated succesfully");
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable int id, HttpSession session) {
		employeeService.deleteEmployee(id);
		session.setAttribute("message", "Employee Deleted succesfully");
		return "redirect:/";
	}

	@GetMapping("/addAttendance/{id}")
	public String addAttendance(Model m, @PathVariable int id) {
		Employee e = employeeService.editEmployee(id);
		m.addAttribute("emp", e);
		return "addAttendance";
	}

	@PostMapping("/saveAttendance")
	public String saveAttendance(@RequestParam("id") Integer employeeId,
			@RequestParam("attendanceStatus") String attendanceStatus,
			@RequestParam("attendanceDate") LocalDate attendanceDate
	,HttpSession session) {
		 employeeService.saveAttendance(employeeId ,attendanceStatus,attendanceDate ); 
		 session.setAttribute("message", "Attendance added successfully");
		return "redirect:/";

	}
	
	
	@GetMapping("/calculateWages/{id}")
	public String calculateWages(Model m, @PathVariable int id) {
		Employee e = employeeService.editEmployee(id);
		m.addAttribute("emp", e);
		return "calculateWages";
	}
	
	@PostMapping("/calculateSalary")
	public String saveAttendance(@RequestParam("id") Integer employeeId,
			@RequestParam("fromDate") LocalDate fromDate,
			@RequestParam("toDate") LocalDate toDate,
			@RequestParam("advancedSalary") Integer advancedSalary,
			Model model
	) {
		Employee e = employeeService.editEmployee(employeeId);
		Integer salary= employeeService.calculateSalary(employeeId,fromDate,toDate,advancedSalary);
		model.addAttribute("salary", salary);
		model.addAttribute("emp", e);
		return "calculateWages";

	}
	
	

}
