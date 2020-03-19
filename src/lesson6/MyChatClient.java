package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyChatClient {

  public static final String EXIT = "/exit";
  private MyChatServer myServer;
  private Socket socket;
  private DataInputStream in;
  private DataOutputStream out;
  private final int ownNumber;

  public int getOwnNumber() {
    return ownNumber;
  }

  public MyChatClient(MyChatServer myServer, Socket socket, int ownNumber) {
    this.ownNumber = ownNumber;
    System.out.printf("Клиент %d подключился%n", this.ownNumber);
    try {
      this.myServer = myServer;
      this.socket = socket;
      this.in = new DataInputStream(socket.getInputStream());
      this.out = new DataOutputStream(socket.getOutputStream());
      new Thread(
              () -> {
                try {
                  myServer.sendAllClients(String.format("Клиент %d зашел в чат", this.ownNumber));
                  myServer.addClient(this);
                  readMessages();
                } catch (IOException e) {
                  System.out.printf("Клиент %d вышел из чата%n", this.ownNumber);
                } finally {
                  closeConnection();
                }
              })
          .start();
    } catch (IOException e) {
      throw new RuntimeException("Проблемы при создании обработчика клиента");
    }
  }

  public void readMessages() throws IOException {
    while (true) {
      String strFromClient = in.readUTF();
      System.out.println("Client " + ownNumber + ": " + strFromClient);
      if (strFromClient.equals(EXIT)) {
        System.out.printf("Клиент %d вышел из чата%n", this.ownNumber);
        return;
      }
    }
  }

  public void sendMsg(String msg) throws IOException {
    out.writeUTF(msg);
  }

  public void closeConnection() {
    myServer.removeClient(this);
    myServer.sendAllClients(String.format("Клиент %d вышел из чата", this.ownNumber));
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
