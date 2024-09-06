package com.matheus.library.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.library.domain.Book;
import com.matheus.library.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Book>> findAll() {
//		Book book1 = new Book("1", "1984", "George Orwell", "1949", "Ficção Cientifica");
//		Book book2 = new Book("2", "Dom Casmurro", "Machado de Assis", "1899", "Romance");
//		Book book3 = new Book("3", "The Lord of the Rings", "J.R.R. Tolkien", "1954", "Fantasia");
//		Book book4 = new Book("4", "Animal Farm", "George Orwell", "1954", "Fabula");

		List<Book> list = bookService.findAll();
		return ResponseEntity.ok().body(list); // usando responseentity para voltar com o código

	}

}
