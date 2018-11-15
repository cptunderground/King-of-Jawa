package shared.net.protocol;

public interface PackageExecutable {

  <T extends Package> void run(T p);

}
