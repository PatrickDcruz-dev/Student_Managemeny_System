package com.sms.ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class ModernButton extends JButton {

    private Color normalBg;
    private Color hoverBg;
    private Color pressedBg;
    private Color borderColor;
    private int cornerRadius = 8;
    private boolean isHovered = false;
    private boolean isPressed = false;

    public ModernButton(String text) {
        this(text, UITheme.PRIMARY_BLUE, Color.WHITE);
    }

    public ModernButton(String text, Icon icon) {
        this(text, UITheme.PRIMARY_BLUE, Color.WHITE);
        setIcon(icon);
    }

    public ModernButton(String text, Color bg, Color fg) {
        super(text);
        this.normalBg = bg;
        this.hoverBg = calculateHoverColor(bg);
        this.pressedBg = calculatePressedColor(bg);
        this.borderColor = bg;

        setForeground(fg);
        setFont(UITheme.FONT_BUTTON);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    public void setCustomBorder(Color border) {
        this.borderColor = border;
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setColors(Color normal, Color hover, Color pressed) {
        this.normalBg = normal;
        this.hoverBg = hover;
        this.pressedBg = pressed;
        this.borderColor = normal;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Color bg;
        if (!isEnabled()) {
            bg = new Color(226, 232, 240);
        } else if (isPressed) {
            bg = pressedBg;
        } else if (isHovered) {
            bg = hoverBg;
        } else {
            bg = normalBg;
        }

        g2d.setColor(bg);
        g2d.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));

        if (borderColor != null && borderColor != normalBg) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(1.2f));
            g2d.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    private static Color calculateHoverColor(Color c) {
        if (c.equals(Color.WHITE)) {
            return new Color(241, 245, 249);
        }
        return new Color(
                Math.max(0, c.getRed() - 15),
                Math.max(0, c.getGreen() - 15),
                Math.max(0, c.getBlue() - 15)
        );
    }

    private static Color calculatePressedColor(Color c) {
        if (c.equals(Color.WHITE)) {
            return new Color(226, 232, 240);
        }
        return new Color(
                Math.max(0, c.getRed() - 35),
                Math.max(0, c.getGreen() - 35),
                Math.max(0, c.getBlue() - 35)
        );
    }
}
