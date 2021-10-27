package ai.tema.constraint;

import java.util.List;
import java.util.stream.Collectors;

public class InputFormatter {
    /**
     * Formateaza o linie citita de la tastatura, returnand o lista de culori.
     *
     * @param line Linia citita de la tastatura.
     * @return Lista de culori.
     */
    public static List<Integer> formatInputLine(String line) throws NumberFormatException {
        line = line.trim();
        List<String> colorSequenceAsString = List.of(line.split(" "));

        return colorSequenceAsString.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}
