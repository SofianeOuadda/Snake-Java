package org.snakeInc.server.controller;

import org.snakeInc.server.model.Score;
import org.snakeInc.server.repository.ScoreStats;
import org.snakeInc.server.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ScoreStats>> getScoreStats() {
        List<ScoreStats> stats = scoreService.getScoreStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping
    public ResponseEntity<List<Score>> getScores(
            @RequestParam(value = "snake", required = false) String snake) {
        List<Score> scores = scoreService.getScores(snake);
        return ResponseEntity.ok(scores);
    }

    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody Score score) {
        try {
            Score savedScore = scoreService.saveScore(score);
            return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}