package com.matheus.library.dto;

import java.io.Serializable;

import com.matheus.library.domain.Book;

//Class used to access and DTO format
public class BookDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String author;
	private Integer year;
	private String genre;
	
	public BookDTO() {
		
	}
	
	public BookDTO(Book book) {
		id = book.getId();
		title = book.getTitle();
		author = book.getAuthor();
		year = book.getYear();
		genre = book.getGenre();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	

}
