package dev.iyanuoluwa.triviaquestions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.iyanuoluwa.triviaquestions.dto.OpenTriviaResponse;
import dev.iyanuoluwa.triviaquestions.dto.TokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TriviaQuestionAPIService {

    private final String TRIVIA_QUESTION_API_URL = "https://opentdb.com/api.php";
    private final RestTemplate restTemplate;


    public OpenTriviaResponse getQuestionsByNumber(int number){
        ParameterizedTypeReference<OpenTriviaResponse> response = new ParameterizedTypeReference<OpenTriviaResponse>(){};
        String url = TRIVIA_QUESTION_API_URL +"?amount="+number;
        ResponseEntity<OpenTriviaResponse> trivialResponse= restTemplate.exchange(url, HttpMethod.GET,null,response);
        return trivialResponse.getBody();
    }

    public String getSessionToken(){
        String url = "https://opentdb.com/api_token.php?command=request";
        ResponseEntity<TokenRequest> sessionTokenResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TokenRequest>() {}
        );
        log.info("SessionToken {}",sessionTokenResponse);
        if (sessionTokenResponse.getStatusCode() == HttpStatus.OK) {
           TokenRequest responseBody = sessionTokenResponse.getBody();
            if (responseBody != null) {
                return responseBody.token();
            }
        }
        return "Something went Wrong!!";
    }
}
