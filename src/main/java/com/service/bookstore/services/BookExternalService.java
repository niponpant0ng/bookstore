package com.service.bookstore.services;

import com.service.bookstore.exceptions.ServerInternalErrorException;
import com.service.bookstore.models.Book;
import com.service.bookstore.payloads.BookExternalResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookExternalService {
    private RestTemplate restTemplate;

    public BookExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BookExternalResponse> findAllBooks() {
        ResponseEntity<List<BookExternalResponse>> books = restTemplate.exchange("https://scb-test-book-publisher.herokuapp.com/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookExternalResponse>>() {});
        return books.getBody();
    }

    public List<BookExternalResponse> findRecommendBooks() {
        ResponseEntity<List<BookExternalResponse>> books = restTemplate.exchange("https://scb-test-book-publisher.herokuapp.com/books/recommendation",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookExternalResponse>>() {});
        return books.getBody();
    }

    public List<Book> findBooks() {
        try {
            List<BookExternalResponse> allRespBooks = findAllBooks();
            List<BookExternalResponse> recommendRespBooks = findRecommendBooks();

            List<Book> recommendBooks = recommendRespBooks.stream()
                    .map(book -> mapToBook(book, true))
                    .collect(Collectors.toList());

            List<Long> recommendBookIds = recommendBooks.stream()
                    .map(Book::getBookId)
                    .collect(Collectors.toList());

            List<Book> allBooks = allRespBooks.stream()
                    .filter(book -> !recommendBookIds.contains(book.getId()))
                    .map(book -> mapToBook(book, false))
                    .collect(Collectors.toList());

            List<Book> books = new ArrayList<>();
            books.addAll(recommendBooks);
            books.addAll(allBooks);

            return books;
        } catch (RuntimeException ex) {
            throw new ServerInternalErrorException();
        }
    }

    private Book mapToBook(BookExternalResponse bookExternalResponse, boolean isRecommend) {
        Book book = new Book();

        book.setBookId(bookExternalResponse.getId());
        book.setName(bookExternalResponse.getBookName());
        book.setAuthor(bookExternalResponse.getAuthorName());
        book.setPrice(bookExternalResponse.getPrice());
        book.setRecommend(isRecommend);

        return book;
    }
}
