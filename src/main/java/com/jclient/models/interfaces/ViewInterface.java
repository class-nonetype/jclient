package com.jclient.models.interfaces;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public interface ViewInterface {

    // Ancho x Alto
    int signInWindowWidth = 680;
    int signInWindowHeight = 400;

    int menuWindowWidth = 0;
    int menuWindowHeight = 0;

    int clientFormWindowWidth = 0;
    int clientFormWindowHeight = 0;

    // Color
    Color primaryBackgroundColor = new Color(250, 250, 250);
    Color secondaryBackgroundColor = new Color(192, 192, 192);


    Color textFieldBorderColor = new Color(36, 114, 200);
    Color textFieldFontColor = new Color(36, 114, 200);
    Color primaryLabelTextColor = new Color(0, 0, 0);

    Color primaryButtonBaseColor = new Color(60, 60, 60);
    Color primaryButtonHoverColor = new Color(90, 90, 90);


    // Borde
    MatteBorder textFieldBorder = BorderFactory.createMatteBorder(
            0, 0, 2, 0, textFieldBorderColor
    );

    // Fuente
    Font textFieldFont = new Font("Segoe UI", Font.PLAIN, 12);
    Font primaryLabelTextFont = new Font("Segoe UI", Font.PLAIN, 15);


}
