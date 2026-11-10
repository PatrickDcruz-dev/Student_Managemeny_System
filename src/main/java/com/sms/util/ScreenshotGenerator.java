package com.sms.util;

import com.formdev.flatlaf.FlatLightLaf;
import com.sms.model.User;
import com.sms.ui.DashboardFrame;
import com.sms.ui.LoginFrame;
import com.sms.ui.RegisterFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenshotGenerator {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception ignored) {
        }

        File outDir = new File("screenshots");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        SwingUtilities.invokeLater(() -> {
            try {

                LoginFrame login = new LoginFrame();
                login.setSize(880, 540);
                login.addNotify();
                login.validate();
                BufferedImage imgLogin = captureComponent(login.getContentPane(), new File(outDir, "1_login_screen.png"));
                login.dispose();

                RegisterFrame register = new RegisterFrame();
                register.setSize(880, 560);
                register.addNotify();
                register.validate();
                BufferedImage imgRegister = captureComponent(register.getContentPane(), new File(outDir, "2_register_screen.png"));
                register.dispose();

                User demoUser = new User("admin", "");
                DashboardFrame dashboard = new DashboardFrame(demoUser);
                dashboard.setSize(1120, 680);
                dashboard.addNotify();
                dashboard.validate();
                BufferedImage imgDashboard = captureComponent(dashboard.getContentPane(), new File(outDir, "3_dashboard_screen.png"));

                Method switchMethod = DashboardFrame.class.getDeclaredMethod("switchView", String.class);
                switchMethod.setAccessible(true);
                switchMethod.invoke(dashboard, "STUDENTS");

                Field tableField = DashboardFrame.class.getDeclaredField("studentsTable");
                tableField.setAccessible(true);
                JTable studentsTable = (JTable) tableField.get(dashboard);
                if (studentsTable.getRowCount() > 1) {
                    studentsTable.setRowSelectionInterval(1, 1);
                }

                Method selectRowMethod = DashboardFrame.class.getDeclaredMethod("populateFormFromSelectedRow", int.class);
                selectRowMethod.setAccessible(true);
                try {
                    selectRowMethod.invoke(dashboard, 1);
                } catch (Exception ignored) {}

                dashboard.validate();
                dashboard.repaint();
                BufferedImage imgStudents = captureComponent(dashboard.getContentPane(), new File(outDir, "4_student_management_screen.png"));
                dashboard.dispose();

                createCompositeGrid(imgLogin, imgRegister, imgDashboard, imgStudents, new File(outDir, "0_sms_overview_grid.png"));

                System.out.println("All screenshots successfully captured in ./screenshots directory!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static BufferedImage captureComponent(Component component, File file) {
        int w = component.getWidth();
        int h = component.getHeight();
        if (w <= 0 || h <= 0) {
            w = 1120;
            h = 680;
            component.setSize(w, h);
            component.validate();
        }

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        component.paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(img, "png", file);
            System.out.println("Saved: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
        return img;
    }

    private static void createCompositeGrid(BufferedImage img1, BufferedImage img2, BufferedImage img3, BufferedImage img4, File file) {
        int targetW = 900;
        int targetH = 540;

        int pad = 20;
        int totalW = targetW * 2 + pad * 3;
        int totalH = targetH * 2 + pad * 3;

        BufferedImage composite = new BufferedImage(totalW, totalH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = composite.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(15, 23, 42));
        g2d.fillRect(0, 0, totalW, totalH);

        g2d.drawImage(img1, pad, pad, targetW, targetH, null);
        g2d.drawImage(img2, pad * 2 + targetW, pad, targetW, targetH, null);
        g2d.drawImage(img3, pad, pad * 2 + targetH, targetW, targetH, null);
        g2d.drawImage(img4, pad * 2 + targetW, pad * 2 + targetH, targetW, targetH, null);

        g2d.setColor(new Color(51, 65, 85));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(pad, pad, targetW, targetH);
        g2d.drawRect(pad * 2 + targetW, pad, targetW, targetH);
        g2d.drawRect(pad, pad * 2 + targetH, targetW, targetH);
        g2d.drawRect(pad * 2 + targetW, pad * 2 + targetH, targetW, targetH);

        g2d.dispose();

        try {
            ImageIO.write(composite, "png", file);
            System.out.println("Saved Composite Overview: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to save composite: " + e.getMessage());
        }
    }
}
