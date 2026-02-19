package poo.gamed.exception;

public class WorkNotBorrowedByUserException extends RuntimeException {
    public WorkNotBorrowedByUserException(String message) {
        super(message);
    }
}
