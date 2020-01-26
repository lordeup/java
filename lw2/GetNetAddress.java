import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class GetNetAddress {
  public enum NetworkAddresses {
    MASK,
    IP_ADDRESS
  }

  private final static int AMOUNT_ELEMENTS = 4;

  private static final String WRONG_IP_ADDRESS = "Wrong ip address";
  private static final String WRONG_MASK = "Wrong mask";
  private static final String PROGRAM_INFO = "GetNetAddress <IP address> <Subnet mask>";

  private static Integer[] parseLine(String line, NetworkAddresses networkAddresses) {
    List<Integer> numbersList = new ArrayList<>();
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher match = pattern.matcher(line);
    int num = 0;
    char lastChar = line.charAt(line.length() - 1);
    while (match.find() && num < AMOUNT_ELEMENTS) {
      String matchGroup = match.group();
      if (Integer.MAX_VALUE < Double.parseDouble(matchGroup) || matchGroup.charAt(0) == '-' || lastChar == '.') {
        throw new IllegalArgumentException(
            networkAddresses == NetworkAddresses.IP_ADDRESS ? WRONG_IP_ADDRESS : WRONG_MASK
        );
      }
      numbersList.add(Integer.valueOf(matchGroup));
      ++num;
    }

    Integer[] numbersArray = new Integer[numbersList.size()];
    numbersArray = numbersList.toArray(numbersArray);

    if (numbersArray.length < AMOUNT_ELEMENTS) {
      throw new IllegalArgumentException(
          networkAddresses == NetworkAddresses.IP_ADDRESS ? WRONG_IP_ADDRESS : WRONG_MASK
      );
    }

    return numbersArray;
  }

  private static void checkAddress(Integer[] numbersAddress) {
    if (numbersAddress[0] > 255) {
      throw new IllegalArgumentException(WRONG_IP_ADDRESS);
    }
  }

  private static boolean maskVerify(Integer[] numbersMask) {
    for (Integer number: numbersMask) {
      int difference = (int) Math.pow(2, 8) - number;
      if ((difference & (difference - 1)) != 0) {
        return true;
      }
    }
    return false;
  }

  private static void checkMask(Integer[] numbersMask) {
    if (numbersMask[0] > 255 || maskVerify(numbersMask)) {
      throw new IllegalArgumentException(WRONG_MASK);
    }
  }

  private static void addressVerification(Integer[] numbersAddress, Integer[] numbersMask) {
    int[] ipAddress = new int[AMOUNT_ELEMENTS];
    for (int i = 0; i < AMOUNT_ELEMENTS; ++i) {
      ipAddress[i] = numbersAddress[i] & numbersMask[i];
    }
    System.out.println(Arrays.stream(ipAddress).mapToObj(String::valueOf).collect(Collectors.joining(".")));
  }

  private static void validateArgs(String[] args) {
    if (args.length != 2) {
      throw new ArrayIndexOutOfBoundsException(PROGRAM_INFO);
    }
  }

  public static void main(String[] args) {
    String [] arrayInput;
    String str = "";

    try {
      Scanner scanner = new Scanner(System.in);
      str = scanner.nextLine();

    } catch (Exception error) {
      error.printStackTrace();
    }

    try {
      arrayInput = str.split(" ");

      validateArgs(arrayInput);
      String address = arrayInput[0];
      String mask = arrayInput[1];

      Integer[] numbersAddress = parseLine(address, NetworkAddresses.IP_ADDRESS);
      Integer[] numbersMask = parseLine(mask, NetworkAddresses.MASK);

      checkAddress(numbersAddress);
      checkMask(numbersMask);

      addressVerification(numbersAddress, numbersMask);

    } catch (Exception error) {
      System.out.println(error.getMessage());
    }
  }
}