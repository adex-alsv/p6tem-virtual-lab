public class SimulationException extends Exception {
  private final int errorCode;
  
  public SimulationException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode
  }
}
