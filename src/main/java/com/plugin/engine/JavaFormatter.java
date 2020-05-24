package com.plugin.engine;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;

import java.io.File;
import java.util.ArrayList;

public class JavaFormatter implements LanguageFormatter {
    private final Formatter formatter;

    public JavaFormatter() {
        formatter = new Formatter();
    }

    /**
     * Set custom codeStyle(0 - Google, 1 - AOSP)
     *
     * @param codeStyleId;
     */
    public JavaFormatter(int codeStyleId) {
        if (codeStyleId < 0 || codeStyleId > 1) {
            throw new IllegalArgumentException("codeStyleId variable must be 0 or 1");
        }

        JavaFormatterOptions.Style codeStyle;
        if (codeStyleId == 1) {
            codeStyle = JavaFormatterOptions.Style.GOOGLE;
        } else {
            codeStyle = JavaFormatterOptions.Style.AOSP;
        }

        formatter = new Formatter(JavaFormatterOptions.builder().style(codeStyle).build());
    }

    public String format(String code) throws FormatterException {
        return formatter.formatSource(code);
    }

    public void formatFile(File file) throws FormatterException {
        // TODO: implement
    }

    public void formatPartOfFile(File file, Integer beginLine, Integer endLine) throws FormatterException {
        // TODO: implement
    }

    public void formatGitDiff(ArrayList<File> files) throws FormatterException {
        // TODO: implement
    }
}
