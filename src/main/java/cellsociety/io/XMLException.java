package cellsociety.io;

public class XMLException extends RuntimeException {

  public XMLException (String message, Object ... values) {
    super(String.format(message, values));
  }

  public XMLException (Throwable cause, String message, Object ... values) {
    super(String.format(message, values), cause);
  }

  public XMLException (Throwable cause) {
    super(cause);
  }

}
