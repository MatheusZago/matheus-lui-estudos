package com.matheus.library.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheus.library.domain.Book;
import com.matheus.library.dto.BookDTO;
import com.matheus.library.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BookDTO>> findAll() {


		List<Book> list = bookService.findAll();
		//Trnasformando a lista em DTO
		List<BookDTO> listDTO = list.stream().map(x -> new BookDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); // usando responseentity para voltar com o código

	} 
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<BookDTO> findById(@PathVariable String id) {
		Book obj = bookService.findById(id);
		return ResponseEntity.ok().body(new BookDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody BookDTO objdto) {
		Book obj = bookService.fromDTO(objdto);
		obj = bookService.insert(obj);
		//Pegando o endereço do novo objeto recebido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/insertAll")
	public void insertAll() {
		bookService.insertAll();

	}
	
    @DeleteMapping("/removeByTitle")
    public void removeByTitle(@RequestParam String title) {
        bookService.removeByTitle(title);
    }

}
