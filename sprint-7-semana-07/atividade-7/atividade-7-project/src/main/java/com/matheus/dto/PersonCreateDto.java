package com.matheus.dto;

public class PersonCreateDto {
	
	//This is the Dto used on the entrance when the person is trying to access the login
	private String name;
	private Integer age;
	
	public PersonCreateDto(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

}
