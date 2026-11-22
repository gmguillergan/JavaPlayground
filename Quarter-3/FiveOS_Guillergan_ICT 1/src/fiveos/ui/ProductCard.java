/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       ProductCard.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Minimalist Loyverse/Square-inspired product card panel
 *              showing item name, category, emerald price, and badges.
 * ---------------------------------------------------------
 */
package fiveos.ui;

import fiveos.model.Product;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductCard extends RoundedPanel {

    public interface ProductCardListener {
        void onProductSelected(Product product, boolean doubleClicked);
    }

    private static final Color BG_NORMAL = Color.WHITE;
    private static final Color BG_HOVER = new Color(0xF9, 0xFA, 0xFB);
    private static final Color BG_SELECTED = new Color(0xF2, 0xF9, 0xF6);
    private static final Color BORDER_NORMAL = new Color(0xE5, 0xE7, 0xEB);
    private static final Color BORDER_SELECTED = new Color(0x13, 0x6F, 0x50);
    private static final Color TEXT_PRIMARY = new Color(0x11, 0x18, 0x27);
    private static final Color TEXT_MUTED = new Color(0x6B, 0x72, 0x80);
    private static final Color ACCENT_GREEN = new Color(0x13, 0x6F, 0x50);
    private static final Color BADGE_BG = new Color(0xE8, 0xF3, 0xEE);

    private final Product product;
    private boolean selected = false;

    public ProductCard(final Product product, final ProductCardListener listener) {
        super(new BorderLayout(0, 8), 16, BG_NORMAL, BORDER_NORMAL);
        this.product = product;
        setBorder(new EmptyBorder(14, 16, 14, 16));
        setPreferredSize(new Dimension(210, 128));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Top section: Product Name & Category
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(TEXT_PRIMARY);

        JLabel categoryLabel = new JLabel(product.getCategory() + " • " + product.getId());
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        categoryLabel.setForeground(TEXT_MUTED);

        topPanel.add(nameLabel);
        topPanel.add(Box.createVerticalStrut(3));
        topPanel.add(categoryLabel);

        // Bottom section: Price & Badges
        JPanel bottomPanel = new JPanel(new BorderLayout(6, 0));
        bottomPanel.setOpaque(false);

        JLabel priceLabel = new JLabel(String.format("\u20B1%.2f", product.getPrice()));
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setForeground(ACCENT_GREEN);

        JPanel badgeFlow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        badgeFlow.setOpaque(false);

        if (product.isBestSeller()) {
            RoundedPanel badge = new RoundedPanel(new BorderLayout(), 10, BADGE_BG, null);
            badge.setBorder(new EmptyBorder(3, 8, 3, 8));
            JLabel badgeText = new JLabel("Best seller");
            badgeText.setFont(new Font("SansSerif", Font.BOLD, 11));
            badgeText.setForeground(ACCENT_GREEN);
            badge.add(badgeText, BorderLayout.CENTER);
            badgeFlow.add(badge);
        } else if (product.isCustomizable()) {
            JLabel customText = new JLabel("Customizable");
            customText.setFont(new Font("SansSerif", Font.PLAIN, 11));
            customText.setForeground(TEXT_MUTED);
            badgeFlow.add(customText);
        }

        bottomPanel.add(priceLabel, BorderLayout.WEST);
        bottomPanel.add(badgeFlow, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    setFillColor(BG_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    setFillColor(BG_NORMAL);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onProductSelected(product, e.getClickCount() >= 2);
                }
            }
        };

        addMouseListener(mouseHandler);
    }

    public Product getProduct() {
        return product;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setFillColor(selected ? BG_SELECTED : BG_NORMAL);
        setBorderColor(selected ? BORDER_SELECTED : BORDER_NORMAL);
    }
}
