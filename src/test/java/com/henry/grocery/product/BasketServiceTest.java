package com.henry.grocery.product;

import com.henry.grocery.basket.Basket;
import com.henry.grocery.basket.BasketService;
import com.henry.grocery.basket.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasketServiceTest {

    private ProductService productService;
    private BasketService basketService;
    private Basket basket;

    @Before
    public void setup() {
        basket = new Basket();
        productService = new ProductService();
        basketService = new BasketService(productService);
    }

    @Test
    public void canAddItemsToBasket() {
        Basket basketAfterAddingSoup = basketService.addItem(basket, "soup", 3);
        assertEquals("Basket must contain newly added item", 1, basketAfterAddingSoup.getItemsList().size());
    }

    @Test
    public void canAddMultipleItemsToBasket() {
        basket = basketService.addItem(basket, "soup", 3);
        Basket basketAfterAddingMultipleItems = basketService.addItem(basket, "milk", 1);
        assertEquals("Basket must contain multiple items", 2, basketAfterAddingMultipleItems.getItemsList().size());
    }

    @Test
    public void canAddQuantityToExistingItemsBasket() {
        basket = basketService.addItem(basket, "soup", 3);
        Basket basketAfterAddingQuantity = basketService.addItem(basket, "soup", 1);
        assertEquals("Basket must contain newly added item", 1, basketAfterAddingQuantity.getItemsList().size());

        Item item = basketAfterAddingQuantity.getItemsList().stream().findFirst().orElse(null);
        assertNotNull(item);

        assertEquals("Item quantity must be updated", 4, item.getQuantity());
    }

    @Test
    public void checkoutCalculatesBasketTotal() {
        basket = basketService.addItem(basket, "soup", 3);
        basket = basketService.addItem(basket, "milk", 1);
        basket = basketService.checkout(basket);

        assertEquals("Basket total must be set after checkout ", 3.25D, basket.getTotal(), 0.0D);
    }
}
