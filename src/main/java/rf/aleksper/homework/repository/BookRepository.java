package rf.aleksper.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rf.aleksper.homework.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
