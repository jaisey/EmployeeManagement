package com.spring.employeeManagement.entity;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Attendance")
public class Attendance {

	public LocalDate attendanceDate;
	public String attendanceStatus;
	
	
	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}
	
	
	
}
