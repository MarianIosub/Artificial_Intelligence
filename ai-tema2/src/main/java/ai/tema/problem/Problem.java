package ai.tema.problem;

import ai.tema.constraint.Constraint;
import ai.tema.entities.Color;
import ai.tema.entities.Region;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@RequiredArgsConstructor
public class Problem {
    public static final String CONSOLE_COLOR_RED = "\u001B[31m";
    public static final String CONSOLE_COLOR_GREEN = "\u001B[32m";
    public static final String CONSOLE_COLOR_BLUE = "\u001B[34m";
    public static final String CONSOLE_COLOR_RESET = "\u001B[0m";

    @NonNull List<Region> regions;
    Map<Region, Boolean> visited;

    /**
     * Cauta in lista de regiuni regiunea cu cel mai mic domeniu.
     *
     * @return Regiunea gasita.
     */
    private Region getTheRegionThatHaveTheSmallestDomain() {
        int numberOfColors = Integer.MAX_VALUE;
        Region chosenRegion = new Region();
        for (Region region : regions) {
            if (region.getDomain().size() < numberOfColors) {
                chosenRegion = region;
                numberOfColors = region.getDomain().size();
            }
        }
        return chosenRegion;
    }

    /**
     * Trece prin domeniul regiunii si returneaza indexul celei mai potrivite culori. O culoare este potrivita daca,
     * prin asignarea ei catre regiunea actuala, regiunile adiacente regiunii actuale nu vor ramane fara nicio culoare
     * in domeniu (pot fi colorate).
     *
     * @param region Regiunea pentru care se face cautarea.
     * @return Index-ul culorii gasite; -1, daca nu a fost gasita nicio culoare.
     */
    private int getRegionsColorIndexThatSatisfiesConstraint(Region region) {
        int index = 0;
        Color assignedColor = region.getDomain().get(index);

        while (index < region.getDomain().size()) {
            if (Constraint.adjacentRegionsCanBeColoredAfterActualRegionIsColored(region, assignedColor)) {
                break;
            }
            index++;
        }

        return index < region.getDomain().size() ?
                index :
                -1;
    }

    /**
     * Afiseaza in consola solutia gasita.
     */
    private void printSolution() {
        for (Region region : regions) {
            System.out.print(CONSOLE_COLOR_RESET + region.getName() + " ---> ");

            Color regionColor = region.getChosenColor();
            System.out.print(regionColor.equals(Color.RED) ?
                    CONSOLE_COLOR_RED :
                    (regionColor.equals(Color.GREEN) ?
                            CONSOLE_COLOR_GREEN :
                            CONSOLE_COLOR_BLUE
                    ));

            System.out.println(regionColor);

            System.out.print(CONSOLE_COLOR_RESET);
        }
    }

    public void resolve() {
        visited = new HashMap<>();
        Queue<Region> queue = new PriorityBlockingQueue<>(1, Comparator.naturalOrder());

        // Caut si adaug in coada nodul cu domeniul cel mai mic
        Region chosenRegion = getTheRegionThatHaveTheSmallestDomain();
        queue.add(chosenRegion);

        // Algoritm
        while (!queue.isEmpty()) {
            // Iau regiunea cu domeniul cel mai mic din coada
            Region region = queue.poll();
            if (!(visited.get(region) == null || !visited.get(region))) {
                continue;
            }

            visited.put(region, true);

            // Iau culoarea cea mai potrivita pentru regiunea curenta (astfel incat constrangerile sunt satisfacute)
            int colorIndex = getRegionsColorIndexThatSatisfiesConstraint(region);
            if (colorIndex == -1) {
                System.out.println(CONSOLE_COLOR_RED + "Nu exista solutie!!!" + CONSOLE_COLOR_RESET);
                return;
            }

            // Setez culoarea aleasa anterior regiunii actuale
            Color assignedColor = region.getDomain().get(colorIndex);
            region.setChosenColor(assignedColor);

            // Adaug in coada regiunile adiacente nevizitate
            for (Region adjacentRegion : region.getAdjacentRegions()) {
                adjacentRegion.extractColor(region.getChosenColor());

                if (visited.get(adjacentRegion) == null || !visited.get(adjacentRegion)) {
                    queue.add(adjacentRegion);
                }
            }
        }

        // Verificarea constrangerii ca oricare doua regiuni adiacente nu pot avea aceeasi culoare.
        for (Region region : regions) {
            if (!Constraint.noAdjacentRegionsCanHaveTheSameColor(region)) {
                System.out.println(
                        CONSOLE_COLOR_RED +
                        "Constrangerea \"Oricare doua regiuni adiacente nu pot avea aceeasi culoare\" nu e satisfacuta!!!" +
                        CONSOLE_COLOR_RESET
                );
                return;
            }
        }

        // Afisarea pe ecran a solutiei
        printSolution();
    }
}
