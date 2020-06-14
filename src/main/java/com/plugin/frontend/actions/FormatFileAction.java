package com.plugin.frontend.actions;

import com.google.googlejavaformat.java.FormatterException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.plugin.engine.JavaFormatter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class FormatFileAction extends AnAction {
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

        try {
            javaFormatter.formatFile(new File(file.getPath()));
        } catch (FormatterException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
