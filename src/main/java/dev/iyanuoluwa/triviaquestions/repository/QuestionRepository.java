package dev.iyanuoluwa.triviaquestions.repository;

import dev.iyanuoluwa.triviaquestions.entity.TriviaQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface QuestionRepository extends JpaRepository<TriviaQuestion, Integer>, MYSQLQueries {

    @Query("select distinct t.category from TriviaQuestion t")
    List<String> findAllCategories();

    @Query(value = MYSQLQueries.QUESTIONS_BY_CATEGORY, nativeQuery = true)
    List<Map<String, Integer>> findBreakdownOfQuestions();

    @Query("select t from TriviaQuestion t where t.category IN :category order by rand()")
    Page<TriviaQuestion> findByCategoryIn(@Param("category") List<String> category, Pageable pageable);

    @Query("select t from TriviaQuestion t where t.difficulty =:difficulty order by rand()")
    Page<TriviaQuestion> findAllByDifficulty(@Param("difficulty") String difficulty, Pageable pageable);

    @Query("select t from TriviaQuestion t where t.category IN :category AND t.difficulty =:difficulty order by rand()")
    Page<TriviaQuestion> findByCategoryInAndDifficulty(List<String> category, String difficulty, Pageable pageable);

    @Query("select t from TriviaQuestion t")
    Page<TriviaQuestion> findAllQuestions(Pageable pageable);

    @Query("select t.correctAnswer from TriviaQuestion t where t.id =:questionId")
    String findCorrectAnswerById(@Param("questionId") int  questionId);

    @Query(value = MYSQLQueries.DIFFICULTY_BY_CATEGORY, nativeQuery = true)
    List<Map<String, Object>> findBreakdownOfCategoryAndDifficulty();
}
