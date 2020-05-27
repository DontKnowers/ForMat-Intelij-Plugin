package com.plugin.engine;

import com.google.googlejavaformat.java.FormatterException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface LanguageFormatter {
    String format(final String code) throws FormatterException;

    void formatFile(final File file) throws FormatterException, IOException;

    void formatPartOfFile(final FileChanges fileChanges) throws FormatterException, IOException;

    void formatGitDiff(final ArrayList<FileChanges> filesChanges) throws FormatterException, IOException;
}
