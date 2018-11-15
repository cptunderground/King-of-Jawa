package shared.net.session;

import java.net.Socket;
import shared.user.User;

public abstract class Session {

  public abstract Socket getSocket();

  public abstract long getPing();

  public abstract User getUser();
}
