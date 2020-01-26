import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class CaesarCipher {

  private enum OperatingMode {
    ENCODING_MODE,
    DECODING_MODE
  }

  private static final int NUMBER_ALPHABET = 26;
  private static final int NUMBER_UPPERCASE_LETTER = 65;
  private static final int NUMBER_LOWERCASE_LETTER = 97;

  private static final String CODING = "-e";
  private static final String DECODING = "-d";

  private static final String PROGRAM_INFO = "CaesarCipher <Mode> <Key> <Text>";

  private static Integer parseKey(String line) {
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher match = pattern.matcher(line);
    if (match.find()) {
      String matchGroup = match.group();
      if (Integer.MAX_VALUE < Double.parseDouble(matchGroup)) {
        throw new IllegalArgumentException(PROGRAM_INFO);
      }
      return Integer.parseInt(matchGroup);
    } else {
      throw new IllegalArgumentException(PROGRAM_INFO);
    }
  }


  private static OperatingMode getMode(String mode) {
    if (mode.equals(CODING)) {
      return OperatingMode.ENCODING_MODE;
    } else if (mode.equals(DECODING)) {
      return OperatingMode.DECODING_MODE;
    } else {
      throw new IllegalArgumentException(PROGRAM_INFO);
    }
  }

  private static String encoding(int key, String line) {
    StringBuilder result = new StringBuilder();
    int numCh;
    for (int i = 0; i < line.length(); i++) {
      int numberChar = line.charAt(i);
      int differenceKey = numberChar + key;
      if (numberChar >= NUMBER_LOWERCASE_LETTER) {
        numCh = (differenceKey - NUMBER_LOWERCASE_LETTER) % NUMBER_ALPHABET + NUMBER_LOWERCASE_LETTER;
      } else {
        numCh = (differenceKey - NUMBER_UPPERCASE_LETTER) % NUMBER_ALPHABET + NUMBER_UPPERCASE_LETTER;
      }
      result.append((char) numCh);
    }
    return result.toString();
  }

  private static String decoding(int key, String line) {
    StringBuilder result = new StringBuilder();
    int numCh;
    for (int i = 0; i < line.length(); i++) {
      int numberChar = line.charAt(i);
      int differenceKey = numberChar - key;
      if (numberChar >= NUMBER_LOWERCASE_LETTER) {
        int number = (differenceKey - NUMBER_LOWERCASE_LETTER) % NUMBER_ALPHABET;
        numCh =  number + NUMBER_LOWERCASE_LETTER;
        if (number < 0 && numCh != NUMBER_LOWERCASE_LETTER) {
          numCh += NUMBER_ALPHABET;
        }
      } else {
        int number = (differenceKey - NUMBER_UPPERCASE_LETTER) % NUMBER_ALPHABET;
        numCh =  number + NUMBER_UPPERCASE_LETTER;
        if (number < 0 && numCh != NUMBER_UPPERCASE_LETTER) {
          numCh += NUMBER_ALPHABET;
        }
      }
      result.append((char) numCh);
    }
    return result.toString();
  }

  private static void validateArgs(String[] arrayInput) {
    if (arrayInput.length != 3) {
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
      OperatingMode operatingMode = getMode(arrayInput[0]);
      String lengthKey = arrayInput[1];
      String line = arrayInput[2];

      int key = parseKey(lengthKey);

      String result = operatingMode == OperatingMode.ENCODING_MODE ? encoding(key, line) : decoding(key, line);

      System.out.println(result);

    } catch (Exception error) {
      System.out.println(error.getMessage());
    }
  }
}