package dev.iyanuoluwa.triviaquestions.service;

import dev.iyanuoluwa.triviaquestions.dto.APIResponse;
import dev.iyanuoluwa.triviaquestions.dto.QuizResponse;
import dev.iyanuoluwa.triviaquestions.dto.ScoreQuestionData;
import dev.iyanuoluwa.triviaquestions.entity.TriviaQuestion;
import dev.iyanuoluwa.triviaquestions.entity.WrongAnswers;
import dev.iyanuoluwa.triviaquestions.exceptions.CategoryNotFoundException;
import dev.iyanuoluwa.triviaquestions.exceptions.DifficultyNotFoundException;
import dev.iyanuoluwa.triviaquestions.exceptions.QuestionNotFoundException;
import dev.iyanuoluwa.triviaquestions.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TriviaQuestionServiceImpl implements TriviaQuestionService{

    private final QuestionRepository questionRepository;


    @Override
    public ResponseEntity<APIResponse> getAllCategories() {
        List<String> categoryList = questionRepository.findAllCategories();
        return ResponseEntity.ok(new APIResponse(HttpStatus.FOUND,categoryList,STR."There are \{categoryList.size()} categories in the Quiz available"));
    }

    @Override
    public ResponseEntity<APIResponse> markQuiz(List<ScoreQuestionData> quizAttempted) {
        List<Integer> scoreList = new ArrayList<>();
        for(ScoreQuestionData questionData : quizAttempted){
            String answer = questionRepository.findCorrectAnswerById(questionData.getQuestionId());
            questionData.setCorrectAnswer(answer);
            if(answer.equalsIgnoreCase(questionData.getSelectedAnswer())){
                questionData.setScore(1);
            }
            else {
                questionData.setScore(0);
            }
            scoreList.add(questionData.getScore());
        }
        int overallScore =0;
        for(int score : scoreList){
            overallScore = overallScore+score;
        }
        return ResponseEntity.ok(new APIResponse(HttpStatus.OK,quizAttempted,STR."You got an overall score of \{overallScore}"));
    }

    @Override
    public ResponseEntity<APIResponse> breakDownDifficulty() {
        List<Map<String, Object>> response = questionRepository.findBreakdownOfCategoryAndDifficulty();
        List<TreeMap<String,Object>> sortedResponse = new ArrayList<>();
        for(Map<String,Object> map : response){
            TreeMap<String,Object> sort = new TreeMap<>(map);
            sortedResponse.add(sort);
        }
        return ResponseEntity.ok(new APIResponse(HttpStatus.FOUND, sortedResponse,"List of Difficulty by Categories"));
    }

    @Override
    public ResponseEntity<APIResponse> questionsBreakDown() {
        List<Map<String, Integer>> response = questionRepository.findBreakdownOfQuestions();
        return ResponseEntity.ok(new APIResponse(HttpStatus.FOUND,response,"All Categories and Number of Questions per Category"));
    }

    @Override
    public ResponseEntity<APIResponse> createQuiz(List<String> category,String difficulty, int page, int size) throws Exception{
        List<String> difficultyList = List.of("easy", "medium", "hard");
        if(!difficultyList.contains(difficulty) & !difficulty.isEmpty()){
            throw new DifficultyNotFoundException("Difficulty Provided not found");
        }

        List<String> listOfCategories = questionRepository.findAllCategories();
        if(!category.isEmpty() & !isCategoryValid(category,listOfCategories)){
            throw new CategoryNotFoundException("There is an issue with one or more of the categories provided, Category not present");
        }

        Pageable pageable = PageRequest.of(page,size);
        Page<TriviaQuestion> listOfQuestions;
        if(category.isEmpty() & difficulty.isEmpty()){
//            log.info("C1");
            listOfQuestions = questionRepository.findAllQuestions(pageable);
        } else if (!category.isEmpty() &  difficulty.isEmpty()) {
//            log.info("C2");
            listOfQuestions= questionRepository.findByCategoryIn(category,pageable);
        }else if (category.isEmpty()) {
//            log.info("C3");
            listOfQuestions = questionRepository.findAllByDifficulty(difficulty, pageable);
        }else {
//            log.info("C4");
            listOfQuestions = questionRepository.findByCategoryInAndDifficulty(category,difficulty,pageable);
        }
        Set<QuizResponse> setOfQuestions = questionSetMapper(listOfQuestions);
        if(setOfQuestions.isEmpty()){
            throw new QuestionNotFoundException("No Question matches the filter provided");
        }

        return ResponseEntity.ok(new APIResponse(HttpStatus.FOUND,setOfQuestions,"ENJOY YOUR QUIZ"));
    }


    private Set<QuizResponse> questionSetMapper (Page<TriviaQuestion> listOfQuestionFromDb){
        Set<QuizResponse> quizResponses = new HashSet<>();
        for(TriviaQuestion question : listOfQuestionFromDb){
            QuizResponse quizResponse = new QuizResponse();
            quizResponse.setQuestionId(question.getId());
            quizResponse.setQuestion(question.getQuestion());
            quizResponse.setCategory(question.getCategory());
            Set<String> options = question.getIncorrectAnswer().stream().map(WrongAnswers::getWrongAnswers).collect(Collectors.toSet());
            options.add(question.getCorrectAnswer());
            quizResponse.setOptions(options);
            quizResponses.add(quizResponse);
        }
        return quizResponses;
    }

    private boolean isCategoryValid(List<String> categoryFromUser, List<String> categoryFromDb){
        boolean allCategoryIsPresent = true;
        for (String option : categoryFromUser){
            if(!categoryFromDb.contains(option)){
                allCategoryIsPresent = false;
                break;
            }
        }
        return allCategoryIsPresent;
    }
}

