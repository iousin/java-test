package com.henry.grocery.product;

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
}
