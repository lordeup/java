import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class PasswordGenerator {

  private static final String PROGRAM_INFO = "PasswordGenerator <Length> <Alphabet>";

  private static Integer parseLengthPassword(String line) {
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher match = pattern.matcher(line);
    if (match.find()) {
      String matchGroup = match.group();
      if (Integer.MAX_VALUE < Double.parseDouble(matchGroup)) {
        throw new ArrayStoreException(PROGRAM_INFO);
      }
      return Integer.parseInt(matchGroup);
    } else {
      throw new ArrayStoreException(PROGRAM_INFO);
    }
  }

  private static StringBuilder getPassword(Integer length, String passwordStr) {
    if (length <= 0) {
      throw new ArrayStoreException(PROGRAM_INFO);
    }

    StringBuilder randString = new StringBuilder();
    for (int i = 0; i < length; ++i) {
      randString.append(passwordStr.charAt((int)(Math.random() * passwordStr.length())));
    }
    return randString;
  }

  private static void validateArgs(String[] arrayInput) {
    if (arrayInput.length != 2) {
      throw new ArrayStoreException(PROGRAM_INFO);
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
      String lengthPasswordStr = arrayInput[0];
      String passwordStr = arrayInput[1];

      Integer length = parseLengthPassword(lengthPasswordStr);
      StringBuilder password = getPassword(length, passwordStr);
      System.out.println(password);

    } catch (Exception error) {
      System.out.println(error.getMessage());
    }
  }
}