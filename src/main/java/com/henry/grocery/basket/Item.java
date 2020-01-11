package com.henry.grocery.basket;

import com.henry.grocery.product.Product;

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

}
