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
    public static void createDir(@NotNull String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                System.err.println("Can't create dir");
            }
        }
    }

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
