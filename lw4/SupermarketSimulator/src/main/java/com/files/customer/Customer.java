package com.files.customer;

import com.files.product.Product;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import com.files.supermarket.cashdesk.bill.Bill;
import com.files.utils.Utils;
import com.files.customer.basket.Basket;
import com.files.customer.customercategory.CustomerCategory;
import com.files.customer.paymentmethod.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Customer {
  private final CustomerCategory customerCategory;
  private final Integer customerNumber;
  private static Integer allCustomerNumber = 0;
  private BigDecimal cash;
  private BigDecimal bankCard;
  private BigDecimal bonus;
  private BigDecimal restMoney;
  private final List<Product> products;
  private final Basket basket;
  private boolean isDiscount;

  public Customer(CustomerCategory customerCategory, BigDecimal cash, BigDecimal bankCard, BigDecimal bonus, List<Product> products) {
    this.customerCategory = customerCategory;
    this.cash = cash;
    this.bankCard = bankCard;
    this.bonus = bonus;
    this.products = products;
    this.restMoney = getAllMoney();
    basket = new Basket();
    isDiscount = false;
    ++allCustomerNumber;
    customerNumber = allCustomerNumber;
  }

  private BigDecimal getPricePayment(BigDecimal residue, BigDecimal money) {
    return residue.compareTo(money) < 0 ? residue : money;
  }

  public void paymentBill(Bill bill, Date date) {
    BigDecimal residue = bill.getPrice();

    if (residue.signum() <= 0) {
      throw new IllegalArgumentException("Customer '" + getName() + "' residue must be greater than 0");
    }

    StringBuilder stringBuilder = new StringBuilder();
    printInfoPaymentBill(stringBuilder, date);

    if (bonus.signum() > 0) {
      BigDecimal bonusPayment = Utils.roundingNumber(getPricePayment(residue, bonus));
      setBonus(bonus.subtract(bonusPayment));
      residue = residue.subtract(bonusPayment);
      stringBuilder.append(" ").append(bonusPayment).append(" by ").append(PaymentMethod.BONUS);
    }
    if (bankCard.signum() > 0 && residue.signum() > 0) {
      BigDecimal bankCardPayment = Utils.roundingNumber(getPricePayment(residue, bankCard));
      setBankCard(bankCard.subtract(bankCardPayment));
      residue = residue.subtract(bankCardPayment);
      stringBuilder.append("; ").append(bankCardPayment).append(" by ").append(PaymentMethod.BANK_CARD);
    }
    if (residue.signum() > 0) {
      residue = Utils.roundingNumber(residue);
      setCash(cash.subtract(residue));
      stringBuilder.append("; ").append(residue).append(" by ").append(PaymentMethod.CASH);
    }
    System.out.println(stringBuilder);
  }

  private void printInfoPaymentBill(StringBuilder stringBuilder, Date date) {
    stringBuilder.append(Utils.getInfoTime(date)).append("Customer '").append(getName()).append("' ");
    if (isDiscount) {
      stringBuilder.append("with discount, ");
    }
    stringBuilder.append("[PAID]");
  }

  public void addProductToBasket(Date date, Integer quantityGoods, int index) {
    Product product = products.get(index);

    BigDecimal productPrice = getProductPriceToCheck(product, quantityGoods);
    restMoney = restMoney.subtract(productPrice);
    product.deleteQuantityGoods(quantityGoods);

    Product productToBasket = new Product(product.getName(), product.getId(), product.getTypeProductSale(),
            product.getProductType(), product.getPrice(), quantityGoods, product.getDiscount());
    basket.addBasket(productToBasket);

    printInfoAddProductToBasket(product, quantityGoods, date);
  }

  private void printInfoAddProductToBasket(Product product, Integer quantityGoods, Date date) {
    String str = Utils.getInfoTime(date) + "Customer '" + getName() + "' picked up "
            + quantityGoods + " " + product.getTypeProductSale().name() + " of '" + product.getName() + "'";
    System.out.println(str);
  }

  private BigDecimal getProductPriceToCheck(Product product, Integer quantityGoods) {
    if (customerCategory == CustomerCategory.CHILD && product.getProductType() == ProductType.PROHIBITED_TO_CHILDREN) {
      throw new IllegalArgumentException("Customer '" + getName() + "' picked up '" + product.getName() + "' which is not for children");
    }

    if (product.getQuantityGoods() < quantityGoods) {
      throw new IllegalArgumentException("Customer '" + getName() + "' picked up '" + product.getName() + "' more products than there are in the supermarket");
    }

    BigDecimal price = getTypeProductSalePrice(product, quantityGoods, product.getPrice());
    price = getDiscountPrice(product, price);

    if (restMoney.compareTo(price) < 0) {
      throw new IllegalArgumentException("Customer '" + getName() + "' not enough money for the product");
    }

    return price;
  }

  private BigDecimal getTypeProductSalePrice(Product product, Integer quantityGoods, BigDecimal price) {
    if (product.getTypeProductSale() == TypeProductSale.BY_COUNT) {
      return price.multiply(new BigDecimal(quantityGoods));
    } else {
      return price.multiply(new BigDecimal(quantityGoods / 1000));
    }
  }

  private BigDecimal getDiscountPrice(Product product, BigDecimal price) {
    Integer value = product.getDiscount().getValue();
    if (customerCategory == CustomerCategory.RETIRED && value != 0) {
      isDiscount = true;
      return price.multiply(new BigDecimal(1 - value / 100));
    } else {
      return price;
    }
  }

  public BigDecimal getPriceCashDesk() {
    return this.getAllMoney().subtract(restMoney);
  }

  BigDecimal getRestMoney() {
    return Utils.roundingNumber(restMoney);
  }

  private BigDecimal getAllMoney() {
    return cash.add(bankCard).add(bonus);
  }

  public String getName() {
    return "customer#" + customerNumber;
  }

  public BigDecimal getCash() {
    return cash;
  }

  public BigDecimal getBankCard() {
    return bankCard;
  }

  public BigDecimal getBonus() {
    return bonus;
  }

  public Basket getBasket() {
    return basket;
  }

  public CustomerCategory getCustomerCategory() {
    return customerCategory;
  }

  public List<Product> getBasketProduct() {
    return basket.getProducts();
  }

  private void setCash(BigDecimal cash) {
    this.cash = cash;
  }

  private void setBankCard(BigDecimal bankCard) {
    this.bankCard = bankCard;
  }

  private void setBonus(BigDecimal bonus) {
    this.bonus = bonus;
  }
}
