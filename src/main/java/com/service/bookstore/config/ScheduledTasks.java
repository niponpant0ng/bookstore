package com.service.bookstore.config;

import com.service.bookstore.services.BookExternalService;
import com.service.bookstore.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private BookService bookService;
    private BookExternalService bookExternalService;

    @Autowired
    public ScheduledTasks(BookService bookService, BookExternalService bookExternalService) {
        this.bookService = bookService;
        this.bookExternalService = bookExternalService;
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void refreshBooks() {
        if(bookService.areBooksEmpty()) {
            bookService.createBooks(bookExternalService.findBooks());
        }
    }
}
