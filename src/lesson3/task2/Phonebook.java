package lesson3.task2;

import java.util.*;

/** Телефонная книга */
public class Phonebook {
  private Map<String, String> list;

  Phonebook() {
    list = new HashMap<>();
  }

  public void add(String number, String surname) {
    list.put(number, surname);
  }

  public void get(String surname) {
    if (list.containsValue(surname)) {
      for (Map.Entry<String, String> item : list.entrySet()) {
        if (item.getValue().equals(surname)) {
          System.out.println(item.getValue() + " : " + item.getKey());
        }
      }
    } else {
      System.out.printf("Абонента %s нет в телефонной книге!", surname);
    }
  }
}
