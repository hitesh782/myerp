package com.example.erpdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.BookException;
import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.model.Book;
import com.example.erpdemo.model.query.SearchEvent;
import com.example.erpdemo.service.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping()
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@PostMapping()
	public Book createBook(@RequestBody Book book) throws BookException {
		return bookService.createBook(book);
	}

	@PostMapping("/criteria-search")
	public Page<Book> search(@RequestBody SearchEvent searchEvent, Pageable pageable) throws SearchException {
		return bookService.search(searchEvent, pageable);
	}

	@PutMapping()
	public Book updateBook(@PathVariable String bookId, @RequestBody Book book) throws BookException {
		return bookService.updateBook(bookId, book);
	}

	@DeleteMapping()
	public void deleteBook(@PathVariable String bookId) throws BookException {
		bookService.deleteBook(bookId);
	}

}
