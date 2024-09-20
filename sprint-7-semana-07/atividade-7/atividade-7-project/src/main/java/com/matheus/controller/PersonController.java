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


@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService service;
	
	@PostMapping()
	public ResponseEntity<PersonResponseDto> login(@RequestBody PersonCreateDto person) {
		PersonResponseDto test = service.login(person);
		return ResponseEntity.ok(test);
	}
	
}
