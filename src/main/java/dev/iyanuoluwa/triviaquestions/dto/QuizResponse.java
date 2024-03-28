package dev.iyanuoluwa.triviaquestions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse{
    private int questionId;
    private String category;
    private String question;
    private String difficulty;
    private Set<String> options;
    private String answer;
}
