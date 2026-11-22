/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       Product.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Domain model representing a 39ers Refreshment Kiosk menu item.
 * ---------------------------------------------------------
 */
package fiveos.model;

public class Product {

    private final String id;
    private final String name;
    private final String category;
    private final double price;
    private final boolean bestSeller;
    private final boolean customizable;

    public Product(String id, String name, String category, double price, boolean bestSeller, boolean customizable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.bestSeller = bestSeller;
        this.customizable = customizable;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBestSeller() {
        return bestSeller;
    }

    public boolean isCustomizable() {
        return customizable;
    }

    @Override
    public String toString() {
        return String.format("%s — \u20B1%.2f (%s)", name, price, category);
    }
}
