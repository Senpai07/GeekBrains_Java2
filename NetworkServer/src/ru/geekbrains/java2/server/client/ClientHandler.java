package ru.geekbrains.java2.server.client;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.CommandType;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.BroadcastMessageCommand;
import ru.geekbrains.java2.client.command.PrivateMessageCommand;
import ru.geekbrains.java2.server.NetworkServer;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

  public static final String UNKNOWN = "<Не авторизован>";
  public static final int AUTH_TIMEOUT = 120000;
  private final NetworkServer networkServer;
  private final Socket clientSocket;

  private ObjectInputStream in;
  private ObjectOutputStream out;

  private String nickname;

  public ClientHandler(NetworkServer networkServer, Socket socket) {
    this.networkServer = networkServer;
    this.clientSocket = socket;
  }

  public String getNickname() {
    return nickname;
  }

  public void run() {
    doHandle(clientSocket);
  }

  private void doHandle(Socket socket) {
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());

      new Thread(
              () -> {
                try {
                  waitCheckAuth();
                  authentication();
                  readMessages();
                } catch (IOException e) {
                  System.out.printf(
                      "Соединение с клиентом %s было закрыто!%n",
                      nickname == null ? UNKNOWN : nickname);
                } finally {
                  closeConnection();
                }
              })
          .start();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void waitCheckAuth() {
    Thread thread =
        new Thread(
            () -> {
              try {
                Thread.sleep(AUTH_TIMEOUT);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              if (getNickname() == null) {
                String errorMessage = "Timeout authentication!";
                System.err.println(errorMessage);
                try {
                  sendMessage(Command.errorCommand(errorMessage));
                } catch (IOException e) {
                  e.printStackTrace();
                } finally {
                  closeConnection();
                }
              }
            });
    thread.setDaemon(true);
    thread.start();
  }

  private void closeConnection() {
    try {
      networkServer.unsubscribe(this);
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void readMessages() throws IOException {
    while (true) {
      Command command = readCommand();
      if (command == null) {
        continue;
      }
      switch (command.getType()) {
        case END:
          System.out.println("Received 'END' command");
          return;
        case PRIVATE_MESSAGE:
          {
            PrivateMessageCommand commandData = (PrivateMessageCommand) command.getData();
            String receiver = commandData.getReceiver();
            String message = commandData.getMessage();
            networkServer.sendMessage(receiver, Command.messageCommand(nickname, message));
            break;
          }
        case BROADCAST_MESSAGE:
          {
            BroadcastMessageCommand commandData = (BroadcastMessageCommand) command.getData();
            String message = commandData.getMessage();
            networkServer.broadcastMessage(Command.messageCommand(nickname, message), this);
            break;
          }
        default:
          System.err.println("Unknown type of command: " + command.getType());
      }
    }
  }

  private Command readCommand() throws IOException {
    try {
      return (Command) in.readObject();
    } catch (ClassNotFoundException e) {
      String errorMessage = "Unknown type of command from client!";
      System.err.println(errorMessage);
      e.printStackTrace();
      sendMessage(Command.errorCommand(errorMessage));
      return null;
    }
  }

  private void authentication() throws IOException {
    while (true) {
      Command command = readCommand();
      if (command == null) {
        continue;
      }
      if (command.getType() == CommandType.AUTH) {
        boolean successfulAuth = processAuthCommand(command);
        if (successfulAuth) {
          return;
        }
      } else {
        System.err.println("Unknown type of command for auth process: " + command.getType());
      }
    }
  }

  private boolean processAuthCommand(Command command) throws IOException {
    AuthCommand commandData = (AuthCommand) command.getData();
    String login = commandData.getLogin();
    String password = commandData.getPassword();
    String username = networkServer.getAuthService().getUsernameByLoginAndPassword(login, password);
    if (username == null) {
      Command authErrorCommand = Command.authErrorCommand("Не верный логин или пароль!");
      sendMessage(authErrorCommand);
      return false;
    } else if (networkServer.isNicknameBusy(username)) {
      Command authErrorCommand = Command.authErrorCommand("Данный пользователь уже авторизован!");
      sendMessage(authErrorCommand);
      return false;
    } else {
      nickname = username;
      String message = nickname + " зашел в чат!";
      networkServer.broadcastMessage(Command.messageCommand(null, message), this);
      commandData.setUsername(nickname);
      sendMessage(command);
      networkServer.subscribe(this);
      return true;
    }
  }

  public void sendMessage(Command command) throws IOException {
    out.writeObject(command);
  }
}
