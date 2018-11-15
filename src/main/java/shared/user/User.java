package shared.user;

import java.util.UUID;

public abstract class User {

  public abstract String getName();

  public abstract UUID getUUid();

  public abstract int getId();


  public abstract int getLobbyId();
}
