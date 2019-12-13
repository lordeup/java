package com.lw3.oop.supermarket;

import com.lw3.oop.customer.Customer;
import com.lw3.oop.product.Product;
import com.lw3.oop.supermarket.cashdesk.CashDesk;
import com.lw3.oop.supermarket.report.Report;
import com.lw3.oop.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supermarket {
  private final List<Product> products;
  private final List<Customer> customers;
  private final CashDesk cashDesk;
  private final Report report;
  private boolean isOpen;

  public Supermarket() {
    products = new ArrayList<>();
    customers = new ArrayList<>();
    report = new Report();
    cashDesk = new CashDesk(report);
    isOpen = false;
  }

  public void openSupermarket(Date date) {
    if (!isOpen) {
      isOpen = true;
      System.out.println(Utils.getInfoTime(date) + "Supermarket is opened");
    }
  }

  public void closeSupermarket(Date date) {
    if (isOpen) {
      isOpen = false;
      System.out.println(Utils.getInfoTime(date) + "Supermarket is closed");
      report.printDailyInfo();
    }
  }

  public void addCustomerToSupermarket(Customer customer) {
    customers.add(customer);
  }

  public void deleteCustomer(Customer customer) {
    if (customers.isEmpty()) {
      throw new IllegalArgumentException("No customers for removing from supermarket");
    }
    customers.remove(customer);
  }

  public void deleteAllCustomer() {
    if (customers.isEmpty()) {
      throw new IllegalArgumentException("No customers for full cleaning from supermarket");
    }
    clearBasketCustomer();
    customers.clear();
  }

  private void clearBasketCustomer() {
    for (Customer customer: customers) {
      for (Product product: customer.getBasketProduct()) {
        Product findProduct = getProduct(product.getId());
        findProduct.addQuantityGoods(product.getQuantityGoods());
      }
    }
  }

  public void printInfoProducts(Date date) {
    System.out.println(Utils.getInfoTime(date) + "Supermarket products have been formed:");
    for (Product product : products) {
      product.printInfo();
    }
  }

  public void printInfoNewCustomer(Date date, Customer customer) {
    String str = Utils.getInfoTime(date) + "New customer '" + customer.getName() + "' arrived; category - " +
            customer.getCustomerCategory().name() + "; cash - " + customer.getCash() + "; bank card - " + customer.getBankCard() + "; bonus - " + customer.getBonus();
    System.out.println(str);
  }

  public Customer getCustomer(int index) {
    return customers.get(index);
  }

  public Product getProduct(int index) {
    return products.get(index);
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public List<Product> getProducts() {
    return products;
  }

  public List<Customer> getCustomers() {
    return customers;
  }

  public CashDesk getCashDesk() {
    return cashDesk;
  }
}
