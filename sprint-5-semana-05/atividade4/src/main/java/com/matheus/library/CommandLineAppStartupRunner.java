package com.matheus.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.matheus.library.services.BookService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Override
    public void run(String... args)  {
        bookService.testConnection();
    }
}