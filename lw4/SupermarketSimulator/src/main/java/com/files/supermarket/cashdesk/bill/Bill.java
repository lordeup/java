package com.files.supermarket.cashdesk.bill;

import java.math.BigDecimal;

public class Bill {
  private final BigDecimal price;

  public Bill(BigDecimal price) {
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be less than 0");
    }
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
