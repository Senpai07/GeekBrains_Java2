package lesson2;

public class MyArrayDataException extends Exception {

  public MyArrayDataException(int col, int row) {
    super(String.format("В ячейке [%d][%d] неверное значение!", col, row));
  }
}
