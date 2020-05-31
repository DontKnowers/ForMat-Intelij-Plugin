package com.plugin.frontend.actions;

import com.google.googlejavaformat.java.FormatterException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
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
        JavaFormatter test = new JavaFormatter();
        VirtualFile file = FileDocumentManager.getInstance().getFile(e.getData(LangDataKeys.EDITOR).getDocument());

        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        int startOffset = editor.getSelectionModel().getSelectionStart();
        int endOffset = editor.getSelectionModel().getSelectionEnd();

        Integer startLine = 0;
        Integer endLine = 0;

        try {
            LineNumberReader r = new LineNumberReader(new FileReader(new File(file.getPath())));
            int count = 0;
            int codeOfSymbol = 0;
            int crutch = 0;
            while (count < startOffset && (codeOfSymbol = r.read()) != -1) {
                count++;
            }
            if (count == startOffset) {
                if (codeOfSymbol != 10) {
                    crutch++;
                }
                startLine = r.getLineNumber() + crutch;
            }

            crutch = 0;
            while (count < endOffset && (codeOfSymbol = r.read()) != -1) {
                count++;
            }
            if (count == endOffset) {
                if (codeOfSymbol != 10) {
                    crutch++;
                }
                endLine = r.getLineNumber() + crutch;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Integer finalStartLine = startLine;
        Integer finalEndLine = endLine;
        try {
            test.formatPartsOfFile(new FileChanges(new File(file.getPath()), new ArrayList<>() {{
                add(new Pair<>(finalStartLine, finalEndLine));
            }}));
        } catch (FormatterException formatterException) {
            formatterException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

