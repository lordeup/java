package com.files.controller;

import com.files.product.Product;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import com.files.supermarket.Supermarket;
import com.files.supermarket.cashdesk.CashDesk;
import com.files.utils.Utils;
import com.files.customer.Customer;
import com.files.customer.customercategory.CustomerCategory;
import com.files.product.discount.Discount;

import java.math.BigDecimal;
import java.util.Date;

public class Controller {

  private final Supermarket supermarket;

  public Controller(Supermarket supermarket) {
    this.supermarket = supermarket;
  }

  public void addProductInSupermarket(Date date) {
    supermarket.addProduct(new Product("candy", 0, TypeProductSale.BY_WEIGHT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(50.0), 1600));
    supermarket.addProduct(new Product("tea", 1, TypeProductSale.BY_COUNT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(89.0), 10, new Discount(50)));
    supermarket.addProduct(new Product("apples", 2, TypeProductSale.BY_WEIGHT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(123.57), 2000, new Discount(75)));
    supermarket.addProduct(new Product("coffee", 3, TypeProductSale.BY_COUNT, ProductType.ALLOWED_TO_CHILDREN, new BigDecimal(202.52), 25, new Discount(30)));
    supermarket.addProduct(new Product("cigarettes", 4, TypeProductSale.BY_COUNT, ProductType.PROHIBITED_TO_CHILDREN, new BigDecimal(125.61), 7));
    supermarket.addProduct(new Product("alcohol", 5, TypeProductSale.BY_COUNT, ProductType.PROHIBITED_TO_CHILDREN, new BigDecimal(540.30), 30));

    supermarket.printInfoProducts(date);
    System.out.println();
  }

  private void addCustomerInCashDesk() {
    int customerSize = supermarket.getCustomers().size();

    if (customerSize > 0) {
      int customerIndex = Utils.getRandomCount(customerSize);
      Customer customer = supermarket.getCustomer(customerIndex);
      if (customer.getBasketProduct().size() > 0) {
        supermarket.getCashDesk().addCustomerToCashDesk(customer);
      }
    }
  }

  private void serviceCustomer(Date date) {
    CashDesk cashDesk = supermarket.getCashDesk();
    if (cashDesk.getCustomers().size() > 0) {
      Customer customer = cashDesk.getCustomer();
      customer.paymentBill(cashDesk.serviceCustomer(date), date);
      cashDesk.deleteCustomerToCashDesk();
      supermarket.deleteCustomer(customer);
    }
  }

  private void addProductInBasket(Date date) {
    int customerSize = supermarket.getCustomers().size();
    int productsSize = supermarket.getProducts().size();

    if (customerSize > 0 && productsSize > 0) {
      int customerIndex = Utils.getRandomCount(customerSize);
      int productIndex = Utils.getRandomCount(productsSize);

      Integer quantityGoodsOfProduct = supermarket.getProduct(productIndex).getQuantityGoods();
      int maxQuantityGoodsOfProduct = quantityGoodsOfProduct > 0 ? quantityGoodsOfProduct : 1;
      int quantityGoods = Utils.getRandomCount(maxQuantityGoodsOfProduct) + 1;

      supermarket.getCustomer(customerIndex).addProductToBasket(date, quantityGoods, productIndex);
    }
  }

  void addCustomerInSupermarket(Date date) {
    int category = Utils.getRandomCount(CustomerCategory.values().length);
    BigDecimal cash = Utils.getRandomMoney(new BigDecimal(0), new BigDecimal(1000));
    BigDecimal bankCard = Utils.getRandomMoney(new BigDecimal(0), new BigDecimal(3000));
    BigDecimal bonus = Utils.getRandomMoney(new BigDecimal(0), new BigDecimal(100));

    Customer customer = new Customer(CustomerCategory.values()[category], cash, bankCard, bonus, supermarket.getProducts());
    supermarket.addCustomerToSupermarket(customer);
    supermarket.printInfoNewCustomer(date, customer);
  }

  public void startSupermarket(Date date, Date dateClose) {
    Utils.randomTime(date);

    supermarket.openSupermarket(date);
    addCustomerInSupermarket(date);

    while (supermarket.getCustomers().size() > 0 || date.getTime() < dateClose.getTime()) {
      try {
        int randomNumber = Utils.getRandomCount(4);
        Utils.randomTime(date);

        if (date.getTime() >= dateClose.getTime()) {
          supermarket.deleteAllCustomer();
          break;
        }

        if (randomNumber == 0) {
          serviceCustomer(date);
        } else if (randomNumber == 1) {
          addCustomerInCashDesk();
        } else if (randomNumber == 2) {
          addCustomerInSupermarket(date);
        } else {
          addProductInBasket(date);
        }

      } catch (IllegalArgumentException error) {
        System.out.println(error.getMessage());
      }
    }
    supermarket.closeSupermarket(date);
  }
}
