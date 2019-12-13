package com.lw3.oop;

import com.lw3.oop.controller.Controller;
import com.lw3.oop.supermarket.Supermarket;
import com.lw3.oop.utils.Utils;

import java.util.Date;

public class SupermarketSimulator {
    public static void main(String[] args) {
        Controller controller = new Controller(new Supermarket());

        Date date = new Date();
        Date dateClose = new Date(date.getTime() + (Utils.ONE_HOUR * 12));

        controller.addProductInSupermarket(date);
        controller.startSupermarket(date, dateClose);
    }
}