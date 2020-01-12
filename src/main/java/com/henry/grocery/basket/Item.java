package com.henry.grocery.basket;

import com.henry.grocery.product.Product;
import com.henry.grocery.util.MathUtils;

public class Item {

    private final Product product;
    private final int quantity;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return calculateTotal(quantity);
    }

    public double getTotalFor(int quantityToTotal) {
        return calculateTotal(quantityToTotal);
    }

    private double calculateTotal(int quantityToTotal) {
        return MathUtils.round(product.getCost() * quantityToTotal);
    }
}
