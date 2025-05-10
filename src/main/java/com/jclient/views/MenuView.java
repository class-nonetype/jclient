package com.jclient.views;

import javax.swing.*;
import java.awt.*;


public class MenuView {

    // Propiedades de la ventana
    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public MenuView(){
        JFrame frame = new JFrame("Menú");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#121212"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //

        //

        frame.setVisible(true);
    }
}
