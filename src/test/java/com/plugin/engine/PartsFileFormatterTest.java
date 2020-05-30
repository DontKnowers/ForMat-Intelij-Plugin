package com.plugin.engine;

import com.google.common.collect.Range;
import com.google.googlejavaformat.java.FormatterException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class PartsFileFormatterTest extends FormatterTest {
    private boolean testSample(FileChanges fileChanges) throws IOException, FormatterException {
        File copiedFile = copyFile(fileChanges.getFile(), "parts_" + getAnswerFile(fileChanges.getFile()).getName());
        new JavaFormatter(0).formatPartsOfFile(new FileChanges(copiedFile, fileChanges.getChanges()));
        return FileUtils.contentEquals(copiedFile, getAnswerFile(fileChanges.getFile()));
    }

    @Test
    public void testFirstSmallSample() throws IOException, FormatterException {
        assertTrue(testSample(new FileChanges(
                getFile("java/small_samples/1.java"),
                new ArrayList<>() {{
                    add(Range.open(6, 7));
                    add(Range.open(171, 190));
                }}
        )));
    }

    @Test
    public void testFirstBigSample() throws IOException, FormatterException {
        assertTrue(testSample(new FileChanges(
                getFile("java/big_samples/1.java"),
                new ArrayList<>() {{
                    add(Range.open(6, 7));
                    add(Range.open(171, 190));
                }}
        )));
    }
}
