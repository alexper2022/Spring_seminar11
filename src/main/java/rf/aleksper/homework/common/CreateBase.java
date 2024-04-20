//package rf.aleksper.homework;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import rf.aleksper.homework.services.BookService;
//import rf.aleksper.homework.services.IssueService;
//import rf.aleksper.homework.services.ReaderService;
//
//@Component
//@RequiredArgsConstructor
//public class CreateBase {
//    private final BookService bookServicey;
//    private final ReaderService readerService;
//    private final IssueService issueService;
//
//    @EventListener(ContextRefreshedEvent.class)
//    public void createdBbase() {
//        bookServicey.addBook("Война и мир");
//        bookServicey.addBook("Мастер и Маргарита");
//        bookServicey.addBook("Приключения Буратино");
//
//        readerService.addReader("docent", "user", "user", "Евгений Иванович Трошкин");
//        readerService.addReader("hmyr", "user", "user", "Гаврила Петрович Шереметьев");
//        readerService.addReader("kosoy", "user", "user", "Фёдор Петрович Ермаков");
//        readerService.addReader("alibaba", "alibaba", "admin", "Василий Алибабаевич Алибаба");
//
//        issueService.createIssue(0, 0);
//        issueService.createIssue(1, 1);
//        issueService.createIssue(2, 2);
//    }
//}
