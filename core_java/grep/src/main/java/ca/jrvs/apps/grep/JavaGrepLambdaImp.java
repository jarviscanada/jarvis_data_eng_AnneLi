package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {

  /**
   * Overriding JavaGrepImp.readLines(), this implementation uses java.nio.Files.
   * @param inputFile:  a file object which lines must be read from. This must be a file, not a directory
   * @return lines: a list of strings containing all readable lines of the supplied file
   */
  @Override
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<String>();
    try {
      lines = Files.lines(inputFile.toPath()).collect(Collectors.toList());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return lines;
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<File>();
    try {
      files = Files.walk(Paths.get(rootDir)).filter(Files::isRegularFile).map(Path::toFile).collect(
          Collectors.toList());
    } catch (IOException ex) {
      logger.error("ERROR: Failed to list/retrieve file", ex);
    }
    return files;
  }

  public static void main(String[] args) {
    if (args.length != 3) {

      //creating JavaGrepLambdaImp instead of JavaGrepImp
      //JavaGrepLambdaImp inherits all methods except two override methods in JavaGrepImp
      JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
      javaGrepLambdaImp.setRegex(args[0]);
      javaGrepLambdaImp.setRootPath(args[1]);
      javaGrepLambdaImp.setOutFile(args[2]);

      try {
        //calling parent method
        //but it will call override method (in this class)
        javaGrepLambdaImp.process();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
