package dev.iyanuoluwa.triviaquestions.controller;

import dev.iyanuoluwa.triviaquestions.dto.APIResponse;
import dev.iyanuoluwa.triviaquestions.dto.ScoreQuestionData;
import dev.iyanuoluwa.triviaquestions.service.TriviaQuestionAPIService;
import dev.iyanuoluwa.triviaquestions.service.TriviaQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trivia")
@RequiredArgsConstructor
public class TriviaQuestionController {

    private final TriviaQuestionService triviaQuestionService;
    private final TriviaQuestionAPIService triviaQuestionAPIService;


    @GetMapping("/getToken")
    public String getToken(){
        return triviaQuestionAPIService.getSessionToken();
    }
    @GetMapping("/allCategories")
    public ResponseEntity<APIResponse> getCategories(){
        return triviaQuestionService.getAllCategories();
    }


    @GetMapping("/breakdownQuiz")
    public ResponseEntity<APIResponse> breakdownOfQuestions(){
        return triviaQuestionService.questionsBreakDown();
    }

    @GetMapping("/breakdownDifficulty")
    public ResponseEntity<APIResponse> breakDownDifficulty(){
        return triviaQuestionService.breakDownDifficulty();
    }


    @GetMapping("/attemptQuiz")
    public ResponseEntity<APIResponse> createQuiz(@RequestParam(name = "category") List<String> category,
                                                  @RequestParam(name = "difficulty") String difficulty,
                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "20") int size) throws Exception{
        return triviaQuestionService.createQuiz(category,difficulty,page,size);
    }

    @PostMapping("/markQuiz")
    public ResponseEntity<APIResponse> markQuiz(@RequestBody List<ScoreQuestionData> quizAttempted){
        return triviaQuestionService.markQuiz(quizAttempted);
    }

}
