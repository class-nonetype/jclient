package com.jclient.views;

import com.jclient.interfaces.ViewInterface;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.geom.RoundRectangle2D;


public class MenuView {

    // Propiedades de la ventana
    private static final int WIDTH  = 600;
    private static final int HEIGHT = 400;

    // Claves para client properties
    private static final String PROP_MENU_KEY      = "menuKey";
    private static final String PROP_ORIGINAL_TEXT = "originalText";

    private JFrame        frame;
    private JPanel        sidebarPanel;
    private JPanel        buttonsHolder;
    private JPanel        contentPanel;
    private CardLayout    cardLayout;
    private JToggleButton toggleButton;
    private boolean       collapsed = false;

    // Iconos Ikonli
    private final Map<String, FontIcon> iconMap = new HashMap<>();

    public MenuView() {
        buildUI();
    }

    private void buildUI() {
        setupIcons();

        frame = new JFrame("Aplicación");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ViewInterface.menuWindowWidth, ViewInterface.menuWindowHeight);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(ViewInterface.primaryBackgroundColor);
        frame.setLayout(new BorderLayout());

        // Sidebar
        sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.setBackground(ViewInterface.sidebarColor);
        sidebarPanel.setPreferredSize(new Dimension(ViewInterface.sidebarExpandedWidth, 0));
        frame.add(sidebarPanel, BorderLayout.WEST);

        // Toggle arriba
        toggleButton = new JToggleButton(ViewInterface.iconMap.get("collapseSidebar"));
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setToolTipText("Ocultar menú");
        toggleButton.addActionListener(e -> toggleSidebar());
        JPanel holderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        holderPanel.setOpaque(false);
        holderPanel.add(toggleButton);
        sidebarPanel.add(holderPanel, BorderLayout.NORTH);

        // Botones: separados del toggle
        String[][] menuItems = {
                { "home",       "Inicio"                },
                { "management", "Gestión de clientes"   },
                { "settings",   "Configuración"         },
                { "help",       "Ayuda"                 },
                { "exit",       "Salir"                 }
        };

        buttonsHolder = new JPanel();
        buttonsHolder.setOpaque(false);
        buttonsHolder.setLayout(new BoxLayout(buttonsHolder, BoxLayout.Y_AXIS));
        buttonsHolder.add(Box.createVerticalStrut(50)); // separación inicial

