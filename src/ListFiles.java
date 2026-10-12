import java.io.File;

class ListFiles {
  public static void listAll(String dirPath) {
    File dir = new File(dirPath);

    if (!dir.exists()) {
      throw new RuntimeException("No such file or directory: " + dirPath);
    }

    File[] files = dir.listFiles();

    for (File file : files) {
      if (file.isDirectory()) {
        listAll(file.getAbsolutePath());
      } else {
        System.out.println(file.getAbsolutePath());
      }
    }
  }

  public static void listTxt(String dirPath) {
    File dir = new File(dirPath);

    if (!dir.exists()) {
      throw new RuntimeException("No such file or directory: " + dirPath);
    }

    File[] files = dir.listFiles();

    for (File file : files) {
      if (file.isDirectory()) {
        listTxt(file.getAbsolutePath());
      } else {
        if (file.getName().endsWith(".txt")) {
          System.out.println(file.getName());
        }
      }
    }
  }

  public static void checkPathType(String dirPath) {
    File dir = new File(dirPath);

    if (!dir.exists()) {
      System.out.println(dirPath + " does not exist");
    } else if (dir.isDirectory()) {
      System.out.println(dirPath + " is a directory");
    } else {
      System.out.println(dirPath + " is a file");
    }
  }
}