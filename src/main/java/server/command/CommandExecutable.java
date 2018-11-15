package server.command;

import java.net.Socket;

public interface CommandExecutable {

  void run(Socket s, String[] params);
}
