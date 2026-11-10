package com.sms.ui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JPasswordField;

public class ModernPasswordField extends JPasswordField {

    private String placeholder = "";
    private Icon leadingIcon;
    private boolean isFocused = false;
    private boolean showPassword = false;
    private final char defaultEchoChar;
    private final int cornerRadius = 8;
    private final int eyeIconSize = 18;

    public ModernPasswordField() {
        this("Password");
    }

    public ModernPasswordField(String placeholder) {
        super();
        this.placeholder = placeholder;
        this.leadingIcon = IconHelper.getLockIcon(18, UITheme.TEXT_MUTED);
        this.defaultEchoChar = getEchoChar();

        setFont(UITheme.FONT_BODY);
        setForeground(UITheme.TEXT_PRIMARY);
        setCaretColor(UITheme.PRIMARY_BLUE);
        setOpaque(false);

        setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 38, 9, 38));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFocused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFocused = false;
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int w = getWidth();

                if (e.getX() >= w - 36 && e.getX() <= w - 6) {
                    togglePasswordVisibility();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int w = getWidth();
                if (e.getX() >= w - 36 && e.getX() <= w - 6) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
            }
        });
    }

    public void togglePasswordVisibility() {
        showPassword = !showPassword;
        if (showPassword) {
            setEchoChar((char) 0);
        } else {
            setEchoChar(defaultEchoChar);
        }
        repaint();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2d.setColor(UITheme.INPUT_BG);
        g2d.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));

        Color border = isFocused ? UITheme.INPUT_BORDER_FOCUS : UITheme.INPUT_BORDER;
        float strokeWidth = isFocused ? 1.6f : 1.0f;
        g2d.setColor(border);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));

        if (leadingIcon != null) {
            int iconY = (h - leadingIcon.getIconHeight()) / 2;
            leadingIcon.paintIcon(this, g2d, 10, iconY);
        }

        Icon eyeIcon = IconHelper.getEyeIcon(eyeIconSize, UITheme.TEXT_MUTED, showPassword);
        int eyeX = w - eyeIconSize - 10;
        int eyeY = (h - eyeIconSize) / 2;
        eyeIcon.paintIcon(this, g2d, eyeX, eyeY);

        if (getPassword().length == 0 && placeholder != null && !placeholder.isEmpty()) {
            g2d.setColor(UITheme.TEXT_MUTED);
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(placeholder, 38, textY);
        }

        g2d.dispose();
        super.paintComponent(g);
    }
}
