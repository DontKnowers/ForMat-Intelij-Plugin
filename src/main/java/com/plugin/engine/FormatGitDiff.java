package com.plugin.engine;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatGitDiff {
    ProcessBuilder gitDiffBuilder;
    Process gitDiffProcess;
    BufferedReader reader;
    public ArrayList<File> diffChanges;

    public FormatGitDiff() throws IOException {
        this.gitDiffBuilder = new ProcessBuilder("git", "diff");
        this.gitDiffProcess = gitDiffBuilder.start();
        this.reader = new BufferedReader(new InputStreamReader(gitDiffProcess.getInputStream()));
        this.diffChanges = new ArrayList<>();
    }

    public ArrayList<File> executeCommand() throws IOException {
        String line = reader.readLine();

        while (line != null) {

            //Found the line with Meta info of changed files and check if this files ara Java
            //Than, creating a File with name and FileWriter to it
            if (line.startsWith("diff")) {
                System.out.println(line);
                String[] res = line.split(" ");
                for (String s : res) {
                    Pattern javaFormatPattern = Pattern.compile(".*[.]java&");
                    Matcher matcher = javaFormatPattern.matcher(s);
                    if (matcher.matches()) {
                        //TODO implement
                        break;
                    }
                }
            }

            //It's actually might be bad to do an empty loop, but
            //I'm searching for actual diffs of java files. They are separated from
            //meta info by empty line

            while ((line = reader.readLine()) != null && !line.equals(" ")) {

            }

            //Searching for the end of git diff or next file and writing to file all lines
            //Substring(1) is here because first symbol in line is '+' or '-' or ' '(line was added or deleted)
            while ((line = reader.readLine()) != null && !line.startsWith("diff")) {

            }

        }

        return diffChanges;
    }

}
