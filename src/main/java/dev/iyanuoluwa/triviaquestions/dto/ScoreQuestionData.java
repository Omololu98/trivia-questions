package dev.iyanuoluwa.triviaquestions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreQuestionData {
    private int questionId;
    private String question;
    private String correctAnswer;
    private String selectedAnswer;
    private int score;
}
