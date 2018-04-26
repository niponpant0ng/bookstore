package com.service.bookstore.reposistories;

import com.service.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findByBookId(Long bookId);

    @Query("SELECT b FROM Book b ORDER BY b.isRecommend DESC")
    List<Book> findAllBooks();

    @Query("SELECT COUNT(b.id) FROM Book b")
    Integer countBook();
}
