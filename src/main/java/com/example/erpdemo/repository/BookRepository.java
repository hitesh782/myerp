package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.erpdemo.model.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

}
