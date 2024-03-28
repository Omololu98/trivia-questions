package dev.iyanuoluwa.triviaquestions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TriviaQuestionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriviaQuestionsApplication.class, args);

	}

}
