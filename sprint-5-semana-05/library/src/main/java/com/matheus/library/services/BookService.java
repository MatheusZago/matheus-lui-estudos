package com.matheus.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.library.domain.Book;
import com.matheus.library.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	//Essas informações vem do MongoRepository, que é oq o bookRepository usa
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	//Metodo para inserir todos os valores 
	public Book insert(Book obj) {
		return bookRepository.insert(obj);
	}
	

}
