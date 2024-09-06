package com.matheus.library.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.library.domain.Book;
import com.matheus.library.dto.BookDTO;
import com.matheus.library.repository.BookRepository;
import com.matheus.library.services.exception.ObjectNotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	//Essas informações vem do MongoRepository, que é oq o bookRepository usa
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	
	public Book findById(String id) {
		Optional<Book> book = bookRepository.findById(id);
		return book.orElseThrow(() -> new ObjectNotFoundException("Object not found..."));
	}
	
	//Metodo para inserir todos os valores 
	public Book insert(Book obj) {
		return bookRepository.insert(obj);
	}
	
	public void Delete(String id) {
		bookRepository.deleteById(id);
	}
	
	
	public void insertAll() {
		Book book1 = new Book(null, "1984", "George Orwell", "1949", "Ficção Cientifica");
		Book book2 = new Book(null, "Dom Casmurro", "Machado de Assis", "1899", "Romance");
		Book book3 = new Book(null, "The Lord of the Rings", "J.R.R. Tolkien", "1954", "Fantasia");
		Book book4 = new Book(null, "Animal Farm", "George Orwell", "1954", "Fabula");
		
		bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4));
	}
	
	public void removeByTitle(String title) {
		bookRepository.deleteByTitle(title);
	}
	
	public List<Book> findByAuthor(String author) { 	
		return bookRepository.findByAuthor(author);
	} 
	
	public List<Book> findByYear(String year) { 	
		return bookRepository.findByYear(year);
	}
	
	public Book update(Book obj) {
		//Esse User obj é só o do App, nn tem reação com o BD
		//Ao fzer isso ele vai instanciar o do BD para ser usado e atualizado.
		Book newObj = bookRepository.findByTitle(obj.getTitle());
		updateData(newObj, obj);
		return bookRepository.save(newObj);
	}

	//Transformando o newObj com os dados do obj
	private void updateData(Book newObj, Book obj) {
		newObj.setTitle(obj.getTitle());
		newObj.setAuthor(obj.getAuthor());
		newObj.setYear(obj.getYear());
		newObj.setGenre(obj.getGenre());

	}
	
	
	public Book fromDTO(BookDTO objDTO) {
		return new Book(objDTO.getId(), objDTO.getTitle(), objDTO.getAuthor(), objDTO.getYear(), objDTO.getGenre());
	}
}
