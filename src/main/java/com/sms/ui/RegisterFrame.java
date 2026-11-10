package com.sms.ui;

import com.sms.service.AuthService;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;

public class RegisterFrame extends JFrame {

    private final AuthService authService;
    private ModernTextField usernameField;
    private ModernPasswordField passwordField;
    private ModernPasswordField confirmPasswordField;
    private JLabel errorLabel;
    private ModernButton registerButton;

    public RegisterFrame() {
        this.authService = new AuthService();
        initComponents();
    }

    private void initComponents() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(880, 560);
        setMinimumSize(new Dimension(840, 520));
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
        formCard.setPreferredSize(new Dimension(380, 480));
        formCard.setMaximumSize(new Dimension(380, 480));

        JLabel avatarLabel = new JLabel(IconHelper.getUserPlusIcon(44, UITheme.PRIMARY_BLUE));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(avatarLabel);

        formCard.add(Box.createVerticalStrut(12));

        JLabel titleLabel = new JLabel("Create a New Account");
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(titleLabel);

        formCard.add(Box.createVerticalStrut(12));

        errorLabel = new JLabel(" ");
        errorLabel.setFont(UITheme.FONT_SMALL);
        errorLabel.setForeground(UITheme.DANGER_RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(errorLabel);

        formCard.add(Box.createVerticalStrut(10));

        usernameField = new ModernTextField("Username", IconHelper.getUserIcon(18, UITheme.TEXT_MUTED));
        usernameField.setPreferredSize(new Dimension(360, 42));
        usernameField.setMaximumSize(new Dimension(360, 42));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(usernameField);

        formCard.add(Box.createVerticalStrut(14));

        passwordField = new ModernPasswordField("Password");
        passwordField.setPreferredSize(new Dimension(360, 42));
        passwordField.setMaximumSize(new Dimension(360, 42));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(passwordField);

        formCard.add(Box.createVerticalStrut(14));

        confirmPasswordField = new ModernPasswordField("Confirm Password");
        confirmPasswordField.setPreferredSize(new Dimension(360, 42));
        confirmPasswordField.setMaximumSize(new Dimension(360, 42));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formCard.add(confirmPasswordField);

        formCard.add(Box.createVerticalStrut(22));

        registerButton = new ModernButton("  Register", IconHelper.getUserPlusIcon(18, Color.WHITE));
        registerButton.setPreferredSize(new Dimension(360, 44));
        registerButton.setMaximumSize(new Dimension(360, 44));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(e -> performRegister());
        formCard.add(registerButton);

        KeyAdapter enterSubmit = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performRegister();
                }
            }
        };
        usernameField.addKeyListener(enterSubmit);
        passwordField.addKeyListener(enterSubmit);
        confirmPasswordField.addKeyListener(enterSubmit);

        formCard.add(Box.createVerticalStrut(22));

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel promptLabel = new JLabel("Already have an account?");
        promptLabel.setFont(UITheme.FONT_BODY);
        promptLabel.setForeground(UITheme.TEXT_SECONDARY);

        JLabel loginLink = new JLabel("Login");
        loginLink.setFont(UITheme.FONT_BODY_BOLD);
        loginLink.setForeground(UITheme.PRIMARY_BLUE);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openLoginScreen();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setText("<html><u>Login</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setText("Login");
            }
        });

        footerPanel.add(promptLabel);
        footerPanel.add(loginLink);
        formCard.add(footerPanel);

        formContainer.add(formCard);
        mainContainer.add(formContainer, BorderLayout.CENTER);

        setContentPane(mainContainer);
    }

    private void performRegister() {
        errorLabel.setText(" ");
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        try {
            registerButton.setEnabled(false);
            authService.register(username, password, confirmPassword);

            JOptionPane.showMessageDialog(
                    this,
                    "Account registered successfully! You can now log in.",
                    "Registration Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            openLoginScreen();
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
            Toolkit.getDefaultToolkit().beep();
        } catch (SQLException ex) {
            errorLabel.setText("Database error: Could not complete registration.");
        } finally {
            registerButton.setEnabled(true);
        }
    }

    private void openLoginScreen() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
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
