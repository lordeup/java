package com.files.customer;

import com.files.customer.customercategory.CustomerCategory;
import com.files.product.Product;
import com.files.product.discount.Discount;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import com.files.supermarket.cashdesk.bill.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
  private final static CustomerCategory CUSTOMER_CHILD_CATEGORY = CustomerCategory.CHILD;
  private final static CustomerCategory CUSTOMER_RETIRED_CATEGORY = CustomerCategory.RETIRED;
  private final static CustomerCategory CUSTOMER_ADULT_CATEGORY = CustomerCategory.ADULT;
  private final static BigDecimal CUSTOMER_CASH = new BigDecimal(300.60);
  private final static BigDecimal CUSTOMER_BANK_CARD = new BigDecimal(10.20);
  private final static BigDecimal CUSTOMER_BONUS = new BigDecimal(3.20);

  private List<Product> products;
  private final ByteArrayOutputStream output = new ByteArrayOutputStream();
  private final Date date = new Date(1577871866);

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));

    products = new ArrayList<>();
    products.add(new Product("tea", 0, TypeProductSale.BY_COUNT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(89.0), 10, new Discount(25)));
    products.add(new Product("candy", 1, TypeProductSale.BY_WEIGHT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(50.0), 1600, new Discount(50)));
    products.add(new Product("cigarettes", 2, TypeProductSale.BY_COUNT, ProductType.PROHIBITED_TO_CHILDREN, new BigDecimal(125.61), 7));
  }

  @Test
  void constructorChildCategory() {
    Customer customer = new Customer(CUSTOMER_CHILD_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    String customerName = "customer#";

    assertAll("constructor",
            () -> assertEquals(CUSTOMER_CHILD_CATEGORY, customer.getCustomerCategory(), "Get customer category"),
            () -> assertEquals(0, customer.getCash().compareTo(CUSTOMER_CASH), "Get customer cash"),
            () -> assertEquals(0, customer.getBankCard().compareTo(CUSTOMER_BANK_CARD), "Get customer bank card"),
            () -> assertEquals(0, customer.getBonus().compareTo(CUSTOMER_BONUS), "Get customer bonus"),
            () -> assertEquals(customerName, customer.getName().substring(0, customerName.length()), "Get customer name"),
            () -> assertTrue(customer.getBasket().getProducts().isEmpty(), "Get basket customer")
    );
  }

  @Test
  void getPriceCashDeskAdultCategory() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    customer.addProductToBasket(date, 1, 0);

    assertEquals(0, customer.getPriceCashDesk().compareTo(new BigDecimal(89.0)), "Get customer price cash desk");
  }

  @Test
  void getRestMoneyAdultCategory() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    customer.addProductToBasket(date, 1, 0);

    assertEquals(0, customer.getRestMoney().compareTo(new BigDecimal(225.0)), "Get customer rest money");
  }

  @Test
  void addProductToBasketRetiredCategory() {
    Customer customer = new Customer(CUSTOMER_RETIRED_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    customer.addProductToBasket(date, 400, 1);

    assertEquals(1200, products.get(1).getQuantityGoods(), "Get quantity goods after add product to basket");
  }

  @Test
  void getBasketProduct() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    customer.addProductToBasket(date, 1, 0);

    assertEquals(1, customer.getBasketProduct().size(), "Get basket product");
  }

  @Test
  void paymentBill() {
    Customer customer = new Customer(CUSTOMER_RETIRED_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);

    customer.addProductToBasket(date, 2, 0);
    Bill bill = new Bill(customer.getPriceCashDesk());

    customer.paymentBill(bill, date);
    String expectedResult = "[09:17] Customer '" + customer.getName() + "' picked up 2 BY_COUNT of 'tea'[09:17] Customer '"
            + customer.getName() + "' with discount, [PAID] 3.20 by BONUS; 10.20 by BANK_CARD; 164.60 by CASH";
    String actualResult = output.toString().replaceAll("\n", "").replaceAll("\r", "");

    assertEquals(expectedResult, actualResult, "Print info payment bill");
  }

  @Test
  void paymentBillNegativeResidue() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
    Bill bill = new Bill(customer.getPriceCashDesk());

    assertThrows(IllegalArgumentException.class, () -> customer.paymentBill(bill, date), "Payment bill negative residue");
  }

  @Test
  void addProductToBasketLargeQuantityGoods() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);

    assertThrows(IllegalArgumentException.class,
            () -> customer.addProductToBasket(date, 232, 2),
            "Add product to basket large quantity goods");
  }

  @Test
  void addProductToBasketChildCategory() {
    Customer customer = new Customer(CUSTOMER_CHILD_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);

    assertThrows(IllegalArgumentException.class,
            () -> customer.addProductToBasket(date, 1, 2),
            "Add product to basket is not for children");
  }

  @Test
  void addProductToBasketNotEnoughMoney() {
    Customer customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);

    assertThrows(IllegalArgumentException.class,
            () -> customer.addProductToBasket(date, 4, 2),
            "Add product to basket not enough money");
  }
}