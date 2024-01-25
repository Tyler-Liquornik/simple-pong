// Advanced Software Engineering, Module 4
// Main for pong game
// Mr. Naccarato
// ICS4U
// Tyler Liquornik & Kirill Dairy
// December 17, 2021

package com.company;

// Imports
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main
{
    public static void main(String[] args)
    {
        // Make the menu window
        JFrame menu = new JFrame("Pong Menu");
        menu.setSize(250, 125);

        // Layout Manager objects
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        // Make the menu panel with a layout manager
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(layout);

        // Single Player button
        JButton singlePlayer = new JButton("1 Player");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        menuPanel.add(singlePlayer, constraints);

        // Multiplayer button
        JButton multiplayer = new JButton("2 Player");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        menuPanel.add(multiplayer, constraints);

        // Quit button
        JButton quit = new JButton("Quit");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(0, 0, 0, 0);
        menuPanel.add(quit, constraints);

        // Add the panel to the window and make the window visible
        menu.add(menuPanel);
        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Variable to keep track of the player launching either 1 player or 2 player
        AtomicBoolean launch1p = new AtomicBoolean(false);
        AtomicBoolean launch2p = new AtomicBoolean(false);

        // Action listener for single player
        singlePlayer.addActionListener
            (Click -> launch1p.set(true));

        // Action listener for multiplayer
        multiplayer.addActionListener
            (Click -> launch2p.set(true));

        // Action listener to quit the game
        quit.addActionListener(Click -> menu.dispose());

        // Check if the player has launched the game
        while(true)
        {
            if (launch1p.get() || launch2p.get())
            {
                // Close the menu window
                menu.dispose();

                // Make the game object
                Game game = new Game();

                // Make the game window, and add the game to the game window
                JFrame frame = new JFrame("Pong");
                frame.setSize(game.windowX, game.windowY);
                frame.add(game);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Launch the game
                while(true)
                {
                    // Launch the game for either 1 player or 2 players
                    if (launch1p.get())
                        game.update(true);
                    if (launch2p.get())
                        game.update(false);

                    // Refresh rate
                    try
                    {
                        Thread.sleep(17);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
