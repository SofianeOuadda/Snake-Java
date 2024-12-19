package org.snakeInc.server.repository;

import org.snakeInc.server.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findBySnake(String snake);

    @Query("SELECT s.snake as snake, MIN(s.score) as min, MAX(s.score) as max, AVG(s.score) as average " +
            "FROM Score s GROUP BY s.snake")
    List<ScoreStats> findScoreStats();

}