package lesson2;

public class Lesson2 {
  public static void main(String[] args) {

    String[][] strArr =
        new String[][] {
          {"3", "2", "1", "0"},
          {"4", "4", "4", "4"},
          {"2", "3", "4", "5"},
          {"69", "77", "88", "100"}
        };

    try {
      int result = cntArrayValues(strArr);
      System.out.printf("Сумма всех элементов массива: %d%n", result);
    } catch (MyArraySizeException | MyArrayDataException e) {
      System.out.println(e.getMessage());
    }
  }

  public static int cntArrayValues(String[][] arr)
      throws MyArraySizeException, MyArrayDataException {
    int count = 0;
    if (arr.length != 4) {
      throw new MyArraySizeException();
    }
    for (int row = 0; row < arr.length; row++) {
      if (arr[row].length != 4) {
        throw new MyArraySizeException();
      }
      for (int col = 0; col < arr[row].length; col++) {
        try {
          count += Integer.parseInt(arr[row][col]);
        } catch (NumberFormatException e) {
          throw new MyArrayDataException(col, row);
        }
      }
    }
    return count;
  }
}
