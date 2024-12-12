package org.snakeinc.snake.model;

import lombok.Getter;
import org.snakeinc.snake.GamePanel;
import java.awt.Graphics;
import java.util.Objects;

@Getter
public class Tile {

    private int x;
    private int y;

    private int upperPixelX;
    private int upperPixelY;

    public Tile(int x, int y) {
        setX(x);
        setY(y);
    }

    public Tile copy() {
        return new Tile(this.x, this.y);
    }

    public void setX(int X) {
        this.x = X;
        upperPixelX = X * GamePanel.TILE_SIZE;
    }

    public void setY(int Y) {
        this.y = Y;
        upperPixelY = Y * GamePanel.TILE_SIZE;
    }

    public void drawRectangle(Graphics g) {
        g.fillRect(upperPixelX, upperPixelY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public void drawOval(Graphics g) {
        g.fillOval(upperPixelX, upperPixelY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public boolean isInsideGame() {
        int leftLimit = 0;
        int rightLimit = GamePanel.GAME_WIDTH / GamePanel.TILE_SIZE;
        int topLimit = (GamePanel.BORDER_THICKNESS + GamePanel.SCORE_PLACE)/GamePanel.TILE_SIZE;
        int bottomLimit = GamePanel.GAME_HEIGHT / GamePanel.TILE_SIZE;

        return (x < leftLimit || x >= rightLimit) || (y < topLimit || y >= bottomLimit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
