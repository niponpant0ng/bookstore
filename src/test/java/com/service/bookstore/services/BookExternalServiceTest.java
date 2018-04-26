package com.service.bookstore.services;

import com.service.bookstore.exceptions.ServerInternalErrorException;
import com.service.bookstore.models.Book;
import com.service.bookstore.payloads.BookExternalResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class BookExternalServiceTest {
    private BookExternalService bookExternalService;

    @Before
    public void before() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        bookExternalService = spy(new BookExternalService(restTemplate));
    }

    @Test
    public void testFindBooksWhenAllBooksAndRecommendBooksAreEmpty() {
        doReturn(new ArrayList<>()).when(bookExternalService).findAllBooks();
        doReturn(new ArrayList<>()).when(bookExternalService).findRecommendBooks();

        List<Book> books = bookExternalService.findBooks();

        assertThat(books, hasSize(0));
    }

    @Test(expected = ServerInternalErrorException.class)
    public void testFindBooksWhenAllBookRequestHasProblem() {
        doThrow(ResourceAccessException.class).when(bookExternalService).findAllBooks();

        bookExternalService.findBooks();
    }

    @Test(expected = ServerInternalErrorException.class)
    public void testFindBooksWhenRecommendBookRequestHasProblem() {
        doThrow(ResourceAccessException.class).when(bookExternalService).findRecommendBooks();

        bookExternalService.findBooks();
    }

    @Test
    public void testFindBooksWhenAllBooksAreEmpty() {
        doReturn(new ArrayList<>()).when(bookExternalService).findAllBooks();
        doReturn(Arrays.asList(
                createBook(1L, 200d),
                createBook(2L, 300d)
        )).when(bookExternalService).findRecommendBooks();

        List<Book> books = bookExternalService.findBooks();

        assertThat(books, hasSize(2));
        assertBook(books.get(0), 1L, true);
        assertBook(books.get(1), 2L, true);
    }

    @Test
    public void testFindBooksWhenRecommendBooksAreEmpty() {
        doReturn(new ArrayList<>()).when(bookExternalService).findRecommendBooks();
        doReturn(Arrays.asList(
                createBook(1L, 200d),
                createBook(2L, 300d)
        )).when(bookExternalService).findAllBooks();

        List<Book> books = bookExternalService.findBooks();

        assertThat(books, hasSize(2));
        assertBook(books.get(0), 1L, false);
        assertBook(books.get(1), 2L, false);
    }

    @Test
    public void testFindBooksWhenAllBooksAndRecommendBooksAreNotEmptyAndNotAnyDuplicate() {
        doReturn(Arrays.asList(
                createBook(1L, 200d),
                createBook(2L, 300d)
        )).when(bookExternalService).findAllBooks();
        doReturn(Arrays.asList(
                createBook(3L, 200d),
                createBook(4L, 300d)
        )).when(bookExternalService).findRecommendBooks();

        List<Book> books = bookExternalService.findBooks();

        assertThat(books, hasSize(4));
        assertBook(books.get(0), 3L, true);
        assertBook(books.get(1), 4L, true);
        assertBook(books.get(2), 1L, false);
        assertBook(books.get(3), 2L, false);
    }

    @Test
    public void testFindBooksWhenAllBooksAndRecommendBooksAreNotEmpty() {
        doReturn(Arrays.asList(
                createBook(1L, 200d),
                createBook(2L, 300d)
        )).when(bookExternalService).findAllBooks();
        doReturn(Arrays.asList(
                createBook(1L, 250d),
                createBook(3L, 300d)
        )).when(bookExternalService).findRecommendBooks();

        List<Book> books = bookExternalService.findBooks();

        assertThat(books, hasSize(3));
        assertBook(books.get(0), 1L, true);
        assertBook(books.get(1), 3L, true);
        assertBook(books.get(2), 2L, false);
    }

    private BookExternalResponse createBook(Long id, Double price) {
        BookExternalResponse book = new BookExternalResponse();

        book.setId(id);
        book.setPrice(price);
        book.setBookName("book" + id);
        book.setAuthorName("author" + id);

        return book;
    }

    private void assertBook(Book book, Long bookId, boolean isRecommend) {
        assertThat(book.getBookId(), is(bookId));
        assertThat(book.getName(), is("book" + bookId));
        assertThat(book.getAuthor(), is("author" + bookId));
        assertThat(book.getRecommend(), is(isRecommend));
    }
}
