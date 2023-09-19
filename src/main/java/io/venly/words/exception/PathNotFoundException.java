package io.venly.words.exception;

public class PathNotFoundException extends RuntimeException {
    public PathNotFoundException(String source, String target) {
        super(String.format("Path not found between: '%s' and '%s'", source, target));
    }
}