        for (String[] item : menuItems) {
            String key   = item[0];
            String label = item[1];
            HoverButton btn = new HoverButton(label, ViewInterface.iconMap.get(key), ViewInterface.sidebarButtonHoverOverlay);
            btn.putClientProperty(PROP_MENU_KEY,      key);
            btn.putClientProperty(PROP_ORIGINAL_TEXT, label);
            btn.setForeground(ViewInterface.sidebarButtonForeground);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(ViewInterface.sidebarButtonIconTextGap);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, ViewInterface.sidebarButtonHeight));
            btn.setPreferredSize(new Dimension(Short.MAX_VALUE, ViewInterface.sidebarButtonHeight));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.addActionListener(e -> onMenuClick(key));
            buttonsHolder.add(btn);
            buttonsHolder.add(Box.createVerticalStrut(ViewInterface.sidebarButtonSpacing));
        }
        // Elimina último espaciador
        if (buttonsHolder.getComponentCount() > 0) {
            buttonsHolder.remove(buttonsHolder.getComponentCount() - 1);
        }
        sidebarPanel.add(buttonsHolder, BorderLayout.CENTER);

        // Contenido con CardLayout
        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(ViewInterface.primaryBackgroundColor);
        frame.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(createLabelPanel("Vista Inicio"),        "home");
        contentPanel.add(createManagementPanel(),                "management");
        contentPanel.add(createLabelPanel("Vista Configuración"), "settings");
        contentPanel.add(createLabelPanel("Vista Ayuda"),         "help");
        contentPanel.add(createLabelPanel("¡Adiós!"),             "exit");
        cardLayout.show(contentPanel, "home");

        frame.setVisible(true);
    }

    private void setupIcons() {
        ViewInterface.iconMap.put("home",            FontIcon.of(FontAwesomeSolid.HOME,            ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("management",      FontIcon.of(FontAwesomeSolid.USERS_COG,       ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("settings",        FontIcon.of(FontAwesomeSolid.COG,             ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("help",            FontIcon.of(FontAwesomeSolid.QUESTION_CIRCLE, ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("exit",            FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT,    ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("collapseSidebar", FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT,    ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
        ViewInterface.iconMap.put("expandSidebar",   FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT,   ViewInterface.sidebarButtonIconSize, ViewInterface.sidebarButtonForeground));
    }

    private JPanel createLabelPanel(String text) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(ViewInterface.primaryBackgroundColor);
        JLabel lbl = new JLabel(text);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 24f));
        lbl.setForeground(ViewInterface.primaryForegroundColor);
        p.add(lbl);
        return p;
    }

    private JPanel createManagementPanel() {
        // 1) Top bar con search, filtros, recargar y “Crear nuevo cliente”
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBackground(ViewInterface.primaryBackgroundColor);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search field con placeholder y borde inferior azul
        JTextField searchField = new JTextField("Buscar cliente...", 20);
        searchField.setFont(ViewInterface.textFieldFont);
        searchField.setForeground(ViewInterface.grayColor.darker());
        searchField.setMaximumSize(new Dimension(250, 30));
        searchField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ViewInterface.blueColor));
        searchField.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if ("Buscar cliente...".equals(searchField.getText())) {
                    searchField.setText("");
                    searchField.setForeground(ViewInterface.primaryForegroundColor);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (searchField.getText().isBlank()) {
                    searchField.setText("Buscar cliente...");
                    searchField.setForeground(ViewInterface.grayColor.darker());
                }
            }
        });
        topBar.add(searchField);
        topBar.add(Box.createRigidArea(new Dimension(10, 0)));

        // Función inline para crear JComboBox “flat” con línea inferior y flecha fija
        String[][] combos = {
                { "Todas", "VIP", "Regular", "Nuevo" },
                { "Activo", "Inactivo" }
        };
        for (String[] items : combos) {
            JComboBox<String> combo = new JComboBox<>(items);
            combo.setFont(ViewInterface.textFieldFont);
            combo.setPreferredSize(new Dimension(200, 30));
            combo.setMaximumSize(new Dimension(200, 30));
            combo.setBorder(BorderFactory.createEmptyBorder());
            combo.setBackground(ViewInterface.primaryBackgroundColor);
            combo.setOpaque(false);
            combo.setUI(new BasicComboBoxUI() {
                @Override protected JButton createArrowButton() {
                    JButton b = new JButton(FontIcon.of(FontAwesomeSolid.CHEVRON_DOWN, 12, ViewInterface.whiteColor));
                    b.setBorder(BorderFactory.createEmptyBorder());
                    b.setBackground(ViewInterface.blueColor);
                    b.setOpaque(true);
                    b.setFocusPainted(false);
                    b.setBorderPainted(false);
                    b.setContentAreaFilled(true);
                    return b;
                }
                @Override public void paint(Graphics g, JComponent c) {
                    super.paint(g, c);
                    g.setColor(ViewInterface.blueColor);
                    g.fillRect(0, c.getHeight() - 2, c.getWidth(), 2);
                }
            });
            topBar.add(combo);
            topBar.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        // Botón recargar con evento y hover
        JButton reloadBtn = new JButton(FontIcon.of(FontAwesomeSolid.SYNC, 16, ViewInterface.blueColor));
        reloadBtn.setPreferredSize(new Dimension(28, 28));
        reloadBtn.setMaximumSize(new Dimension(28, 28));
        reloadBtn.setFocusPainted(false);
        reloadBtn.setBorderPainted(false);
        reloadBtn.setContentAreaFilled(false);
        reloadBtn.setToolTipText("Recargar lista");
        reloadBtn.addActionListener(e -> System.out.println("Recargando..."));
        reloadBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                reloadBtn.setIcon(FontIcon.of(FontAwesomeSolid.SYNC, 16, ViewInterface.orangeColor));
            }
            @Override public void mouseExited(MouseEvent e) {
                reloadBtn.setIcon(FontIcon.of(FontAwesomeSolid.SYNC, 16, ViewInterface.blueColor));
            }
        });
        topBar.add(reloadBtn);

        topBar.add(Box.createHorizontalGlue());

        // Botón “Crear nuevo cliente”
        JButton createBtn = new JButton("Crear nuevo cliente");
        createBtn.setFont(ViewInterface.primaryLabelTextFont);
        createBtn.setBackground(ViewInterface.orangeColor);
        createBtn.setForeground(ViewInterface.whiteColor);
        createBtn.setFocusPainted(false);
        createBtn.setBorderPainted(false);
        createBtn.setMaximumSize(new Dimension(180, 30));
        createBtn.setPreferredSize(new Dimension(180, 30));
        createBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topBar.add(createBtn);

        // 2) Generar y mostrar 5000 registros en la tabla flat
        String[] columnNames = { "ID", "Nombre", "Email", "Teléfono" };
        int rowCount = 100000;
        Object[][] data = new Object[rowCount][columnNames.length];
        for (int i = 0; i < rowCount; i++) {
            data[i][0] = i + 1;
            data[i][1] = "Cliente " + (i + 1);
            data[i][2] = "cliente" + (i + 1) + "@example.com";
            data[i][3] = String.format("569 4948 %04d", i);
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBorder(null);
        table.setFont(ViewInterface.textFieldFont);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setBackground(ViewInterface.primaryBackgroundColor);
        table.setForeground(ViewInterface.primaryForegroundColor);
        table.setSelectionBackground(ViewInterface.sidebarColor);
        table.setSelectionForeground(ViewInterface.whiteColor);

        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setBackground(ViewInterface.primaryBackgroundColor);
        header.setForeground(ViewInterface.primaryForegroundColor);
        header.setFont(ViewInterface.primaryLabelTextFont);
        header.setBorder(null);

        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 230, 230, 128));
        table.setShowVerticalLines(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable tbl, Object val,
                                                                     boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(tbl, val, sel, foc, row, col);
                ((JComponent) this).setBorder(null);
                if (sel) {
                    setBackground(ViewInterface.sidebarColor);
                    setForeground(ViewInterface.whiteColor);
                } else {
                    setBackground(ViewInterface.primaryBackgroundColor);
                    setForeground(ViewInterface.primaryForegroundColor);
                }
                return this;
            }
        });


        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(ViewInterface.primaryBackgroundColor);

        scroll.getVerticalScrollBar().setUI(new MinimalScrollBarUI());
        scroll.getHorizontalScrollBar().setUI(new MinimalScrollBarUI());

        // 3) Panel final con separación a los lados
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ViewInterface.primaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }


    private void toggleSidebar() {
        collapsed = !collapsed;
        int width = collapsed
                ? ViewInterface.sidebarCollapsedWidth
                : ViewInterface.sidebarExpandedWidth;
        sidebarPanel.setPreferredSize(new Dimension(width, 0));

        String toggleKey = collapsed ? "expandSidebar" : "collapseSidebar";
        toggleButton.setIcon(ViewInterface.iconMap.get(toggleKey));
        toggleButton.setToolTipText(collapsed ? "Mostrar menú" : "Ocultar menú");

        for (Component comp : buttonsHolder.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String orig = (String) btn.getClientProperty(PROP_ORIGINAL_TEXT);
                btn.setText(collapsed ? "" : orig);
            }
        }

        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private void onMenuClick(String key) {
        if ("exit".equals(key)) {
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
        @Override protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(hoverColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }

    private static class MinimalScrollBarUI extends BasicScrollBarUI {
        private static final int THUMB_SIZE = 8;
        private Color thumbColor     = new Color(0, 0, 0, 80);
        private Color thumbHoverColor= new Color(0, 0, 0, 120);

        @Override protected void configureScrollBarColors() {
            trackColor = new Color(0,0,0,0);
        }

        @Override protected JButton createDecreaseButton(int orientation) {
            return zeroButton();
        }
        @Override protected JButton createIncreaseButton(int orientation) {
            return zeroButton();
        }
        private JButton zeroButton() {
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(0,0));
            b.setMinimumSize(new Dimension(0,0));
            b.setMaximumSize(new Dimension(0,0));
            return b;
        }

        @Override protected void installListeners() {
            super.installListeners();
            scrollbar.addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) {
                    thumbColor = thumbHoverColor;
                    scrollbar.repaint();
                }
                @Override public void mouseExited(MouseEvent e) {
                    thumbColor = new Color(0,0,0,80);
                    scrollbar.repaint();
                }
            });
        }

        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // nada
        }

        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) return;
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = THUMB_SIZE;
            int x = thumbBounds.x + (thumbBounds.width - w) / 2;
            int y = thumbBounds.y;
            int h = thumbBounds.height;

            RoundRectangle2D thumb = new RoundRectangle2D.Double(x, y, w, h, w, w);
            g2.setColor(thumbColor);
            g2.fill(thumb);
            g2.dispose();
        }

        @Override protected Dimension getMinimumThumbSize() {
            return new Dimension(THUMB_SIZE, THUMB_SIZE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuView::new);
    }
}
