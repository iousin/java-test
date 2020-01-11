package com.henry.grocery.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ProductServiceTest {

    private ProductService productService;

    @Before
    public void setup() {
        productService = new ProductService();
    }

    @Test
    public void canFindAvailableProducts() {
        Product soup = productService.findByName("soup");
        assertNotNull(soup);
    }

    @Test(expected = ProductNotFoundException.class)
    public void throwsExceptionWhenAProductIsNotFound() {
        Product soup = productService.findByName("invalid");
    }

}
