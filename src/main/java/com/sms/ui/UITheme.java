package com.sms.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class UITheme {

    public static final Color PRIMARY_BLUE = new Color(26, 86, 219);
    public static final Color PRIMARY_DARK = new Color(30, 58, 138);
    public static final Color BRAND_BG = new Color(21, 84, 192);
    public static final Color BRAND_BG_DARK = new Color(13, 62, 150);

    public static final Color SUCCESS_GREEN = new Color(22, 163, 74);
    public static final Color SUCCESS_HOVER = new Color(21, 128, 61);
    public static final Color INFO_BLUE = new Color(2, 132, 199);
    public static final Color INFO_HOVER = new Color(3, 105, 161);
    public static final Color DANGER_RED = new Color(220, 38, 38);
    public static final Color DANGER_HOVER = new Color(185, 28, 28);
    public static final Color NEUTRAL_BTN_BG = new Color(255, 255, 255);
    public static final Color NEUTRAL_BTN_BORDER = new Color(203, 213, 225);
    public static final Color NEUTRAL_BTN_TEXT = new Color(51, 65, 85);

    public static final Color BG_MAIN = new Color(248, 250, 252);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color CARD_BORDER = new Color(226, 232, 240);
    public static final Color SIDEBAR_BG = new Color(21, 84, 192);
    public static final Color SIDEBAR_ACTIVE = new Color(37, 99, 235);
    public static final Color SIDEBAR_TEXT_MUTED = new Color(191, 219, 254);

    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_MUTED = new Color(148, 163, 184);

    public static final Color INPUT_BORDER = new Color(203, 213, 225);
    public static final Color INPUT_BORDER_FOCUS = new Color(37, 99, 235);
    public static final Color INPUT_BG = Color.WHITE;

    public static final Color TABLE_HEADER_BG = new Color(241, 245, 249);
    public static final Color TABLE_HEADER_TEXT = new Color(71, 85, 105);
    public static final Color TABLE_ROW_ALT = new Color(250, 250, 250);
    public static final Color TABLE_SELECTION_BG = new Color(219, 234, 254);
    public static final Color TABLE_SELECTION_TEXT = new Color(15, 23, 42);
    public static final Color TABLE_GRID = new Color(241, 245, 249);

    public static final Font FONT_TITLE_LARGE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_METRIC = new Font("Segoe UI", Font.BOLD, 24);

    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        );
    }

    public static Border createFieldBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(INPUT_BORDER, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
    }
}
