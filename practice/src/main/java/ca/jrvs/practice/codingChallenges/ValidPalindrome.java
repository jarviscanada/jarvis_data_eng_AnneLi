package ca.jrvs.practice.codingChallenges;

import java.util.Locale;
import java.util.stream.IntStream;

/**
 * Ticker URL: https://www.notion.so/jarvisdev/Valid-Palindrome-0842f6c93af347cf8c4bcabcb8430dec
 * Checks if the given string is a valid palindrome.
 * (A palindrome is a word or phrase that reads the same backward as it does forward)
 * Input: String
 * Output: true/false, depending on whether the String is a palindrome or not
 * Time: O(N)
 * Space: O(N)
 */
public class ValidPalindrome {

  //method 1
  public static boolean palindromeCheckerTraditional(String original) {
    String normalized = original.toLowerCase();
    StringBuilder reversed = new StringBuilder();

    for (int i = normalized.length() - 1; i >= 0; i--) {
      reversed.append(normalized.charAt(i));
    }
    return normalized.equals(reversed.toString());
  }

  //method 2
  public static boolean palindromeCheckerStreams(String original) {
    original = original.replaceAll("\\p{Punct}","");
    original = original.replace(" ","");
    String normalized = original.toLowerCase();

    return IntStream.range(0, normalized.length() / 2)
        .allMatch(i ->
            normalized.charAt(i) ==
              normalized.charAt(normalized.length() -i -1));
  }

}
