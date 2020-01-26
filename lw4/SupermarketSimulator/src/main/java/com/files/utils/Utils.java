package com.files.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {
  public static final Integer ONE_HOUR = 3600000;

  public static String getInfoTime(Date date) {
    return "[" + new SimpleDateFormat("HH:mm").format(date) + "] ";
  }

  public static BigDecimal roundingNumber(BigDecimal number) {
    return number.setScale(2, RoundingMode.HALF_UP);
  }

  public static int getRandomCount(int limitation) {
    return new Random().nextInt(limitation);
  }

  public static BigDecimal getRandomMoney(BigDecimal minTotal, BigDecimal maxTotal) {
    BigDecimal randomBigDecimal = minTotal.add(new BigDecimal(Math.random()).multiply(maxTotal.subtract(minTotal)));
    return roundingNumber(randomBigDecimal);
  }

  public static void randomTime(Date date) {
    date.setTime(date.getTime() + getRandomCount(ONE_HOUR / 2));
  }
}
