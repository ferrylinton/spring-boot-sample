package com.bloghu.jpahibernateehchache.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bloghu.jpahibernateehchache.entity.Book;
import com.bloghu.jpahibernateehchache.repository.BookRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

	private final BookRepository bookRepository;
	
	@GetMapping
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Book> findById(@PathVariable Long id) {
		return bookRepository.findById(id);
	}
	
	@GetMapping("/update")
	public Optional<Book> update() {
		Optional<Book> book = bookRepository.findById(1L);
		
		if(book.isPresent()) {
			book.get().setName("xxxxxxxxxxxxxx");
			bookRepository.save(book.get());
		}
		
		return bookRepository.findById(1L);
	}

}
