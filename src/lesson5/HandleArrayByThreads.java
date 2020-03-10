package lesson5;

import java.util.Arrays;

public class HandleArrayByThreads {
  static final int size = 10000000;
  static final int h = size / 2;

  public static void main(String[] args) {
    float[] arr = new float[size];
    Arrays.fill(arr, 1);
    long a = System.currentTimeMillis();
    handleArray(arr);
    System.out.printf("Handle without threads: %d%n", System.currentTimeMillis() - a);

    float[] halfArr1 = new float[h];
    float[] halfArr2 = new float[h];
    Arrays.fill(arr, 1);
    a = System.currentTimeMillis();
    // Split array
    System.arraycopy(arr, 0, halfArr1, 0, h);
    System.arraycopy(arr, h, halfArr2, 0, h);
    Thread thread1 = new Thread(() -> handleArray(halfArr1));
    Thread thread2 = new Thread(() -> handleArray(halfArr2, h));
    thread1.start();
    thread2.start();
    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Join array
    System.arraycopy(halfArr1, 0, arr, 0, h);
    System.arraycopy(halfArr2, 0, arr, h, h);

    System.out.printf("Handle with threads: %d", System.currentTimeMillis() - a);
  }

  private static void handleArray(float[] arr, int offset) {
    for (int i = 0; i < arr.length - 1; i++) {
      arr[i] =
          (float)
              (arr[i]
                  * Math.sin(0.2f + (i + offset) / 5)
                  * Math.cos(0.2f + (i + offset) / 5)
                  * Math.cos(0.4f + (i + offset) / 2));
    }
  }

  private static void handleArray(float[] arr) {
    handleArray(arr, 0);
  }
}
