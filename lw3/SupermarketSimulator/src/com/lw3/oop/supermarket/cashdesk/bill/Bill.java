package com.lw3.oop.supermarket.cashdesk.bill;

import java.math.BigDecimal;

public class Bill {
  private final BigDecimal price;

  public Bill(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
