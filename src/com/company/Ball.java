// Advanced Software Engineering, Module 4
// Ball class for pong game
// Mr. Naccarato
// ICS4U
// Tyler Liquornik & Kirill Dairy
// December 17, 2021

package com.company;

// Imports
import java.awt.*;

public class Ball
{
    // Class variables
    int x;
    int y;
    int diameter;

    int speed;
    boolean isDown;
    boolean isRight;

    // Set variables
    public Ball(int x, int y, int speed, boolean isDown, boolean isRight)
    {
        this.x = x;
        this.y = y;
        this.diameter = 15;
        this.speed = speed;
        this.isDown = isDown;
        this.isRight = isRight;
    }

    // Paint the ball
    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillOval(this.x, this.y, this.diameter, this.diameter);
    }
}
