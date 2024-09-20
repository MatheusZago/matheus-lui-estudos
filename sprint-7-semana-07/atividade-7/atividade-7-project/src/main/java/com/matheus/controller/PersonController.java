package com.matheus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.dto.PersonCreateDto;
import com.matheus.dto.PersonResponseDto;
import com.matheus.service.PersonService;

//Controller to deal with the requests for the method
@RestController
@RequestMapping("/person")
public class PersonController {

	//Creating a dependency with service to use its methods
	@Autowired
	private PersonService service;
	
	@PostMapping()
	public ResponseEntity<PersonResponseDto> login(@RequestBody PersonCreateDto person) {
		PersonResponseDto test = service.login(person);
		//Returning a request body with the values of the ResponseDto
		return ResponseEntity.ok(test);
	}
	
}
