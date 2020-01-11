package com.henry.grocery.basket;

import com.henry.grocery.product.Product;

import java.util.*;

public class Basket {

    private final Map<Product, Item> items;
    private final double total;

    public Basket() {
        this(new HashMap<>());
    }

    public Basket(Map<Product, Item> items) {
        this(items, 0.0D);
    }

    public Basket(Map<Product, Item> items, double total) {
        this.items = items;
        this.total = total;
    }

    public List<Item> getItemsList() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    public Map<Product, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public double getTotal() {
        return total;
    }

    public void addItem(Product product, int quantity) {
        Item existingItem = items.getOrDefault(product, new Item(product, 0));
        items.put(product, new Item(product, existingItem.getQuantity() + quantity));
    }

}
