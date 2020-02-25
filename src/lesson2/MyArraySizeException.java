package lesson2;

public class MyArraySizeException extends Exception {
  public MyArraySizeException() {
    super("Размер массива не соответствует 4х4!");
  }
}
