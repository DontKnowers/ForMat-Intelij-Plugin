package com.plugin.engine;

import com.google.googlejavaformat.java.FormatterException;

import java.io.File;
import java.util.ArrayList;

public interface LanguageFormatter {
    String format(String code) throws FormatterException;

    void formatFile(File file) throws FormatterException;

    void formatPartOfFile(File file, Integer beginLine, Integer endLine) throws FormatterException;

    void formatGitDiff(ArrayList<File> files) throws FormatterException;
}
