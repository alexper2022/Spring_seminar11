package rf.aleksper.homework.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rf.aleksper.homework.entity.Reader;
import rf.aleksper.homework.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReaderService {
    private final ReaderRepository readerRepository;


    public Reader addReader(String login, String password, String role, String name) {
        return readerRepository.save(new Reader(login, password, role, name));
    }

    public void deleteReader(long id) {
        if (readerRepository.findById(id) == null) {
            log.info("Не удалось найти книгу с id " + id);
            throw new NoSuchElementException("Не удалось найти книгу с id " + id);
        }

        readerRepository.deleteById(id);
    }

    public Reader findById(long id) {
        System.out.println(id);
        return readerRepository.findById(id).orElse(null);
    }

    public List<Reader> allReaders() {
        return readerRepository.findAll();
    }
}
