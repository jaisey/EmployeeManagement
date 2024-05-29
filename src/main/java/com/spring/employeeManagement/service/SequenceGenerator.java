package com.spring.employeeManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.spring.employeeManagement.entity.EmployeeSequence;

@Service
public class SequenceGenerator {

	
	@Autowired
	private MongoOperations mongoOperations;
	
	public int generateSequence(String seqName)
	{
		EmployeeSequence counter=mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
				new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true),
				EmployeeSequence.class);
		
		return counter != null ? counter.getSeq() : 1;
	}
}
