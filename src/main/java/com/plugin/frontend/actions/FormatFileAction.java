package com.plugin.frontend.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.plugin.engine.JavaFormatter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class FormatFileAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JavaFormatter test = new JavaFormatter();
        VirtualFile file = FileDocumentManager.getInstance().getFile(Objects.requireNonNull(e.getData(LangDataKeys.EDITOR)).getDocument());

        if (file == null) {
            return;
        }

        try {
            test.formatFile(new File(file.getPath()));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
