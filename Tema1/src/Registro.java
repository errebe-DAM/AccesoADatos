/*
* Como no especifica que hay que crear me lo he inventado, tanto el tamaño de los campos como los datos
*/

import java.io.IOException;
import java.io.RandomAccessFile;

public class Registro {
  private static final int DNI_SIZE = 9;
  private static final int NAME_SIZE = 20;
  private static final int EMAIL_SIZE = 30;

  private static final int CHAR_SIZE = Character.BYTES;
  private static final int SIZE = DNI_SIZE * CHAR_SIZE + NAME_SIZE * CHAR_SIZE + EMAIL_SIZE * CHAR_SIZE;

  private String dni;
  private String name;
  private String email;

  public Registro(String dni, String name, String email) {
    this.dni = dni;
    this.name = name;
    this.email = email;
  }

  public void save(RandomAccessFile file) throws IOException {
    file.writeChars(trimString(dni, DNI_SIZE));
    file.writeChars(trimString(name, NAME_SIZE));
    file.writeChars(trimString(email, EMAIL_SIZE));
  }

  private static String trimString(String str, int size) {
    if (str.length() > size) {
      return str.substring(0, size);
    } else if (str.length() < size) {
      return String.format("%-" + size + "s", str);
    }

    return str;
  }

  @Override
  public String toString() {
    return String.format("DNI: %s Name: %s Email: %s", dni, name, email);
  }
}
