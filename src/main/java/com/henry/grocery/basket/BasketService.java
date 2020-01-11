package com.henry.grocery.basket;

import com.henry.grocery.product.Product;
import com.henry.grocery.product.ProductService;

import java.util.HashMap;

public class BasketService {

    private final ProductService productService;

    public BasketService(ProductService productService) {
        this.productService = productService;
    }

    public Basket addItem(Basket basket, String productName, int quantity) {
        Product product = productService.findByName(productName);
        basket.addItem(product, quantity);
        return new Basket(new HashMap<Product, Item>(basket.getItems()));
    }

    public Basket checkout(Basket basket) {
        double basketTotal = calculateBasketTotal(basket);
        return new Basket(basket.getItems(), basketTotal);
    }

    private double calculateBasketTotal(Basket basket) {
        double basketTotal = basket.getItemsList().stream().reduce(0.0D, (itemTotal, item) -> itemTotal + item.getTotal(), Double::sum);
        return basketTotal;
    }
}
