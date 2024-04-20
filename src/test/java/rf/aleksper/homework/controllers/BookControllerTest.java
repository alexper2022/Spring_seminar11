package rf.aleksper.homework.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import rf.aleksper.homework.entity.Book;
import rf.aleksper.homework.repository.BookRepository;
import rf.aleksper.homework.services.BookService;

import java.util.List;
import java.util.Objects;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void getAllBooksWebTest() {
        List<Book> expectedBookList = List.of(
                new Book("Book1"),
                new Book("Book2"),
                new Book("Book3")
        );
        bookRepository.saveAll(expectedBookList);

        List<Book> responseBody = webTestClient.get()
                .uri("book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(expectedBookList.size(), responseBody.size());
        for (Book Book : responseBody) {
            boolean found = expectedBookList.stream()
                    .filter(book -> Objects.equals(Book.getId(), book.getId()))
                    .allMatch(book -> Objects.equals(Book.getName(), book.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void getByIdWebTestSuccess() {
        Book expectedBook = bookRepository.save(new Book("Book4"));

        Book responseBody = webTestClient.get()
                .uri("book/" + expectedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedBook.getId(), responseBody.getId());
        Assertions.assertEquals(expectedBook.getName(), responseBody.getName());

    }

    @Test
    void getByIdNotFound() {

        bookRepository.saveAll(List.of(
                new Book("Book5"),
                new Book("Book6"),
                new Book("Book7")
        ));

        webTestClient.get()
                .uri("book/4")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void removeBookByIdWebTest() {

        Book expectedBook = bookRepository.save(new Book("Book8"));

        Book responseBody = webTestClient.delete()
                .uri("book/" + expectedBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedBook.getId(), responseBody.getId());
        Assertions.assertEquals(expectedBook.getName(), responseBody.getName());

    }

    @Test
    void createBookIntegrationTest() {
        Book actualBook = bookService.addBook("Book9");

        Assertions.assertNotNull(actualBook);
        Assertions.assertEquals(actualBook.getName(), "Book9");
    }

    @Test
    void createBookWebTest() {
        Book expectedBook = new Book("Book10");

        Book responseBody = webTestClient.post()
                .uri("book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expectedBook), Book.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expectedBook.getName(), responseBody.getName());
    }
}