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

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        int startOffset = -1;
        int endOffset = -1;

        if (file == null || editor == null) {
            return;
        }

        try {
            startOffset = editor.getSelectionModel().getSelectionStart();
            endOffset = editor.getSelectionModel().getSelectionEnd();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        if (startOffset == -1 || endOffset == -1) {
            return;
        }

        LineNumberReader r;
        Integer startLine = 0;
        int endLine = 0;
        int count = 0;

        try {
            r = new LineNumberReader(new FileReader(new File(file.getPath())));
            zxc start = getLineNumber(startOffset, false, r, count);
            startLine = start.line;
            count = start.count;
            endLine = getLineNumber(endOffset, true, r, count).line;
            System.out.println(startLine);
            System.out.println(endLine);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Integer finalStartLine = startLine;
        Integer finalEndLine = endLine;

        try {
            javaFormatter.formatPartsOfFile(new FileChanges(new File(file.getPath()), new ArrayList<>() {{
                add(new Pair<>(finalStartLine, finalEndLine)); // объявить здесь переменные - не получилось
            }}));
        } catch (FormatterException | IOException exception) {
            exception.printStackTrace();
        }
    }

    //position 0 - start; position 1 - end
    private zxc getLineNumber(int offset, boolean position, LineNumberReader r, int count) throws IOException {
        int line = 0;
        int crutch = 0;
        int codeOfSymbol = 0;

        while (count < offset && (codeOfSymbol = r.read()) != -1) {
            count++;
        }

        if (count == offset) {
            if (codeOfSymbol != 10 || !position) {
                crutch++;
            }
            line = r.getLineNumber() + crutch;
        }
        zxc ans = new zxc(count, line);

        return ans;
    }

    private class zxc{
        int count;
        int line;

        zxc(int count, int line) {
            this.count = count;
            this.line = line;
        }
    }
}

