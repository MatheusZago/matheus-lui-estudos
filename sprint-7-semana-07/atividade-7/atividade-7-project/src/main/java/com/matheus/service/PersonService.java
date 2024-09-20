package com.matheus.service;

import org.springframework.stereotype.Service;

import com.matheus.dto.PersonCreateDto;
import com.matheus.dto.PersonResponseDto;

@Service
public class PersonService {
	
	public PersonResponseDto login(PersonCreateDto person) {
		if(person.getAge() >= 18) {
			return new PersonResponseDto(person.getName(), "Entry allowed!");
		} else {
			return new PersonResponseDto(person.getName(), "Entry denied...");
		}
		
		
	}

}
