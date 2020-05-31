package com.plugin.engine;

import com.google.googlejavaformat.java.FormatterException;
import com.intellij.openapi.util.Pair;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PartsFileFormatterTest extends FormatterTest {
    private boolean testSample(FileChanges fileChanges) throws IOException, FormatterException {
        File copiedFile = copyFile(
                fileChanges.getFile(),
                "parts_" + getAnswerFile(fileChanges.getFile()).getName()
        );
        new JavaFormatter(0).formatPartsOfFile(new FileChanges(copiedFile, fileChanges.getChanges()));
        return FileUtils.contentEquals(copiedFile, getAnswerFile(fileChanges.getFile()));
    }

    @Test
    public void testFirstSample() throws IOException, FormatterException {
        assertTrue(testSample(new FileChanges(
                getFile("java/samples/1.java"),
                new ArrayList<>() {{
                    add(new Pair<>(6, 6));
                    add(new Pair<>(171, 190));
                }}
        )));
    }

    @Test
    public void testSecondSample() throws IOException, FormatterException {
        assertTrue(testSample(new FileChanges(
                getFile("java/samples/2.java"),
                new ArrayList<>() {{
                    add(new Pair<>(1, 1));
                }}
        )));
    }

    @Test
    public void testThirdSample() throws IOException, FormatterException {
        assertTrue(testSample(new FileChanges(
                getFile("java/samples/3.java"),
                new ArrayList<>() {{
                    add(new Pair<>(1, 1));
                    add(new Pair<>(4, 4));
                }}
        )));
    }

    @Test
    public void testFourthSample() throws IOException, FormatterException {
        assertFalse(testSample(new FileChanges(
                getFile("java/samples/4.java"),
                new ArrayList<>() {{
                    add(new Pair<>(1, 1));
                    add(new Pair<>(4, 4));
                }}
        )));
    }
}
