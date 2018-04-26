package com.service.bookstore.config;

import com.service.bookstore.services.BookExternalService;
import com.service.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Configure {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Component
    public class Initializer implements CommandLineRunner {
        @Autowired
        private BookService bookService;

        @Autowired
        private BookExternalService bookExternalService;

        @Override
        public void run(String... strings) {
            if(bookService.areBooksEmpty()) {
                bookService.createBooks(bookExternalService.findBooks());
            }
        }
    }
}
