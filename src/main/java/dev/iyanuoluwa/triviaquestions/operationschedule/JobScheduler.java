package dev.iyanuoluwa.triviaquestions.operationschedule;


import dev.iyanuoluwa.triviaquestions.dto.OpenTriviaResponse;
import dev.iyanuoluwa.triviaquestions.dto.QuestionData;
import dev.iyanuoluwa.triviaquestions.entity.TriviaQuestion;
import dev.iyanuoluwa.triviaquestions.entity.WrongAnswers;
import dev.iyanuoluwa.triviaquestions.repository.QuestionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobScheduler {

    private final QuestionRepository questionRepository;
    private final RestTemplate restTemplate;
    @Getter
    private  List<String> listOfCategories = new ArrayList<>();


    //TODO @Scheduled(fixedDelay = 30000) when the code is moving into production and change to Once every day
    public void loadDataFromOpenTriviaQuestions(){
        log.info("loadDataFromOpenTriviaQuestions-- ran at {}", LocalDateTime.now());
        int number = 50;
        String token = getSessionToken();
        assert token != null;
        OpenTriviaResponse apiResponse = getQuestionsByNumber(number,token);
        List<TriviaQuestion> triviaQuestionsToSave = new ArrayList<>();
        List<TriviaQuestion> existingQuestions = questionRepository.findAll();
        Set<String> existingQuestionSet = existingQuestions.stream().map(TriviaQuestion::getQuestion).collect(Collectors.toSet());

        for (QuestionData newQuestion : apiResponse.getResults()) {
            if (!existingQuestionSet.contains(newQuestion.getQuestion())) {
                TriviaQuestion triviaQuestion = new TriviaQuestion();
                triviaQuestion.setQuestion(newQuestion.getQuestion());
                triviaQuestion.setDifficulty(newQuestion.getDifficulty());
                triviaQuestion.setCategory(newQuestion.getCategory());
                triviaQuestion.setCorrectAnswer(newQuestion.getCorrectAnswer());

                // Mapping incorrect answers
                List<WrongAnswers> wrongAnswersList = new ArrayList<>();
                for (String incorrectAnswer : newQuestion.getIncorrectAnswer()) {
                    wrongAnswersList.add(new WrongAnswers(incorrectAnswer));
                }
                triviaQuestion.setIncorrectAnswer(wrongAnswersList);

                triviaQuestionsToSave.add(triviaQuestion);
            }
        }

        if (!triviaQuestionsToSave.isEmpty()) {
            questionRepository.saveAll(triviaQuestionsToSave);
        }

    }

    @Scheduled(fixedDelay = 3600000)
    public void loadListOfCategories(){
        this.listOfCategories = questionRepository.findAllCategories();
    }

    public String getSessionToken(){
        String url = "https://opentdb.com/api_token.php?command=request";
        ResponseEntity<Map<String, String>> sessionTokenResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, String>>() {}
        );
        if (sessionTokenResponse.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = sessionTokenResponse.getBody();
            if (responseBody != null && responseBody.containsKey("token")) {
                return responseBody.get("token");
            }
        }
        return "Something went Wrong!!";
    }




    private OpenTriviaResponse getQuestionsByNumber(int number,String token){
        ParameterizedTypeReference<OpenTriviaResponse> response = new ParameterizedTypeReference<OpenTriviaResponse>(){};
        String TRIVIA_QUESTION_API_URL = "https://opentdb.com/api.php";
        String url = STR."\{TRIVIA_QUESTION_API_URL}?amount=\{number}&token=\{token}";
        ResponseEntity<OpenTriviaResponse> trivialResponse= restTemplate.exchange(url, HttpMethod.GET,null,response);
        return trivialResponse.getBody();
    }


}
