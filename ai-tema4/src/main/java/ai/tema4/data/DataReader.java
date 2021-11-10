package ai.tema4.data;

import ai.tema4.entity.Instance;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataReader {
    @SneakyThrows
    public static List<Instance> readFile(String filename) {
        URL res = DataReader.class.getClassLoader().getResource(filename);
        File file = Paths.get(res.toURI()).toFile();
        Scanner scanner = new Scanner(file);
        List<Instance> content = new ArrayList<>();
        Instance instance;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineSplitted = line.split(",");

            instance = new Instance(
                    Integer.valueOf(lineSplitted[0]),
                    Integer.valueOf(lineSplitted[1]),
                    Integer.valueOf(lineSplitted[2])
            );
            content.add(instance);
        }

        return content;
    }
}
