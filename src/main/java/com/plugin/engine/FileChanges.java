package com.plugin.engine;

import com.google.common.collect.Range;

import java.io.File;
import java.util.ArrayList;

public class FileChanges {
    private File file;
    private ArrayList<Range<Integer>> changes;

    public FileChanges(File file, ArrayList<Range<Integer>> changes) {
        this.file = file;
        this.changes = changes;
    }

    public File getFile() {
        return file;
    }

    public ArrayList<Range<Integer>> getChanges() {
        return changes;
    }
}
