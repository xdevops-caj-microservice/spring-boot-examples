package cn.xdevops.exception;

public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException(Long id) {
        super("Publisher not found via id: " + id);
    }
}
