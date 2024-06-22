package com.example.erpdemo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.BookNotFoundException;
import com.example.erpdemo.Exception.BookValidationException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.config.QueryBuilder;
import com.example.erpdemo.model.Book;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.repository.BookRepository;
import com.example.erpdemo.service.BookService;

import io.micrometer.common.util.StringUtils;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}

	@Override
	public Book createBook(Book book) throws BookException {
		validateBook(book);
		return bookRepo.save(book);
	}

	@Override
	public Book updateBook(String bookId, Book book) throws BookException {

		Optional<Book> bookOptional = bookRepo.findById(bookId);

		if (bookOptional.isPresent()) {
			validateBook(book);
			book.setId(bookId);
			return bookRepo.save(book);

		} else {
			throw new BookNotFoundException("book with given id is not found");
		}

	}

	@Override
	public void deleteBook(String bookId) throws BookNotFoundException {

		Optional<Book> bookOptional = bookRepo.findById(bookId);

		if (bookOptional.isPresent()) {
			bookRepo.deleteById(bookId);
		} else {
			throw new BookNotFoundException("book with given id is not found");
		}

	}

	void validateBook(Book book) throws BookException {
		if (StringUtils.isEmpty(book.getName())) {
			throw new BookValidationException("book name can not be empty");
		} else if (StringUtils.isEmpty(book.getISBN())) {
			throw new BookValidationException("isbn can not be empty");
		}
	}

	@Override
	public Page<Book> search(SearchEvent searchEvent, Pageable pageable) throws SearchException {
		Query query = null;

		query = QueryBuilder.buildSearchQuery(searchEvent, pageable);

		query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));

		return new PageImpl<>(this.mongoOperations.find(query, Book.class), pageable,
				this.mongoOperations.count(query, Book.class));
	}

}
