package lesson6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyChatServer {
  private static final int PORT = 8189;
  private static final String EXIT = "/exit";
  private BufferedReader consoleReader;

  private List<MyChatClient> clients;

  public MyChatServer() {
    try (ServerSocket server = new ServerSocket(PORT)) {
      int clientNumber = 0;
      clients = new ArrayList<>();
      consoleReader = new BufferedReader(new InputStreamReader(System.in));
      // Создаем поток обработки ввода с консоли
      new Thread(this::consoleHandler).start();
      // Подключаем клиентов...
      System.out.println("Сервер ожидает подключения");
      while (true) {
        System.out.println("Введите сообщение:");
        try {
          Socket socket = server.accept();
          new MyChatClient(this, socket, clientNumber++);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println("Ошибка в работе сервера");
    }
  }

  private void consoleHandler() {
    try {
      while (true) {
        String strToClient = consoleReader.readLine();
        sendAllClients(strToClient);
        if (strToClient.equalsIgnoreCase(EXIT)) {
          System.out.println("Получена команда выхода!");
          clients.clear();
          System.exit(0);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized void sendAllClients(String msg) {
    for (MyChatClient o : clients) {
      try {
        o.sendMsg(msg);
      } catch (IOException e) {
        System.out.printf("Клиент %d отключен%n", o.getOwnNumber());
      }
    }
    if (clients.isEmpty()) {
      System.out.println("Не подключено ни одного клиента!");
    } else {
      System.out.println("Сообщение отправлено.");
    }
  }

  public synchronized void removeClient(MyChatClient o) {
    clients.remove(o);
  }

  public synchronized void addClient(MyChatClient o) {
    clients.add(o);
  }
}
