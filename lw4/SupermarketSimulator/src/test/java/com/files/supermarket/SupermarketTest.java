package com.files.supermarket;

import com.files.customer.Customer;
import com.files.customer.customercategory.CustomerCategory;
import com.files.product.Product;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupermarketTest {
  private final static CustomerCategory CUSTOMER_ADULT_CATEGORY = CustomerCategory.ADULT;
  private final static BigDecimal CUSTOMER_CASH = new BigDecimal(300.60);
  private final static BigDecimal CUSTOMER_BANK_CARD = new BigDecimal(10.20);
  private final static BigDecimal CUSTOMER_BONUS = new BigDecimal(3.20);
  private final Product product = new Product("cigarettes", 0, TypeProductSale.BY_COUNT, ProductType.PROHIBITED_TO_CHILDREN, new BigDecimal(125.61), 7);
  private final Date date = new Date(1577871866);

  private final ByteArrayOutputStream output = new ByteArrayOutputStream();

  private List<Product> products;
  private Supermarket supermarket;
  private Customer customer;

  String replaceStr(String str) {
    return str.replaceAll("\n", "").replaceAll("\r", "");
  }

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));

    supermarket = new Supermarket();
    products = new ArrayList<>();
    products.add(product);
    customer = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
  }

  @Test
  void constructor() {
    assertAll("constructor",
            () -> assertTrue(supermarket.getProducts().isEmpty(), "Get products supermarket"),
            () -> assertTrue(supermarket.getCustomers().isEmpty(), "Get customers supermarket"),
            () -> assertFalse(supermarket.isOpen(), "Is open supermarket")
    );
  }

  @Test
  void openSupermarket() {
    supermarket.openSupermarket(date);

    assertTrue(supermarket.isOpen(), "Open supermarket");
  }

  @Test
  void openSupermarketPrintInfo() {
    supermarket.openSupermarket(date);

    String expectedResult = "[09:17] Supermarket is opened";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Open supermarket print info");
  }

  @Test
  void closeSupermarket() {
    supermarket.openSupermarket(date);
    supermarket.closeSupermarket(date);

    assertFalse(supermarket.isOpen(), "Close supermarket");
  }

  @Test
  void addCustomerToSupermarket() {
    supermarket.addCustomerToSupermarket(customer);

    assertEquals(customer, supermarket.getCustomer(0), "Add customer to supermarket");
  }

  @Test
  void deleteCustomer() {
    supermarket.addCustomerToSupermarket(customer);
    supermarket.deleteCustomer(customer);

    assertTrue(supermarket.getCustomers().isEmpty(), "Delete customer");
  }

  @Test
  void deleteCustomerEmptyCustomers() {
    assertThrows(IllegalArgumentException.class, () -> supermarket.deleteCustomer(customer), "Delete customer empty customers");
  }

  @Test
  void deleteAllCustomer() {
    supermarket.addProduct(product);
    Customer customer1 = new Customer(CUSTOMER_ADULT_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);

    supermarket.addCustomerToSupermarket(customer);
    supermarket.addCustomerToSupermarket(customer1);

    customer.addProductToBasket(date, 1, 0);
    supermarket.deleteAllCustomer();

    assertTrue(supermarket.getCustomers().isEmpty(), "Delete all customer");
  }

  @Test
  void deleteAllCustomerEmptyCustomers() {
    assertThrows(IllegalArgumentException.class, supermarket::deleteAllCustomer, "Delete all customer empty customers");
  }

  @Test
  void getCashDesk() {
    assertTrue(supermarket.getCashDesk().getCustomers().isEmpty(), "Get cash desk");
  }

  @Test
  void getProduct() {
    supermarket.addProduct(product);

    assertEquals(product, supermarket.getProduct(0), "Get product");
  }

  @Test
  void printInfoProducts() {
    supermarket.addProduct(product);
    supermarket.printInfoProducts(date);

    String expectedResult = "[09:17] Supermarket products have been formed:" +
            "['cigarettes': price - 125.61; type product - BY_COUNT; quantity goods - 7]";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print info products");
  }

  @Test
  void printInfoNewCustomer() {
    supermarket.addCustomerToSupermarket(customer);

    supermarket.printInfoNewCustomer(date, customer);

    String expectedResult = "[09:17] New customer '" + customer.getName() + "' arrived; category - ADULT; cash - 300.60; bank card - 10.20; bonus - 3.20";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print info new customer");
  }

}