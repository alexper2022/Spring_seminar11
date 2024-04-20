package rf.aleksper.homework.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rf.aleksper.homework.entity.Book;
import rf.aleksper.homework.repository.BookRepository;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

//    @BeforeEach
//    public void setUp() {
//        bookRepository.deleteAll();
//    }

    @Test
    void allBooks() {
        List<Book> expectedBookList = List.of(
                new Book("Book1"),
                new Book("Book2"),
                new Book("Book3")
        );
        bookRepository.saveAll(expectedBookList);

        List<Book> actualBooks = bookService.allBooks();

        Assertions.assertEquals(expectedBookList.size(), actualBooks.size());
        for (Book book : actualBooks) {
            boolean found = expectedBookList.stream()
                    .filter(x -> Objects.equals(book.getId(), x.getId()))
                    .allMatch(x -> Objects.equals(book.getName(), x.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void findById() {
        Book expectedBook = bookRepository.save(new Book("Book4"));

        Book actualBook = bookService.findById(expectedBook.getId());

        Assertions.assertNotNull(actualBook);
        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());

    }

    @Test
    void deleteBook() {
        Book expectedBook = bookRepository.save(new Book("Book5"));
        long deletedBookId = expectedBook.getId();
        Assertions.assertNotNull(bookService.findById(deletedBookId));
        bookService.deleteBook(deletedBookId);
        Assertions.assertNull(bookService.findById(deletedBookId));
    }
}