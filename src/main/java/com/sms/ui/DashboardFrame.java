package com.sms.ui;

import com.sms.model.Student;
import com.sms.model.User;
import com.sms.service.AuthService;
import com.sms.service.StudentService;
import com.sms.service.StudentService.DashboardStats;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class DashboardFrame extends JFrame {

    private final User currentUser;
    private final StudentService studentService;

    private JPanel contentContainer;
    private CardLayout cardLayout;
    private NavButton navDashboardBtn;
    private NavButton navStudentsBtn;

    private JLabel totalStudentsValue;
    private JLabel departmentsValue;
    private JLabel averageMarksValue;
    private JTable recentStudentsTable;
    private DefaultTableModel recentTableModel;

    private JTable studentsTable;
    private DefaultTableModel studentsTableModel;
    private ModernTextField searchField;
    private ModernTextField nameField;
    private ModernTextField emailField;
    private JComboBox<String> departmentCombo;
    private JComboBox<Integer> yearCombo;
    private ModernTextField marksField;
    private int selectedStudentId = -1;

    private ModernButton addBtn;
    private ModernButton updateBtn;
    private ModernButton deleteBtn;
    private ModernButton clearBtn;

    public DashboardFrame(User user) {
        this.currentUser = user != null ? user : new User("Admin", "");
        this.studentService = new StudentService();
        initComponents();
        loadAllData();
    }

    private void initComponents() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 680);
        setMinimumSize(new Dimension(1020, 620));
        setLocationRelativeTo(null);
        setIconImage(createFrameIcon());

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(UITheme.BG_MAIN);

        JPanel sidebar = createSidebar();
        rootPanel.add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentContainer = new JPanel(cardLayout);
        contentContainer.setBackground(UITheme.BG_MAIN);

        JPanel dashboardView = createDashboardView();
        JPanel studentsView = createStudentsView();

        contentContainer.add(dashboardView, "DASHBOARD");
        contentContainer.add(studentsView, "STUDENTS");

        rootPanel.add(contentContainer, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(210, 680));
        sidebar.setBackground(UITheme.SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(20, 14, 20, 14));

        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        brandPanel.setOpaque(false);
        JLabel capLabel = new JLabel(IconHelper.getGraduationCapIcon(28, Color.WHITE));
        JLabel titleLabel = new JLabel("SMS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        brandPanel.add(capLabel);
        brandPanel.add(titleLabel);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setOpaque(false);
        menuPanel.setBorder(new EmptyBorder(28, 0, 0, 0));

        navDashboardBtn = new NavButton("Dashboard", IconHelper.getHomeIcon(18, Color.WHITE), true);
        navStudentsBtn = new NavButton("Students", IconHelper.getStudentsIcon(18, UITheme.SIDEBAR_TEXT_MUTED), false);

        navDashboardBtn.addActionListener(e -> switchView("DASHBOARD"));
        navStudentsBtn.addActionListener(e -> switchView("STUDENTS"));

        menuPanel.add(navDashboardBtn);
        menuPanel.add(Box.createVerticalStrut(8));
        menuPanel.add(navStudentsBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        ModernButton logoutBtn = new ModernButton("  Logout", IconHelper.getLogoutIcon(18, Color.WHITE));
        logoutBtn.setColors(new Color(255, 255, 255, 20), new Color(255, 255, 255, 40), new Color(255, 255, 255, 60));
        logoutBtn.setCustomBorder(new Color(255, 255, 255, 40));
        logoutBtn.setPreferredSize(new Dimension(180, 40));
        logoutBtn.addActionListener(e -> handleLogout());
        bottomPanel.add(logoutBtn, BorderLayout.CENTER);

        JPanel topGroup = new JPanel(new BorderLayout());
        topGroup.setOpaque(false);
        topGroup.add(brandPanel, BorderLayout.NORTH);
        topGroup.add(menuPanel, BorderLayout.CENTER);

        sidebar.add(topGroup, BorderLayout.NORTH);
        sidebar.add(bottomPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private void switchView(String viewName) {
        cardLayout.show(contentContainer, viewName);
        if ("DASHBOARD".equals(viewName)) {
            navDashboardBtn.setActive(true);
            navStudentsBtn.setActive(false);
            refreshDashboardData();
        } else {
            navDashboardBtn.setActive(false);
            navStudentsBtn.setActive(true);
            refreshStudentsData();
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            AuthService.logout();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            dispose();
        }
    }

    private JPanel createDashboardView() {
        JPanel view = new JPanel(new BorderLayout(0, 18));
        view.setBackground(UITheme.BG_MAIN);
        view.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);

        JLabel viewTitle = new JLabel("Dashboard");
        viewTitle.setFont(UITheme.FONT_TITLE);
        viewTitle.setForeground(UITheme.TEXT_PRIMARY);

        String username = (currentUser != null && currentUser.getUsername() != null)
                ? currentUser.getUsername() : "admin";
        JLabel viewSubtitle = new JLabel("Welcome, " + username + "!");
        viewSubtitle.setFont(UITheme.FONT_SUBTITLE);
        viewSubtitle.setForeground(UITheme.TEXT_SECONDARY);

        titleBox.add(viewTitle);
        titleBox.add(Box.createVerticalStrut(3));
        titleBox.add(viewSubtitle);

        JLabel userAvatar = new JLabel(IconHelper.getUserSolidIcon(34, UITheme.TEXT_PRIMARY));
        JPanel userHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        userHeader.setOpaque(false);
        userHeader.add(userAvatar);
        userHeader.add(titleBox);

        headerPanel.add(userHeader, BorderLayout.WEST);
        view.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 18));
        centerPanel.setOpaque(false);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 18, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(800, 95));

        totalStudentsValue = new JLabel("0");
        JPanel card1 = createStatCard(
                "Total Students",
                totalStudentsValue,
                IconHelper.getStudentsIcon(22, UITheme.PRIMARY_BLUE),
                new Color(239, 246, 255)
        );

        departmentsValue = new JLabel("0");
        JPanel card2 = createStatCard(
                "Departments",
                departmentsValue,
                IconHelper.getBookIcon(22, UITheme.SUCCESS_GREEN),
                new Color(240, 253, 244)
        );

        averageMarksValue = new JLabel("0.00");
        JPanel card3 = createStatCard(
                "Average Marks",
                averageMarksValue,
                IconHelper.getBarChartIcon(22, new Color(5, 150, 105)),
                new Color(236, 253, 245)
        );

        statsRow.add(card1);
        statsRow.add(card2);
        statsRow.add(card3);
        centerPanel.add(statsRow, BorderLayout.NORTH);

        CardPanel tableCard = new CardPanel(new BorderLayout(0, 12));
        tableCard.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel sectionTitle = new JLabel("Recent Students");
        sectionTitle.setFont(UITheme.FONT_HEADING);
        sectionTitle.setForeground(UITheme.TEXT_PRIMARY);
        tableCard.add(sectionTitle, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Department", "Year", "Marks"};
        recentTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recentStudentsTable = new JTable(recentTableModel);
        ModernTableHelper.styleTable(recentStudentsTable);

        recentStudentsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        recentStudentsTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        recentStudentsTable.getColumnModel().getColumn(2).setPreferredWidth(140);
        recentStudentsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        recentStudentsTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = ModernTableHelper.createModernScrollPane(recentStudentsTable);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(tableCard, BorderLayout.CENTER);
        view.add(centerPanel, BorderLayout.CENTER);

        return view;
    }

    private JPanel createStatCard(String labelText, JLabel valueLabel, Icon icon, Color iconBg) {
        CardPanel card = new CardPanel(new BorderLayout(14, 0));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel iconContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(iconBg);
                g2d.fillOval(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        iconContainer.setOpaque(false);
        iconContainer.setPreferredSize(new Dimension(48, 48));
        iconContainer.setLayout(new GridBagLayout());
        iconContainer.add(new JLabel(icon));

        JPanel textBox = new JPanel();
        textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
        textBox.setOpaque(false);

        JLabel title = new JLabel(labelText);
        title.setFont(UITheme.FONT_SMALL);
        title.setForeground(UITheme.TEXT_SECONDARY);

        valueLabel.setFont(UITheme.FONT_METRIC);
        valueLabel.setForeground(UITheme.TEXT_PRIMARY);

        textBox.add(Box.createVerticalGlue());
        textBox.add(title);
        textBox.add(Box.createVerticalStrut(2));
        textBox.add(valueLabel);
        textBox.add(Box.createVerticalGlue());

        card.add(iconContainer, BorderLayout.WEST);
        card.add(textBox, BorderLayout.CENTER);

        return card;
    }

    private JPanel createStudentsView() {
        JPanel view = new JPanel(new BorderLayout(0, 16));
        view.setBackground(UITheme.BG_MAIN);
        view.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel headerPanel = new JPanel(new BorderLayout(16, 0));
        headerPanel.setOpaque(false);

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);

        JLabel viewTitle = new JLabel("Student Management");
        viewTitle.setFont(UITheme.FONT_TITLE);
        viewTitle.setForeground(UITheme.TEXT_PRIMARY);

        JLabel viewSubtitle = new JLabel("Add, update, delete and search student records.");
        viewSubtitle.setFont(UITheme.FONT_SUBTITLE);
        viewSubtitle.setForeground(UITheme.TEXT_SECONDARY);

        titleBox.add(viewTitle);
        titleBox.add(Box.createVerticalStrut(3));
        titleBox.add(viewSubtitle);

        JLabel studentsHeaderIcon = new JLabel(IconHelper.getStudentsIcon(30, UITheme.PRIMARY_BLUE));
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        leftHeader.setOpaque(false);
        leftHeader.add(studentsHeaderIcon);
        leftHeader.add(titleBox);

        searchField = new ModernTextField("Search by ID or name...", IconHelper.getSearchIcon(16, UITheme.TEXT_MUTED));
        searchField.setPreferredSize(new Dimension(260, 38));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });

        headerPanel.add(leftHeader, BorderLayout.WEST);
        headerPanel.add(searchField, BorderLayout.EAST);
        view.add(headerPanel, BorderLayout.NORTH);

        JPanel splitPanel = new JPanel(new BorderLayout(18, 0));
        splitPanel.setOpaque(false);

        CardPanel detailsCard = createStudentDetailsForm();
        splitPanel.add(detailsCard, BorderLayout.WEST);

        CardPanel recordsCard = createStudentRecordsTable();
        splitPanel.add(recordsCard, BorderLayout.CENTER);

        view.add(splitPanel, BorderLayout.CENTER);
        return view;
    }

    private CardPanel createStudentDetailsForm() {
        CardPanel card = new CardPanel(new BorderLayout(0, 14));
        card.setPreferredSize(new Dimension(380, 500));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));

        JLabel title = new JLabel("Student Details");
        title.setFont(UITheme.FONT_HEADING);
        title.setForeground(UITheme.TEXT_PRIMARY);
        card.add(title, BorderLayout.NORTH);

        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 4, 10, 4);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.32;
        JLabel nameLbl = new JLabel("Name *");
        nameLbl.setFont(UITheme.FONT_BODY_BOLD);
        formGrid.add(nameLbl, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.68;
        nameField = new ModernTextField();
        nameField.setPreferredSize(new Dimension(210, 36));
        formGrid.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.32;
        JLabel emailLbl = new JLabel("Email *");
        emailLbl.setFont(UITheme.FONT_BODY_BOLD);
        formGrid.add(emailLbl, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.68;
        emailField = new ModernTextField();
        emailField.setPreferredSize(new Dimension(210, 36));
        formGrid.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.32;
        JLabel deptLbl = new JLabel("Department *");
        deptLbl.setFont(UITheme.FONT_BODY_BOLD);
        formGrid.add(deptLbl, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.68;
        String[] depts = {"CSE", "ECE", "IT", "ME", "Civil", "AI & DS", "EEE"};
        departmentCombo = new JComboBox<>(depts);
        departmentCombo.setFont(UITheme.FONT_BODY);
        departmentCombo.setBackground(Color.WHITE);
        departmentCombo.setPreferredSize(new Dimension(210, 36));
        formGrid.add(departmentCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.32;
        JLabel yearLbl = new JLabel("Year *");
        yearLbl.setFont(UITheme.FONT_BODY_BOLD);
        formGrid.add(yearLbl, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.68;
        Integer[] years = {1, 2, 3, 4};
        yearCombo = new JComboBox<>(years);
        yearCombo.setFont(UITheme.FONT_BODY);
        yearCombo.setBackground(Color.WHITE);
        yearCombo.setPreferredSize(new Dimension(210, 36));
        formGrid.add(yearCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.32;
        JLabel marksLbl = new JLabel("Marks *");
        marksLbl.setFont(UITheme.FONT_BODY_BOLD);
        formGrid.add(marksLbl, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.68;
        marksField = new ModernTextField();
        marksField.setPreferredSize(new Dimension(210, 36));
        formGrid.add(marksField, gbc);

        card.add(formGrid, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 8, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setPreferredSize(new Dimension(340, 42));

        addBtn = new ModernButton(" Add", IconHelper.getPlusIcon(13, Color.WHITE));
        addBtn.setBackground(UITheme.SUCCESS_GREEN);
        addBtn.setColors(UITheme.SUCCESS_GREEN, UITheme.SUCCESS_HOVER, UITheme.SUCCESS_HOVER.darker());
        addBtn.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
        addBtn.addActionListener(e -> handleAddStudent());

        updateBtn = new ModernButton(" Update", IconHelper.getEditIcon(13, Color.WHITE));
        updateBtn.setColors(UITheme.INFO_BLUE, UITheme.INFO_HOVER, UITheme.INFO_HOVER.darker());
        updateBtn.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
        updateBtn.addActionListener(e -> handleUpdateStudent());

        deleteBtn = new ModernButton(" Delete", IconHelper.getTrashIcon(13, Color.WHITE));
        deleteBtn.setColors(UITheme.DANGER_RED, UITheme.DANGER_HOVER, UITheme.DANGER_HOVER.darker());
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
        deleteBtn.addActionListener(e -> handleDeleteStudent());

        clearBtn = new ModernButton(" Clear", UITheme.NEUTRAL_BTN_BG, UITheme.NEUTRAL_BTN_TEXT);
        clearBtn.setIcon(IconHelper.getRefreshIcon(13, UITheme.NEUTRAL_BTN_TEXT));
        clearBtn.setCustomBorder(UITheme.NEUTRAL_BTN_BORDER);
        clearBtn.setBorder(BorderFactory.createEmptyBorder(8, 2, 8, 2));
        clearBtn.addActionListener(e -> clearForm());

        buttonsPanel.add(addBtn);
        buttonsPanel.add(updateBtn);
        buttonsPanel.add(deleteBtn);
        buttonsPanel.add(clearBtn);

        card.add(buttonsPanel, BorderLayout.SOUTH);

        return card;
    }

    private CardPanel createStudentRecordsTable() {
        CardPanel card = new CardPanel(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel title = new JLabel("Student Records");
        title.setFont(UITheme.FONT_HEADING);
        title.setForeground(UITheme.TEXT_PRIMARY);
        card.add(title, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Email", "Department", "Year", "Marks"};
        studentsTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentsTable = new JTable(studentsTableModel);
        ModernTableHelper.styleTable(studentsTable);

        studentsTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        studentsTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        studentsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        studentsTable.getColumnModel().getColumn(3).setPreferredWidth(85);
        studentsTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        studentsTable.getColumnModel().getColumn(5).setPreferredWidth(65);

        studentsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFormFromSelectedRow(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = ModernTableHelper.createModernScrollPane(studentsTable);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private void loadAllData() {
        refreshDashboardData();
        refreshStudentsData();
    }

    private void refreshDashboardData() {
        try {
            DashboardStats stats = studentService.getDashboardStats();
            totalStudentsValue.setText(String.valueOf(stats.totalStudents()));
            departmentsValue.setText(String.valueOf(stats.departmentCount()));
            averageMarksValue.setText(String.format("%.2f", stats.averageMarks()));

            List<Student> recentStudents = studentService.getRecentStudents(5);
            recentTableModel.setRowCount(0);
            for (Student s : recentStudents) {
                recentTableModel.addRow(new Object[]{
                        s.getStudentId(),
                        s.getName(),
                        s.getDepartment(),
                        s.getYear(),
                        s.getMarks()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database error while loading dashboard: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshStudentsData() {
        try {
            List<Student> list = studentService.getAllStudents();
            displayStudentsInTable(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database error while loading students: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayStudentsInTable(List<Student> students) {
        studentsTableModel.setRowCount(0);
        for (Student s : students) {
            studentsTableModel.addRow(new Object[]{
                    s.getStudentId(),
                    s.getName(),
                    s.getEmail(),
                    s.getDepartment(),
                    s.getYear(),
                    s.getMarks()
            });
        }
    }

    private void populateFormFromSelectedRow(int row) {
        try {
            selectedStudentId = (int) studentsTableModel.getValueAt(row, 0);
            nameField.setText((String) studentsTableModel.getValueAt(row, 1));
            emailField.setText((String) studentsTableModel.getValueAt(row, 2));

            String dept = (String) studentsTableModel.getValueAt(row, 3);
            departmentCombo.setSelectedItem(dept);

            Integer year = (Integer) studentsTableModel.getValueAt(row, 4);
            yearCombo.setSelectedItem(year);

            Double marks = (Double) studentsTableModel.getValueAt(row, 5);
            marksField.setText(String.format("%.2f", marks));
        } catch (Exception ex) {

        }
    }

    private void performSearch() {
        String keyword = searchField.getText();
        try {
            List<Student> searchResults = studentService.searchStudents(keyword);
            displayStudentsInTable(searchResults);
        } catch (SQLException e) {

        }
    }

    private void handleAddStudent() {
        try {
            Student student = parseStudentFromForm();
            boolean added = studentService.addStudent(student);

            if (added) {
                JOptionPane.showMessageDialog(this,
                        "Student added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshStudentsData();
                refreshDashboardData();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateStudent() {
        if (selectedStudentId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student from the table to update.",
                    "No Student Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Student student = parseStudentFromForm();
            student.setStudentId(selectedStudentId);

            boolean updated = studentService.updateStudent(student);
            if (updated) {
                JOptionPane.showMessageDialog(this,
                        "Student record updated successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshStudentsData();
                refreshDashboardData();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteStudent() {
        if (selectedStudentId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student from the table to delete.",
                    "No Student Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = nameField.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete student: " + name + " (ID: " + selectedStudentId + ")?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = studentService.deleteStudent(selectedStudentId);
                if (deleted) {
                    JOptionPane.showMessageDialog(this,
                            "Student deleted successfully.", "Deleted",
                            JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                    refreshStudentsData();
                    refreshDashboardData();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        selectedStudentId = -1;
        nameField.setText("");
        emailField.setText("");
        departmentCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        marksField.setText("");
        studentsTable.clearSelection();
    }

    private Student parseStudentFromForm() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dept = (String) departmentCombo.getSelectedItem();
        Integer year = (Integer) yearCombo.getSelectedItem();

        String marksStr = marksField.getText().trim();
        if (marksStr.isEmpty()) {
            throw new IllegalArgumentException("Marks cannot be empty.");
        }

        double marks;
        try {
            marks = Double.parseDouble(marksStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Marks must be a valid numeric value (e.g. 85.50).");
        }

        return new Student(name, email, dept, (year != null ? year : 1), marks);
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

    private static class NavButton extends JButton {
        private boolean active;
        private final Icon icon;
        private final String labelText;

        public NavButton(String text, Icon icon, boolean active) {
            this.labelText = text;
            this.icon = icon;
            this.active = active;

            setFont(UITheme.FONT_BUTTON);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(180, 42));
            setMaximumSize(new Dimension(180, 42));
        }

        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            if (active) {

                g2d.setColor(UITheme.SIDEBAR_ACTIVE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, w, h, 10, 10));
                g2d.setColor(Color.WHITE);
            } else {
                g2d.setColor(UITheme.SIDEBAR_TEXT_MUTED);
            }

            int iconY = (h - icon.getIconHeight()) / 2;
            icon.paintIcon(this, g2d, 16, iconY);

            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textY = (h - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(labelText, 48, textY);

            g2d.dispose();
        }
    }
}
