/*
 * ---------------------------------------------------------
 * Subject:     Computer Programming 4
 * Class:       MenuRepository.java
 * Author:      chanchanjeu
 * Section:     ICT 12-01
 * Date:        November 22, 2026
 * Description: In-memory repository providing 39ers Refreshment Kiosk menu items.
 * ---------------------------------------------------------
 */
package fiveos.service;

import fiveos.model.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuRepository {

    private final List<Product> products;

    public MenuRepository() {
        List<Product> list = new ArrayList<Product>();
        list.add(new Product("MT-01", "Taro Milk Tea", "Milk Tea", 39.00, true, true));
        list.add(new Product("MT-02", "Okinawa Milk Tea", "Milk Tea", 39.00, true, true));
        list.add(new Product("MT-03", "Wintermelon Milk Tea", "Milk Tea", 39.00, true, true));
        list.add(new Product("MT-04", "Dark Choco Milk Tea", "Milk Tea", 39.00, false, true));
        list.add(new Product("MT-05", "Cheesecake Milk Tea", "Milk Tea", 49.00, true, true));
        list.add(new Product("FT-01", "Strawberry Fruit Tea", "Fruit Tea", 39.00, false, true));
        list.add(new Product("FT-02", "Mango Fruit Tea", "Fruit Tea", 39.00, true, true));
        list.add(new Product("SN-01", "Cheesy Fries", "Snacks", 49.00, true, false));
        list.add(new Product("SN-02", "Chicken Poppers", "Snacks", 59.00, false, false));
        list.add(new Product("SN-03", "Nachos Overload", "Snacks", 59.00, false, false));
        this.products = Collections.unmodifiableList(list);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> filterProducts(String category, String searchQuery) {
        List<Product> filtered = new ArrayList<Product>();
        String query = (searchQuery == null) ? "" : searchQuery.trim().toLowerCase();
        boolean allCategories = (category == null || category.trim().isEmpty() || "All".equalsIgnoreCase(category));

        for (Product product : products) {
            boolean matchesCategory = allCategories || product.getCategory().equalsIgnoreCase(category);
            boolean matchesQuery = query.isEmpty()
                    || product.getName().toLowerCase().contains(query)
                    || product.getCategory().toLowerCase().contains(query)
                    || product.getId().toLowerCase().contains(query);
            if (matchesCategory && matchesQuery) {
                filtered.add(product);
            }
        }
        return filtered;
    }
}
