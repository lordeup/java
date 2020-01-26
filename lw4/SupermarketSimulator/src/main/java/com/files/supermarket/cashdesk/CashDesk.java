package com.files.supermarket.cashdesk;

import com.files.customer.Customer;
import com.files.supermarket.cashdesk.bill.Bill;
import com.files.supermarket.report.Report;
import com.files.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

public class CashDesk {
  private final Queue<Customer> customers;
  private final Report report;

  public CashDesk(Report report) {
    customers = new ArrayDeque<>();
    this.report = report;
  }

  public void addCustomerToCashDesk(Customer customer) {
    customers.add(customer);
  }

  public void deleteCustomerToCashDesk() {
    if (customers.isEmpty()) {
      throw new IllegalArgumentException("No customers for removing from cash desk");
    }
    customers.remove();
  }

  public Bill serviceCustomer(Date date) {
    if (customers.isEmpty()) {
      throw new IllegalArgumentException("No customers");
    }

    Customer customer = getCustomer();
    BigDecimal priceCashDesk = customer.getPriceCashDesk();

    if (priceCashDesk.signum() > 0) {
      printInfo(date, customer, priceCashDesk);
    }

    return new Bill(priceCashDesk);
  }

  private void printInfo(Date date, Customer customer, BigDecimal price) {
    System.out.println(Utils.getInfoTime(date) + "Customer '" + customer.getName()
            + "' at the cash desk, amount to pay: " + Utils.roundingNumber(price));
    report.addBasketReport(customer.getBasket(), price);
  }

  public Queue<Customer> getCustomers() {
    return customers;
  }

  public Customer getCustomer() {
    return customers.element();
  }
}
