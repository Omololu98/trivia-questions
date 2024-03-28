package dev.iyanuoluwa.triviaquestions.exceptions;

public class QuestionNotFoundException extends Exception{
    public QuestionNotFoundException() {
        super();
    }

    public QuestionNotFoundException(String message) {
        super(message);
    }

    protected QuestionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
