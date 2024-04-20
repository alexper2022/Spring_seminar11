package rf.aleksper.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rf.aleksper.homework.entity.Reader;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByLogin(String login);
}
