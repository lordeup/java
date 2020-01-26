package com.files.supermarket.cashdesk.bill;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

  @Test
  void getPrice() {
    BigDecimal billPrice = new BigDecimal(345);
    Bill bill = new Bill(billPrice);
    BigDecimal actualResult = bill.getPrice();

    assertEquals(billPrice, actualResult, "Get price");
  }

  @Test
  void negativeBill() {
    BigDecimal billPrice = new BigDecimal(-1);

    assertThrows(IllegalArgumentException.class, () -> new Bill(billPrice), "Negative bill");
  }
}