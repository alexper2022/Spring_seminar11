package rf.aleksper.homework.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rf.aleksper.homework.entity.Book;
import rf.aleksper.homework.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void getAll() {
        List<Book> expectedBooks = List.of(
                new Book("Book1"),
                new Book("Book2"),
                new Book("Book3")
        );
        Mockito.when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.allBooks();

        Assertions.assertEquals(expectedBooks.size(), actualBooks.size());
    }

    @Test
    void getById() {
        Book expectedBook = new Book("Book4");
        Optional<Book> bookOptional = Optional.of(expectedBook);
        Mockito.when(bookRepository.findById(1L)).thenReturn(bookOptional);

        Book actualBook = bookService.findById(1);

        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());
    }

    @Test
    void getByIdThrowException() {

        Mockito.when(bookRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> bookService.findById(1));

    }


    @Test
    void removeById() {
        Book expectedBook = new Book("Book5");
        Optional<Book> bookOptional = Optional.of(expectedBook);
        Mockito.when(bookRepository.findById(1L)).thenReturn(bookOptional);
        bookService.deleteBook(1L);

        Assertions.assertNull(bookService.findById(expectedBook.getId()));

    }

    @Test
    void createBook() {
        Book expectedBook = new Book("Book6");
        Mockito.when(bookRepository.save(expectedBook)).thenReturn(expectedBook);

        Book actualBook = bookService.addBook("Book6");

        Assertions.assertEquals(expectedBook.getId(), actualBook.getId());
        Assertions.assertEquals(expectedBook.getName(), actualBook.getName());
    }
}