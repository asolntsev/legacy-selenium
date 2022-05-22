package helpers;

public class Pauses {
  public static void sleep(Timeouts seconds) {
    try {
      Thread.sleep(seconds.getValue() * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void sleep() {
    sleep(Timeouts.FAST);
  }
}
