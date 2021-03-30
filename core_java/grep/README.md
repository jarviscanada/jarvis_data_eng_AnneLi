# Java Grep App
This grep app echoes partial functionality of the linux command `grep`. The app searches for a text 
pattern recursively through all the files in a given directory and outputs the matched lines to a 
designated file.

# Introduction
This application leverages Java streams to lazily load text documents into the JVM's memory one line 
at a time in search for occurences of a user-defined regular expression pattern in limited heap 
space conditions.
This project served as an introduction to Java's Regex classes (Pattern and Matcher), Streams, 
Lambdas, and the java.nio library.

# Usage 
Input Parameters:
- **regex**: a Regular Expression text string to describe a search pattern
- **rootPath**: root directory path
- **outFile**: output filename

These parameters are similar to the usage for egrep command:

`egrep -r {regex} {rootPath} > {outFile}`

# Implementation

## Pseudocode
The following is a high level description of how JavaGrep functions.
```
Recursively walk through the search root directory, get a list of files in it.
For each file found in target directory,
    For each line in the file,
        Test the line against the Regex pattern,
        If the  pattern is found, write the line to the output file.
```

## Performance Issue
The application had performance issues resulting in an `OutOfMemoryError` exception error. This is a
case of overloading the JVM heap memory and no more memory could be made available by the garbage 
collector.

As performing disk I/O is relatively slow and linear searches are being performed on each line,
This program may run slowly when processing large files. This is addressed by setting minimum and 
maximum heap size and utilizing Java Stream and Lambda APIs. The use of Java Stream APIs enables the 
app to process huge data with a small heap memory through functional programming.

## Improvement
- Add the option to display the filename and line number where the regex pattern matches.
- More heavy utilization of functional programming using Streams APIs for code readability and lazy
  pipeline to directly write each line it finds that matches the regex pattern to the output file 
  without needing the use of a list object.
- Implement more exception handling to output errors that make it easier to debug