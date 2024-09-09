package com.matheus.library.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.library.domain.Book;

//This class is responsability is to access the Database
@Repository //To access the repository from mongo
public interface BookRepository extends MongoRepository<Book, String>{ 
	
    //These methods are made from MongoRepository with Query Methods
    void deleteByTitle(String title);
    Book findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByYear(String year);
    List<Book> findByYearGreaterThan(int year);
    List<Book> findByYearLessThan(int year);
}
