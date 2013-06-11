package main;

public class InputReaderException extends Exception {

    public InputReaderException(String message) {
        super(message);
        
    }

    public InputReaderException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
