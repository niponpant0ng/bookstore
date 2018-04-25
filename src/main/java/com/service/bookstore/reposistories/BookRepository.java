package com.service.bookstore.reposistories;

import com.service.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by nipon on 4/23/18.
 */
public interface BookRepository extends JpaRepository<Book, UUID> {
    Book findByBookId(Long bookId);
}
