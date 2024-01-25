// Advanced Software Engineering, Module 4
// Game components for Pong
// Mr. Naccarato
// ICS4U
// Tyler Liquornik & Kirill Dairy
// December 17, 2021

package com.company;

// Imports
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements KeyListener
{
    // Window Dimensions
    int windowX = 1000;
    int windowY = 750;

    // Paddle x distance from the wall
    int paddleDist = 50;

    // Random boolean method
    Random random = new Random();
    public boolean randomBool()
    {
        return random.nextBoolean();
    }

    // Paddle ball and objects
    Paddle leftPaddle = new Paddle(paddleDist, windowY / 2 - 75);
    Paddle rightPaddle = new Paddle(windowX - paddleDist - 25, windowY / 2 - 75);
    Ball ball = new Ball(windowX / 2 - 15, windowY / 2 - 65, 2, randomBool(), randomBool());

    // Scores
    int p1Score = 0;
    int p2Score = 0;

    // Reset round variable
    boolean endRound = false;

    // Reset method
    public void reset(int addScoreToPlayer)
    {
        // Reset ball and paddle positions, and ball speed
        leftPaddle.y = rightPaddle.y = windowY / 2 - 75;
        ball.x = windowX / 2 - 15;
        ball.y = windowY / 2 - 65;
        ball.speed = 2;
        ball.isDown = randomBool();
        ball.isRight = randomBool();

        // If player 1 won the round
        if (addScoreToPlayer == 1)
        {
            p1Score ++;

            // Display win window if player 1 won, resetting the score
            if (p1Score == 10)
            {
                JOptionPane.showMessageDialog(null, "Player 1 Victory!");
                p1Score = 0;
                p2Score = 0;
            }
        }

        // if player 2 won the round
        else
        {
            p2Score ++;

            // Display win window if player 2 won, resetting the score
            if (p2Score == 10)
            {
                JOptionPane.showMessageDialog(null, "Player 2 Victory!");
                p1Score = 0;
                p2Score = 0;
            }
        }

        // End the round
        endRound = true;
    }

    // Move the ball
    public void moveBall(int xI, int yI, boolean isDownI, boolean isRightI)
    {
        // Variables for x and y positions
        int xF;
        int yF;

        // Left wall collision check
        if (ball.x - ball.speed <= 0)
            reset(2);

        // Right wall collision check
        if (ball.x + ball.speed >= getWidth() - ball.diameter)
            reset(1);

        //Left paddle edges collision check
        if (ball.x <= leftPaddle.x + leftPaddle.width - ball.diameter / 2 && ball.x + ball.diameter >= leftPaddle.x
                && ((leftPaddle.y + leftPaddle.height >= ball.y
                && leftPaddle.y + leftPaddle.height <= ball.y + ball.diameter) ||
                (leftPaddle.y <= ball.y + ball.diameter && leftPaddle.y >= ball.y)))
            reset(2);

        //Right paddle edges collision check
        if (ball.x + ball.diameter >= rightPaddle.x + ball.diameter / 2 && ball.x <= rightPaddle.x + rightPaddle.width
                && ((rightPaddle.y + rightPaddle.height >= ball.y
                && rightPaddle.y + rightPaddle.height <= ball.y + ball.diameter) ||
                (rightPaddle.y <= ball.y + ball.diameter && rightPaddle.y >= ball.y)))
            reset(1);

        if (!endRound)
        {
            // Top and bottom border collision check
            if (ball.y + ball.speed >= getHeight() - ball.diameter || ball.y - ball.speed <= 0)
            {
                // Flip the vertical direction on collision
                ball.isDown = !ball.isDown;
                isDownI = !isDownI;
            }

            // Left paddle collision check (front)
            if (ball.x <= leftPaddle.x + leftPaddle.width
                    && ball.y + ball.diameter >= leftPaddle.y && leftPaddle.y + leftPaddle.height >= ball.y)
            {
                // Flip the horizontal direction on collision
                ball.isRight = !ball.isRight;
                isRightI = !isRightI;
                ball.speed ++;
            }

            // Right paddle collision check
            if (ball.x + ball.diameter >= rightPaddle.x
                    && ball.y + ball.diameter >= rightPaddle.y && rightPaddle.y + rightPaddle.height >= ball.y)
            {
                // Flip the horizontal direction on collision
                ball.isRight = !ball.isRight;
                isRightI = !isRightI;
                ball.speed ++;
            }

            // Calculate new x and y positions
            if (isDownI)
                yF = yI + ball.speed;
            else
                yF = yI - ball.speed;

            if (isRightI)
                xF = xI + ball.speed;
            else
                xF = xI - ball.speed;

            // Change x and y positions
            ball.x = xF;
            ball.y = yF;
        }

        endRound = false;
    }

    // Update the game by checking for pressed keys, and repainting the screen.
    public void update(boolean is1Player)
    {
        keyCheck(is1Player);
        computer(is1Player);
        moveBall(ball.x, ball.y, ball.isDown, ball.isRight);
        repaint();
    }

    // Clear the screen, then paint the background, paddles, and ball
    public void paint(Graphics g)
    {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("SansSerif", Font.BOLD, 250));
        g.setColor(Color.DARK_GRAY);
        g.drawString(String.valueOf(p1Score), windowX / 2 - 300, windowY / 3);
        g.drawString(String.valueOf(p2Score), windowX / 2 + 100, windowY / 3);
        leftPaddle.paint(g);
        rightPaddle.paint(g);
        ball.paint(g);
    }

    // Distance to move the paddle for each repaint
    int moveDist = windowY / 75;

    // Methods to move the paddle, checking if the paddle has reached the border
    public void moveLDown()
    {
        if (leftPaddle.y < getHeight() - leftPaddle.height)
            leftPaddle.y += moveDist;
    }

    public void moveLUp()
    {
        if (leftPaddle.y > 0)
            leftPaddle.y -= moveDist;
    }

    public void moveRDown()
    {
        if (rightPaddle.y < getHeight() - rightPaddle.height)
            rightPaddle.y += moveDist;
    }

    public void moveRUp()
    {
        if (rightPaddle.y > 0)
            rightPaddle.y -= moveDist;
    }

    // AI Movement
    public void computer(boolean is1Player)
    {
        if (is1Player)
        {
            if (ball.y + ball.diameter < rightPaddle.y)
                moveRUp();
            if (ball.y > rightPaddle.y + rightPaddle.height)
                moveRDown();
        }
    }

    // Variables to keep track of buttons pressed
    boolean lDown;
    boolean lUp;
    boolean rDown;
    boolean rUp;

    // Move if keys are pressed, including combinations of keys
    public void keyCheck(boolean is1player)
    {
        // Controls for 2 player
        if (!is1player)
        {
            if (lDown && rDown)
            {
                moveLDown();
                moveRDown();
            }

            else if (lDown && rUp)
            {
                moveLDown();
                moveRUp();
            }

            else if (lUp && rDown)
            {
                moveLUp();
                moveRDown();
            }

            else if (lUp && rUp)
            {
                moveLUp();
                moveRUp();
            }

            else if (rDown)
                moveRDown();
            else if (rUp)
                moveRUp();
            else if (lDown)
                moveLDown();
            else if (lUp)
                moveLUp();
        }

        // Controls for 1 player
        else
        {
            if (lDown)
                moveLDown();
            else if (lUp)
                moveLUp();
        }
    }

    // Check for pressed keys
    public void press(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_S)
            lDown = true;
        if (e.getKeyCode() == KeyEvent.VK_W)
            lUp = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            rDown = true;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            rUp = true;
    }

    // Add the key listener to JPanel
    public Game()
    {
        super();
        super.setFocusable(true);
        super.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    // Run the method to check for pressed keys when a key is pressed
    @Override
    public void keyPressed(KeyEvent e)
    {
        press(e);
    }

    // Reset variables that keep track of pressed keys if they are released
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_S)
            lDown = false;
        if (e.getKeyCode() == KeyEvent.VK_W)
            lUp = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            rDown = false;
        if (e.getKeyCode() == KeyEvent.VK_UP)
            rUp = false;
    }
}