package org.snakeinc.snake.model;

import org.snakeinc.snake.GamePanel;
import java.util.Random;

public class RandomDifficultyStrategy implements DifficultyStrategy {
    private final Random random = new Random();

    @Override
    public void updateLocation(EatableItem item, Snake snake) {
        int maxAttempts = GamePanel.N_TILES_X * GamePanel.N_TILES_Y;
        int attempts = 0;

        do {
            int x = random.nextInt(GamePanel.N_TILES_X);
            int y = random.nextInt((GamePanel.BORDER_THICKNESS + GamePanel.SCORE_PLACE) / GamePanel.TILE_SIZE, GamePanel.N_TILES_Y);
            item.setPosition(new Tile(x, y));
            attempts++;
        } while (snake.getBody().contains(item.getPosition()) && attempts < maxAttempts);
    }
}
