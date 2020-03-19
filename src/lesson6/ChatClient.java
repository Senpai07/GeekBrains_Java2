package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
  public static final int PORT = 8189;
  public static final String HOST = "localhost";
  public static final String EXIT = "/exit";
  private static Socket socket;
  private static DataInputStream in;
  private static DataOutputStream out;

  public static void main(String[] args) throws IOException {
    try {
      try {
        socket = new Socket(HOST, PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Thread t = new Thread(ChatClient::readMessage);
        //      t.setDaemon(true);
        t.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
      Scanner in = new Scanner(System.in);
      System.out.println("Введите сообщение:");
      String message2Clients;
      do {
        message2Clients = in.nextLine();
        sendMessage(message2Clients);
        System.out.println("Сообщение отправлено.");
      } while (!message2Clients.equals(EXIT));
    } finally {
      System.out.println("Закрываем соединение!");
      if (socket != null) socket.close();
    }
  }

  private static void readMessage() {
    try {
      while (true) {
        String strFromServer = in.readUTF();
        System.out.println("Server: " + strFromServer);
        if (strFromServer.equalsIgnoreCase(EXIT)) {
          System.out.println("Получена команда выхода!");
          if (socket != null) socket.close();
          System.exit(0);
        }
      }
    } catch (Exception e) {
      System.out.println("Соединение закрыто!");
    }
  }

  private static void sendMessage(String msg) {
    try {
      out.writeUTF(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
