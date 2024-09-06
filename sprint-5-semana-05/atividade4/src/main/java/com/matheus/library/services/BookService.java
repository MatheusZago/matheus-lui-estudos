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
	
	
	public void testConnection() {
		Book book = new Book();
		book.setTitle("teste");
		bookRepository.save(book);
		
		Book bookFound = bookRepository.findById(book.getId()).orElse(null);
		if(bookFound != null) {
			System.out.println("Conexão feita com sucesso");
		} else {
			System.out.println("Não achou");
		}
		
	}
	
	
	public List<Book> findAll(){
		return bookRepository.findAll();
	}

}
