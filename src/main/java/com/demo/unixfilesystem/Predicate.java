package com.demo.unixfilesystem;

public interface Predicate {
    // Checks if the given file matches the search condition
    boolean isMatch(final File inputFile);
}