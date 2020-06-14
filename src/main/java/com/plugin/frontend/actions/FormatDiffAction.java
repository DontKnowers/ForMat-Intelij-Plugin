package com.plugin.frontend.actions;

import com.google.googlejavaformat.java.FormatterException;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.plugin.engine.FormatGitDiff;
import com.plugin.engine.JavaFormatter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FormatDiffAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        JavaFormatter javaFormatter = new JavaFormatter();

        try {
            FormatGitDiff gitDiff = new FormatGitDiff();

            String projDir = e.getData(LangDataKeys.EXECUTION_ENVIRONMENT).getProject().getBasePath();

            ArrayList<File> gitDiffFiles = gitDiff.executeCommand();

            for (File file : gitDiffFiles) {
                javaFormatter.formatFile(file);
            }

        } catch (FormatterException | IOException exception) {
            exception.printStackTrace();
        }
    }
}