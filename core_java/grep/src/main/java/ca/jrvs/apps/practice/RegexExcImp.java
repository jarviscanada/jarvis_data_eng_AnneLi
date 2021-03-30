package ca.jrvs.apps.practice;
import java.util.regex.*;

public class RegexExcImp implements RegexExc {

  @Override
  public boolean matchJpeg(String filename) {
    Pattern pattern = Pattern.compile(".jpg$|.jpeg$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(filename);
    return matcher.find();
  }

  @Override
  public boolean matchIp(String ip) {
    Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
    Matcher matcher = pattern.matcher(ip);
    return matcher.find();
  }

  @Override
  public boolean isEmptyLine(String line) {
    Pattern pattern = Pattern.compile("^\\s*$");
    Matcher matcher = pattern.matcher(line);
    return matcher.find();
  }

  public static void main(String[] args) {
    RegexExcImp regexObj = new RegexExcImp();

    if (regexObj.matchJpeg("smile.jPeg")) {
      System.out.println("Filename extension is jpg/jpeg");
    } else {
      System.out.println("Filename extension is not jpg/jpeg");
    }

    if (regexObj.matchIp("999.255.255.989")) {
      System.out.println("The IP address is valid");
    } else {
      System.out.println("Invalid IP address");
    }
    if (regexObj.isEmptyLine("yy")) {
      System.out.println("This is an empty line");
    } else {
      System.out.println("Not an empty line");
    }
  }
}
