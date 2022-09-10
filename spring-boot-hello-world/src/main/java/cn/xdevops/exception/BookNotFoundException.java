package cn.xdevops.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Boot not found via id: " + id);
    }
}
