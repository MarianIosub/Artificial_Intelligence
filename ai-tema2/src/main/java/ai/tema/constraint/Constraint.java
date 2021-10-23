package ai.tema.constraint;

import ai.tema.entities.Color;
import ai.tema.entities.Region;

import java.util.ArrayList;
import java.util.List;

public class Constraint {
    /**
     * Verifica daca constrangerea problemei este satisfacuta: nicio regiune adiaceta regiunii primite ca si parametru
     * nu poate avea aceeasi culoare aleasa ca si culoarea aleasa pentru aceasta.
     *
     * @param region Regiunea pentru care se face verificarea.
     * @return true, daca conditia este respectata; false, altfel
     */
    public static boolean noAdjacentRegionsCanHaveTheSameColor(Region region) {
        if (region.getChosenColor() == null) {
            return false;
        }

        for (Region adjactentRegion : region.getAdjacentRegions()) {
            if (region.getChosenColor().equals(adjactentRegion.getChosenColor())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica daca, dupa asignarea unei culori catre regiunea actuala, regiunile adiacente acesteia mai pot fi
     * colorate. Mai precis, verifica daca regiunile adiacente mai au cel putin o culoare in domeniu.
     *
     * @param region  Regiunea actuala.
     * @param colours Culoarea cu care aceasta va fi colorata.
     * @return true, daca constrangerea este satisfacuta; false, atlfel.
     */
    public static boolean adjactentRegionsCanBeColoredAfterActualRegionIsColored(Region region, Color colours) {
        for (Region neighbour : region.getAdjacentRegions()) {
            if (neighbour.getChosenColor() == null) {
                List<Color> colours1 = new ArrayList<>(neighbour.getDomain());
                colours1.remove(colours);
                if (colours1.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
