package com.bloghu.jpahibernateehchache.repository;

import org.springframework.data.repository.CrudRepository;

import com.bloghu.jpahibernateehchache.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

}
