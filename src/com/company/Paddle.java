// Advanced Software Engineering, Module 4
// Paddle class for pong game
// Mr. Naccarato
// ICS4U
// Tyler Liquornik & Kirill Dairy
// December 17, 2021

package com.company;

// Imports
import java.awt.*;

public class Paddle
{
    // Class variables
    int x;
    int y;
    int width;
    int height;

    // Set variables
    public Paddle(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = 25;
        this.height = 100;
    }

    // Paint the paddles
    public void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}
