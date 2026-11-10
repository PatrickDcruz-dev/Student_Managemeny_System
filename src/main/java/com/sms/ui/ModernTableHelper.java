package com.sms.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ModernTableHelper {

    public static void styleTable(JTable table) {
        table.setRowHeight(38);
        table.setFont(UITheme.FONT_BODY);
        table.setForeground(UITheme.TEXT_PRIMARY);
        table.setSelectionBackground(UITheme.TABLE_SELECTION_BG);
        table.setSelectionForeground(UITheme.TABLE_SELECTION_TEXT);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(UITheme.TABLE_GRID);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setFont(UITheme.FONT_BODY_BOLD);
        header.setForeground(UITheme.TABLE_HEADER_TEXT);
        header.setBackground(UITheme.TABLE_HEADER_BG);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.CARD_BORDER));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : UITheme.BG_MAIN);
                    c.setForeground(UITheme.TEXT_PRIMARY);
                } else {
                    c.setBackground(UITheme.TABLE_SELECTION_BG);
                    c.setForeground(UITheme.TABLE_SELECTION_TEXT);
                }

                String colName = tbl.getColumnName(column).toLowerCase();
                if (colName.equals("id") || colName.equals("year")) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else if (colName.equals("marks")) {
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    if (value instanceof Number num) {
                        setText(String.format("%.2f", num.doubleValue()));
                    }
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });
    }

    public static JScrollPane createModernScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UITheme.CARD_BORDER, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }
}
