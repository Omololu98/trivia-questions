package dev.iyanuoluwa.triviaquestions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TriviaQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String difficulty;
    private String category;
    private String question;
    private String correctAnswer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="correct_answer_id", referencedColumnName = "id")
    private List<WrongAnswers> incorrectAnswer;
}
