package lesson3.task2;

import java.text.ParseException;

/** Task 2 */
public class Main {
  public static void main(String[] args) {
    Phonebook phonebook = new Phonebook();

    phonebook.add("+7(918)234-34-67", "Жуков");
    phonebook.add("+7(909)345-23-46", "Рокоссовский");
    phonebook.add("+7(918)834-34-68", "Жуков");
    phonebook.add("+7(900)234-51-23", "Конев");
    phonebook.add("+7(928)345-32-53", "Ватутин");
    phonebook.add("+7(918)788-54-89", "Конев");
    phonebook.add("+7(961)352-34-69", "Жуков");

    phonebook.get("Жуков");
    phonebook.get("Конев");
    phonebook.get("Ватутин");
    phonebook.get("Василевский");
  }
}
