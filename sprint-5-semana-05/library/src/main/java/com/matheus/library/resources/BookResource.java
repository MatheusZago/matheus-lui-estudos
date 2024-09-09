package com.matheus.library.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheus.library.domain.Book;
import com.matheus.library.dto.BookDTO;
import com.matheus.library.services.BookService;

//This class is used to to resolve requests of the project
@RestController //Rest Controller for the app to know that this is a controller
@RequestMapping(value = "/books") //To know the endpoint to call this methods
public class BookResource {

	@Autowired //Dependency
	private BookService bookService;

	@GetMapping //Find all for tests
	public ResponseEntity<List<BookDTO>> findAll() {

		List<Book> list = bookService.findAll();
		// Trnasformando a lista em DTO
		List<BookDTO> listDTO = list.stream().map(x -> new BookDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO); // usando responseentity para voltar com o código

	}
	
	//Insert all to suffice item 2
	@PostMapping("/insertAll")
	public void insertAll() {
		bookService.insertAll();
		
	}
	
	//Remove by title to suffice item 3
	@DeleteMapping("/removeByTitle")
	public ResponseEntity<Void> removeByTitle(@RequestParam String title) {
		bookService.removeByTitle(title);
		return ResponseEntity.noContent().build();
	}
	
	//Update by title to suffice item 4
	@PutMapping(value = "/{title}")
	public ResponseEntity<Void> update(@RequestBody BookDTO objDto, @PathVariable String title) {
		Book obj = bookService.fromDTO(objDto);
		obj.setTitle(title);
		obj = bookService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	//Find by author to sufice Item 5 
    @GetMapping("/findByAuthor/{author}")
    public ResponseEntity<List<BookDTO>> findByAuthor(@PathVariable String author) {
        List<Book> books = bookService.findByAuthor(author);
        List<BookDTO> listDTO = books.stream().map(BookDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
    
    //Find by year to suffice Item 6
    @GetMapping("/findByYear/{year}")
    public ResponseEntity<List<BookDTO>> findByYear(@PathVariable String year) {
        List<Book> books = bookService.findByYear(year);
        List<BookDTO> listDTO = books.stream().map(BookDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    //FindById for use in tests and in other methods
	@GetMapping(value = "/{id}")
	public ResponseEntity<BookDTO> findById(@PathVariable String id) {
		Book obj = bookService.findById(id);
		return ResponseEntity.ok().body(new BookDTO(obj));
	}

	//Single insert for tests
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody BookDTO objdto) {
		Book obj = bookService.fromDTO(objdto);
		obj = bookService.insert(obj);
		// Pegando o endereço do novo objeto recebido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}



}
