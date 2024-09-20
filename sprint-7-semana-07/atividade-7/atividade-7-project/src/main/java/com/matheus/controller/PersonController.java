package com.matheus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	private PersonService service;
	
}
