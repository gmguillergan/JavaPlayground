/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       CartItem.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: Represents a line item in the active POS order cart.
 * ---------------------------------------------------------
 */
package fiveos.model;

public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return product.getPrice() * quantity;
    }
}
