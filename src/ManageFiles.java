import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ManageFiles {
  private static String tmpPath = System.getProperty("java.io.tmpdir");

  public static void createFileTree() {
    File dir = new File(tmpPath);

    String[] paths = {
        "test",
        "test/subtest1",
        "test/subtest2",
        "test/subtest1/subsubtest1",
        "test/subtest2/subsubtest2",
        "test/subtest3",
    };

    for (String path : paths) {
      new File(dir, path).mkdirs();
    }
  }

  public static void deleteUnusedFiles(String dirPath) {
    File dir = new File(dirPath);

    if (!dir.exists()) {
      throw new RuntimeException("No such file or directory: " + dirPath);
    }

    File[] files = dir.listFiles(f -> f.isFile() && isOld(f));

    for (File file : files) {
      System.out.println("Deleting: " + file.getAbsolutePath());
      file.delete();
    }
  }

  public static void copyTypedFiles(String sourcePath, String fileType, String destPath) {
    File sourceDir = new File(sourcePath);
    File destDir = new File(destPath);

    if (!sourceDir.exists() && !sourceDir.isDirectory()) {
      throw new RuntimeException("The source path is not a directory or does not exist: " + sourcePath);
    } else if (!destDir.exists() || !destDir.isDirectory()) {
      throw new RuntimeException("The destination path is not a directory or does not exist: " + destPath);
    }

    File[] files = sourceDir.listFiles(f -> f.isFile() && f.getName().endsWith(fileType));

    try {
      for (File f : files) {
        Files.move(f.toPath(), destDir.toPath(), StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException ioe) {
      System.err.println("Error copying files: " + ioe.getMessage());
    }
  }

  public static void printSize(String dirPath) {
    File dir = new File(dirPath);

    if (!dir.exists()) {
      throw new RuntimeException("No such file or directory: " + dirPath);
    }

    float size = (float) calcSize(dir) / 1024 / 1024;

    System.out.printf("Total size of %s: [%.2f] MB\n", dirPath, size);
  }

  private static long calcSize(File dir) {
    if (dir.isFile()) {
      return dir.length();
    }

    long size = 0;

    if (dir.isDirectory()) {
      File[] files = dir.listFiles();
      if (files != null) {
        for (File f : files) {
          size += calcSize(f);
        }
      }
    }

    return size;
  }

  private static boolean isOld(File file) {
    try {
      BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

      Instant creationTime = attrs.creationTime().toInstant();

      return creationTime.isBefore(Instant.now().minus(7, ChronoUnit.DAYS));
    } catch (IOException ioe) {
      System.err.println("Error checking file: " + file.getAbsolutePath());
      return false;
    }
  }
}
