package dev.iyanuoluwa.triviaquestions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wrongAnswersId;
    private String wrongAnswers;

    public WrongAnswers(String wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }
}
