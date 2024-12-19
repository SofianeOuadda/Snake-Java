package org.snakeinc.snake.model;

import lombok.Getter;
import org.snakeinc.snake.Direction;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public abstract class Snake {

    @Getter
    protected final LinkedList<Tile> body;
    protected Color color;
    protected int growthRate;

    public Snake(Color color, int growthRate) {
        this.body = new LinkedList<>();
        this.color = color;
        this.growthRate = growthRate;
        this.body.add(new Tile(5, 5));
    }

    public Tile getHead() {
        return body.getFirst();
    }

    public void move(Direction direction) {
        Tile newHead = getHead().copy();

        switch (direction) {
            case UP -> newHead.setY(newHead.getY() - 1);
            case DOWN -> newHead.setY(newHead.getY() + 1);
            case LEFT -> newHead.setX(newHead.getX() - 1);
            case RIGHT -> newHead.setX(newHead.getX() + 1);
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        for (Tile t : body) {
            t.drawRectangle(g);
        }
    }

    public boolean checkSelfCollision() {
        for (int i = 1; i < body.size(); i++) {
            if (getHead().equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision() {
        return getHead().isInsideGame();
    }

    public abstract void eat(EatableItem item);

    public static Snake createRandomSnake() {
        Random random = new Random();
        int type = random.nextInt(3);
        return switch (type) {
            case 0 -> new Anaconda();
            case 1 -> new Python();
            case 2 -> new BoaConstrictor();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
