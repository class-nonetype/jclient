package com.jclient.views;

import javax.swing.*;
import java.awt.*;


public class MenuView {

    // Propiedades de la ventana
    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    public MenuView(){
        JFrame frame = new JFrame("Formulario de Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#121212"));

        JButton button = new JButton("Guardar");
        button.setBounds(150, 70, 200, 30);

        // estilo plano, sin bordes ni efectos clásicos
        button.setFocusPainted(false);       // Elimina el recuadro de enfoque al hacer click
        button.setBorderPainted(false);      // Elimina el borde visible
        button.setContentAreaFilled(false);  // Evita que Swing pinte el fondo predeterminado
        button.setOpaque(true);              // Permite que el color de fondo personalizado sea visible

        // Colores
        Color baseColor = new Color(60, 60, 60);     // Color normal del botón (gris oscuro)
        Color hoverColor = new Color(90, 90, 90);    // Color cuando el mouse pasa por encima

        button.setBackground(baseColor);     // Establece el color inicial
        button.setForeground(Color.WHITE);   // Color del texto

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor); // Color más claro al hacer hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor); // Vuelve al color original
            }
        });


        JButton saveClientButton = new JButton("Crear +cliente");
        saveClientButton.setBounds(150, 150, 200, 35);
        //saveClientButton.addActionListener(e -> handleSaveClient(frame));
        frame.add(button);

        frame.setVisible(true);
    }
}
