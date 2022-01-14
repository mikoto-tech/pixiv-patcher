package net.mikoto.pixiv.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author mikoto
 * @date 2022/1/2 1:33
 */
public class FileUtil {
    /**
     * Create a new dir or don't do anything.
     *
     * @param dirName The name of the dir.
     */
    public static void createDir(@NotNull String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                System.err.println("Can't create dir");
            }
        }
    }

    /**
     * Create a new file of don't do anything.
     *
     * @param file  The path of the file.
     * @param input The data in the file.
     * @throws IOException An error.
     */
    public static void createFile(@NotNull File file, @NotNull String input) throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(input);
                fileWriter.close();
            } else {
                System.err.println("Can't create config");
            }
        }
    }

    /**
     * Write data into the file.
     *
     * @param file  The path of the file.
     * @param input The data in the file.
     * @throws IOException An error.
     */
    public static void writeFile(@NotNull File file, @NotNull String input) throws IOException {
        if (!file.exists()) {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(input);
                fileWriter.close();
            } else {
                System.err.println("Can't create config");
            }
        } else {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(input);
            fileWriter.close();
        }
    }
}
