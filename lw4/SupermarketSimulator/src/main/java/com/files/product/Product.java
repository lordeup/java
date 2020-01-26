package com.files.product;

import com.files.product.discount.Discount;
import com.files.product.producttype.ProductType;
import com.files.product.typeproductsale.TypeProductSale;
import com.files.utils.Utils;

import java.math.BigDecimal;

public class Product {
  private final String name;
  private final Integer id;
  private BigDecimal price;
  private final TypeProductSale typeProductSale;
  private final ProductType productType;
  private final Discount discount;
  private Integer quantityGoods = 0;

  public Product(String name, Integer id, TypeProductSale typeProductSale, ProductType productType, BigDecimal price, Integer quantityGoods) {
    this(name, id, typeProductSale, productType, price, quantityGoods, new Discount(0));
  }

  public Product(String name, Integer id, TypeProductSale typeProductSale, ProductType productType, BigDecimal price, Integer quantityGoods, Discount discount) {
    this.priceCheck(price);
    this.addQuantityGoods(quantityGoods);
    this.name = name;
    this.id = id;
    this.typeProductSale = typeProductSale;
    this.productType = productType;
    this.discount = discount;
  }

  private void priceCheck(BigDecimal price) {
    if (price.signum() <= 0) {
      throw new IllegalArgumentException("The price must be positive");
    }
    this.price = Utils.roundingNumber(price);
  }

  public void addQuantityGoods(Integer quantityGoods) {
    if (quantityGoods <= 0) {
      throw new IllegalArgumentException("The number of products when adding must be positive");
    }
    this.quantityGoods += quantityGoods;
  }

  public void deleteQuantityGoods(Integer quantityGoods) {
    if (quantityGoods <= 0) {
      throw new IllegalArgumentException("The number of products when deleting must be positive");
    }
    this.quantityGoods -= quantityGoods;
  }

  public void printInfo() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("['").append(name).append("': price - ").append(price).append("; type product - ")
            .append(typeProductSale.name()).append("; quantity goods - ").append(quantityGoods);

    if (discount.getValue() != 0) {
      stringBuilder.append("; discount - ").append(discount.getValue());
    }

    stringBuilder.append("]");
    System.out.println(stringBuilder);
  }

  public String getName() {
    return name;
  }

  public Integer getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public TypeProductSale getTypeProductSale() {
    return typeProductSale;
  }

  public ProductType getProductType() {
    return productType;
  }

  public Discount getDiscount() {
    return discount;
  }

  public Integer getQuantityGoods() {
    return quantityGoods;
  }
}