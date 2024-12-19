package org.snakeinc.snake.model;

import java.awt.*;

public class Python extends Snake {
    public Python() {
        super(Color.GREEN, 1);
    }

    @Override
    public void eat(EatableItem item) {
        if (item instanceof Apple) {
            body.addLast(new Tile(item.getPosition().getX(), item.getPosition().getY()));
        } else if (item instanceof Broccoli) {
            for (int i = 0; i < 2 && body.size() > 1; i++) {
                body.removeLast();
            }
        }
    }
}
