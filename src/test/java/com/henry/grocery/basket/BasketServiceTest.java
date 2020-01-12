package com.henry.grocery.basket;

import com.henry.grocery.product.ProductService;
import com.henry.grocery.promotion.MultiProductPromotion;
import com.henry.grocery.promotion.ProductPromotion;
import com.henry.grocery.promotion.PromotionService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasketServiceTest {

    private ProductService productService;
    private PromotionService promotionService;
    private BasketService basketService;
    private Basket basket;

    @Before
    public void setup() {
        basket = new Basket();
        productService = new ProductService();
        promotionService = new PromotionService();
        basketService = new BasketService(productService, promotionService);
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

    @Test
    public void singleProductPromotionIsApplied() {
        promotionService.addPromotion(new ProductPromotion("apples", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusDays(7), 0.10D, 1));
        basket = basketService.addItem(basket, "bread", 1);
        basket = basketService.addItem(basket, "apples", 10);
        basket = basketService.checkout(basket, LocalDateTime.now().plusDays(2));

        assertEquals("Basket total must be set after adjusting promotion discount ", 1.70D, basket.getTotal(), 0.0D);
    }

    @Test
    public void multiProductPromotionIsApplied() {
        promotionService.addPromotion(
                new MultiProductPromotion("bread", "soup", LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusDays(7), 0.50D, 2));
        basket = basketService.addItem(basket, "bread", 3);
        basket = basketService.addItem(basket, "soup", 4);
        basket = basketService.checkout(basket, LocalDateTime.now().plusDays(5));

        assertEquals("Basket total must be set after adjusting promotion discount ", 4.20D, basket.getTotal(), 0.0D);
    }

}
