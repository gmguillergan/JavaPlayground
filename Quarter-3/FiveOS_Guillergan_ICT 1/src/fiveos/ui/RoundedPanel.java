/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       RoundedPanel.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Custom antialiased rounded JPanel with crisp 1px border.
 * ---------------------------------------------------------
 */
package fiveos.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private Color fillColor;
    private Color borderColor;

    public RoundedPanel(int cornerRadius, Color fillColor, Color borderColor) {
        super();
        this.cornerRadius = cornerRadius;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, int cornerRadius, Color fillColor, Color borderColor) {
        super(layout);
        this.cornerRadius = cornerRadius;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        setOpaque(false);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        }

        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
