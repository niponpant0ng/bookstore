package com.service.bookstore.services;

import com.service.bookstore.exceptions.BadRequestException;
import com.service.bookstore.models.Book;
import com.service.bookstore.models.Order;
import com.service.bookstore.models.User;
import com.service.bookstore.payloads.UserOrderResponse;
import com.service.bookstore.reposistories.BookRepository;
import com.service.bookstore.reposistories.OrderReposistory;
import com.service.bookstore.reposistories.UserReposistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Created by nipon on 4/26/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserOrderServiceTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserReposistory userReposistory;

    @Autowired
    private OrderReposistory orderReposistory;

    @Autowired
    private BookRepository bookRepository;

    private UserOrderService userOrderService;

    @Before
    public void before() {
        userOrderService = new UserOrderService(userReposistory, orderReposistory, bookRepository);
    }

    @After
    public void after() {
        orderReposistory.deleteAll();
        userReposistory.deleteAll();
    }

    @Test(expected = BadRequestException.class)
    public void testGetUserAndOrdersWhenUserIsEmpty() {
        User user = new User();
        user.setId(UUID.randomUUID());

        userOrderService.getUserAndOrders(user);
    }

    @Test
    public void testGetUserAndOrdersWhenOrderIsEmpty() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);

        UserOrderResponse userOrderResponse = userOrderService.getUserAndOrders(createdUser);

        assertThat(userOrderResponse.getName(), is("john"));
        assertThat(userOrderResponse.getSurname(), is("doe"));
        assertThat(userOrderResponse.getDateOfBirth(), is(birthDate));
        assertThat(userOrderResponse.getBooks(), hasSize(0));
    }

    @Test
    public void testGetUserAndOrdersWhenOrderIsNotEmpty() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);
        Book book1 = createBook(1L);
        Book book2 = createBook(2L);

        createOrder(createdUser, book1);
        createOrder(createdUser, book2);

        UserOrderResponse userOrderResponse = userOrderService.getUserAndOrders(createdUser);

        assertThat(userOrderResponse.getName(), is("john"));
        assertThat(userOrderResponse.getSurname(), is("doe"));
        assertThat(userOrderResponse.getDateOfBirth(), is(birthDate));

        assertThat(userOrderResponse.getBooks(), hasSize(2));
        assertThat(userOrderResponse.getBooks().get(0), is(1L));
        assertThat(userOrderResponse.getBooks().get(1), is(2L));
    }

    @Test
    public void testDeleteUserAndOrderWhenUserIsEmpty() {
        User user = new User();
        user.setId(UUID.randomUUID());

        userOrderService.deleteUserAndOrders(user);

        assertThat(userReposistory.findAll(), hasSize(0));
        assertThat(orderReposistory.findAll(), hasSize(0));
    }

    @Test
    public void testDeleteUserAndOrderWhenOrderIsEmpty() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);

        userOrderService.deleteUserAndOrders(createdUser);

        assertThat(userReposistory.findAll(), hasSize(0));
        assertThat(orderReposistory.findAll(), hasSize(0));
    }

    @Test
    public void testDeleteUserAndOrderWhenUserAndOrderAreNotEmpty() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);
        Book book1 = createBook(1L);
        Book book2 = createBook(2L);
        createOrder(createdUser, book1);
        createOrder(createdUser, book2);

        userOrderService.deleteUserAndOrders(createdUser);

        assertThat(userReposistory.findAll(), hasSize(0));
        assertThat(orderReposistory.findAll(), hasSize(0));
    }

    @Test(expected = BadRequestException.class)
    public void testOrderUserBookWhenBookIdsAreEmpty() {
        User user = new User();
        user.setId(UUID.randomUUID());

        userOrderService.orderUserBook(user, new ArrayList<>());
    }

    @Test(expected = BadRequestException.class)
    public void testOrderUserBookWhenSomeBookIdsIsNotFound() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);
        createBook(1L);

        userOrderService.orderUserBook(createdUser, Arrays.asList(1L, 2L));
    }

    @Test
    public void testOrderUserBookWhenUserAndBookIdsAreNotEmpty() {
        UUID userId = UUID.randomUUID();
        LocalDate birthDate = LocalDate.now();

        User createdUser = createUser(userId, birthDate);
        createBook(1L, 150d);
        createBook(2L, 55.5d);

        Double price = userOrderService.orderUserBook(createdUser, Arrays.asList(1L, 2L));

        assertThat(price, is(205.5d));
    }

    private User createUser(UUID userId, LocalDate birthDate) {
        User user = new User();
        user.setId(userId);
        user.setName("john");
        user.setSurname("doe");
        user.setBirthDate(birthDate);

        return userReposistory.save(user);
    }

    private Book createBook(Long bookId, Double price) {
        Book book = new Book();
        book.setBookId(bookId);
        book.setPrice(price);

        return testEntityManager.persist(book);
    }

    private Book createBook(Long bookId) {
        return createBook(bookId, 0d);
    }

    private Order createOrder(User user, Book book1) {
        Order order = new Order();
        order.setUser(user);
        order.setBook(book1);

        return testEntityManager.persist(order);
    }
}
