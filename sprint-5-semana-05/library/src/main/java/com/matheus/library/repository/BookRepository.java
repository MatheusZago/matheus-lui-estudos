package com.matheus.library.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.library.domain.Book;

@Repository //Para virar um repositório
public interface BookRepository extends MongoRepository<Book, String>{ 
	
	//Usando Metodo Query para deleção
//	@Query("{ 'title': ?0 }")
//    void deleteByTitle(String title);

    // Método de deleção baseado em nome do campo
    void deleteByTitle(String title);
    Book findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByYear(String year);
}
