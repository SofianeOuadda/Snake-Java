package org.snakeinc.snake;

import org.snakeinc.snake.model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    public static final int TILE_SIZE = 20;
    public static final int N_TILES_X = 25;
    public static final int N_TILES_Y = 25;
    public static final int GAME_WIDTH = TILE_SIZE * N_TILES_X;
    public static final int GAME_HEIGHT = TILE_SIZE * N_TILES_Y;
    public static final int BORDER_THICKNESS = TILE_SIZE;
    public static final int SCORE_PLACE = 2 * TILE_SIZE;
    private Timer timer;
    private Snake snake;
    private EatableItem fruit;
    private boolean running = false;
    private boolean gameOver = false;
    private Direction direction = Direction.RIGHT;
    private int score = 0;


    private String currentSnakeName;

    public GamePanel() {
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        startGame();
    }

    private void startGame() {
        snake = Snake.createRandomSnake();

        switch (snake) {
            case Anaconda anaconda -> currentSnakeName = "anaconda";
            case Python python -> currentSnakeName = "python";
            case BoaConstrictor boaConstrictor -> currentSnakeName = "boaConstrictor";
            default -> currentSnakeName = "python";
        }

        fruit = createRandomFruit();
        timer = new Timer(100, this);
        timer.start();
        running = true;
        gameOver = false;
        score = 0;
        direction = Direction.RIGHT;
    }

    private DifficultyStrategy getRandomStrategy() {
        Random r = new Random();
        int choice = r.nextInt(3);
        return switch (choice) {
            case 0 -> new RandomDifficultyStrategy();
            case 1 -> new EasyDifficultyStrategy();
            case 2 -> new DifficultDifficultyStrategy();
            default -> new RandomDifficultyStrategy(); // Par défaut
        };
    }

    private EatableItem createRandomFruit() {
        Random random = new Random();
        EatableItem newFruit = random.nextBoolean() ? new Apple() : new Broccoli();
        newFruit.setStrategy(getRandomStrategy());
        newFruit.updateLocation(snake);
        return newFruit;
    }

    private void checkCollision() {
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            gameOver = true;
            timer.stop();

            sendScoreToServer();
        }
        if (snake.getHead().equals(fruit.getPosition())) {
            snake.eat(fruit);
            fruit = createRandomFruit();
            score++;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBorders(g);
        drawScore(g);
        if (running) {
            fruit.draw(g);
            snake.draw(g);
        } else if (gameOver) {
            gameOver(g);
        }
    }

    private void drawBorders(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, SCORE_PLACE, GAME_WIDTH, BORDER_THICKNESS);
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        String scoreText = "Score: " + score;
        g.drawString(scoreText, (GAME_WIDTH + 2 * BORDER_THICKNESS - metrics.stringWidth(scoreText)) / 2, 20);
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (GAME_WIDTH - metrics.stringWidth("Game Over")) / 2 + BORDER_THICKNESS, GAME_HEIGHT / 2 + 30);
        g.drawString("Press any key to replay", (GAME_WIDTH - metrics.stringWidth("Press any key to replay")) / 2 + BORDER_THICKNESS, GAME_HEIGHT / 2 + 60);
        g.drawString("Final Score: " + score, (GAME_WIDTH - metrics.stringWidth("Final Score: " + score)) / 2 + BORDER_THICKNESS, GAME_HEIGHT / 2 + 90);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move(direction);
            checkCollision();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            startGame();
            repaint();
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != Direction.RIGHT) direction = Direction.LEFT;
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != Direction.LEFT) direction = Direction.RIGHT;
                }
                case KeyEvent.VK_UP -> {
                    if (direction != Direction.DOWN) direction = Direction.UP;
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != Direction.UP) direction = Direction.DOWN;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}


    private void sendScoreToServer() {
        try {
            URL url = new URL("http://localhost:8080/api/v1/scores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{ \"snake\": \"" + currentSnakeName + "\", \"score\": " + score + " }";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            System.out.println("POST /api/v1/scores returned status: " + status);

            conn.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
