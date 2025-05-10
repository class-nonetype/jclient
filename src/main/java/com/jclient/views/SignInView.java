package com.jclient.views;

import com.jclient.clients.HTTPClient;
import com.jclient.models.requests.SignInRequest;
import com.jclient.models.responses.SignInResponse;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class SignInView {

    private static final int frameWidth = 680;
    private static final int frameHeight = 400;

    private static final String backgroundColor = "#FAFAFA";

    // Colores
    private static final Color textFieldBorderColor = new Color(36, 114, 200);
    private static final Color textFieldFontColor = new Color(36, 114, 200);
    private static final Color labelTextColor = new Color(0, 0, 0);

    private static final Color buttonBaseColor = new Color(60, 60, 60);
    private static final Color buttonHoverColor = new Color(90, 90, 90);

    // Bordes y fuente
    private static final MatteBorder textFieldBorder = BorderFactory.createMatteBorder(
            0, 0, 2, 0, textFieldBorderColor
    );
    private static final Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font labelTextFont = new Font("Segoe UI", Font.PLAIN, 15);

    public SignInView() {
        // Ventana principal
        JFrame frame = new JFrame("Iniciar sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode(backgroundColor));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Panel lateral para logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 350, frameHeight);
        logoPanel.setBackground(Color.LIGHT_GRAY);
        logoPanel.setLayout(null);
        frame.add(logoPanel);

        // Label y campo de usuario
        JLabel usernameLabel = new JLabel("Usuario");
        usernameLabel.setBounds(400, 60, 240, 15);
        usernameLabel.setForeground(labelTextColor);
        usernameLabel.setFont(labelTextFont);
        frame.add(usernameLabel);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(400, 80, 240, 25);
        usernameTextField.setOpaque(false);
        usernameTextField.setBackground(new Color(0, 0, 0, 0));
        usernameTextField.setForeground(textFieldFontColor);
        usernameTextField.setHorizontalAlignment(JTextField.CENTER);
        usernameTextField.setFont(textFieldFont);
        usernameTextField.setBorder(textFieldBorder);
        usernameTextField.setCaretColor(textFieldFontColor);
        frame.add(usernameTextField);

        // Efecto hover en input usuario
        usernameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                usernameTextField.setBorder(BorderFactory.createMatteBorder(
                        0, 0, 2, 0, buttonHoverColor));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                usernameTextField.setBorder(textFieldBorder);
            }
        });

        // Label y campo de contraseña
        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setBounds(400, 140, 240, 15);
        passwordLabel.setForeground(labelTextColor);
        passwordLabel.setFont(labelTextFont);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 160, 240, 25);
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(0, 0, 0, 0));
        passwordField.setForeground(textFieldFontColor);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setFont(textFieldFont);
        passwordField.setBorder(textFieldBorder);
        passwordField.setCaretColor(textFieldFontColor);
        frame.add(passwordField);

        // Efecto hover en input contraseña
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                passwordField.setBorder(BorderFactory.createMatteBorder(
                        0, 0, 2, 0, buttonHoverColor));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                passwordField.setBorder(textFieldBorder);
            }
        });

        // Botón "Iniciar sesión"
        JButton signInButton = new JButton("Iniciar sesión");
        signInButton.setBounds(400, 220, 240, 30);
        signInButton.setFocusPainted(false);
        signInButton.setBorderPainted(false);
        signInButton.setContentAreaFilled(false);
        signInButton.setOpaque(true);
        signInButton.setBackground(buttonBaseColor);
        signInButton.setForeground(Color.WHITE);
        // Efecto hover en botón
        signInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                signInButton.setBackground(buttonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                signInButton.setBackground(buttonBaseColor);
            }
        });

        signInButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(frame,
                        "Por favor completa todos los campos",
                        "Atención",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            HTTPClient httpClient = new HTTPClient();
            SignInRequest payload = new SignInRequest(username, password);
            try {
                SignInResponse signInResponse = httpClient.signIn(payload);

                if (signInResponse.token() != null) {
                    JOptionPane.showMessageDialog(frame,
                            "Bienvenido " + username,
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new MenuView();
                }

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame,
                        "Error al conectarse al servidor:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(signInButton);

        // Mostrar ventana
        frame.setVisible(true);
    }

    private boolean buildUI() {
        return true;
    }
}