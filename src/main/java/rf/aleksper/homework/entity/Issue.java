package rf.aleksper.homework.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "issues")
@NoArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "readerId")
    private long readerId;
    @Column(name = "bookId")
    private long bookId;
    @Column(name = "issuedAt")
    private LocalDateTime issuedAt = LocalDateTime.now(); // дата выдачи
    @Column(name = "returnedAt")
    private LocalDateTime returnedAt = null; // дата возврата

    public Issue(long readerId, long bookId) {
        this.readerId = readerId;
        this.bookId = bookId;
    }
}
