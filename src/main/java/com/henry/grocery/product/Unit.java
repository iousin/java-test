package com.henry.grocery.product;

public enum Unit {

    TIN("tin"), LOAF("loaf"), BOTTLE("bottle"), SINGLE("single");

    private final String unitRepresentation;

    Unit(String unitRepresentation) {
        this.unitRepresentation = unitRepresentation;
    }
}
