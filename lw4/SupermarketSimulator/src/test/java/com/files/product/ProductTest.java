package com.files.product;

import com.files.product.discount.Discount;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
  private final static String PRODUCT_NAME = "milk";
  private final static Integer PRODUCT_ID = 1;
  private final static TypeProductSale PRODUCT_TYPE_PRODUCT_SALE = TypeProductSale.BY_COUNT;
  private final static ProductType PRODUCT_TYPE = ProductType.ALLOWED_TO_CHILDREN;
  private final static BigDecimal PRODUCT_PRICE = new BigDecimal(35.00);
  private final static Integer PRODUCT_QUANTITY_GOODS = 5;
  private final static Discount PRODUCT_DISCOUNT = new Discount(50);

  private final ByteArrayOutputStream output = new ByteArrayOutputStream();

  String replaceStr(String str) {
    return str.replaceAll("\n", "").replaceAll("\r", "");
  }

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));
  }

  @Test
  void constructorWithDiscount() {
    Product product = new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, PRODUCT_QUANTITY_GOODS, PRODUCT_DISCOUNT);

    assertAll("constructor",
            () -> assertEquals(PRODUCT_NAME, product.getName(), "Get name product"),
            () -> assertEquals(PRODUCT_ID, product.getId(), "Get id product"),
            () -> assertEquals(PRODUCT_TYPE_PRODUCT_SALE, product.getTypeProductSale(), "Get type product sale"),
            () -> assertEquals(PRODUCT_TYPE, product.getProductType(), "Get product type"),
            () -> assertEquals(0, product.getPrice().compareTo(PRODUCT_PRICE), "Get price product"),
            () -> assertEquals(PRODUCT_QUANTITY_GOODS, product.getQuantityGoods(), "Get quantity goods product"),
            () -> assertEquals(PRODUCT_DISCOUNT.getValue(), product.getDiscount().getValue(), "Get discount product")
    );
  }

  @Test
  void constructorWithoutDiscount() {
    Product product = new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, PRODUCT_QUANTITY_GOODS);

    assertEquals(new Discount(0).getValue(), product.getDiscount().getValue(), "Get zero discount product");
  }

  @Test
  void negativePrice() {
    BigDecimal negativePrice = new BigDecimal(-20.0);

    assertThrows(IllegalArgumentException.class,
            () -> new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, negativePrice, PRODUCT_QUANTITY_GOODS),
            "Negative price product");
  }

  @Test
  void addNegativeQuantityGoods() {
    Integer productNegativeQuantityGoods = -1;

    assertThrows(IllegalArgumentException.class,
            () -> new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, productNegativeQuantityGoods),
            "Add negative quantity goods product");
  }

  @Test
  void deleteQuantityGoods() {
    Product product = new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, PRODUCT_QUANTITY_GOODS);
    Integer deleteProductQuantityGoods = 1;

    product.deleteQuantityGoods(deleteProductQuantityGoods);

    assertEquals(PRODUCT_QUANTITY_GOODS - deleteProductQuantityGoods, product.getQuantityGoods(), "Delete quantity goods product");
  }

  @Test
  void deleteNegativeQuantityGoods() {
    Product product = new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, PRODUCT_QUANTITY_GOODS);
    Integer productNegativeQuantityGoods = -3;

    assertThrows(IllegalArgumentException.class, () -> product.deleteQuantityGoods(productNegativeQuantityGoods), "Delete negative quantity goods product");
  }

  @Test
  void printInfoProduct() {
    Product product = new Product(PRODUCT_NAME, PRODUCT_ID, PRODUCT_TYPE_PRODUCT_SALE, PRODUCT_TYPE, PRODUCT_PRICE, PRODUCT_QUANTITY_GOODS, PRODUCT_DISCOUNT);

    product.printInfo();

    String expectedResult = "['milk': price - 35.00; type product - BY_COUNT; quantity goods - 5; discount - 50]";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print info product");
  }
}