package com.henry.grocery;

import com.henry.grocery.basket.BasketService;
import com.henry.grocery.console.CommandLineClient;
import com.henry.grocery.product.ProductService;
import com.henry.grocery.promotion.MultiProductPromotion;
import com.henry.grocery.promotion.ProductPromotion;
import com.henry.grocery.promotion.PromotionService;

import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class GroceryApplication {

    public static void main(String[] args) {
        ProductService productService = new ProductService();
        PromotionService promotionService = new PromotionService();

        setupPromotions(promotionService);

        BasketService basketService = new BasketService(productService, promotionService);

        CommandLineClient commandLineClient = new CommandLineClient(basketService);
        commandLineClient.startCommandLineInterface();
    }

    private static void setupPromotions(PromotionService promotionService) {
        promotionService.addPromotion(
                new ProductPromotion("apples", LocalDateTime.now().plusDays(3), LocalDateTime.now().plusMonths(1).with(lastDayOfMonth()), 0.10D, 1)
        );
        promotionService.addPromotion(
                new MultiProductPromotion("bread", "soup", LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(6), 0.50D, 2)
        );
    }
}
