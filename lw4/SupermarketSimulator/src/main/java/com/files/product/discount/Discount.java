package com.files.product.discount;

public class Discount {
  private Integer value;

  public Discount(Integer value) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException("The discount cannot be negative or be more than 100");
    }
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
