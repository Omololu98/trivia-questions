package dev.iyanuoluwa.triviaquestions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {

    private HttpStatus status;

    private Object data;

    private String message;

    public APIResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
