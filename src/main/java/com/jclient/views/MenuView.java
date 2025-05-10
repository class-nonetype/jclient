package com.jclient.views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class MenuView {

    // Propiedades de la ventana
    private static final int WIDTH           = 600;
    private static final int HEIGHT          = 400;
    private static final int EXPANDED_WIDTH  = 180;
    private static final int COLLAPSED_WIDTH = 50;

    // Colores Material UI
    private static final Color PRIMARY_COLOR    = new Color(0x3F51B5);
    private static final Color SURFACE_COLOR    = new Color(0xF5F5F5);
    private static final Color ON_PRIMARY_COLOR = Color.WHITE;
    private static final Color ON_SURFACE_COLOR = new Color(0x212121);
    private static final Color HOVER_OVERLAY    = new Color(0, 0, 0, 20);

    private JFrame        frame;
    private JPanel        sidebarPanel;
    private JPanel        buttonsHolder;
    private JPanel        contentPanel;
    private CardLayout    cardLayout;
    private JToggleButton toggleBtn;
    private boolean       collapsed = false;

    // Iconos Ikonli
    private final Map<String, FontIcon> iconMap = new HashMap<>();

    public MenuView() {
        initIcons();
        initFrame();
        setupSidebar();
        setupContent();
        frame.setVisible(true);
    }

    private void initIcons() {
        iconMap.put("Inicio",        FontIcon.of(FontAwesomeSolid.HOME,             20, ON_PRIMARY_COLOR));
        iconMap.put("Configuración", FontIcon.of(FontAwesomeSolid.COG,              20, ON_PRIMARY_COLOR));
        iconMap.put("Ayuda",         FontIcon.of(FontAwesomeSolid.QUESTION_CIRCLE,  20, ON_PRIMARY_COLOR));
        iconMap.put("Salir",         FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT,    20, ON_PRIMARY_COLOR));
        iconMap.put("COLLAPSE",      FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT,     24, ON_PRIMARY_COLOR));
        iconMap.put("EXPAND",        FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT,    24, ON_PRIMARY_COLOR));
    }

    private void initFrame() {
        frame = new JFrame("Menú Responsivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(SURFACE_COLOR);
        frame.setLayout(new BorderLayout());
    }

    private void setupSidebar() {
        // Panel lateral
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(PRIMARY_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(EXPANDED_WIDTH, 0));
        frame.add(sidebarPanel, BorderLayout.WEST);

        // Toggle arriba
        toggleBtn = new JToggleButton(iconMap.get("COLLAPSE"));
        toggleBtn.setFocusPainted(false);
        toggleBtn.setBorderPainted(false);
        toggleBtn.setContentAreaFilled(false);
        toggleBtn.setToolTipText("Ocultar menú");
        toggleBtn.addActionListener(e -> toggleSidebar());
        JPanel topHolder = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topHolder.setOpaque(false);
        topHolder.add(toggleBtn);
        sidebarPanel.add(topHolder, BorderLayout.NORTH);

        // Botones en centro
        buttonsHolder = new JPanel();
        buttonsHolder.setOpaque(false);
        buttonsHolder.setLayout(new BoxLayout(buttonsHolder, BoxLayout.Y_AXIS));

        String[] labels = {"Inicio", "Configuración", "Ayuda", "Salir"};
        for (String text : labels) {
            HoverButton btn = new HoverButton(text, iconMap.get(text), HOVER_OVERLAY);
            btn.putClientProperty("originalText", text);
            btn.setForeground(ON_PRIMARY_COLOR);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(12);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            btn.addActionListener(e -> onMenuClick(text));
            buttonsHolder.add(Box.createVerticalStrut(10));
            buttonsHolder.add(btn);
        }
        buttonsHolder.add(Box.createVerticalGlue());
        sidebarPanel.add(buttonsHolder, BorderLayout.CENTER);
    }

    private void setupContent() {
        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(SURFACE_COLOR);
        frame.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(createLabelPanel("Vista Inicio"),        "Inicio");
        contentPanel.add(createLabelPanel("Vista Configuración"), "Configuración");
        contentPanel.add(createLabelPanel("Vista Ayuda"),         "Ayuda");
        contentPanel.add(createLabelPanel("¡Adiós!"),             "Salir");

        cardLayout.show(contentPanel, "Inicio");
    }

    private JPanel createLabelPanel(String text) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(SURFACE_COLOR);
        JLabel lbl = new JLabel(text);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 24f));
        lbl.setForeground(ON_SURFACE_COLOR);
        p.add(lbl);
        return p;
    }

    private void toggleSidebar() {
        collapsed = !collapsed;
        int width = collapsed ? COLLAPSED_WIDTH : EXPANDED_WIDTH;

        // Ajusta ancho del sidebar
        sidebarPanel.setPreferredSize(new Dimension(width, 0));

        // Actualiza icono y tooltip
        toggleBtn.setIcon(collapsed ? iconMap.get("EXPAND") : iconMap.get("COLLAPSE"));
        toggleBtn.setToolTipText(collapsed ? "Mostrar menú" : "Ocultar menú");

        // Oculta o muestra texto de los botones
        for (Component comp : buttonsHolder.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn    = (JButton) comp;
                String origTxt = (String) btn.getClientProperty("originalText");
                btn.setText(collapsed ? "" : origTxt);
            }
        }

        // Fuerza re-layout
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void onMenuClick(String key) {
        if ("Salir".equals(key)) {
            System.exit(0);
        } else {
            cardLayout.show(contentPanel, key);
        }
    }

    /** JButton custom que dibuja overlay al hover sin deformar */
    private static class HoverButton extends JButton {
        private final Color hoverColor;
        public HoverButton(String text, Icon icon, Color hoverColor) {
            super(text, icon);
            this.hoverColor = hoverColor;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setRolloverEnabled(true);
        }
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(hoverColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuView::new);
    }
}
