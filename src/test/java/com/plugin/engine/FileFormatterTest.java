package com.plugin.engine;

import com.google.googlejavaformat.java.FormatterException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FileFormatterTest extends FormatterTest {
    private boolean testSamples(String dirPath) throws IOException, FormatterException {
        for (File file : getFile(dirPath).listFiles()) {
            if (file.getName().matches("\\d\\.java")) {
                File copiedFile = copyFile(file, "file_" + getAnswerFile(file).getName());
                new JavaFormatter(0).formatFile(copiedFile);
                if (!FileUtils.contentEquals(copiedFile, getAnswerFile(file))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testSmallSamples() throws IOException, FormatterException {
        assertTrue(testSamples("java/small_samples"));
    }

    @Test
    public void testBigSamples() throws IOException, FormatterException {
        assertTrue(testSamples("java/big_samples"));
    }
}
