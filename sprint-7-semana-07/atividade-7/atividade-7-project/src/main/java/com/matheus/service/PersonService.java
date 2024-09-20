package com.matheus.service;

import org.springframework.stereotype.Service;

import com.matheus.dto.PersonCreateDto;
import com.matheus.dto.PersonResponseDto;

@Service
public class PersonService {
	
	//This is the entry logic that the controller calls
	public PersonResponseDto login(PersonCreateDto person) {
		//It will create a PersonResponse depending on the age that comes with PerosnCreateDto
		if(person.getAge() >= 18) {
			return new PersonResponseDto(person.getName(), "Entry allowed!");
		} else {
			return new PersonResponseDto(person.getName(), "Entry denied...");
		}
		
		
	}

}
