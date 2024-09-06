package com.matheus.library.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.library.domain.Book;

@Repository //Para virar um repositório
public interface BookRepository extends MongoRepository<Book, String>{ 

}
