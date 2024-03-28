package dev.iyanuoluwa.triviaquestions.exceptions;

public class DifficultyNotFoundException extends Exception{
    public DifficultyNotFoundException() {
        super();
    }

    public DifficultyNotFoundException(String message) {
        super(message);
    }

    protected DifficultyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
