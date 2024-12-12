package org.snakeinc.snake.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.Graphics;
import java.util.Random;

public abstract class EatableItem {
    @Setter
    @Getter
    protected Tile position;
    protected final Random random = new Random();

    @Setter
    @Getter
    private DifficultyStrategy strategy;

    public void updateLocation(Snake snake) {
        if (strategy != null) {
            strategy.updateLocation(this, snake);
        }
    }

    public abstract void draw(Graphics g);
}
