package org.snakeinc.snake.model;

import java.awt.Color;
import java.awt.Graphics;

public class Broccoli extends EatableItem {

    // Idem que Apple
    public Broccoli() {
    }

    @Override
    public void draw(Graphics g) {
        if (position != null) {
            g.setColor(Color.GREEN);
            position.drawOval(g);
        }
    }
}
