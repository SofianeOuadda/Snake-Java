package org.snakeInc.server;

import org.snakeInc.server.model.Score;
import org.snakeInc.server.repository.ScoreStats;
import org.snakeInc.server.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getScores(String snake) {
        if (snake == null || snake.isBlank()) {
            return scoreRepository.findAll();
        }
        return scoreRepository.findBySnake(snake);
    }

    public List<ScoreStats> getScoreStats() {
        return scoreRepository.findScoreStats();
    }

    public Score saveScore(Score score) {
        validateScore(score);
        return scoreRepository.save(score);
    }

    private void validateScore(Score score) {
        List<String> validSnakes = List.of("python", "anaconda", "boaConstrictor");
        if (!validSnakes.contains(score.getSnake())) {
            throw new IllegalArgumentException("Invalid snake name: " + score.getSnake());
        }
        if (score.getScore() < 0) {
            throw new IllegalArgumentException("Score cannot be negative.");
        }
    }
}