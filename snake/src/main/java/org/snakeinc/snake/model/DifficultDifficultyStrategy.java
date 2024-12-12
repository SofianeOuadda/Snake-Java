package org.snakeinc.snake.model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultDifficultyStrategy implements DifficultyStrategy {
    private Timer relocationTimer;

    @Override
    public void updateLocation(EatableItem item, Snake snake) {
        if (item.getPosition() == null) {
            new RandomDifficultyStrategy().updateLocation(item, snake);
        }

        if (relocationTimer == null) {
            relocationTimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new RandomDifficultyStrategy().updateLocation(item, snake);
                }
            });
            relocationTimer.start();
        }
    }
}
