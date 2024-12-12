package org.snakeinc.snake.model;

import java.util.Random;

public class EasyDifficultyStrategy implements DifficultyStrategy {
    private final Random random = new Random();

    @Override
    public void updateLocation(EatableItem item, Snake snake) {
        Tile head = snake.getHead();
        Tile newPosition;

        do {
            int offsetX = random.nextInt(3) - 1; // -1, 0, 1
            int offsetY = random.nextInt(3) - 1;
            newPosition = new Tile(head.getX() + offsetX, head.getY() + offsetY);
        } while (newPosition.isInsideGame() || snake.getBody().contains(newPosition));

        item.setPosition(newPosition);
    }
}
