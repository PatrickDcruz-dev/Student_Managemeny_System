package com.sms.ui;

import com.sms.model.User;
import com.sms.service.AuthService;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private final AuthService authService;
    private ModernTextField usernameField;
    private ModernPasswordField passwordField;
    private JLabel errorLabel;
    private ModernButton loginButton;

    public LoginFrame() {
        this.authService = new AuthService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 540);
        setMinimumSize(new Dimension(840, 500));
        setLocationRelativeTo(null);

        setIconImage(createFrameIcon());

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        BrandingPanel brandingPanel = new BrandingPanel();
        mainContainer.add(brandingPanel, BorderLayout.WEST);

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setPreferredSize(new Dimension(380, 440));
        formCard.setMaximumSize(new Dimension(380, 440));

        JLabel avatarLabel = new JLabel(IconHelper.getUserSolidIcon(44, UITheme.PRIMARY_BLUE));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(avatarLabel);

        formCard.add(Box.createVerticalStrut(14));

        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(titleLabel);

        formCard.add(Box.createVerticalStrut(18));

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER_RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(errorLabel);

        formCard.add(Box.createVerticalStrut(10));

        usernameField = new ModernTextField("Username", IconHelper.getUserIcon(18, UITheme.TEXT_MUTED));
        usernameField.setPreferredSize(new Dimension(360, 44));
        usernameField.setMaximumSize(new Dimension(360, 44));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(usernameField);

        formCard.add(Box.createVerticalStrut(16));

        passwordField = new ModernPasswordField("Password");
        passwordField.setPreferredSize(new Dimension(360, 44));
        passwordField.setMaximumSize(new Dimension(360, 44));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(passwordField);

        formCard.add(Box.createVerticalStrut(24));

        loginButton = new ModernButton("  Login", IconHelper.getLoginArrowIcon(18, Color.WHITE));
        loginButton.setPreferredSize(new Dimension(360, 44));
        loginButton.setMaximumSize(new Dimension(360, 44));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> performLogin());
        formCard.add(loginButton);

        KeyAdapter enterSubmit = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };
        usernameField.addKeyListener(enterSubmit);
        passwordField.addKeyListener(enterSubmit);

        formCard.add(Box.createVerticalStrut(24));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel promptLabel = new JLabel("Don't have an account?");
        promptLabel.setFont(UITheme.FONT_BODY);
        promptLabel.setForeground(UITheme.TEXT_SECONDARY);

        JLabel registerLink = new JLabel("Register");
        registerLink.setFont(UITheme.FONT_BODY_BOLD);
        registerLink.setForeground(UITheme.PRIMARY_BLUE);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRegisterScreen();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLink.setText("<html><u>Register</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLink.setText("Register");
            }
        });

        footerPanel.add(promptLabel);
        footerPanel.add(registerLink);
        formCard.add(footerPanel);

        formContainer.add(formCard);
        mainContainer.add(formContainer, BorderLayout.CENTER);

        setContentPane(mainContainer);
    }

    private void performLogin() {
        errorLabel.setText(" ");
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            loginButton.setEnabled(false);
            User user = authService.login(username, password);

            SwingUtilities.invokeLater(() -> {
                DashboardFrame dashboard = new DashboardFrame(user);
                dashboard.setVisible(true);
                dispose();
            });
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (SQLException ex) {
            showError("Database connection failed. Please check MySQL.");
        } finally {
            loginButton.setEnabled(true);
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);

        Toolkit.getDefaultToolkit().beep();
    }

    private void openRegisterScreen() {
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
        dispose();
    }

    private Image createFrameIcon() {
        int s = 32;
        Image img = new java.awt.image.BufferedImage(s, s, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(UITheme.PRIMARY_BLUE);
        g.fillRoundRect(0, 0, s, s, 8, 8);
        IconHelper.getGraduationCapIcon(20, Color.WHITE).paintIcon(null, g, 6, 6);
        g.dispose();
        return img;
    }
}
