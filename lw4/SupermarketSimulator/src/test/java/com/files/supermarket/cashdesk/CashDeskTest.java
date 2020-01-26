package com.files.supermarket.cashdesk;

import com.files.customer.Customer;
import com.files.customer.customercategory.CustomerCategory;
import com.files.product.Product;
import com.files.product.discount.Discount;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import com.files.supermarket.cashdesk.bill.Bill;
import com.files.supermarket.report.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CashDeskTest {
  private final static CustomerCategory CUSTOMER_CHILD_CATEGORY = CustomerCategory.CHILD;
  private final static BigDecimal CUSTOMER_CASH = new BigDecimal(300.60);
  private final static BigDecimal CUSTOMER_BANK_CARD = new BigDecimal(10.20);
  private final static BigDecimal CUSTOMER_BONUS = new BigDecimal(3.20);

  private final ByteArrayOutputStream output = new ByteArrayOutputStream();
  private final Date date = new Date(1577871866);

  private CashDesk cashDesk;
  private Customer customer;

  String replaceStr(String str) {
    return str.replaceAll("\n", "").replaceAll("\r", "");
  }

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));

    List<Product> products = new ArrayList<>();
    products.add(new Product("tea", 0, TypeProductSale.BY_COUNT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(89.0), 10, new Discount(25)));
    products.add(new Product("candy", 1, TypeProductSale.BY_WEIGHT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(50.0), 1600, new Discount(50)));
    products.add(new Product("cigarettes", 2, TypeProductSale.BY_COUNT, ProductType.PROHIBITED_TO_CHILDREN, new BigDecimal(125.61), 7));

    cashDesk = new CashDesk(new Report());
    customer = new Customer(CUSTOMER_CHILD_CATEGORY, CUSTOMER_CASH, CUSTOMER_BANK_CARD, CUSTOMER_BONUS, products);
  }

  @Test
  void getCustomers() {
    assertTrue(cashDesk.getCustomers().isEmpty(), "Get customers");
  }

  @Test
  void addCustomerToCashDesk() {
    cashDesk.addCustomerToCashDesk(customer);

    assertEquals(customer, cashDesk.getCustomer(), "Add customer to cash desk");
  }

  @Test
  void deleteCustomerToCashDesk() {
    cashDesk.addCustomerToCashDesk(customer);
    cashDesk.deleteCustomerToCashDesk();

    assertTrue(cashDesk.getCustomers().isEmpty(), "Delete customer to cash desk");
  }

  @Test
  void deleteCustomerToCashDeskEmptyCustomers() {
    assertThrows(IllegalArgumentException.class, cashDesk::deleteCustomerToCashDesk, "Delete customer to cash desk empty customers");
  }

  @Test
  void serviceCustomer() {
    customer.addProductToBasket(date, 1, 0);
    cashDesk.addCustomerToCashDesk(customer);
    Bill bill = cashDesk.serviceCustomer(date);

    assertEquals(0, bill.getPrice().compareTo(new BigDecimal(89.0)), "Service customer");
  }

  @Test
  void printInfo() {
    customer.addProductToBasket(date, 1, 0);
    cashDesk.addCustomerToCashDesk(customer);
    cashDesk.serviceCustomer(date);

    String expectedResult = "[09:17] Customer '" + customer.getName() + "' picked up 1 BY_COUNT of 'tea'[09:17] " +
            "Customer '" + customer.getName() + "' at the cash desk, amount to pay: 89.00";
    String actualResult = replaceStr(output.toString());

    assertEquals(expectedResult, actualResult, "Print info cash desk");
  }

  @Test
  void serviceCustomerEmptyCustomers() {
    assertThrows(IllegalArgumentException.class, () -> cashDesk.serviceCustomer(date), "Service customer empty customers");
  }
}