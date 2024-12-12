package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.Direction;
import org.snakeinc.snake.model.Apple;
import org.snakeinc.snake.model.EatableItem;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Tile;
import org.snakeinc.snake.GamePanel;

import java.awt.*;

public class SnakeTest {
    private static class TestSnake extends Snake {
        public TestSnake() {
            super(Color.GREEN, 1);
            body.add(new Tile(5, 5));
        }

        @Override
        public void eat(EatableItem item) {
            Tile tail = body.getLast();
            body.add(new Tile(tail.getX(), tail.getY()));
        }
    }


    @Test
    public void snakeMovesUp_ReturnCorrectHead() {
        Snake snake = new TestSnake();
        snake.move(Direction.UP);
        Assertions.assertEquals(5, snake.getHead().getX());
        Assertions.assertEquals(4, snake.getHead().getY());
    }

    @Test
    public void appleDoesNotSpawnOnSnake() {
        Snake snake = new TestSnake();
        Apple apple = new Apple();
        apple.updateLocation(snake);
        Assertions.assertFalse(snake.getBody().contains(apple.getPosition()));
    }

    @Test
    public void snakeHitsWall_GameStops() {
        Snake snake = new TestSnake();
        for (int i = 0; i < GamePanel.N_TILES_Y; i++) {
            snake.move(Direction.UP);
        }
        Assertions.assertTrue(snake.checkWallCollision());
    }
}
