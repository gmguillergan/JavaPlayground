/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       PillButton.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Custom rounded/pill JButton with Graphics2D antialiasing
 *              for category filters, quick-cash buttons, and primary CTAs.
 * ---------------------------------------------------------
 */
package fiveos.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class PillButton extends JButton {

    private Color backgroundNormal;
    private Color backgroundHover;
    private Color borderColor;
    private Color textColor;
    private int cornerRadius;
    private boolean hovered = false;

    public PillButton(String text, int cornerRadius, Color bgNormal, Color bgHover, Color borderColor, Color textColor) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.backgroundNormal = bgNormal;
        this.backgroundHover = bgHover;
        this.borderColor = borderColor;
        this.textColor = textColor;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 12));
        setMargin(new Insets(6, 16, 6, 16));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    hovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }
        });
    }

    public static PillButton createCategoryPill(String text, boolean active) {
        PillButton btn = new PillButton(
                text,
                28,
                active ? new Color(0xE8, 0xF3, 0xEE) : Color.WHITE,
                active ? new Color(0xD8, 0xEC, 0xE3) : new Color(0xF3, 0xF4, 0xF6),
                active ? new Color(0x13, 0x6F, 0x50) : new Color(0xE5, 0xE7, 0xEB),
                active ? new Color(0x13, 0x6F, 0x50) : new Color(0x11, 0x18, 0x27)
        );
        return btn;
    }

    public static PillButton createPrimaryButton(String text) {
        return new PillButton(
                text,
                12,
                new Color(0x13, 0x6F, 0x50),
                new Color(0x0E, 0x59, 0x40),
                new Color(0x13, 0x6F, 0x50),
                Color.WHITE
        );
    }

    public static PillButton createOutlineButton(String text) {
        return new PillButton(
                text,
                12,
                Color.WHITE,
                new Color(0xF3, 0xF4, 0xF6),
                new Color(0xE5, 0xE7, 0xEB),
                new Color(0x11, 0x18, 0x27)
        );
    }

    public static PillButton createTintedButton(String text) {
        return new PillButton(
                text,
                12,
                new Color(0xE8, 0xF3, 0xEE),
                new Color(0xD8, 0xEC, 0xE3),
                new Color(0x13, 0x6F, 0x50),
                new Color(0x13, 0x6F, 0x50)
        );
    }

    public void setPalette(Color bgNormal, Color bgHover, Color borderColor, Color textColor) {
        this.backgroundNormal = bgNormal;
        this.backgroundHover = bgHover;
        this.borderColor = borderColor;
        this.textColor = textColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int radius = cornerRadius > 0 ? cornerRadius : h;

        Color fill = hovered && isEnabled() ? backgroundHover : backgroundNormal;
        if (!isEnabled()) {
            fill = new Color(0xF3, 0xF4, 0xF6);
        }

        g2.setColor(fill);
        g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);

        if (borderColor != null) {
            g2.setColor(isEnabled() ? borderColor : new Color(0xE5, 0xE7, 0xEB));
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);
        }

        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        String txt = getText();
        int textX = (w - fm.stringWidth(txt)) / 2;
        int textY = (h - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(isEnabled() ? textColor : new Color(0x9C, 0xA3, 0xAF));
        g2.drawString(txt, textX, textY);

        g2.dispose();
    }
}
