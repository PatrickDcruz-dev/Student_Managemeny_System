package com.sms.ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class BrandingPanel extends JPanel {

    public BrandingPanel() {
        setPreferredSize(new Dimension(360, 520));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        GradientPaint bgGradient = new GradientPaint(
                0, 0, UITheme.BRAND_BG,
                0, h, UITheme.BRAND_BG_DARK
        );
        g2d.setPaint(bgGradient);
        g2d.fillRect(0, 0, w, h);

        int iconSize = 72;
        int iconX = (w - iconSize) / 2;
        int iconY = 48;
        IconHelper.getGraduationCapIcon(iconSize, Color.WHITE).paintIcon(this, g2d, iconX, iconY);

        g2d.setColor(Color.WHITE);
        g2d.setFont(UITheme.FONT_TITLE_LARGE);
        FontMetrics fm = g2d.getFontMetrics();

        String line1 = "Student";
        String line2 = "Management";
        String line3 = "System";

        int textY = iconY + iconSize + 36;
        g2d.drawString(line1, (w - fm.stringWidth(line1)) / 2, textY);
        textY += fm.getHeight() - 2;
        g2d.drawString(line2, (w - fm.stringWidth(line2)) / 2, textY);
        textY += fm.getHeight() - 2;
        g2d.drawString(line3, (w - fm.stringWidth(line3)) / 2, textY);

        g2d.setColor(UITheme.SIDEBAR_TEXT_MUTED);
        g2d.setFont(UITheme.FONT_SUBTITLE);
        FontMetrics subFm = g2d.getFontMetrics();

        String sub1 = "Manage students, records";
        String sub2 = "and academic information";
        textY += 26;
        g2d.drawString(sub1, (w - subFm.stringWidth(sub1)) / 2, textY);
        textY += subFm.getHeight();
        g2d.drawString(sub2, (w - subFm.stringWidth(sub2)) / 2, textY);

        drawBooksIllustration(g2d, w / 2, h - 85);

        g2d.dispose();
    }

    private void drawBooksIllustration(Graphics2D g2d, int cx, int cy) {

        g2d.setColor(new Color(255, 255, 255, 18));
        g2d.fill(new Ellipse2D.Double(cx - 100, cy + 25, 200, 28));

        Color leafColor = new Color(255, 255, 255, 45);
        g2d.setColor(leafColor);

        drawLeaf(g2d, cx - 75, cy - 10, -0.6, 22, 10);
        drawLeaf(g2d, cx - 85, cy + 5, -0.3, 26, 12);
        drawLeaf(g2d, cx - 70, cy + 20, 0.1, 20, 9);

        drawLeaf(g2d, cx + 75, cy - 10, 0.6, 22, 10);
        drawLeaf(g2d, cx + 85, cy + 5, 0.3, 26, 12);
        drawLeaf(g2d, cx + 70, cy + 20, -0.1, 20, 9);

        drawBook(g2d, cx - 55, cy + 8, 110, 22,
                new Color(45, 105, 205),
                new Color(240, 246, 255),
                new Color(30, 80, 180));

        drawBook(g2d, cx - 48, cy - 14, 96, 20,
                new Color(59, 130, 246),
                new Color(245, 250, 255),
                new Color(37, 99, 235));

        drawBook(g2d, cx - 40, cy - 32, 80, 17,
                new Color(96, 165, 250),
                new Color(255, 255, 255),
                new Color(59, 130, 246));
    }

    private void drawBook(Graphics2D g2d, int x, int y, int bw, int bh, Color cover, Color pages, Color spine) {

        int spineW = 14;
        g2d.setColor(spine);
        g2d.fill(new RoundRectangle2D.Double(x, y, spineW + 4, bh, 6, 6));

        g2d.setColor(pages);
        g2d.fill(new RoundRectangle2D.Double(x + spineW, y + 2, bw - spineW - 2, bh - 4, 3, 3));

        g2d.setColor(new Color(210, 225, 245));
        g2d.drawLine(x + spineW + 2, y + bh / 2, x + bw - 4, y + bh / 2);

        g2d.setColor(cover);
        g2d.fill(new RoundRectangle2D.Double(x + 2, y, bw - 2, 3, 2, 2));
        g2d.fill(new RoundRectangle2D.Double(x + 2, y + bh - 3, bw - 2, 3, 2, 2));
    }

    private void drawLeaf(Graphics2D g2d, int x, int y, double angle, int len, int width) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.rotate(angle);

        Path2D leaf = new Path2D.Double();
        leaf.moveTo(0, 0);
        leaf.curveTo(len * 0.4, -width, len * 0.8, -width * 0.5, len, 0);
        leaf.curveTo(len * 0.8, width * 0.5, len * 0.4, width, 0, 0);
        leaf.closePath();
        g2d.fill(leaf);

        g2d.setTransform(old);
    }
}
