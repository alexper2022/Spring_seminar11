package rf.aleksper.homework.controllers;

import lombok.Data;

@Data
public class ReaderRequest {
    private final long readerId;
    private final String login;
    private final String password;
    private final String role;
    private final String readerName;
}
