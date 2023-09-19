package io.venly.words.exception;

public class RelationAlreadyExistsException extends RuntimeException {
    public RelationAlreadyExistsException(String wordOne, String wordTwo) {
        super(String.format("Relation already exists between: '%s' and '%s'", wordOne, wordTwo));
    }
}
