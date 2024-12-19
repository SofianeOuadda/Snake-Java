package org.snakeinc.snake.model;

import java.awt.*;

public class Anaconda extends Snake {
    public Anaconda() {
        super(Color.GRAY, 2);
    }

    @Override
    public void eat(EatableItem item) {
        if (item instanceof Apple) {
            for (int i = 0; i < growthRate; i++) {
                body.addLast(new Tile(item.getPosition().getX(), item.getPosition().getY()));
            }
        } else if (item instanceof Broccoli && body.size() > 1) {
            body.removeLast();
        }
    }
}
