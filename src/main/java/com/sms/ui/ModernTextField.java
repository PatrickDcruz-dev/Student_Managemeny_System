package com.sms.ui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JTextField;

public class ModernTextField extends JTextField {

    private String placeholder = "";
    private Icon leadingIcon = null;
    private boolean isFocused = false;
    private int cornerRadius = 8;

    public ModernTextField() {
        this("", "");
    }

    public ModernTextField(String placeholder) {
        this(placeholder, "");
    }

    public ModernTextField(String placeholder, Icon leadingIcon) {
        this(placeholder, "");
        this.leadingIcon = leadingIcon;
    }

    public ModernTextField(String placeholder, String text) {
        super(text);
        this.placeholder = placeholder;
        setFont(UITheme.FONT_BODY);
        setForeground(UITheme.TEXT_PRIMARY);
        setCaretColor(UITheme.PRIMARY_BLUE);
        setOpaque(false);

        updateBorderPadding();

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
    }

    public void setLeadingIcon(Icon icon) {
        this.leadingIcon = icon;
        updateBorderPadding();
        repaint();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    private void updateBorderPadding() {
        int left = (leadingIcon != null) ? 38 : 12;
        setBorder(javax.swing.BorderFactory.createEmptyBorder(9, left, 9, 12));
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

        if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty()) {
            g2d.setColor(UITheme.TEXT_MUTED);
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int leftPad = (leadingIcon != null) ? 38 : 12;
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(placeholder, leftPad, textY);
        }

        g2d.dispose();
        super.paintComponent(g);
    }
}
