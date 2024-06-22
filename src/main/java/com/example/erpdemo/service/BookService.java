package com.example.erpdemo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.model.Book;
import com.example.erpdemo.model.query.SearchEvent;

public interface BookService {
	List<Book> getAllBooks();

	Book createBook(Book book) throws BookException;

	Book updateBook(String bookId, Book book) throws BookException;

	void deleteBook(String bookId) throws BookException;

	Page<Book> search(SearchEvent searchEvent, Pageable pageable) throws SearchException;
}
