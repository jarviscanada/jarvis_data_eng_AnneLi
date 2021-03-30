package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamImp implements LambdaStreamExc {
  /**
   * Create a String stream from array
   *
   * note: arbitrary number of value will be stored in an array
   *
   * @param strings
   * @return
   */
  public Stream<String> createStrStream(String ... strings) {
    return Arrays.stream(strings);
  }

  /**
   * Convert all strings to uppercase
   * please use createStrStream
   *
   * @param strings
   * @return
   */
  public Stream<String> toUpperCase(String ... strings) {
    return createStrStream().map(string2 -> string2.toUpperCase());
  }

  /**
   * filter strings that contains the pattern
   * e.g
   * filter(stringStream, "a") will return another stream which no element contains a
   *
   *@param stringStream: A Stream of Strings which we want to add a filter to
   *@param pattern: A pattern which we want to filter out of the Stream
   *@return a new Stream of Strings with the filter applied
   */
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(string -> !string.contains(pattern));
  }

  /**
   * Create a intStream from a arr[]
   * @param arr: an array of type int
   * @return an IntStream made from the int array
   */
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  /**
   * Convert a stream to list
   *
   * @param stream
   * @param <E> stream: a generic Stream to be converted to a list of the same type
   * @return a list of the same type as the Stream passed in
   */
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  /**
   * Convert a intStream to list
   * @param intStream: an intStream to be converted to a list
   * @return a list of integers
   */
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  /**
   * Create a IntStream range from start to end inclusive
   * @param start: an integer indicating the start of a number
   * @param end: an integer indicating the end of a number
   * @return an IntStream whose source is all ints from (start, end)
   */
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  /**
   * Convert a intStream to a doubleStream
   * and compute square root of each element
   * @param intStream
   * @return
   */
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.asDoubleStream().map(num -> Math.sqrt(num));
  }

  /**
   * filter all even number and return odd numbers from a intStream
   * @param intStream: a stream of integers to filter
   * @return an IntStream with a filter applied to return odd numbers
   */
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(num -> num % 2 != 0);
  }

  /**
   * Return a lambda function that print a message with a prefix and suffix
   * This lambda can be useful to format logs
   *
   * You will learn:
   *   - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig
   *   - lambda syntax
   *
   * e.g.
   * LambdaStreamExc lse = new LambdaStreamImp();
   * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
   * printer.accept("Message body");
   *
   * sout:
   * start>Message body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return a lambda print statement
   */
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return (message -> System.out.println(prefix + message + suffix));
  }

  /**
   * Print each message with a given printer
   * Please use `getLambdaPrinter` method
   *
   * e.g.
   * String[] messages = {"a","b", "c"};
   * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
   *
   * sout:
   * msg:a!
   * msg:b!
   * msg:c!
   *
   * @param messages
   * @param printer
   */
  public void printMessages(String[] messages, Consumer<String> printer) {
    this.createStrStream(messages).forEach(printer);
  }

  /**
   * Print all odd number from a intStream.
   * Please use `createIntStream` and `getLambdaPrinter` methods
   *
   * e.g.
   * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   *
   * sout:
   * odd number:1!
   * odd number:3!
   * odd number:5!
   *
   * @param intStream: a stream of integers
   * @param printer: a lambda that prints the values given to it
   */
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    this.getOdd(intStream).mapToObj(String::valueOf).forEach(printer);
  }

  /**
   * Square each number from the input.
   * Please write two solutions and compare difference
   *   - using flatMap
   *
   * @param ints: a stream of List of Integers
   * @return A stream of Integer objects
   */
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(List::stream).map(integer -> integer*integer);
  }

  public static void main(String[] args) {
    LambdaStreamImp lsi = new LambdaStreamImp();
    String[] strings = new String[]{"one", "two", "three"};
    LambdaStreamExc lse = new LambdaStreamImp();
    Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
    printer.accept("Message body");
  }
}
