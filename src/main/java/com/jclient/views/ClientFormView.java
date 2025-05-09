package com.jclient.views;

/*
import com.manager.controllers.ClientController;
import com.manager.models.Client;
 */
import javax.swing.*;
import java.awt.*;

public class ClientFormView {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    private final JTextField rutTextField;
    private final JTextField fullNameTextField;
    private final JTextField addressTextField;

    // private final ClientController clientController;

    public ClientFormView() {
        // clientController = new ClientController();

        JFrame frame = new JFrame("Formulario de Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#121212"));

        // Campos
        rutTextField = createTextField(frame, "RUT", 30);
        fullNameTextField = createTextField(frame, "Nombre completo", 70);
        addressTextField = createTextField(frame, "Dirección", 110);

        JButton saveClientButton = new JButton("Guardar cliente");
        saveClientButton.setBounds(150, 150, 200, 35);
        saveClientButton.addActionListener(e -> handleSaveClient(frame));
        frame.add(saveClientButton);

        frame.setVisible(true);
    }

    private JTextField createTextField(JFrame frame, String labelText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(30, y, 120, 25);
        frame.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(150, y, 200, 25);
        frame.add(textField);

        return textField;
    }

    private void handleSaveClient(JFrame frame) {
        try {
            /*
            Client client = clientController.createClient(
                    rutTextField.getText(),
                    fullNameTextField.getText(),
                    addressTextField.getText()
            );

             */

            JOptionPane.showMessageDialog(frame, "message", "Cliente Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
