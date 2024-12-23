package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;

public class Apple extends EatableItem {

    public Apple() {
    }

    @Override
    public void draw(Graphics g) {
        if (position != null) {
            g.setColor(Color.RED);
            position.drawOval(g);
        }
    }
}
