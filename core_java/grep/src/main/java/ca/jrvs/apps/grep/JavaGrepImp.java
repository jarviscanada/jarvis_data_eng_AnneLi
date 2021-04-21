package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;
//  private Logger jgrepLogger;
//
//  public JavaGrepImp(String regex, String rootPath, String outFile) {
//    this.regex = regex;
//    this.rootPath = rootPath;
//    this.outFile = outFile;
//  }

  @Override
  public void process() throws IOException {
    try {
      FileWriter fileWriter = new FileWriter(outFile, true);
    } catch (IOException e) {
      logger.error("Failed to create FileWriter; check if output file path is valid.", e);
    }
    List<String> lines = new ArrayList<>();

    for (File file: listFiles(rootPath)) {
      for (String line : readLines(file)) {
        if (containsPattern((line))) {
          lines.add(line);
        }
      }
    }
    writeToFile(lines);
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the root directory
   */
  @Override
  public List<File> listFiles(String rootDir) {
    List<File> fileList = new ArrayList<>();
    File root = new File(rootDir);
    File[] filesToCheck;
    if (root.isDirectory()) {
      filesToCheck = root.listFiles();
      if (filesToCheck != null) {
        for (File file : filesToCheck) {
          if (file.isFile()) {
            fileList.add(file);
          } else if (file.isDirectory()) {
            fileList.addAll((listFiles(file.getAbsolutePath())));
          }
        }
      }
    }
    return fileList;
  }

  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    List<String> lines = new ArrayList<>();
    String line;
    /**
     * Try to read one line then, while the current line isn't null,
     * add it to the list and get the next, otherwise return
     */
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      line = reader.readLine();
      while(line != null) {
        lines.add(line);
        line = reader.readLine();
      }
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.compile(regex).matcher(line).find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

    for (String line : lines) {
      writer.write(line + "\n");
    }
    writer.close();
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) throws ClassNotFoundException {
    Logger logger = LoggerFactory.getLogger(Class.forName("Main"));
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }

}
