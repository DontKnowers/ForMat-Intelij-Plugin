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

    }
}

