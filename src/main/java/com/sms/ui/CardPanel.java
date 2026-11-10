package com.sms.ui;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class CardPanel extends JPanel {

    private int cornerRadius = 12;
    private Color bgColor = UITheme.CARD_BG;
    private Color borderColor = UITheme.CARD_BORDER;

    public CardPanel() {
        setOpaque(false);
    }

    public CardPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    public CardPanel(LayoutManager layout) {
        super(layout);
        setOpaque(false);
    }

    public void setCardColors(Color bg, Color border) {
        this.bgColor = bg;
        this.borderColor = border;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2d.setColor(bgColor);
        g2d.fill(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));

        if (borderColor != null) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.draw(new RoundRectangle2D.Double(1, 1, w - 2, h - 2, cornerRadius, cornerRadius));
        }

        g2d.dispose();
        super.paintComponent(g);
    }
}
