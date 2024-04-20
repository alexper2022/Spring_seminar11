package rf.aleksper.homework.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rf.aleksper.homework.aop.Timer;
import rf.aleksper.homework.entity.Book;
import rf.aleksper.homework.entity.Issue;
import rf.aleksper.homework.entity.Reader;
import rf.aleksper.homework.services.BookService;
import rf.aleksper.homework.services.IssueService;
import rf.aleksper.homework.services.ReaderService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "ui")
@Timer
public class UiController {

    @Autowired
    private MeterRegistry meterRegistry;
    private final IssueService issueService;
    private final ReaderService readerService;
    private final BookService bookService;

    @GetMapping("issues")
    public String issuesAll(Model model) {
        meterRegistry.counter("my_web_get_uiissues").increment();
        log.info("Поступил запрос /ui/issues/");
        List<Issue> issuesList = issueService.allIssues();
        model.addAttribute("issues", issuesList);
        return "issues";
    }

    @GetMapping("readers")
    public String readersAll(Model model) {
        meterRegistry.counter("my_web_get_uireaders").increment();
        log.info("Поступил запрос /ui/readers/");
        List<Reader> readersList = readerService.allReaders();
        model.addAttribute("readers", readersList);
        return "readers";
    }

    @GetMapping("books")
    public String booksAll(Model model) {
        meterRegistry.counter("my_web_get_uibooks").increment();
        log.info("Поступил запрос /ui/books/");
        List<Book> booksList = bookService.allBooks();
        model.addAttribute("books", booksList);
        return "books";
    }
}
