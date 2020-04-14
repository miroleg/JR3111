package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName, partOfContent;
    private int  minSize = Integer.MIN_VALUE;
    private int  maxSize = Integer.MAX_VALUE;
    private List<Path> foundFiles = new LinkedList<>();

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;

    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;

    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;

    }

    public void setMinSize(int minSize) {
        this.minSize =  minSize;


    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file);  // размер файла: content.length
        String allFileText =  new String(content);
        boolean isPartOfName = true;
        boolean isPartOfContent = true;
        boolean isMinSize = (long) content.length >= minSize;
        boolean isMaxSize = (long) content.length <= maxSize;
        if (partOfName != null) isPartOfName = file.getFileName().toString().contains(partOfName);
        if (partOfContent != null) isPartOfContent = allFileText.contains(partOfContent);

        if (isMaxSize && isMinSize && isPartOfContent && isPartOfName) foundFiles.add(file);

        return super.visitFile(file, attrs);
    }
}
