package rf.aleksper.homework.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.aleksper.homework.entity.Issue;
import rf.aleksper.homework.services.IssueService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    @Autowired
    private MeterRegistry meterRegistry;

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest issueRequest) {
        meterRegistry.counter("Added Issue Request Counter").increment();
        log.info("Поступил запрос на выдачу: readerId={}, bookId={}"
                , issueRequest.getReaderId(), issueRequest.getBookId());
        if (issueService.isReaderIssueBook(issueRequest.getReaderId()) == null) {
            return ResponseEntity.status(409).build();
        } else {
            try {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(issueService.createIssue(issueRequest.getReaderId(), issueRequest.getBookId()));
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Issue> findBookInIssues(@PathVariable long id) {
        meterRegistry.counter("Get Issue Request Counter").increment();

        log.info("Поступил запрос на проверку выдачи книги: bookId={}", id);
        if (issueService.findBookInIssue(id) != null) {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(issueService.findBookInIssue(id));
            } catch (NoSuchElementException e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
