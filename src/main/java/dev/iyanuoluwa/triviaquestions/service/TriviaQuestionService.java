package dev.iyanuoluwa.triviaquestions.service;

import dev.iyanuoluwa.triviaquestions.dto.APIResponse;
import dev.iyanuoluwa.triviaquestions.dto.ScoreQuestionData;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TriviaQuestionService {

    ResponseEntity<APIResponse> getAllCategories();


    ResponseEntity<APIResponse> questionsBreakDown();


    ResponseEntity<APIResponse> createQuiz(List<String> category,String difficulty, int page, int size) throws Exception;

    ResponseEntity<APIResponse> markQuiz(List<ScoreQuestionData> quizAttempted);

    ResponseEntity<APIResponse> breakDownDifficulty();
}
