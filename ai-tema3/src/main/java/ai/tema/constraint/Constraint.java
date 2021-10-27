package ai.tema.constraint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Constraint {
    /**
     * Verifica daca urmatoarea constrangere este satisfacuta: o culoare poate aparea in secventa de cel mult
     * m ori.
     * @param colorSequence Secventa de culori in care se face verificarea.
     * @param m Numarul maxim de aparitii pe care culoare o poate avea.
     * @param chosenColor Culoarea pentru care se face verificarea.
     * @return true, daca constrangerea este satisfacuta; false, altfel.
     */
    public static boolean verifyMConstraint(List<Integer> colorSequence,
                                            int m,
                                            Integer chosenColor) {
        if(chosenColor != null) {
            List<Integer> colorAppearances = colorSequence.stream()
                    .filter(color -> Objects.equals(color, chosenColor))
                    .collect(Collectors.toList());

            return colorAppearances.size() + 1 <= m;
        }

        Map<Integer, Integer> mapOfColors = new HashMap<>();
        for(Integer color : colorSequence) {
            mapOfColors.merge(color, 1, Integer::sum);
        }

        for(Integer color : mapOfColors.keySet()) {
            if(mapOfColors.get(color).compareTo(m) > 0) {
                return false;
            }
        }

        return true;
    }
}
