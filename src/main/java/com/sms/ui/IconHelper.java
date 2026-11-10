package com.sms.ui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.Icon;

public class IconHelper {

    public static Icon getGraduationCapIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);

            Path2D cap = new Path2D.Double();
            cap.moveTo(w * 0.5, h * 0.20);
            cap.lineTo(w * 0.92, h * 0.40);
            cap.lineTo(w * 0.5, h * 0.60);
            cap.lineTo(w * 0.08, h * 0.40);
            cap.closePath();
            g2d.fill(cap);

            Path2D base = new Path2D.Double();
            base.moveTo(w * 0.25, h * 0.52);
            base.lineTo(w * 0.25, h * 0.72);
            base.curveTo(w * 0.25, h * 0.84, w * 0.75, h * 0.84, w * 0.75, h * 0.72);
            base.lineTo(w * 0.75, h * 0.52);
            base.curveTo(w * 0.60, h * 0.62, w * 0.40, h * 0.62, w * 0.25, h * 0.52);
            base.closePath();
            g2d.fill(base);

            g2d.setStroke(new BasicStroke(Math.max(1.5f, size * 0.06f)));
            Path2D tassel = new Path2D.Double();
            tassel.moveTo(w * 0.5, h * 0.40);
            tassel.lineTo(w * 0.15, h * 0.55);
            tassel.lineTo(w * 0.15, h * 0.80);
            g2d.draw(tassel);
            g2d.fillOval((int) (w * 0.11), (int) (h * 0.78), (int) (w * 0.08), (int) (h * 0.08));
        });
    }

    public static Icon getUserIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            double headR = w * 0.24;
            g2d.draw(new Ellipse2D.Double(w * 0.5 - headR, h * 0.12, headR * 2, headR * 2));

            Path2D shoulders = new Path2D.Double();
            shoulders.moveTo(w * 0.15, h * 0.88);
            shoulders.curveTo(w * 0.15, h * 0.62, w * 0.85, h * 0.62, w * 0.85, h * 0.88);
            g2d.draw(shoulders);
        });
    }

    public static Icon getUserSolidIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);

            double headR = w * 0.26;
            g2d.fill(new Ellipse2D.Double(w * 0.5 - headR, h * 0.14, headR * 2, headR * 2));

            Path2D body = new Path2D.Double();
            body.moveTo(w * 0.15, h * 0.88);
            body.curveTo(w * 0.15, h * 0.60, w * 0.85, h * 0.60, w * 0.85, h * 0.88);
            body.closePath();
            g2d.fill(body);
        });
    }

    public static Icon getUserPlusIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);

            double headR = w * 0.23;
            g2d.fill(new Ellipse2D.Double(w * 0.42 - headR, h * 0.14, headR * 2, headR * 2));

            Path2D body = new Path2D.Double();
            body.moveTo(w * 0.10, h * 0.88);
            body.curveTo(w * 0.10, h * 0.60, w * 0.74, h * 0.60, w * 0.74, h * 0.88);
            body.closePath();
            g2d.fill(body);

            g2d.setStroke(new BasicStroke(Math.max(2.0f, size * 0.09f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double px = w * 0.82;
            double py = h * 0.35;
            double arm = w * 0.14;
            g2d.drawLine((int) (px - arm), (int) py, (int) (px + arm), (int) py);
            g2d.drawLine((int) px, (int) (py - arm), (int) px, (int) (py + arm));
        });
    }

    public static Icon getLockIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D shackle = new Path2D.Double();
            shackle.moveTo(w * 0.30, h * 0.48);
            shackle.lineTo(w * 0.30, h * 0.30);
            shackle.curveTo(w * 0.30, h * 0.12, w * 0.70, h * 0.12, w * 0.70, h * 0.30);
            shackle.lineTo(w * 0.70, h * 0.48);
            g2d.draw(shackle);

            RoundRectangle2D body = new RoundRectangle2D.Double(w * 0.20, h * 0.46, w * 0.60, h * 0.44, 4, 4);
            g2d.draw(body);

            g2d.fill(new Ellipse2D.Double(w * 0.46, h * 0.60, w * 0.08, h * 0.08));
            g2d.drawLine((int) (w * 0.5), (int) (h * 0.66), (int) (w * 0.5), (int) (h * 0.74));
        });
    }

    public static Icon getEyeIcon(int size, Color color, boolean open) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.6f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D eye = new Path2D.Double();
            eye.moveTo(w * 0.12, h * 0.50);
            eye.curveTo(w * 0.30, h * 0.24, w * 0.70, h * 0.24, w * 0.88, h * 0.50);
            eye.curveTo(w * 0.70, h * 0.76, w * 0.30, h * 0.76, w * 0.12, h * 0.50);
            eye.closePath();
            g2d.draw(eye);

            double pr = w * 0.12;
            g2d.fill(new Ellipse2D.Double(w * 0.5 - pr, h * 0.5 - pr, pr * 2, pr * 2));

            if (!open) {

                g2d.drawLine((int) (w * 0.20), (int) (h * 0.80), (int) (w * 0.80), (int) (h * 0.20));
            }
        });
    }

    public static Icon getLoginArrowIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(2.0f, size * 0.10f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2d.drawLine((int) (w * 0.25), (int) (h * 0.50), (int) (w * 0.75), (int) (h * 0.50));

            Path2D head = new Path2D.Double();
            head.moveTo(w * 0.55, h * 0.30);
            head.lineTo(w * 0.75, h * 0.50);
            head.lineTo(w * 0.55, h * 0.70);
            g2d.draw(head);
        });
    }

    public static Icon getHomeIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D roof = new Path2D.Double();
            roof.moveTo(w * 0.15, h * 0.50);
            roof.lineTo(w * 0.50, h * 0.18);
            roof.lineTo(w * 0.85, h * 0.50);
            g2d.draw(roof);

            Path2D walls = new Path2D.Double();
            walls.moveTo(w * 0.24, h * 0.44);
            walls.lineTo(w * 0.24, h * 0.82);
            walls.lineTo(w * 0.76, h * 0.82);
            walls.lineTo(w * 0.76, h * 0.44);
            g2d.draw(walls);

            g2d.draw(new Rectangle2D.Double(w * 0.42, h * 0.58, w * 0.16, h * 0.24));
        });
    }

    public static Icon getStudentsIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);

            g2d.fill(new Ellipse2D.Double(w * 0.38, h * 0.18, w * 0.24, h * 0.24));
            Path2D body1 = new Path2D.Double();
            body1.moveTo(w * 0.24, h * 0.82);
            body1.curveTo(w * 0.24, h * 0.54, w * 0.76, h * 0.54, w * 0.76, h * 0.82);
            body1.closePath();
            g2d.fill(body1);

            g2d.fill(new Ellipse2D.Double(w * 0.16, h * 0.26, w * 0.18, h * 0.18));
            Path2D body2 = new Path2D.Double();
            body2.moveTo(w * 0.06, h * 0.80);
            body2.curveTo(w * 0.06, h * 0.60, w * 0.32, h * 0.60, w * 0.32, h * 0.80);
            body2.closePath();
            g2d.fill(body2);

            g2d.fill(new Ellipse2D.Double(w * 0.66, h * 0.26, w * 0.18, h * 0.18));
            Path2D body3 = new Path2D.Double();
            body3.moveTo(w * 0.68, h * 0.80);
            body3.curveTo(w * 0.68, h * 0.60, w * 0.94, h * 0.60, w * 0.94, h * 0.80);
            body3.closePath();
            g2d.fill(body3);
        });
    }

    public static Icon getLogoutIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D door = new Path2D.Double();
            door.moveTo(w * 0.55, h * 0.20);
            door.lineTo(w * 0.20, h * 0.20);
            door.lineTo(w * 0.20, h * 0.80);
            door.lineTo(w * 0.55, h * 0.80);
            g2d.draw(door);

            g2d.drawLine((int) (w * 0.40), (int) (h * 0.50), (int) (w * 0.85), (int) (h * 0.50));
            Path2D arrow = new Path2D.Double();
            arrow.moveTo(w * 0.70, h * 0.35);
            arrow.lineTo(w * 0.85, h * 0.50);
            arrow.lineTo(w * 0.70, h * 0.65);
            g2d.draw(arrow);
        });
    }

    public static Icon getBookIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D left = new Path2D.Double();
            left.moveTo(w * 0.50, h * 0.30);
            left.curveTo(w * 0.35, h * 0.22, w * 0.20, h * 0.24, w * 0.12, h * 0.28);
            left.lineTo(w * 0.12, h * 0.74);
            left.curveTo(w * 0.20, h * 0.70, w * 0.35, h * 0.68, w * 0.50, h * 0.76);
            left.closePath();
            g2d.draw(left);

            Path2D right = new Path2D.Double();
            right.moveTo(w * 0.50, h * 0.30);
            right.curveTo(w * 0.65, h * 0.22, w * 0.80, h * 0.24, w * 0.88, h * 0.28);
            right.lineTo(w * 0.88, h * 0.74);
            right.curveTo(w * 0.80, h * 0.70, w * 0.65, h * 0.68, w * 0.50, h * 0.76);
            right.closePath();
            g2d.draw(right);
        });
    }

    public static Icon getBarChartIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            double barW = w * 0.18;
            double r = 3;

            g2d.fill(new RoundRectangle2D.Double(w * 0.16, h * 0.55, barW, h * 0.35, r, r));

            g2d.fill(new RoundRectangle2D.Double(w * 0.41, h * 0.35, barW, h * 0.55, r, r));

            g2d.fill(new RoundRectangle2D.Double(w * 0.66, h * 0.18, barW, h * 0.72, r, r));
        });
    }

    public static Icon getPlusIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(2.2f, size * 0.12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine((int) (w * 0.22), (int) (h * 0.50), (int) (w * 0.78), (int) (h * 0.50));
            g2d.drawLine((int) (w * 0.50), (int) (h * 0.22), (int) (w * 0.50), (int) (h * 0.78));
        });
    }

    public static Icon getEditIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Path2D pencil = new Path2D.Double();
            pencil.moveTo(w * 0.24, h * 0.76);
            pencil.lineTo(w * 0.20, h * 0.80);
            pencil.lineTo(w * 0.28, h * 0.78);
            pencil.lineTo(w * 0.74, h * 0.32);
            pencil.lineTo(w * 0.68, h * 0.26);
            pencil.closePath();
            g2d.draw(pencil);

            g2d.drawLine((int) (w * 0.60), (int) (h * 0.34), (int) (w * 0.66), (int) (h * 0.40));
        });
    }

    public static Icon getTrashIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2d.drawLine((int) (w * 0.40), (int) (h * 0.18), (int) (w * 0.60), (int) (h * 0.18));
            g2d.drawLine((int) (w * 0.22), (int) (h * 0.28), (int) (w * 0.78), (int) (h * 0.28));

            Path2D bin = new Path2D.Double();
            bin.moveTo(w * 0.28, h * 0.28);
            bin.lineTo(w * 0.32, h * 0.82);
            bin.curveTo(w * 0.32, h * 0.86, w * 0.68, h * 0.86, w * 0.68, h * 0.82);
            bin.lineTo(w * 0.72, h * 0.28);
            g2d.draw(bin);

            g2d.drawLine((int) (w * 0.42), (int) (h * 0.40), (int) (w * 0.42), (int) (h * 0.72));
            g2d.drawLine((int) (w * 0.58), (int) (h * 0.40), (int) (w * 0.58), (int) (h * 0.72));
        });
    }

    public static Icon getRefreshIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.09f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Arc2D arc = new Arc2D.Double(w * 0.20, h * 0.20, w * 0.60, h * 0.60, 45, 270, Arc2D.OPEN);
            g2d.draw(arc);

            Path2D tip = new Path2D.Double();
            tip.moveTo(w * 0.58, h * 0.12);
            tip.lineTo(w * 0.72, h * 0.24);
            tip.lineTo(w * 0.58, h * 0.36);
            g2d.draw(tip);
        });
    }

    public static Icon getSearchIcon(int size, Color color) {
        return new VectorIcon(size, size, (g2d, w, h) -> {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(Math.max(1.8f, size * 0.08f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            double r = w * 0.28;
            g2d.draw(new Ellipse2D.Double(w * 0.16, h * 0.16, r * 2, r * 2));

            g2d.drawLine((int) (w * 0.62), (int) (h * 0.62), (int) (w * 0.84), (int) (h * 0.84));
        });
    }

    @FunctionalInterface
    public interface IconPainter {
        void paint(Graphics2D g2d, int width, int height);
    }

    public static class VectorIcon implements Icon {
        private final int width;
        private final int height;
        private final IconPainter painter;

        public VectorIcon(int width, int height, IconPainter painter) {
            this.width = width;
            this.height = height;
            this.painter = painter;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.translate(x, y);
            painter.paint(g2d, width, height);
            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }
    }
}
