package com.henry.grocery.product;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.henry.grocery.product.Unit.*;

public class ProductService {

    private static final Map<String, Product> AVAILABLE_PRODUCTS = Collections.unmodifiableMap(
            Stream.of(
                    new Product("soup", TIN, 0.65D),
                    new Product("bread", LOAF, 0.80D),
                    new Product("milk", BOTTLE, 1.30D),
                    new Product("apples", SINGLE, 0.10D)
            ).collect(
                    Collectors.toMap(Product::getName, (p) -> p))
    );

    public Product findByName(String name) {
        Product product = AVAILABLE_PRODUCTS.get(name);
        if (product == null) {
            throw new ProductNotFoundException(String.format("Product with name (%s) not found", name));
        }

        return product;
    }
}
