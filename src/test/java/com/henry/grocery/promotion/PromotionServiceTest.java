package com.henry.grocery.promotion;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static org.junit.Assert.assertEquals;

public class PromotionServiceTest {

    private PromotionService promotionService;

    @Before
    public void setup() {
        promotionService = new PromotionService();

        LocalDateTime applesPromotionStartDate = LocalDateTime.now().plusDays(3);
        LocalDateTime applesPromotionEndDate = LocalDateTime.now().plusMonths(1).with(lastDayOfMonth());
        ProductPromotion applesPromotion = new ProductPromotion("apple", applesPromotionStartDate, applesPromotionEndDate, 0.10D, 1);

        promotionService.addPromotion(applesPromotion);
    }

    @Test
    public void cannotFindInactivePromotions() {
        List<ProductPromotion> promotions = promotionService.getActivePromotions(LocalDateTime.now());
        assertEquals("No promotions should be returned for today", 0, promotions.size());

        List<ProductPromotion> promotionsAtFutureDate = promotionService.getActivePromotions(LocalDateTime.now().plusMonths(2));
        assertEquals("No promotions should be returned for future date", 0, promotionsAtFutureDate.size());
    }

    @Test
    public void canFindActivePromotions() {
        List<ProductPromotion> promotions = promotionService.getActivePromotions(LocalDateTime.now().plusDays(3).plusHours(1));
        assertEquals("Promotions within active date should be returned", 1, promotions.size());
        List<ProductPromotion> promotionsBeforeEnd = promotionService.getActivePromotions(LocalDateTime.now().plusMonths(1).with(lastDayOfMonth()).minusHours(1));
        assertEquals("Promotions just before expiry date should be returned", 1, promotions.size());
    }

}
