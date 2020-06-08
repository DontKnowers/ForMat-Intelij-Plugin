package com.plugin.frontend.actions;

import com.google.googlejavaformat.java.FormatterException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.plugin.engine.FileChanges;
import com.plugin.engine.JavaFormatter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class FormatAreaAction extends AnAction {
    private int startOffset;
    private int endOffset;
    int count;
    int codeOfSymbol;

    LineNumberReader r;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JavaFormatter javaFormatter = new JavaFormatter();
        VirtualFile file = null;
        try {
            Document document = (e.getData(LangDataKeys.EDITOR)).getDocument();
            file = FileDocumentManager.getInstance().getFile(document);
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        if (file == null) {
            return;
        }

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        try {
            startOffset = editor.getSelectionModel().getSelectionStart();
            endOffset = editor.getSelectionModel().getSelectionEnd();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        int startLine = 0;
        int endLine = 0;
        count = 0;

        try {
            r = new LineNumberReader(new FileReader(new File(file.getPath())));
            startLine = changeCount(startOffset);
            endLine = changeCount(endOffset);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Integer finalStartLine = startLine;
        Integer finalEndLine = endLine;

        try {
            javaFormatter.formatPartsOfFile(new FileChanges(new File(file.getPath()), new ArrayList<>() {{
                add(new Pair<>(finalStartLine, finalEndLine));
            }}));
        } catch (FormatterException | IOException exception) {
            exception.printStackTrace();
        }
    }

    private int changeCount(int offset) throws IOException {
        int line = 0;
        int crutch = 0;
        while (count < offset && (codeOfSymbol = r.read()) != -1) {
            count++;
        }
        if (count == offset) {
            if (codeOfSymbol != 10 || offset == startOffset) {
                crutch++;
            }
            line = r.getLineNumber() + crutch;
        }
        return line;
    }
}

