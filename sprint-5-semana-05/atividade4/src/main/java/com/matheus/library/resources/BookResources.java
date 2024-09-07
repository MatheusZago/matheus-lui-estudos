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
public class BookResources {

	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Book>> findAll() {
		List<Book> list = bookService.findAll();
		return ResponseEntity.ok().body(list);
	}

}
