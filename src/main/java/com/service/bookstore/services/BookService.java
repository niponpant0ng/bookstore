package com.service.bookstore.services;

import com.service.bookstore.models.Book;
import com.service.bookstore.reposistories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void createBooks(List<Book> books) {
        books.forEach(book -> bookRepository.save(book));
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    public boolean areBooksEmpty() {
        return bookRepository.countBook() == 0;
    }
}
