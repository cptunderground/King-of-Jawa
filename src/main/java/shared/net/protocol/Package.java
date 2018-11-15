package shared.net.protocol;

import java.net.Socket;
import shared.net.protocol.packages.PackageType;
import shared.util.Chain;
import shared.util.ConsoleLog;
import shared.util.Serialization;

public class Package {

  private static int id = 0;
  private String name;
  private String packedString;
  private int packageId;
  private Serialization serialization;
  private Socket socket;
  private PackageType type;

  /**
   * This class prepares data to be sent as package.
   *
   * @param name Its execution name
   * @param type The packages type.
   * @param s The packages socket through which it has to be sent.
   */
  public Package(String name, PackageType type, Socket s) {
    serialization = new Serialization();
    serialization.append("header");
    serialization.append("data");
    this.type = type;
    this.name = name;
    this.socket = s;
    this.packageId = Package.id;
    Package.id++;
    generateHeader();
  }

  /**
   * This class prepares data to be sent as package.
   *
   * @param s String, which is a received package information.
   * @param socket The socket, the package has to be sent through.
   */
  public Package(String s, Socket socket) {
    this.packedString = s;
    serialization = new Serialization();
    if (packedString != null) {
      serialization.unpack(packedString);
      if (validate()) {
        this.name = getHeader("name");
        this.type = PackageType.valueOf(getHeader("type"));
        this.socket = socket;
      }
    }
    Package.id++;
  }

  /**
   * This class prepares data to be sent as package.
   *
   * @param p Package, which contains received package information.
   */
  public Package(Package p) {
    this.serialization = p.getSerialization();
    if (validate()) {
      this.name = p.getHeader("name");
      this.type = PackageType.valueOf(p.getHeader("type"));
      this.socket = p.getSocket();
    }
  }


  /**
   * Generates the package header, which contains important metadata.
   */
  private void generateHeader() {
    setHeader("type", type.toString());
    setHeader("name", name);
    setHeader("id", packageId + "");
  }

  /**
   * Sets the header information.
   *
   * @param key Key of the header information
   * @param value Value of the header information
   */
  private void setHeader(String key, String value) {
    Chain c = serialization.find("header");
    if (c.getValue() == null) {
      c.setValue(new Chain(key, value));
    } else {
      ((Chain) c.getValue()).insert(key, value);
    }
  }

  /**
   * Getter for the header inside the chain.
   *
   * @param key The key of the header information.
   * @return header inside the chain.
   */
  public String getHeader(String key) {
    Chain header = serialization.find("header");
    if (header == null) {
      return null;
    }
    Chain headerValue = (Chain) header.getValue();
    if (headerValue == null) {
      return null;
    }
    Chain headerInfo = headerValue.find(key);
    if (headerInfo == null) {
      return null;
    }

    return (String) headerInfo.getValue();
  }

  /**
   * Writes data into the serializerchain.
   *
   * @param key Key of the data.
   * @param value Value of data.
   */
  public void setData(String key, String value) {
    Chain c = serialization.find("data");
    if (c.getValue() == null) {
      c.setValue(new Chain(key, value));
    } else {
      ((Chain) c.getValue()).insert(key, value);
    }
  }

  //TODO ADD setData with more datatypes e.g. ArrayList

  /**
   * Getter for the data inside the chain.
   *
   * @param key The key of the package-data.
   * @return data inside the chain.
   */
  public String getData(String key) {
    if (((Chain) serialization.find("data").getValue()).find(key) == null) {
      return null;
    }
    return (String) ((Chain) serialization.find("data").getValue()).find(key).getValue();
  }

  public int getId() {
    return packageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    generateHeader();
  }

  public PackageType getType() {
    return type;
  }

  public void setType(PackageType type) {
    this.type = type;
    generateHeader();
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  /**
   * Packs the chain into a serialized string which will be sent as package.
   */
  public void pack() {
    packedString = serialization.pack();
  }

  /**
   * Checks if a chain element exists with the key 'header'.
   *
   * @return boolean which determines whether a header was found or not.
   */
  public boolean checkHeader() {
    return (serialization.find("header") != null);
  }

  /**
   * Checks if a chain element exists with the key 'data'.
   *
   * @return boolean which determines whether data was found or not.
   */
  public boolean checkData() {
    return (serialization.find("data") != null);
  }

  /**
   * TODO document lan.
   *
   * @return boolean which determines whether a package was valid or not.
   */
  public boolean validate() {
    boolean headerContents = true;
    if (getHeader("type") == null || getHeader("type").equals("")) {
      ConsoleLog.debug("Package is not valid. no type found!");
      headerContents = false;
    }
    if (getHeader("name") == null || getHeader("name").equals("")) {
      ConsoleLog.debug("Package is not valid. no name found!");
      headerContents = false;
    }
    if (getHeader("id") == null || getHeader("id").equals("")) {
      ConsoleLog.debug("Package is not valid. no id found!");
      headerContents = false;
    }
    return (checkData() && checkHeader() && headerContents);
  }

  /**
   * Returns the packed string of this package.
   *
   * @return the packed string of this package.
   */
  public String toString() {
    pack();
    return packedString;
  }

  public boolean compareCaller(String str) {
    return getName().equals(str);
  }

  public <T extends Package> T cast() {
    return getType().cast(this);
  }

  public Serialization getSerialization() {
    return serialization;
  }

  public static int getIdCounter() {
    return id;
  }
}