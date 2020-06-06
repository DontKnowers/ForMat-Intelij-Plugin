package com.plugin.frontend.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.plugin.engine.FileChanges;
import com.plugin.engine.JavaFormatter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FormatAreaAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JavaFormatter test = new JavaFormatter();
        VirtualFile file = FileDocumentManager.getInstance().getFile(Objects.requireNonNull(e.getData(LangDataKeys.EDITOR)).getDocument());

        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        assert editor != null;
        SelectionModel start = editor.getSelectionModel();
        SelectionModel end = editor.getSelectionModel();

        if (file == null) {
            return;
        }
        int startOffset = start.getSelectionStart();
        int endOffset = end.getSelectionEnd();
        int startLine = 0;
        int endLine = 0;


        try {
            LineNumberReader r = new LineNumberReader(new FileReader(new File(file.getPath())));
            int count = 0;
            int codeOfSymbol = 0;
            int crutch = 0;
            startLine = changeCount(startOffset, r, count, codeOfSymbol, crutch);
            crutch = 0;
            endLine = changeCount(endOffset, r, count, codeOfSymbol, crutch);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Integer finalStartLine = startLine;
        Integer finalEndLine = endLine;
        try {
            test.formatPartsOfFile(new FileChanges(new File(file.getPath()), new ArrayList<>() {{
                add(new Pair<>(finalStartLine, finalEndLine));
            }}));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private int changeCount(int offset, LineNumberReader r, int count, int codeOfSymbol, int crutch) throws IOException {
        int line = 0;
        while (count < offset && (codeOfSymbol = r.read()) != -1) {
            count++;
        }
        if (count == offset) {
            if (codeOfSymbol != 10) {
                crutch++;
            }
            line = r.getLineNumber() + crutch;
        }
        return line;
    }
}

