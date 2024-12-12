package org.snakeinc.snake.model;

public interface DifficultyStrategy {
    void updateLocation(EatableItem item, Snake snake);
}
