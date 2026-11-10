package com.sms;

import com.formdev.flatlaf.FlatLightLaf;
import com.sms.database.DBConnection;
import com.sms.ui.LoginFrame;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        try {
            FlatLightLaf.setup();

            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "FlatLaf could not be initialized, falling back to system look and feel.", e);
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
        }

        try {
            DBConnection.initializeDatabase();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Initial database setup note: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            try {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to launch application UI", e);
                JOptionPane.showMessageDialog(null,
                        "Error launching application: " + e.getMessage(),
                        "Launch Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
