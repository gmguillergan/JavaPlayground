/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       FiveOS_POS_Guillergan.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Main JFrame Point of Sale (POS) interface for
 *              FiveOS — 39ers Refreshment Kiosk (Molino 1 Branch).
 *              Includes NetBeans 8.2 GUI Builder (.form) support.
 * ---------------------------------------------------------
 */
package fiveos.ui;
import myloginsystem_guillergan.Guillergan_LoginUI_ICT1201;

import fiveos.model.CartItem;
import fiveos.model.Product;
import fiveos.service.BillingService;
import fiveos.service.BillingService.BillSummary;
import fiveos.service.MenuRepository;
import fiveos.service.ReceiptFormatter;
import fiveos.ui.ProductCard.ProductCardListener;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class FiveOS_POS_Guillergan extends javax.swing.JFrame {

    // Strict Visual Design System Colors
    private static final Color CANVAS_BG = new Color(0xF8, 0xF9, 0xFA);
    private static final Color CARD_SURFACE = new Color(0xFF, 0xFF, 0xFF);
    private static final Color BORDER_LIGHT = new Color(0xE5, 0xE7, 0xEB);
    private static final Color PRIMARY_ACCENT = new Color(0x13, 0x6F, 0x50);
    private static final Color TINT_ACCENT = new Color(0xE8, 0xF3, 0xEE);
    private static final Color TEXT_PRIMARY = new Color(0x11, 0x18, 0x27);
    private static final Color TEXT_SECONDARY = new Color(0x6B, 0x72, 0x80);

    private static final String CARD_CART_VIEW = "CART_VIEW";
    private static final String CARD_RECEIPT_VIEW = "RECEIPT_VIEW";

    // Services and State
    private final MenuRepository menuRepository;
    private final BillingService billingService;
    private final ReceiptFormatter receiptFormatter;
    private final List<CartItem> cartItems;
    private final List<ProductCard> productCards;

    private String activeCategory = "All";
    private Product selectedProduct;
    private int orderSequence = 101;
    private boolean syncingComboBox = false;
    private boolean totalComputed = false;
    private DefaultTableModel cartTableModel;

    public FiveOS_POS_Guillergan() {
        this.menuRepository = new MenuRepository();
        this.billingService = new BillingService();
        this.receiptFormatter = new ReceiptFormatter();
        this.cartItems = new ArrayList<CartItem>();
        this.productCards = new ArrayList<ProductCard>();

        initComponents();
        initCustomPosSetup();
        refreshProductGrid();
        resetBillingDisplay();
    }

    private void initCustomPosSetup() {
        getContentPane().setBackground(CANVAS_BG);

        // Apply crisp 1px borders and clean flat tablet POS styling
        headerBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_LIGHT),
                new EmptyBorder(12, 22, 12, 22)
        ));
        filterCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        searchBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        orderEntryBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(12, 16, 12, 16)
        ));
        sidebarCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        summaryBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        paymentBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        quantityField.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        cashTenderedField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                new EmptyBorder(4, 8, 4, 8)
        ));
        tableScroll.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        tableScroll.getViewport().setBackground(CARD_SURFACE);
        receiptScroll.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        gridScrollPane.getViewport().setBackground(CANVAS_BG);
        gridScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Style Buttons consistently across Cross-Platform/System LAFs
        stylePillButton(catAllBtn, true);
        stylePillButton(catMilkTeaBtn, false);
        stylePillButton(catFruitTeaBtn, false);
        stylePillButton(catSnacksBtn, false);

        stylePillButton(tabCartButton, true);
        stylePillButton(tabReceiptButton, false);

        styleOutlineButton(minusBtn);
        styleOutlineButton(plusBtn);
        stylePrimaryButton(addToOrderBtn);
        styleOutlineButton(clearResetBtn);
        styleOutlineButton(removeBtn);

        stylePillButton(exactPillBtn, true);
        styleOutlineButton(cash50Btn);
        styleOutlineButton(cash100Btn);
        styleOutlineButton(cash500Btn);

        stylePillButton(computeTotalBtn, true);
        stylePrimaryButton(generateReceiptBtn);
        styleOutlineButton(backToSaleBtn);
        stylePrimaryButton(newOrderBtn);

        // Populate Product JComboBox
        DefaultComboBoxModel<Product> comboModel = new DefaultComboBoxModel<Product>();
        List<Product> allProducts = menuRepository.getAllProducts();
        for (Product p : allProducts) {
            comboModel.addElement(p);
        }
        syncingComboBox = true;
        productComboBox.setModel(comboModel);
        syncingComboBox = false;

        if (!allProducts.isEmpty()) {
            selectedProduct = allProducts.get(0);
            updateSelectedProductLabel();
        }

        // Configure Search Live Filtering
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshProductGrid();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshProductGrid();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshProductGrid();
            }
        });

        // Configure Cart JTable
        cartTableModel = (DefaultTableModel) cartTable.getModel();
        cartTable.setGridColor(BORDER_LIGHT);
        cartTable.setShowVerticalLines(false);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.setSelectionBackground(TINT_ACCENT);
        cartTable.setSelectionForeground(PRIMARY_ACCENT);

        JTableHeader header = cartTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(CANVAS_BG);
        header.setForeground(TEXT_SECONDARY);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        cartTable.getColumnModel().getColumn(0).setPreferredWidth(165);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(65);
        cartTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(45);
        cartTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        cartTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
    }

    private void stylePillButton(JButton btn, boolean active) {
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (active) {
            btn.setBackground(TINT_ACCENT);
            btn.setForeground(PRIMARY_ACCENT);
            btn.setBorder(BorderFactory.createLineBorder(PRIMARY_ACCENT, 1));
        } else {
            btn.setBackground(CARD_SURFACE);
            btn.setForeground(TEXT_PRIMARY);
            btn.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        }
    }

    private void stylePrimaryButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(PRIMARY_ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(PRIMARY_ACCENT, 1));
    }

    private void styleOutlineButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(CARD_SURFACE);
        btn.setForeground(TEXT_PRIMARY);
        btn.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerBar = new javax.swing.JPanel();
        brandPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        titleStack = new javax.swing.JPanel();
        storeNameLabel = new javax.swing.JLabel();
        taglineLabel = new javax.swing.JLabel();
        statusWrapper = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JButton();
        cashierStatusLabel = new javax.swing.JLabel();
        mainContent = new javax.swing.JPanel();
        leftCatalogPanel = new javax.swing.JPanel();
        filterCard = new javax.swing.JPanel();
        searchAndComboRow = new javax.swing.JPanel();
        searchBox = new javax.swing.JPanel();
        searchIcon = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        productComboBox = new javax.swing.JComboBox<>();
        categoryRow = new javax.swing.JPanel();
        catAllBtn = new javax.swing.JButton();
        catMilkTeaBtn = new javax.swing.JButton();
        catFruitTeaBtn = new javax.swing.JButton();
        catSnacksBtn = new javax.swing.JButton();
        gridScrollPane = new javax.swing.JScrollPane();
        gridAnchor = new javax.swing.JPanel();
        productGridPanel = new javax.swing.JPanel();
        orderEntryBar = new javax.swing.JPanel();
        selectedInfoPanel = new javax.swing.JPanel();
        titleHint = new javax.swing.JLabel();
        selectedProductLabel = new javax.swing.JLabel();
        controlsFlow = new javax.swing.JPanel();
        qtyCaption = new javax.swing.JLabel();
        minusBtn = new javax.swing.JButton();
        quantityField = new javax.swing.JTextField();
        plusBtn = new javax.swing.JButton();
        addToOrderBtn = new javax.swing.JButton();
        clearResetBtn = new javax.swing.JButton();
        sidebarCard = new javax.swing.JPanel();
        topHeader = new javax.swing.JPanel();
        saleTitle = new javax.swing.JLabel();
        segmentedToggle = new javax.swing.JPanel();
        tabCartButton = new javax.swing.JButton();
        tabReceiptButton = new javax.swing.JButton();
        rightCardContainer = new javax.swing.JPanel();
        cartAndBillingView = new javax.swing.JPanel();
        tableSection = new javax.swing.JPanel();
        tableScroll = new javax.swing.JScrollPane();
        cartTable = new javax.swing.JTable();
        cartActionsRow = new javax.swing.JPanel();
        cartHintLabel = new javax.swing.JLabel();
        removeBtn = new javax.swing.JButton();
        billingPanel = new javax.swing.JPanel();
        summaryBox = new javax.swing.JPanel();
        subtotalRow = new javax.swing.JPanel();
        subtotalLabel = new javax.swing.JLabel();
        subtotalValueLabel = new javax.swing.JLabel();
        vatRow = new javax.swing.JPanel();
        vatLabel = new javax.swing.JLabel();
        vatValueLabel = new javax.swing.JLabel();
        discountRow = new javax.swing.JPanel();
        seniorDiscountCheckBox = new javax.swing.JCheckBox();
        discountValueLabel = new javax.swing.JLabel();
        totalBanner = new javax.swing.JPanel();
        totalCaption = new javax.swing.JLabel();
        totalDueValueLabel = new javax.swing.JLabel();
        paymentBox = new javax.swing.JPanel();
        cashInputRow = new javax.swing.JPanel();
        cashLabel = new javax.swing.JLabel();
        cashTenderedField = new javax.swing.JTextField();
        quickCashRow = new javax.swing.JPanel();
        exactPillBtn = new javax.swing.JButton();
        cash50Btn = new javax.swing.JButton();
        cash100Btn = new javax.swing.JButton();
        cash500Btn = new javax.swing.JButton();
        changeRow = new javax.swing.JPanel();
        changeCaption = new javax.swing.JLabel();
        changeDueValueLabel = new javax.swing.JLabel();
        actionButtonsRow = new javax.swing.JPanel();
        computeTotalBtn = new javax.swing.JButton();
        generateReceiptBtn = new javax.swing.JButton();
        receiptDisplayView = new javax.swing.JPanel();
        receiptInfoLabel = new javax.swing.JLabel();
        receiptScroll = new javax.swing.JScrollPane();
        receiptTextArea = new javax.swing.JTextArea();
        bottomActions = new javax.swing.JPanel();
        backToSaleBtn = new javax.swing.JButton();
        newOrderBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FiveOS");
        setBackground(new java.awt.Color(248, 249, 250));
        setMinimumSize(new java.awt.Dimension(1180, 740));
        setPreferredSize(new java.awt.Dimension(1360, 820));

        headerBar.setBackground(new java.awt.Color(255, 255, 255));
        headerBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 22, 12, 22));
        headerBar.setLayout(new java.awt.BorderLayout(16, 0));

        brandPanel.setOpaque(false);
        brandPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));

        logoLabel.setBackground(new java.awt.Color(255, 255, 255));
        logoLabel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        logoLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fiveos/assets/39ers-3.png"))); // NOI18N
        logoLabel.setOpaque(true);
        brandPanel.add(logoLabel);

        titleStack.setOpaque(false);
        titleStack.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        storeNameLabel.setFont(new java.awt.Font("JetBrainsMonoNL NFM ExtraBold", 1, 17)); // NOI18N
        storeNameLabel.setForeground(new java.awt.Color(17, 24, 39));
        storeNameLabel.setText("39ers Molino 1");
        titleStack.add(storeNameLabel);

        taglineLabel.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        taglineLabel.setForeground(new java.awt.Color(107, 114, 128));
        taglineLabel.setText("Where every sip feels like home.");
        titleStack.add(taglineLabel);

        brandPanel.add(titleStack);

        headerBar.add(brandPanel, java.awt.BorderLayout.WEST);

        statusWrapper.setOpaque(false);
        statusWrapper.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 4));

        logoutBtn.setBackground(new java.awt.Color(255, 255, 255));
        logoutBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        logoutBtn.setForeground(new java.awt.Color(17, 24, 39));
        logoutBtn.setText("Log out");
        logoutBtn.setPreferredSize(new java.awt.Dimension(114, 36));
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        statusWrapper.add(logoutBtn);

        cashierStatusLabel.setBackground(new java.awt.Color(232, 243, 238));
        cashierStatusLabel.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        cashierStatusLabel.setForeground(new java.awt.Color(19, 111, 80));
        cashierStatusLabel.setText("    Cashier: admin | Terminal #01  ");
        cashierStatusLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(19, 111, 80)));
        cashierStatusLabel.setOpaque(true);
        cashierStatusLabel.setPreferredSize(new java.awt.Dimension(310, 32));
        statusWrapper.add(cashierStatusLabel);

        headerBar.add(statusWrapper, java.awt.BorderLayout.EAST);

        getContentPane().add(headerBar, java.awt.BorderLayout.NORTH);

        mainContent.setBackground(new java.awt.Color(248, 249, 250));
        mainContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 20, 18, 20));
        mainContent.setLayout(new java.awt.BorderLayout(16, 0));

        leftCatalogPanel.setOpaque(false);
        leftCatalogPanel.setLayout(new java.awt.BorderLayout(0, 12));

        filterCard.setBackground(new java.awt.Color(255, 255, 255));
        filterCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        filterCard.setLayout(new java.awt.BorderLayout(0, 12));

        searchAndComboRow.setOpaque(false);
        searchAndComboRow.setLayout(new java.awt.GridLayout(1, 2, 12, 0));

        searchBox.setBackground(new java.awt.Color(248, 249, 250));
        searchBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 10, 6, 10));
        searchBox.setLayout(new java.awt.BorderLayout(8, 0));

        searchIcon.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        searchIcon.setForeground(new java.awt.Color(107, 114, 128));
        searchIcon.setText("Search:");
        searchBox.add(searchIcon, java.awt.BorderLayout.WEST);

        searchField.setBackground(new java.awt.Color(248, 249, 250));
        searchField.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        searchField.setForeground(new java.awt.Color(17, 24, 39));
        searchField.setToolTipText("Search 39ers products by name or code...");
        searchField.setBorder(null);
        searchBox.add(searchField, java.awt.BorderLayout.CENTER);

        searchAndComboRow.add(searchBox);

        productComboBox.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        productComboBox.setForeground(new java.awt.Color(17, 24, 39));
        productComboBox.setPreferredSize(new java.awt.Dimension(240, 34));
        productComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productComboBoxActionPerformed(evt);
            }
        });
        searchAndComboRow.add(productComboBox);

        filterCard.add(searchAndComboRow, java.awt.BorderLayout.NORTH);

        categoryRow.setOpaque(false);
        categoryRow.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        catAllBtn.setBackground(new java.awt.Color(232, 243, 238));
        catAllBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        catAllBtn.setForeground(new java.awt.Color(19, 111, 80));
        catAllBtn.setText("All");
        catAllBtn.setPreferredSize(new java.awt.Dimension(105, 34));
        catAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catAllBtnActionPerformed(evt);
            }
        });
        categoryRow.add(catAllBtn);

        catMilkTeaBtn.setBackground(new java.awt.Color(255, 255, 255));
        catMilkTeaBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        catMilkTeaBtn.setForeground(new java.awt.Color(17, 24, 39));
        catMilkTeaBtn.setText("Milk Tea");
        catMilkTeaBtn.setPreferredSize(new java.awt.Dimension(105, 34));
        catMilkTeaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catMilkTeaBtnActionPerformed(evt);
            }
        });
        categoryRow.add(catMilkTeaBtn);

        catFruitTeaBtn.setBackground(new java.awt.Color(255, 255, 255));
        catFruitTeaBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        catFruitTeaBtn.setForeground(new java.awt.Color(17, 24, 39));
        catFruitTeaBtn.setText("Fruit Tea");
        catFruitTeaBtn.setPreferredSize(new java.awt.Dimension(105, 34));
        catFruitTeaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catFruitTeaBtnActionPerformed(evt);
            }
        });
        categoryRow.add(catFruitTeaBtn);

        catSnacksBtn.setBackground(new java.awt.Color(255, 255, 255));
        catSnacksBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        catSnacksBtn.setForeground(new java.awt.Color(17, 24, 39));
        catSnacksBtn.setText("Snacks");
        catSnacksBtn.setPreferredSize(new java.awt.Dimension(105, 34));
        catSnacksBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catSnacksBtnActionPerformed(evt);
            }
        });
        categoryRow.add(catSnacksBtn);

        filterCard.add(categoryRow, java.awt.BorderLayout.SOUTH);

        leftCatalogPanel.add(filterCard, java.awt.BorderLayout.NORTH);

        gridScrollPane.setBorder(null);

        gridAnchor.setBackground(new java.awt.Color(248, 249, 250));
        gridAnchor.setLayout(new java.awt.BorderLayout());

        productGridPanel.setBackground(new java.awt.Color(248, 249, 250));
        productGridPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        productGridPanel.setLayout(new java.awt.GridLayout(0, 3, 12, 12));
        gridAnchor.add(productGridPanel, java.awt.BorderLayout.NORTH);

        gridScrollPane.setViewportView(gridAnchor);

        leftCatalogPanel.add(gridScrollPane, java.awt.BorderLayout.CENTER);

        orderEntryBar.setBackground(new java.awt.Color(255, 255, 255));
        orderEntryBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16));
        orderEntryBar.setLayout(new java.awt.BorderLayout(14, 0));

        selectedInfoPanel.setOpaque(false);
        selectedInfoPanel.setLayout(new java.awt.GridLayout(2, 1, 0, 2));

        titleHint.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        titleHint.setForeground(new java.awt.Color(107, 114, 128));
        titleHint.setText("SELECTED ITEM");
        selectedInfoPanel.add(titleHint);

        selectedProductLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        selectedProductLabel.setForeground(new java.awt.Color(17, 24, 39));
        selectedProductLabel.setText("Taro Milk Tea — ₱39.00 (Milk Tea)");
        selectedInfoPanel.add(selectedProductLabel);

        orderEntryBar.add(selectedInfoPanel, java.awt.BorderLayout.CENTER);

        controlsFlow.setOpaque(false);
        controlsFlow.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));

        qtyCaption.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        qtyCaption.setForeground(new java.awt.Color(107, 114, 128));
        qtyCaption.setText("Qty:");
        controlsFlow.add(qtyCaption);

        minusBtn.setBackground(new java.awt.Color(255, 255, 255));
        minusBtn.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        minusBtn.setText("-");
        minusBtn.setPreferredSize(new java.awt.Dimension(38, 36));
        minusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusBtnActionPerformed(evt);
            }
        });
        controlsFlow.add(minusBtn);

        quantityField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        quantityField.setForeground(new java.awt.Color(17, 24, 39));
        quantityField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantityField.setText("1");
        quantityField.setPreferredSize(new java.awt.Dimension(48, 36));
        controlsFlow.add(quantityField);

        plusBtn.setBackground(new java.awt.Color(255, 255, 255));
        plusBtn.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        plusBtn.setText("+");
        plusBtn.setPreferredSize(new java.awt.Dimension(38, 36));
        plusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusBtnActionPerformed(evt);
            }
        });
        controlsFlow.add(plusBtn);

        addToOrderBtn.setBackground(new java.awt.Color(19, 111, 80));
        addToOrderBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        addToOrderBtn.setForeground(new java.awt.Color(255, 255, 255));
        addToOrderBtn.setText("Add to Order");
        addToOrderBtn.setPreferredSize(new java.awt.Dimension(128, 36));
        addToOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToOrderBtnActionPerformed(evt);
            }
        });
        controlsFlow.add(addToOrderBtn);

        clearResetBtn.setBackground(new java.awt.Color(255, 255, 255));
        clearResetBtn.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        clearResetBtn.setForeground(new java.awt.Color(17, 24, 39));
        clearResetBtn.setText("Clear / Reset");
        clearResetBtn.setPreferredSize(new java.awt.Dimension(114, 36));
        clearResetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearResetBtnActionPerformed(evt);
            }
        });
        controlsFlow.add(clearResetBtn);

        orderEntryBar.add(controlsFlow, java.awt.BorderLayout.EAST);

        leftCatalogPanel.add(orderEntryBar, java.awt.BorderLayout.SOUTH);

        mainContent.add(leftCatalogPanel, java.awt.BorderLayout.CENTER);

        sidebarCard.setBackground(new java.awt.Color(255, 255, 255));
        sidebarCard.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 16, 14, 16));
        sidebarCard.setPreferredSize(new java.awt.Dimension(455, 680));
        sidebarCard.setLayout(new java.awt.BorderLayout(0, 10));

        topHeader.setOpaque(false);
        topHeader.setLayout(new java.awt.BorderLayout(8, 0));

        saleTitle.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        saleTitle.setForeground(new java.awt.Color(17, 24, 39));
        saleTitle.setText("Current Sale");
        topHeader.add(saleTitle, java.awt.BorderLayout.WEST);

        segmentedToggle.setOpaque(false);
        segmentedToggle.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));

        tabCartButton.setBackground(new java.awt.Color(232, 243, 238));
        tabCartButton.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        tabCartButton.setForeground(new java.awt.Color(19, 111, 80));
        tabCartButton.setText("Cart & Billing");
        tabCartButton.setPreferredSize(new java.awt.Dimension(122, 30));
        tabCartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabCartButtonActionPerformed(evt);
            }
        });
        segmentedToggle.add(tabCartButton);

        tabReceiptButton.setBackground(new java.awt.Color(255, 255, 255));
        tabReceiptButton.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        tabReceiptButton.setForeground(new java.awt.Color(17, 24, 39));
        tabReceiptButton.setText("Receipt View");
        tabReceiptButton.setPreferredSize(new java.awt.Dimension(122, 30));
        tabReceiptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabReceiptButtonActionPerformed(evt);
            }
        });
        segmentedToggle.add(tabReceiptButton);

        topHeader.add(segmentedToggle, java.awt.BorderLayout.EAST);

        sidebarCard.add(topHeader, java.awt.BorderLayout.NORTH);

        rightCardContainer.setOpaque(false);
        rightCardContainer.setLayout(new java.awt.CardLayout());

        cartAndBillingView.setOpaque(false);
        cartAndBillingView.setLayout(new java.awt.BorderLayout(0, 10));

        tableSection.setOpaque(false);
        tableSection.setLayout(new java.awt.BorderLayout(0, 6));

        tableScroll.setPreferredSize(new java.awt.Dimension(380, 195));

        cartTable.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        cartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Price", "Qty", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cartTable.setRowHeight(30);
        tableScroll.setViewportView(cartTable);

        tableSection.add(tableScroll, java.awt.BorderLayout.CENTER);

        cartActionsRow.setOpaque(false);
        cartActionsRow.setLayout(new java.awt.BorderLayout());

        cartHintLabel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cartHintLabel.setForeground(new java.awt.Color(107, 114, 128));
        cartHintLabel.setText("Select a line item to adjust or remove");
        cartActionsRow.add(cartHintLabel, java.awt.BorderLayout.WEST);

        removeBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeBtn.setFont(new java.awt.Font("Inter", 1, 11)); // NOI18N
        removeBtn.setText("Remove Selected");
        removeBtn.setPreferredSize(new java.awt.Dimension(136, 28));
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });
        cartActionsRow.add(removeBtn, java.awt.BorderLayout.EAST);

        tableSection.add(cartActionsRow, java.awt.BorderLayout.SOUTH);

        cartAndBillingView.add(tableSection, java.awt.BorderLayout.CENTER);

        billingPanel.setOpaque(false);
        billingPanel.setLayout(new java.awt.BorderLayout(0, 8));

        summaryBox.setBackground(new java.awt.Color(248, 249, 250));
        summaryBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12));
        summaryBox.setLayout(new java.awt.GridLayout(4, 1, 0, 6));

        subtotalRow.setOpaque(false);
        subtotalRow.setLayout(new java.awt.BorderLayout());

        subtotalLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        subtotalLabel.setForeground(new java.awt.Color(107, 114, 128));
        subtotalLabel.setText("Subtotal");
        subtotalRow.add(subtotalLabel, java.awt.BorderLayout.WEST);

        subtotalValueLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        subtotalValueLabel.setForeground(new java.awt.Color(17, 24, 39));
        subtotalValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subtotalValueLabel.setText("₱0.00");
        subtotalRow.add(subtotalValueLabel, java.awt.BorderLayout.EAST);

        summaryBox.add(subtotalRow);

        vatRow.setOpaque(false);
        vatRow.setLayout(new java.awt.BorderLayout());

        vatLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        vatLabel.setForeground(new java.awt.Color(107, 114, 128));
        vatLabel.setText("Tax / VAT (12%)");
        vatRow.add(vatLabel, java.awt.BorderLayout.WEST);

        vatValueLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        vatValueLabel.setForeground(new java.awt.Color(17, 24, 39));
        vatValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        vatValueLabel.setText("₱0.00");
        vatRow.add(vatValueLabel, java.awt.BorderLayout.EAST);

        summaryBox.add(vatRow);

        discountRow.setOpaque(false);
        discountRow.setLayout(new java.awt.BorderLayout());

        seniorDiscountCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        seniorDiscountCheckBox.setForeground(new java.awt.Color(17, 24, 39));
        seniorDiscountCheckBox.setText("Senior Citizen Discount (20%)");
        seniorDiscountCheckBox.setOpaque(false);
        seniorDiscountCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seniorDiscountCheckBoxActionPerformed(evt);
            }
        });
        discountRow.add(seniorDiscountCheckBox, java.awt.BorderLayout.WEST);

        discountValueLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        discountValueLabel.setForeground(new java.awt.Color(19, 111, 80));
        discountValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        discountValueLabel.setText("-₱0.00");
        discountRow.add(discountValueLabel, java.awt.BorderLayout.EAST);

        summaryBox.add(discountRow);

        totalBanner.setOpaque(false);
        totalBanner.setLayout(new java.awt.BorderLayout());

        totalCaption.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        totalCaption.setForeground(new java.awt.Color(17, 24, 39));
        totalCaption.setText("Total Amount Due");
        totalBanner.add(totalCaption, java.awt.BorderLayout.WEST);

        totalDueValueLabel.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        totalDueValueLabel.setForeground(new java.awt.Color(17, 24, 39));
        totalDueValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalDueValueLabel.setText("₱0.00");
        totalBanner.add(totalDueValueLabel, java.awt.BorderLayout.EAST);

        summaryBox.add(totalBanner);

        billingPanel.add(summaryBox, java.awt.BorderLayout.NORTH);

        paymentBox.setBackground(new java.awt.Color(255, 255, 255));
        paymentBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12));
        paymentBox.setLayout(new java.awt.BorderLayout(0, 8));

        cashInputRow.setOpaque(false);
        cashInputRow.setLayout(new java.awt.BorderLayout(10, 0));

        cashLabel.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cashLabel.setForeground(new java.awt.Color(17, 24, 39));
        cashLabel.setText("Cash Tendered (₱):");
        cashInputRow.add(cashLabel, java.awt.BorderLayout.WEST);

        cashTenderedField.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        cashTenderedField.setForeground(new java.awt.Color(17, 24, 39));
        cashTenderedField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        cashTenderedField.setText("0.00");
        cashTenderedField.setPreferredSize(new java.awt.Dimension(140, 32));
        cashInputRow.add(cashTenderedField, java.awt.BorderLayout.CENTER);

        paymentBox.add(cashInputRow, java.awt.BorderLayout.NORTH);

        quickCashRow.setOpaque(false);
        quickCashRow.setLayout(new java.awt.GridLayout(1, 4, 6, 0));

        exactPillBtn.setBackground(new java.awt.Color(232, 243, 238));
        exactPillBtn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        exactPillBtn.setForeground(new java.awt.Color(19, 111, 80));
        exactPillBtn.setText("Exact");
        exactPillBtn.setPreferredSize(new java.awt.Dimension(70, 30));
        exactPillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exactPillBtnActionPerformed(evt);
            }
        });
        quickCashRow.add(exactPillBtn);

        cash50Btn.setBackground(new java.awt.Color(255, 255, 255));
        cash50Btn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cash50Btn.setText("₱50");
        cash50Btn.setPreferredSize(new java.awt.Dimension(70, 30));
        cash50Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cash50BtnActionPerformed(evt);
            }
        });
        quickCashRow.add(cash50Btn);

        cash100Btn.setBackground(new java.awt.Color(255, 255, 255));
        cash100Btn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cash100Btn.setText("₱100");
        cash100Btn.setPreferredSize(new java.awt.Dimension(70, 30));
        cash100Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cash100BtnActionPerformed(evt);
            }
        });
        quickCashRow.add(cash100Btn);

        cash500Btn.setBackground(new java.awt.Color(255, 255, 255));
        cash500Btn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cash500Btn.setText("₱500");
        cash500Btn.setPreferredSize(new java.awt.Dimension(70, 30));
        cash500Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cash500BtnActionPerformed(evt);
            }
        });
        quickCashRow.add(cash500Btn);

        paymentBox.add(quickCashRow, java.awt.BorderLayout.CENTER);

        changeRow.setOpaque(false);
        changeRow.setLayout(new java.awt.BorderLayout());

        changeCaption.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        changeCaption.setForeground(new java.awt.Color(107, 114, 128));
        changeCaption.setText("Change Due");
        changeRow.add(changeCaption, java.awt.BorderLayout.WEST);

        changeDueValueLabel.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        changeDueValueLabel.setForeground(new java.awt.Color(19, 111, 80));
        changeDueValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        changeDueValueLabel.setText("₱0.00");
        changeRow.add(changeDueValueLabel, java.awt.BorderLayout.EAST);

        paymentBox.add(changeRow, java.awt.BorderLayout.SOUTH);

        billingPanel.add(paymentBox, java.awt.BorderLayout.CENTER);

        actionButtonsRow.setOpaque(false);
        actionButtonsRow.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        computeTotalBtn.setBackground(new java.awt.Color(232, 243, 238));
        computeTotalBtn.setFont(new java.awt.Font("Inter", 1, 13)); // NOI18N
        computeTotalBtn.setForeground(new java.awt.Color(19, 111, 80));
        computeTotalBtn.setText("Compute Total");
        computeTotalBtn.setPreferredSize(new java.awt.Dimension(150, 40));
        computeTotalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeTotalBtnActionPerformed(evt);
            }
        });
        actionButtonsRow.add(computeTotalBtn);

        generateReceiptBtn.setBackground(new java.awt.Color(19, 111, 80));
        generateReceiptBtn.setFont(new java.awt.Font("Inter", 1, 13)); // NOI18N
        generateReceiptBtn.setForeground(new java.awt.Color(255, 255, 255));
        generateReceiptBtn.setText("Generate Receipt");
        generateReceiptBtn.setPreferredSize(new java.awt.Dimension(150, 40));
        generateReceiptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateReceiptBtnActionPerformed(evt);
            }
        });
        actionButtonsRow.add(generateReceiptBtn);

        billingPanel.add(actionButtonsRow, java.awt.BorderLayout.SOUTH);

        cartAndBillingView.add(billingPanel, java.awt.BorderLayout.SOUTH);

        rightCardContainer.add(cartAndBillingView, "CART_VIEW");

        receiptDisplayView.setOpaque(false);
        receiptDisplayView.setLayout(new java.awt.BorderLayout(0, 10));

        receiptInfoLabel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        receiptInfoLabel.setForeground(new java.awt.Color(107, 114, 128));
        receiptInfoLabel.setText("Formatted Thermal Receipt Preview (Monospaced)");
        receiptDisplayView.add(receiptInfoLabel, java.awt.BorderLayout.NORTH);

        receiptTextArea.setEditable(false);
        receiptTextArea.setBackground(new java.awt.Color(248, 249, 250));
        receiptTextArea.setColumns(20);
        receiptTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        receiptTextArea.setForeground(new java.awt.Color(17, 24, 39));
        receiptTextArea.setRows(5);
        receiptTextArea.setText("==========================================\n         39ERS REFRESHMENT KIOSK          \n     Unit B, L.O Manabat Bldg, Burol,     \n     Molino I, City of Bacoor, Cavite     \n        https://39ers-molino1.com         \n==========================================\n\n  Add items to the cart and click\n  'Generate Receipt' to build an official\n  printable POS receipt.\n\n==========================================\n For your own records. Not a BIR invoice. \n\n    Thank you for refreshing at 39ers     \n               COME AGAIN!                \n==========================================");
        receiptTextArea.setMargin(new java.awt.Insets(12, 14, 12, 14));
        receiptScroll.setViewportView(receiptTextArea);

        receiptDisplayView.add(receiptScroll, java.awt.BorderLayout.CENTER);

        bottomActions.setOpaque(false);
        bottomActions.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        backToSaleBtn.setBackground(new java.awt.Color(255, 255, 255));
        backToSaleBtn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        backToSaleBtn.setText("Back to Cart & Billing");
        backToSaleBtn.setPreferredSize(new java.awt.Dimension(150, 38));
        backToSaleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToSaleBtnActionPerformed(evt);
            }
        });
        bottomActions.add(backToSaleBtn);

        newOrderBtn.setBackground(new java.awt.Color(19, 111, 80));
        newOrderBtn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        newOrderBtn.setForeground(new java.awt.Color(255, 255, 255));
        newOrderBtn.setText("Start New Order");
        newOrderBtn.setPreferredSize(new java.awt.Dimension(150, 38));
        newOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newOrderBtnActionPerformed(evt);
            }
        });
        bottomActions.add(newOrderBtn);

        receiptDisplayView.add(bottomActions, java.awt.BorderLayout.SOUTH);

        rightCardContainer.add(receiptDisplayView, "RECEIPT_VIEW");

        sidebarCard.add(rightCardContainer, java.awt.BorderLayout.CENTER);

        mainContent.add(sidebarCard, java.awt.BorderLayout.EAST);

        getContentPane().add(mainContent, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void productComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productComboBoxActionPerformed
        if (!syncingComboBox) {
            Product chosen = (Product) productComboBox.getSelectedItem();
            if (chosen != null) {
                selectProduct(chosen);
            }
        }
    }//GEN-LAST:event_productComboBoxActionPerformed

    private void catAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catAllBtnActionPerformed
        setActiveCategory("All");
    }//GEN-LAST:event_catAllBtnActionPerformed

    private void catMilkTeaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catMilkTeaBtnActionPerformed
        setActiveCategory("Milk Tea");
    }//GEN-LAST:event_catMilkTeaBtnActionPerformed

    private void catFruitTeaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catFruitTeaBtnActionPerformed
        setActiveCategory("Fruit Tea");
    }//GEN-LAST:event_catFruitTeaBtnActionPerformed

    private void catSnacksBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catSnacksBtnActionPerformed
        setActiveCategory("Snacks");
    }//GEN-LAST:event_catSnacksBtnActionPerformed

    private void minusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusBtnActionPerformed
        adjustQuantity(-1);
    }//GEN-LAST:event_minusBtnActionPerformed

    private void plusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusBtnActionPerformed
        adjustQuantity(1);
    }//GEN-LAST:event_plusBtnActionPerformed

    private void addToOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToOrderBtnActionPerformed
        handleAddSelectedToOrder();
    }//GEN-LAST:event_addToOrderBtnActionPerformed

    private void clearResetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearResetBtnActionPerformed
        handleClearResetOrder();
    }//GEN-LAST:event_clearResetBtnActionPerformed

    private void tabCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabCartButtonActionPerformed
        switchRightSidebarTab(CARD_CART_VIEW);
    }//GEN-LAST:event_tabCartButtonActionPerformed

    private void tabReceiptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabReceiptButtonActionPerformed
        switchRightSidebarTab(CARD_RECEIPT_VIEW);
    }//GEN-LAST:event_tabReceiptButtonActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        handleRemoveSelectedCartItem();
    }//GEN-LAST:event_removeBtnActionPerformed

    private void seniorDiscountCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seniorDiscountCheckBoxActionPerformed
        if (totalComputed) {
            updateBillingSummary(false);
        }
    }//GEN-LAST:event_seniorDiscountCheckBoxActionPerformed

    private void exactPillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exactPillBtnActionPerformed
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please add items to the order cart first.",
                    "Empty Order Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }
        totalComputed = true;
        BillSummary summary = billingService.calculateBill(cartItems, seniorDiscountCheckBox.isSelected(), 0.0);
        cashTenderedField.setText(String.format("%.2f", summary.getTotalDue()));
        updateBillingSummary(false);
    }//GEN-LAST:event_exactPillBtnActionPerformed

    private void cash50BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cash50BtnActionPerformed
        cashTenderedField.setText("50.00");
        if (totalComputed) {
            updateBillingSummary(false);
        }
    }//GEN-LAST:event_cash50BtnActionPerformed

    private void cash100BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cash100BtnActionPerformed
        cashTenderedField.setText("100.00");
        if (totalComputed) {
            updateBillingSummary(false);
        }
    }//GEN-LAST:event_cash100BtnActionPerformed

    private void cash500BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cash500BtnActionPerformed
        cashTenderedField.setText("500.00");
        if (totalComputed) {
            updateBillingSummary(false);
        }
    }//GEN-LAST:event_cash500BtnActionPerformed

    private void computeTotalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeTotalBtnActionPerformed
        handleComputeTotal();
    }//GEN-LAST:event_computeTotalBtnActionPerformed

    private void generateReceiptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateReceiptBtnActionPerformed
        handleGenerateReceipt();
    }//GEN-LAST:event_generateReceiptBtnActionPerformed

    private void backToSaleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToSaleBtnActionPerformed
        switchRightSidebarTab(CARD_CART_VIEW);
    }//GEN-LAST:event_backToSaleBtnActionPerformed

    private void newOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newOrderBtnActionPerformed
        handleClearResetOrder();
        switchRightSidebarTab(CARD_CART_VIEW);
    }//GEN-LAST:event_newOrderBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        new Guillergan_LoginUI_ICT1201().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void switchRightSidebarTab(String cardName) {
        CardLayout cl = (CardLayout) rightCardContainer.getLayout();
        cl.show(rightCardContainer, cardName);
        boolean isCart = CARD_CART_VIEW.equals(cardName);
        stylePillButton(tabCartButton, isCart);
        stylePillButton(tabReceiptButton, !isCart);
    }

    private void setActiveCategory(String category) {
        this.activeCategory = category;
        stylePillButton(catAllBtn, "All".equals(category));
        stylePillButton(catMilkTeaBtn, "Milk Tea".equals(category));
        stylePillButton(catFruitTeaBtn, "Fruit Tea".equals(category));
        stylePillButton(catSnacksBtn, "Snacks".equals(category));
        refreshProductGrid();
    }

    private void refreshProductGrid() {
        productGridPanel.removeAll();
        productCards.clear();

        String query = (searchField != null) ? searchField.getText() : "";
        List<Product> filtered = menuRepository.filterProducts(activeCategory, query);

        ProductCardListener cardListener = new ProductCardListener() {
            @Override
            public void onProductSelected(Product product, boolean doubleClicked) {
                selectProduct(product);
                if (doubleClicked) {
                    addProductToCart(product, 1);
                }
            }
        };

        for (Product product : filtered) {
            ProductCard card = new ProductCard(product, cardListener);
            if (selectedProduct != null && selectedProduct.getId().equals(product.getId())) {
                card.setSelected(true);
            }
            productCards.add(card);
            productGridPanel.add(card);
        }

        productGridPanel.revalidate();
        productGridPanel.repaint();
    }

    private void selectProduct(Product product) {
        this.selectedProduct = product;
        for (ProductCard card : productCards) {
            card.setSelected(card.getProduct().getId().equals(product.getId()));
        }
        updateSelectedProductLabel();

        if (productComboBox != null) {
            syncingComboBox = true;
            productComboBox.setSelectedItem(product);
            syncingComboBox = false;
        }
    }

    private void updateSelectedProductLabel() {
        if (selectedProductLabel != null) {
            if (selectedProduct == null) {
                selectedProductLabel.setText("None selected");
            } else {
                selectedProductLabel.setText(String.format("%s — \u20B1%.2f (%s)",
                        selectedProduct.getName(), selectedProduct.getPrice(), selectedProduct.getCategory()));
            }
        }
    }

    private void adjustQuantity(int delta) {
        int current = parseQuantityField(false);
        int updated = Math.max(1, current + delta);
        quantityField.setText(String.valueOf(updated));
    }

    private int parseQuantityField(boolean showAlertOnError) {
        String text = quantityField.getText().trim();
        try {
            int qty = Integer.parseInt(text);
            if (qty <= 0) {
                if (showAlertOnError) {
                    JOptionPane.showMessageDialog(this,
                            "Quantity must be a whole number greater than 0.",
                            "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                }
                return 1;
            }
            return qty;
        } catch (NumberFormatException ex) {
            if (showAlertOnError) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric quantity.",
                        "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
            }
            return -1;
        }
    }

    private void handleAddSelectedToOrder() {
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a product from the catalog or dropdown first.",
                    "No Product Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty = parseQuantityField(true);
        if (qty <= 0) {
            quantityField.setText("1");
            return;
        }

        addProductToCart(selectedProduct, qty);
        quantityField.setText("1");
    }

    private void addProductToCart(Product product, int qtyToAdd) {
        CartItem existing = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                existing = item;
                break;
            }
        }

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + qtyToAdd);
        } else {
            cartItems.add(new CartItem(product, qtyToAdd));
        }

        totalComputed = false;
        refreshCartTable();
        resetBillingDisplay();
    }

    private void handleRemoveSelectedCartItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= cartItems.size()) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item row in the order cart table to remove.",
                    "No Cart Row Selected", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        cartItems.remove(selectedRow);
        totalComputed = false;
        refreshCartTable();
        resetBillingDisplay();
    }

    private void handleClearResetOrder() {
        cartItems.clear();
        totalComputed = false;
        quantityField.setText("1");
        cashTenderedField.setText("0.00");
        seniorDiscountCheckBox.setSelected(false);
        searchField.setText("");
        setActiveCategory("All");
        refreshCartTable();
        resetBillingDisplay();
    }

    private void resetBillingDisplay() {
        subtotalValueLabel.setText("\u20B10.00");
        vatValueLabel.setText("\u20B10.00");
        discountValueLabel.setText("-\u20B10.00");
        totalDueValueLabel.setText("\u20B10.00");
        changeDueValueLabel.setText("\u20B10.00");
        changeDueValueLabel.setForeground(PRIMARY_ACCENT);
    }

    private void handleComputeTotal() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Your order cart is empty.\nPlease add at least one item using 'Add to Order' (or double-click a product card) before computing the total.",
                    "Empty Order Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cash = parseCashTendered(true);
        if (cash < 0) {
            return;
        }

        totalComputed = true;
        updateBillingSummary(false);
    }

    private void refreshCartTable() {
        cartTableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            Object[] row = new Object[]{
                item.getProduct().getName(),
                String.format("\u20B1%.2f", item.getProduct().getPrice()),
                item.getQuantity(),
                String.format("\u20B1%.2f", item.getLineTotal())
            };
            cartTableModel.addRow(row);
        }
    }

    private double parseCashTendered(boolean showWarning) {
        String raw = cashTenderedField.getText().trim().replace(",", "");
        if (raw.isEmpty()) {
            return 0.0;
        }
        try {
            double val = Double.parseDouble(raw);
            if (val < 0) {
                if (showWarning) {
                    JOptionPane.showMessageDialog(this,
                            "Cash tendered cannot be negative.",
                            "Invalid Cash Input", JOptionPane.WARNING_MESSAGE);
                }
                return -1.0;
            }
            return val;
        } catch (NumberFormatException ex) {
            if (showWarning) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric cash amount (e.g., 100 or 200.00).",
                        "Invalid Cash Input", JOptionPane.WARNING_MESSAGE);
            }
            return -1.0;
        }
    }

    private BillSummary updateBillingSummary(boolean validateCashInput) {
        double cash = parseCashTendered(validateCashInput);
        if (cash < 0) {
            cash = 0.0;
        }

        BillSummary summary = billingService.calculateBill(
                cartItems,
                seniorDiscountCheckBox.isSelected(),
                cash
        );

        subtotalValueLabel.setText(String.format("\u20B1%.2f", summary.getSubtotal()));
        vatValueLabel.setText(String.format("\u20B1%.2f", summary.getVatAmount()));
        discountValueLabel.setText(String.format("-\u20B1%.2f", summary.getDiscountAmount()));
        totalDueValueLabel.setText(String.format("\u20B1%.2f", summary.getTotalDue()));

        if (cash >= summary.getTotalDue() && summary.getTotalDue() > 0) {
            changeDueValueLabel.setText(String.format("\u20B1%.2f", summary.getChangeDue()));
            changeDueValueLabel.setForeground(PRIMARY_ACCENT);
        } else if (summary.getTotalDue() == 0) {
            changeDueValueLabel.setText("\u20B10.00");
            changeDueValueLabel.setForeground(PRIMARY_ACCENT);
        } else {
            double shortfall = summary.getTotalDue() - cash;
            changeDueValueLabel.setText(String.format("Short \u20B1%.2f", shortfall));
            changeDueValueLabel.setForeground(new Color(0xDC, 0x26, 0x26));
        }

        return summary;
    }

    private void handleGenerateReceipt() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cannot generate a receipt for an empty cart. Please add at least one 39ers item.",
                    "Empty Order Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cash = parseCashTendered(true);
        if (cash < 0) {
            return;
        }

        totalComputed = true;
        BillSummary summary = updateBillingSummary(false);
        if (cash < summary.getTotalDue()) {
            JOptionPane.showMessageDialog(this,
                    String.format("Insufficient cash tendered.\nTotal Amount Due: \u20B1%.2f\nCash Tendered: \u20B1%.2f",
                            summary.getTotalDue(), cash),
                    "Insufficient Payment", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String transId = String.format("39ERS-M1-%04d", orderSequence++);
        String receiptText = receiptFormatter.formatReceipt(
                cartItems,
                summary,
                transId,
                "Terminal #01",
                "admin"
        );

        receiptTextArea.setText(receiptText);
        receiptTextArea.setCaretPosition(0);
        switchRightSidebarTab(CARD_RECEIPT_VIEW);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to default Look and Feel cleanly
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FiveOS_POS_Guillergan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionButtonsRow;
    private javax.swing.JButton addToOrderBtn;
    private javax.swing.JButton backToSaleBtn;
    private javax.swing.JPanel billingPanel;
    private javax.swing.JPanel bottomActions;
    private javax.swing.JPanel brandPanel;
    private javax.swing.JPanel cartActionsRow;
    private javax.swing.JPanel cartAndBillingView;
    private javax.swing.JLabel cartHintLabel;
    private javax.swing.JTable cartTable;
    private javax.swing.JButton cash100Btn;
    private javax.swing.JButton cash500Btn;
    private javax.swing.JButton cash50Btn;
    private javax.swing.JPanel cashInputRow;
    private javax.swing.JLabel cashLabel;
    private javax.swing.JTextField cashTenderedField;
    private javax.swing.JLabel cashierStatusLabel;
    private javax.swing.JButton catAllBtn;
    private javax.swing.JButton catFruitTeaBtn;
    private javax.swing.JButton catMilkTeaBtn;
    private javax.swing.JButton catSnacksBtn;
    private javax.swing.JPanel categoryRow;
    private javax.swing.JLabel changeCaption;
    private javax.swing.JLabel changeDueValueLabel;
    private javax.swing.JPanel changeRow;
    private javax.swing.JButton clearResetBtn;
    private javax.swing.JButton computeTotalBtn;
    private javax.swing.JPanel controlsFlow;
    private javax.swing.JPanel discountRow;
    private javax.swing.JLabel discountValueLabel;
    private javax.swing.JButton exactPillBtn;
    private javax.swing.JPanel filterCard;
    private javax.swing.JButton generateReceiptBtn;
    private javax.swing.JPanel gridAnchor;
    private javax.swing.JScrollPane gridScrollPane;
    private javax.swing.JPanel headerBar;
    private javax.swing.JPanel leftCatalogPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JPanel mainContent;
    private javax.swing.JButton minusBtn;
    private javax.swing.JButton newOrderBtn;
    private javax.swing.JPanel orderEntryBar;
    private javax.swing.JPanel paymentBox;
    private javax.swing.JButton plusBtn;
    private javax.swing.JComboBox<Product> productComboBox;
    private javax.swing.JPanel productGridPanel;
    private javax.swing.JLabel qtyCaption;
    private javax.swing.JTextField quantityField;
    private javax.swing.JPanel quickCashRow;
    private javax.swing.JPanel receiptDisplayView;
    private javax.swing.JLabel receiptInfoLabel;
    private javax.swing.JScrollPane receiptScroll;
    private javax.swing.JTextArea receiptTextArea;
    private javax.swing.JButton removeBtn;
    private javax.swing.JPanel rightCardContainer;
    private javax.swing.JLabel saleTitle;
    private javax.swing.JPanel searchAndComboRow;
    private javax.swing.JPanel searchBox;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchIcon;
    private javax.swing.JPanel segmentedToggle;
    private javax.swing.JPanel selectedInfoPanel;
    private javax.swing.JLabel selectedProductLabel;
    private javax.swing.JCheckBox seniorDiscountCheckBox;
    private javax.swing.JPanel sidebarCard;
    private javax.swing.JPanel statusWrapper;
    private javax.swing.JLabel storeNameLabel;
    private javax.swing.JLabel subtotalLabel;
    private javax.swing.JPanel subtotalRow;
    private javax.swing.JLabel subtotalValueLabel;
    private javax.swing.JPanel summaryBox;
    private javax.swing.JButton tabCartButton;
    private javax.swing.JButton tabReceiptButton;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JPanel tableSection;
    private javax.swing.JLabel taglineLabel;
    private javax.swing.JLabel titleHint;
    private javax.swing.JPanel titleStack;
    private javax.swing.JPanel topHeader;
    private javax.swing.JPanel totalBanner;
    private javax.swing.JLabel totalCaption;
    private javax.swing.JLabel totalDueValueLabel;
    private javax.swing.JLabel vatLabel;
    private javax.swing.JPanel vatRow;
    private javax.swing.JLabel vatValueLabel;
    // End of variables declaration//GEN-END:variables
}
