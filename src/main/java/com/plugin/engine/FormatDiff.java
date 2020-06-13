package com.plugin.engine;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatDiff {
    ProcessBuilder gitDiffBuilder;
    Process gitDiffProcess;
    BufferedReader reader;

    public FormatDiff() throws IOException {
        this.gitDiffBuilder = new ProcessBuilder("git", "diff");
        this.gitDiffProcess = gitDiffBuilder.start();
        this.reader = new BufferedReader(new InputStreamReader(gitDiffProcess.getInputStream()));
    }

    public File executeCommand() throws IOException {
        String line = reader.readLine();

        boolean isCurDiffDileJava = false;

        File file = new File("GitDiff");
        FileWriter fw = new FileWriter(file);

        while (line != null) {


            //Found the line with Meta info of changed files and check if this files ara Java
            //Than, creating a File with name and FileWriter to it
            if (line.startsWith("diff")) {
                String[] res = line.split(" ");
                for (String s : res) {
                    Pattern javaFormatPattern = Pattern.compile(".*[.]java&");
                    Matcher matcher = javaFormatPattern.matcher(s);
                    if (matcher.matches()) {
                        isCurDiffDileJava = true;
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
                if(isCurDiffDileJava) {
                    fw.write(line.substring(1) + '\n');
                }
            }
            isCurDiffDileJava = false;
        }

        return file;
    }

}
