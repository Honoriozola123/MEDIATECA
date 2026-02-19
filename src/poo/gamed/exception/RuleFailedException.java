package poo.gamed.exception;

public class RuleFailedException extends RuntimeException {
    public RuleFailedException(String message) {
        super(message);
    }
}
