package com.demo.unixfilesystem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSearch {
    // Performs a recursive search through the file system starting from root
    // Returns a list of files that match the given criteria
    public List<File> search(final File root, final FileSearchCriteria criteria) {
        // List to store matching files
        final List<File> result = new ArrayList<>();
        // Stack to handle recursive traversal without actual recursion
        final ArrayDeque<File> recursionStack = new ArrayDeque<>();
        // Start with the root directory
        recursionStack.add(root);
        // Continue until we've processed all files
        while (!recursionStack.isEmpty()) {
            // Get the next file to process
            File next = recursionStack.pop();
            // Check if the file matches our criteria
            if (criteria.isMatch(next)) {
                result.add(next);
            }
            // Add all directory entries to the stack for processing
            for (File entry : next.getEntries()) {
                recursionStack.push(entry);
            }
        }
        return result;
    }

    public void printFile(File root) {

        class Node {
            File file;
            String prefix;
            boolean isLast;

            Node(File file, String prefix, boolean isLast) {
                this.file = file;
                this.prefix = prefix;
                this.isLast = isLast;
            }
        }

        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.push(new Node(root, "", true));

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            System.out.println(
                    node.prefix +
                            (node.isLast ? "└── " : "├── ") +
                            node.file.getFilename()
            );

            Set<File> entries = node.file.getEntries();
            List<File> children = new ArrayList<>(entries); // convert Set → List

            for (int i = children.size() - 1; i >= 0; i--) {
                File child = children.get(i);
                boolean isLastChild = (i == children.size() - 1);

                String newPrefix = node.prefix +
                        (node.isLast ? "    " : "│   ");

                stack.push(new Node(child, newPrefix, isLastChild));
            }
        }
    }
}