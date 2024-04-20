package rf.aleksper.homework.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.aleksper.homework.entity.Book;
import rf.aleksper.homework.services.BookService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @Autowired
    private MeterRegistry meterRegistry;

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) {
        meterRegistry.counter("Deleted Book Request Counter").increment();
        log.info("Поступил запрос на удаление книги: bookId={}", id);
        if (bookService.findById(id) != null) {
            try {
                bookService.deleteBook(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest issueRequest) {
        meterRegistry.counter("Added Book Request Counter").increment();
        log.info("Поступил запрос на добавление книги: bookName={}", issueRequest.getBookName());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(issueRequest.getBookName()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> infoBook(@PathVariable long id) {
        meterRegistry.counter("Get Book Request Counter").increment();
        log.info("Поступил запрос на информацию о книге: bookId={}", id);
        if (bookService.findById(id) != null) {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
