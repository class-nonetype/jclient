package com.jclient.interfaces;

import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface ViewInterface {

    // Ancho x Alto
    int signInWindowWidth         = 680;
    int signInWindowHeight        = 400;

    int menuWindowWidth           = 700;
    int menuWindowHeight          = 500;

    int clientFormWindowWidth     = 0;
    int clientFormWindowHeight    = 0;

    // Colores de fondo
    Color primaryBackgroundColor   = new Color(250, 250, 250);
    Color secondaryBackgroundColor = new Color(192, 192, 192);

    // Colores de texto y bordes
    Color textFieldBorderColor     = new Color(36, 114, 200);
    Color textFieldFontColor       = new Color(36, 114, 200);
    Color primaryLabelTextColor    = new Color(0, 0, 0);
    Color primaryForegroundColor   = new Color(33, 33, 33);

    // Colores de botones
    Color primaryButtonBaseColor   = new Color(33, 33, 33);
    Color primaryButtonHoverColor  = new Color(60, 60, 60);

    // Sidebar
    Color sidebarColor             = new Color(46, 64, 83);
    int   sidebarExpandedWidth     = 200;
    int   sidebarCollapsedWidth    = 58;
    int   sidebarButtonHeight      = 58;
    int   sidebarButtonIconSize    = 22;
    int   sidebarButtonIconTextGap = 12;                // espacio icono-texto
    int   sidebarButtonSpacing     = 5;                 // espaciado vertical
    Color sidebarButtonForeground  = new Color(250, 250, 250);
    Color sidebarButtonHoverOverlay= new Color(0, 0, 0, 20); // overlay al hover

    // Colores auxiliares
    Color orangeColor              = new Color(230, 126, 34);
    Color blueColor                = new Color(36, 114, 200);
    Color blackColor               = new Color(33, 33, 33);
    Color whiteColor               = new Color(250, 250, 250);
    Color grayColor                = new Color(192, 192, 192);

    // Borde por defecto para text fields
    MatteBorder textFieldBorder = BorderFactory.createMatteBorder(
            0, 0, 2, 0, textFieldBorderColor
    );

    // Fuentes
    Font textFieldFont         = new Font("Segoe UI", Font.PLAIN, 12);
    Font primaryLabelTextFont  = new Font("Segoe UI", Font.PLAIN, 15);


    // Iconos Ikonli
    Map<String, FontIcon> iconMap = new HashMap<>();

}



