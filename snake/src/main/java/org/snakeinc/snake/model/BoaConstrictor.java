package org.snakeinc.snake.model;

import java.awt.*;

public class BoaConstrictor extends Snake {
    public BoaConstrictor() {
        super(Color.BLUE, 3);
    }

    @Override
    public void eat(EatableItem item) {
        if (item instanceof Apple) {
            for (int i = 0; i < growthRate; i++) {
                body.addLast(new Tile(item.getPosition().getX(), item.getPosition().getY()));
            }
        }
    }
}
