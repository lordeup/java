package com.files.product.discount;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

  @Test
  void getValueZeroDiscount() {
    Integer discountValue = 0;
    Discount discount = new Discount(discountValue);
    Integer actualResult = discount.getValue();

    assertEquals(discountValue, actualResult, "Zero discount");
  }

  @Test
  void getValue100Discount() {
    Integer discountValue = 100;
    Discount discount = new Discount(discountValue);
    Integer actualResult = discount.getValue();

    assertEquals(discountValue, actualResult, "100 discount");
  }

  @Test
  void negativeDiscount() {
    Integer discountValue = -1;

    assertThrows(IllegalArgumentException.class, () -> new Discount(discountValue), "Negative discount");
  }

  @Test
  void moreThan100Discount() {
    Integer discountValue = 129;

    assertThrows(IllegalArgumentException.class, () -> new Discount(discountValue), "More than 100 discount");
  }
}