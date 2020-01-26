class TypesTable {
  public static void main(String[] args) {
    System.out.print("Long" + "\t");
    System.out.print(Long.MIN_VALUE + "\t");
    System.out.print(Long.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Long.BYTES));

    System.out.print("Integer" + "\t");
    System.out.print(Integer.MIN_VALUE + "\t");
    System.out.print(Integer.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Integer.BYTES));

    System.out.print("Short" + "\t");
    System.out.print(Short.MIN_VALUE + "\t");
    System.out.print(Short.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Short.BYTES));

    System.out.print("Byte" + "\t");
    System.out.print(Byte.MIN_VALUE + "\t");
    System.out.print(Byte.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Byte.BYTES));

    System.out.print("Double" + "\t");
    System.out.print(Double.MIN_VALUE + "\t");
    System.out.print(Double.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Double.BYTES));

    System.out.print("Float" + "\t");
    System.out.print(Float.MIN_VALUE + "\t");
    System.out.print(Float.MAX_VALUE + "\t");
    System.out.println(String.valueOf(Float.BYTES));
  }
}