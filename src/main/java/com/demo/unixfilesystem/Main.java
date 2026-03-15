package com.demo.unixfilesystem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final File root = new File(true, 0, "adam", "root");
        final File a = new File(false, 2000, "adam", "a");
        final File b = new File(false, 3000, "george", "b");
        final File e = new File(true, 3000, "abhi", "games");
        final File s = new File(false, 1000, "abhi", "spiderman.txt");
        final File x = new File(false, 1000, "abhi", "fifa.txt");

        root.addEntry(a);
        root.addEntry(b);
        root.addEntry(e);
        e.addEntry(x);
        e.addEntry(s);

        // Search criteria: Find non-directory files owned by users matching "ge.*"
        final FileSearchCriteria criteria =
                new FileSearchCriteria(
                        new AndPredicate(
                                List.of(
                                        new SimplePredicate<>(
                                                FileAttribute.IS_DIRECTORY,
                                                new EqualsOperator<>(),
                                                false),
                                        new SimplePredicate<>(
                                                FileAttribute.OWNER,
                                                new RegexMatchOperator<>(),
                                                "ge.*"))));

        final FileSearchCriteria criteria1 =
                new FileSearchCriteria(
                        new OrPredicate(
                                List.of(
                                        new SimplePredicate<>(
                                                FileAttribute.IS_DIRECTORY,
                                                new EqualsOperator<>(),
                                                false),
                                        new SimplePredicate<>(
                                                FileAttribute.SIZE,
                                                new GreaterThanOperator<>(),
                                                500))));

        final FileSearchCriteria criteria2 =
                new FileSearchCriteria(
                        new AndPredicate(
                                List.of(
                                        new SimplePredicate<>(
                                                FileAttribute.IS_DIRECTORY,
                                                new EqualsOperator<>(),
                                                false),
                                        new SimplePredicate<>(
                                                FileAttribute.OWNER,
                                                new EqualsOperator<>(),
                                                "abhi"),
                                        new SimplePredicate<>(
                                                FileAttribute.FILENAME,
                                                new RegexMatchOperator<>(),
                                                "sp.*"))));

        final FileSearchCriteria criteria3 =
                new FileSearchCriteria(
                        new NotPredicate(
                                new SimplePredicate<>(
                                        FileAttribute.IS_DIRECTORY,
                                        new EqualsOperator<>(),
                                        false)
                        ));

        // Execute the search and get results
        final FileSearch fileSearch = new FileSearch();
        System.out.println("File and directory structure is below ");
        fileSearch.printFile(root);

        final List<File> result = fileSearch.search(root, criteria);
        System.out.println("Files found for the search criteria of (no directory AND owner name like ge*) are");
        printResult(result);

        final List<File> result1 = fileSearch.search(root, criteria1);
        System.out.println("Files found for the search criteria of (no directory OR file size > 500) are");
        printResult(result1);

        final List<File> result2 = fileSearch.search(root, criteria2);
        System.out.println("Files found for the search criteria of (no directory AND OWNER = abhi AND fileName like s*) are");
        printResult(result2);

        final List<File> result3 = fileSearch.search(root, criteria3);
        System.out.println("Files found for the search criteria of (NOT (DIRECTORY is false)) are");
        printResult(result3);
    }

    public static void printResult(List<File> files) {
        for (File file : files) {
            System.out.print(file.getFilename() + "   ");
        }
        System.out.println();
    }
}
