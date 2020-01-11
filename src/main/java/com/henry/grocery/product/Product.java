package com.henry.grocery.product;

import java.util.Objects;

public class Product {

    private final String name;
    private final Unit unit;
    private final double cost;

    public Product(String name, Unit unit, double cost) {
        this.name = name;
        this.unit = unit;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
