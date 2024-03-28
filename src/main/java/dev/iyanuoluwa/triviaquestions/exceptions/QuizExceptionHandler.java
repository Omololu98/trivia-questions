package dev.iyanuoluwa.triviaquestions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class QuizExceptionHandler {
    @ExceptionHandler(DifficultyNotFoundException.class)
    public ResponseEntity<ErrorMessage> difficultyNotFoundExceptionHandler(DifficultyNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),
                status,
                ZonedDateTime.now());
        return new ResponseEntity<>(errorMessage,status);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> categoryNotFoundExceptionHandler(CategoryNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),status,ZonedDateTime.now());
        return new ResponseEntity<>(errorMessage,status);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorMessage> questionNotFoundExceptionHandler(QuestionNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage message = new ErrorMessage(exception.getMessage(),status,ZonedDateTime.now());
        return new ResponseEntity<>(message,status);
    }

}
