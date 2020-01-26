package com.files.controller;

import com.files.supermarket.Supermarket;
import com.files.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

  private final ByteArrayOutputStream output = new ByteArrayOutputStream();
  private final Date date = new Date(1577871866);
  private Supermarket supermarket;
  private Controller controller;

  @BeforeEach
  void init() {
    System.setOut(new PrintStream(output));

    supermarket = new Supermarket();
    controller = new Controller(supermarket);
  }

  @Test
  void addProductInSupermarket() {
    controller.addProductInSupermarket(date);

    assertEquals(6, supermarket.getProducts().size());
  }

  @Test
  void addCustomerInSupermarket() {
    controller.addCustomerInSupermarket(date);

    assertEquals(1, supermarket.getCustomers().size(), "Add customer in supermarket");
  }

  @Test
  void startSupermarket() {
    Date dateClose = new Date(date.getTime() + (Utils.ONE_HOUR * 12));
    controller.startSupermarket(date, dateClose);

    assertAll("startSupermarket",
            () -> assertTrue(supermarket.getCustomers().isEmpty(), "Get customers supermarket"),
            () -> assertFalse(supermarket.isOpen(), "Close supermarket")
    );
  }
}