package com.files.supermarket.report;

import com.files.customer.basket.Basket;
import com.files.product.Product;
import com.files.product.discount.Discount;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
  private final ByteArrayOutputStream output = new ByteArrayOutputStream();

  private Basket basket;
  private Report report;

  String replaceStr(String str) {
    return str.replaceAll("\n", "").replaceAll("\r", "");
  }

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));

    basket = new Basket();
    basket.addBasket(new Product("tea", 0, TypeProductSale.BY_COUNT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(60.0), 1, new Discount(25)));
    basket.addBasket(new Product("candy", 1, TypeProductSale.BY_WEIGHT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(50.0), 1000));

    report = new Report();
  }

  @Test
  void addBasketReport() {
    BigDecimal price = new BigDecimal(110.0);

    report.addBasketReport(basket, price);
    assertEquals(1, report.getReportSize(), "Get report size");
  }

  @Test
  void printDailyInfo() {
    BigDecimal price = new BigDecimal(110.0);
    report.addBasketReport(basket, price);
    report.printDailyInfo();

    String expectedResult = "Daily information: [tea: 1 BY_COUNT, candy: 1000 BY_WEIGHT]: 110";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print daily info");
  }

  @Test
  void printDailyInfoEmptyReport() {
    report.printDailyInfo();

    String expectedResult = "Nothing was sold in a day";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print daily info empty report");
  }
}