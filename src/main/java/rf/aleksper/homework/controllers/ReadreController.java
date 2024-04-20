package rf.aleksper.homework.controllers;
/*
 * 1.2 Реализовать контроллер по управлению читателями (аналогично контроллеру с книгами из 1.1)
 * Реализовать контроллер по управлению читателями с ручками:
 * GET    /reader/{id} - получить описание читателя,
 * DELETE /reader/{id} - удалить читателя,
 * POST   /reader - создать читателя
 */

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.aleksper.homework.entity.Issue;
import rf.aleksper.homework.entity.Reader;
import rf.aleksper.homework.services.IssueService;
import rf.aleksper.homework.services.ReaderService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("reader")
@RequiredArgsConstructor
public class ReadreController {

    private final ReaderService readerService;
    private final IssueService issueService;
    @Autowired
    private MeterRegistry meterRegistry;

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable long id) {
        meterRegistry.counter("Deleted Reader Request Counter").increment();
        log.info("Поступил запрос на удаление читателя: readerId={}", id);
        try {
            readerService.deleteReader(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody ReaderRequest readerRequest) {
        meterRegistry.counter("Added Reader Request Counter").increment();
        log.info("Поступил запрос на добавление читателя: readerName={}", readerRequest.getReaderName());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(readerService.addReader(readerRequest.getLogin(), readerRequest.getPassword(), readerRequest.getRole(), readerRequest.getReaderName()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Reader> infoReader(@PathVariable long id) {
        meterRegistry.counter("Get Reader Request Counter").increment();
        log.info("Поступил запрос на информацию о читателе: readerId={}", id);
        if (readerService.findById(id) != null) {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(readerService.findById(id));
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /*
     * 2.2 В сервис читателя добавить ручку
     * GET /reader/{id}/issue - вернуть список всех выдачей для данного читателя
     */

    @GetMapping("{id}/issue")
    public ResponseEntity<Iterable<Issue>> findBookIssueReader(@PathVariable long id) {
        meterRegistry.counter("Get Issue by ReaderId Request Counter").increment();
        log.info("Поступил запрос на информацию о читателе: readerId={}", id);
        if (readerService.findById(id) != null) {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(issueService.findIssueForReader(id));
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }


}
