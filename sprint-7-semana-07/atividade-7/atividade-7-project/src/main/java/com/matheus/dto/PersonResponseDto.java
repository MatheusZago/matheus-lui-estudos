package com.matheus.dto;

public class PersonResponseDto {

	private String name;
	private String msg;

	public PersonResponseDto(String name, String msg) {
		this.name = name;
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public String getMsg() {
		return msg;
	}
	
}
