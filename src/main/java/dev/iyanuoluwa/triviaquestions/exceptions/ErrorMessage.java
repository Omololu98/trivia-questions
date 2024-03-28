package dev.iyanuoluwa.triviaquestions.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;

}
