import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFiles {
  /*
   * Este metodo asume que el archivo es un txt simple con un campo y un valor por
   * cada linea.
   * 
   * campo1=valor1
   * 
   * Ej:
   * Nombre=Juan
   * Edad=25
   */
  public static void readSimpleFile(String filePath) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));

      String line;
      while ((line = br.readLine()) != null) {
        String[] split = line.split("=");
        System.out.printf("%s: %s\n", split[0], split[1]);
      }

      br.close();
    } catch (IOException ioe) {
      System.err.println("Error reading file: " + ioe.getMessage());
    }
  }

  /*
   * Este metodo asume que el fichero tiene siempre 3 campos y que siempre son del
   * tipo correcto para construir una persona.
   */
  public static ArrayList<Object> readCsvFile(String filePath) {
    ArrayList<Object> list = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));

      String line;
      while ((line = br.readLine()) != null) {
        String[] split = line.split(",");
        list.add(new Persona(split[0], Integer.parseInt(split[1].trim()), split[2]));
      }
      br.close();
      return list;
    } catch (IOException ioe) {
      System.err.println("Error reading file: " + ioe.getMessage());
      return list;
    }
  }

  // He creado esta clase porque el ej 8.2.2 pide cargar los datos en un array de
  // objetos y no se a que se refiere exactamente.
  private static class Persona {
    public String nombre;
    public int edad;
    public String correo;

    public Persona(String nombre, int edad, String correo) {
      this.nombre = nombre;
      this.edad = edad;
      this.correo = correo;
    }
  }
}
