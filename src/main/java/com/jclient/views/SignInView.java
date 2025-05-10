package com.jclient.views;

import com.jclient.clients.HTTPClient;

import com.jclient.controllers.RequestController;
import com.jclient.models.requests.SignInRequest;
import com.jclient.models.responses.SessionResponse;
import com.jclient.models.responses.SignInResponse;
import com.jclient.interfaces.ViewInterface;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.prefs.Preferences;

public class SignInView {

    private JFrame frame;
    private JPanel logoPanel;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JCheckBox rememberMe;
    private JButton signInButton;

    private MessageView messageView;



    public SignInView() {
        buildUI();

        frame.setVisible(true);
    }

    private void verifySession(){
        Preferences prefs = Preferences.userNodeForPackage(SignInView.class);
        boolean wasRemembered = prefs.getBoolean("rememberMe", false);

        if (wasRemembered) {
            String token = prefs.get("userAccessToken", null);
            if (token != null) {
                HTTPClient httpClient = new HTTPClient();
                try {
                    SessionResponse session = httpClient.verifySession(token);
                    if (session.token() != null) {
                        frame.dispose();
                        new MenuView();
                        return;
                    } else {
                        // token inválido: limpiar prefs
                        prefs.remove("userAccessToken");
                        prefs.putBoolean("rememberMe", false);
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }



    private void buildUI(){
        // Contenedor
        frame = new JFrame("Inicia sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ViewInterface.signInWindowWidth, ViewInterface.signInWindowHeight);
        frame.setLayout(null);
        frame.getContentPane().setBackground(ViewInterface.primaryBackgroundColor);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        messageView = new MessageView(frame);


        logoPanel = new JPanel();
        logoPanel.setBounds(0, 0, 350, ViewInterface.signInWindowHeight);
        logoPanel.setBackground(ViewInterface.secondaryBackgroundColor);
        logoPanel.setLayout(null);
        frame.add(logoPanel);

        usernameLabel = new JLabel("Usuario");
        usernameLabel.setBounds(400, 60, 240, 15);
        usernameLabel.setForeground(ViewInterface.primaryLabelTextColor);
        usernameLabel.setFont(ViewInterface.primaryLabelTextFont);
        frame.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(400, 80, 240, 25);
        usernameTextField.setOpaque(false);
        usernameTextField.setBackground(new Color(0, 0, 0, 0));
        usernameTextField.setForeground(ViewInterface.textFieldFontColor);
        usernameTextField.setHorizontalAlignment(JTextField.CENTER);
        usernameTextField.setFont(ViewInterface.textFieldFont);
        usernameTextField.setBorder(ViewInterface.textFieldBorder);
        usernameTextField.setCaretColor(ViewInterface.textFieldFontColor);
        usernameTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                usernameTextField.setBorder(BorderFactory.createMatteBorder(
                        0, 0, 2, 0, ViewInterface.primaryButtonHoverColor));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                usernameTextField.setBorder(ViewInterface.textFieldBorder);
            }
        });
        frame.add(usernameTextField);



        // Label y campo de contraseña
        passwordLabel = new JLabel("Contraseña");
        passwordLabel.setBounds(400, 140, 240, 15);
        passwordLabel.setForeground(ViewInterface.primaryLabelTextColor);
        passwordLabel.setFont(ViewInterface.primaryLabelTextFont);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(400, 160, 240, 25);
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(0, 0, 0, 0));
        passwordField.setForeground(ViewInterface.textFieldFontColor);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setFont(ViewInterface.textFieldFont);
        passwordField.setBorder(ViewInterface.textFieldBorder);
        passwordField.setCaretColor(ViewInterface.textFieldFontColor);
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                passwordField.setBorder(BorderFactory.createMatteBorder(
                        0, 0, 2, 0, ViewInterface.primaryButtonHoverColor));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                passwordField.setBorder(ViewInterface.textFieldBorder);
            }
        });
        frame.add(passwordField);



        // Opción "Mantener sesión" como JRadioButton plano Material UI
        rememberMe = new JCheckBox("Recuérdame");
        rememberMe.setBounds(400, 200, 240, 20);
        rememberMe.setOpaque(false);
        rememberMe.setFocusPainted(false);
        rememberMe.setBorderPainted(false);
        rememberMe.setContentAreaFilled(false);
        rememberMe.setFocusable(false);
        rememberMe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rememberMe.setFont(ViewInterface.primaryLabelTextFont);
        rememberMe.setForeground(ViewInterface.primaryLabelTextColor);
        rememberMe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                rememberMe.setForeground(ViewInterface.primaryButtonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                rememberMe.setForeground(ViewInterface.primaryLabelTextColor);
            }
        });

        frame.add(rememberMe);


        // Botón "Iniciar sesión"
        signInButton = new JButton("Iniciar sesión");

        signInButton.setFocusPainted(false);
        signInButton.setBorderPainted(false);
        signInButton.setContentAreaFilled(false);
        signInButton.setOpaque(true);
        signInButton.setBackground(ViewInterface.primaryButtonBaseColor);
        signInButton.setForeground(Color.WHITE);
        signInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                signInButton.setBackground(ViewInterface.primaryButtonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                signInButton.setBackground(ViewInterface.primaryButtonBaseColor);
            }
        });


        signInButton.addActionListener(e -> {
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isBlank() || password.isBlank()) {
                messageView.showWarningDialog(
                        "Por favor completa todos los campos",
                        "Atención"
                );
                return;
            }

            RequestController requestController = new RequestController();

            try {
                SignInResponse signInResponse = requestController.signIn(username, password);
                if (signInResponse.token() != null) {
                    Preferences prefs = Preferences.userNodeForPackage(SignInView.class);
                    if (rememberMe.isSelected()) {
                        prefs.put("userAccessToken", signInResponse.token());
                        prefs.putBoolean("rememberMe", true);
                    } else {
                        prefs.remove("userAccessToken");
                        prefs.putBoolean("rememberMe", false);
                    }



                    messageView.showInfoDialog(username + (rememberMe.isSelected() ? "\nSesión guardada." : ""), "Exito");


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

        // Opcional
        rememberMe.setVisible(false);
        if (!rememberMe.isShowing()){
            signInButton.setBounds(400, 250, 240, 42);

        } else {
            signInButton.setBounds(400, 260, 240, 42);
        }
        //

        frame.add(signInButton);
    }



}