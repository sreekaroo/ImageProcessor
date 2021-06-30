import java.io.IOException;

/**
 * a mock appendable that throws IOExceptions (for testing purposes).
 */
public class AppendableMock implements Appendable {

  /**
   * throws an IOException no matter what.
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
