package lesson3.task1;

import java.util.*;

/** Task 1 */
public class Main {
  public static void main(String[] args) {
    String[] words = {
      "танк", "самолет", "поезд", "танк", "ракета", "бмп", "бтр", "самолет", "танк", "катер",
      "танк", "поезд", "самолет"
    };

    getUnique(words);
    countEntry(words);
  }

  /**
   * Вывести список уникальных слов.
   *
   * @param arrWords String[]
   */
  public static void getUnique(String[] arrWords) {
    Set<String> uniqueSet = new LinkedHashSet<>(Arrays.asList(arrWords));
    System.out.println("Перечень уникальных слов:");
    System.out.println(uniqueSet);
  }

  /**
   * Посчитать сколько раз встречается каждое слово.
   *
   * @param arrWords String[]
   */
  public static void countEntry(String[] arrWords) {
    Map<String, Integer> cntMapWords = new LinkedHashMap<>();
    for (String x : arrWords) {
      cntMapWords.put(x, cntMapWords.getOrDefault(x, 0) + 1);
    }
    System.out.println("Сколько раз встречаются слова:");
    System.out.println(cntMapWords);
  }
}
