package com.plugin.engine;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private String getTextFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    private void writeTextToFile(File file, String text) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();
    }

    public String format(final String code) throws FormatterException {
        System.out.println(code);
        return formatter.formatSource(code);
    }

    public void formatFile(final File file) throws FormatterException, IOException {
        writeTextToFile(file, format(getTextFromFile(file)));
    }

    public void formatPartOfFile(final FileChanges fileChanges) throws FormatterException, IOException {
        String sourceCode = getTextFromFile(fileChanges.getFile());

        for (Range<Integer> range : fileChanges.getChanges()) {
            int beginCharId = 0;
            int endCharId = sourceCode.length();

            for (int charId = 0, line = 1; charId < sourceCode.length() && line <= range.upperEndpoint(); charId++) {
                if (sourceCode.charAt(charId) == '\n') {
                    line++;
                    if (line == range.lowerEndpoint()) {
                        beginCharId = charId;
                    }
                    if (line == range.upperEndpoint() + 1) {
                        endCharId = charId;
                    }
                }
            }

            writeTextToFile(fileChanges.getFile(), formatter.formatSource(
                    getTextFromFile(fileChanges.getFile()),
                    ImmutableList.of(Range.open(beginCharId, endCharId))
            ));
        }
    }

    public void formatGitDiff(ArrayList<FileChanges> filesChanges) throws FormatterException, IOException {
        for (FileChanges fileChanges : filesChanges) {
            formatPartOfFile(fileChanges);
        }
    }
}
