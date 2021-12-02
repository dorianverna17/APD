package sampleForkJoinPool.recursiveSerial;

import java.io.File;

public class Main {
    public static void show(String path) {
        File file = new File(path);
        if (file.isFile()) {
            System.out.println(file.getPath());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    show(f.getPath());
                }
            }
        }
    }

    public static void main(String[] args) {
        show("files");
    }
}
