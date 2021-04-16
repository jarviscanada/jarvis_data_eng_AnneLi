package ca.jrvs.practice.codingChallenges;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidPalindromeTest {

  private ValidPalindrome palindrome;
  String s1 = "aBba";
  String s2 = "raceCAr";
  String s3 = "Wifi";
  String s4 = "A man, a plan, a canal: Panama";

  @Before
  public void setUp() {
    palindrome = new ValidPalindrome();
  }

  @Test
  public void palindromeCheckerTraditional() {
    assertTrue(palindrome.palindromeCheckerTraditional(s1));
    assertTrue(palindrome.palindromeCheckerTraditional(s2));
    assertFalse(palindrome.palindromeCheckerTraditional(s3));
  }

  @Test
  public void palindromeCheckerStreams() {
    assertTrue(palindrome.palindromeCheckerStreams(s1));
    assertTrue(palindrome.palindromeCheckerStreams(s2));
    assertFalse(palindrome.palindromeCheckerStreams(s3));
    assertTrue(palindrome.palindromeCheckerStreams(s4));
  }
}