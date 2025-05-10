package com.jclient.views;

import com.jclient.interfaces.ViewInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MessageView {

    private final JFrame parentFrame;

    public enum DialogType { INFO, WARNING, ERROR }

    public MessageView(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    /** Muestra diálogo de solo “Entendido” con icono de warning */
    public void showWarningDialog(String message, String title) {
        showDialog(message, title, DialogType.WARNING);
    }

    /** Muestra diálogo de solo “Entendido” con icono de info */
    public void showInfoDialog(String message, String title) {
        showDialog(message, title, DialogType.INFO);
    }

    /** Muestra diálogo de solo “Entendido” con icono de error */
    public void showErrorDialog(String message, String title) {
        showDialog(message, title, DialogType.ERROR);
    }

    /** Muestra diálogo de confirmación “Sí/No” y devuelve true si pulsa Sí */
    public boolean showConfirmDialog(String message, String title) {
        final boolean[] result = { false };
        JDialog dlg = createDialog(title);
        dlg.setLayout(new BorderLayout(10, 10));

        // Icono y mensaje
        Icon icon = UIManager.getIcon("OptionPane.questionIcon");
        JLabel lblIcon = new JLabel(icon);
        JLabel lblMsg  = new JLabel(message);
        lblMsg.setFont(ViewInterface.primaryLabelTextFont);
        lblMsg.setForeground(ViewInterface.textFieldFontColor);

        JPanel msgPanel = new JPanel(new BorderLayout(10,10));
        msgPanel.setOpaque(false);
        msgPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        msgPanel.add(lblIcon, BorderLayout.WEST);
        msgPanel.add(lblMsg,  BorderLayout.CENTER);
        dlg.add(msgPanel, BorderLayout.CENTER);

        // Botones
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setOpaque(false);

        JButton btnYes = createDialogButton("Sí");
        JButton btnNo  = createDialogButton("No");

        btnYes.addActionListener(e -> { result[0] = true;  dlg.dispose(); });
        btnNo .addActionListener(e -> { result[0] = false; dlg.dispose(); });

        btnPanel.add(btnYes);
        btnPanel.add(btnNo);
        dlg.add(btnPanel, BorderLayout.SOUTH);

        dlg.pack();
        dlg.setLocationRelativeTo(parentFrame);
        dlg.setVisible(true);

        return result[0];
    }

    /** Muestra un diálogo de entrada de texto y devuelve el String (o null si canceló) */
    public String showInputDialog(String message, String title) {
        final String[] input = { null };
        JDialog dlg = createDialog(title);
        dlg.setLayout(new BorderLayout(10,10));

        JLabel lblMsg = new JLabel(message);
        lblMsg.setFont(ViewInterface.primaryLabelTextFont);
        lblMsg.setForeground(ViewInterface.textFieldFontColor);
        lblMsg.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        dlg.add(lblMsg, BorderLayout.NORTH);

        JTextField textField = new JTextField(20);
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        fieldPanel.setOpaque(false);
        fieldPanel.add(textField);
        dlg.add(fieldPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setOpaque(false);
        JButton btnOk     = createDialogButton("OK");
        JButton btnCancel = createDialogButton("Cancelar");

        btnOk    .addActionListener(e -> { input[0] = textField.getText(); dlg.dispose(); });
        btnCancel.addActionListener(e -> { input[0] = null;               dlg.dispose(); });

        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);
        dlg.add(btnPanel, BorderLayout.SOUTH);

        dlg.pack();
        dlg.setLocationRelativeTo(parentFrame);
        dlg.setVisible(true);

        return input[0];
    }

    // ----------------- MÉTODOS PRIVADOS -----------------

    /** Crea el diálogo base (undecorated y modal) */
    private JDialog createDialog(String title) {
        JDialog dlg = new JDialog(parentFrame, title, true);

        // Activar en caso de quitar la barra de titulo
        // dlg.setUndecorated(true);

        dlg.getContentPane().setBackground(ViewInterface.primaryBackgroundColor);
        return dlg;
    }

    /**
     * Diálogo genérico de solo “Entendido”, con icono INFO/WARNING/ERROR
     */
    private void showDialog(String message, String title, DialogType type) {
        JDialog dlg = createDialog(title);
        dlg.setLayout(new BorderLayout(10,10));

        Icon icon;
        switch (type) {
            case WARNING: icon = UIManager.getIcon("OptionPane.warningIcon"); break;
            case ERROR:   icon = UIManager.getIcon("OptionPane.errorIcon");   break;
            default:      icon = UIManager.getIcon("OptionPane.informationIcon");
        }

        JLabel lblIcon = new JLabel(icon);
        JLabel lblMsg  = new JLabel(message);
        lblMsg.setFont(ViewInterface.primaryLabelTextFont);
        lblMsg.setForeground(ViewInterface.textFieldFontColor);

        JPanel msgPanel = new JPanel(new BorderLayout(10,10));
        msgPanel.setOpaque(false);
        msgPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        msgPanel.add(lblIcon, BorderLayout.WEST);
        msgPanel.add(lblMsg,  BorderLayout.CENTER);
        dlg.add(msgPanel, BorderLayout.CENTER);

        JButton okButton = createDialogButton("Entendido");
        okButton.addActionListener(e -> dlg.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(okButton);
        dlg.add(btnPanel, BorderLayout.SOUTH);

        dlg.pack();
        dlg.setLocationRelativeTo(parentFrame);
        dlg.setVisible(true);
    }

    /** Crea un JButton “Material-like” sin borde y con hover */
    private JButton createDialogButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);                           // <— quita el borde
        btn.setOpaque(true);
        btn.setBackground(ViewInterface.primaryButtonBaseColor);
        btn.setForeground(Color.WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));  // padding

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ViewInterface.primaryButtonHoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ViewInterface.primaryButtonBaseColor);
            }
        });
        return btn;
    }
}
