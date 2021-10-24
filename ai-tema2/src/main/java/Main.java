import ai.tema.entities.Color;
import ai.tema.entities.Region;
import ai.tema.problem.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static List<Color> createColorsList(Color a, Color b, Color c) {
        List<Color> colors = new ArrayList<>();
        if (a != null) {
            colors.add(a);
        }
        if (b != null) {
            colors.add(b);
        }
        if (c != null) {
            colors.add(c);
        }
        return colors;
    }

    public static List<Region> example1() {
        Region WA = new Region("WA", createColorsList(Color.RED, Color.BLUE, Color.BLUE));
        Region SA = new Region("SA", createColorsList(Color.GREEN, Color.RED, null));
        Region NT = new Region("NT", createColorsList(Color.GREEN, null, null));

        WA.setAdjacentRegions(Arrays.asList(SA, NT));
        SA.setAdjacentRegions(Arrays.asList(WA, NT));
        NT.setAdjacentRegions(Arrays.asList(SA, WA));

        return Arrays.asList(WA, SA, NT);
    }

    public static List<Region> example2() {
        Region WA = new Region("WA", createColorsList(Color.RED, null, null));
        Region SA = new Region("SA", createColorsList(Color.GREEN, Color.RED, Color.BLUE));
        Region NT = new Region("NT", createColorsList(Color.GREEN, Color.BLUE, Color.RED));
        Region Q = new Region("Q", createColorsList(Color.GREEN, null, null));
        Region NSW = new Region("NSW", createColorsList(Color.GREEN, Color.BLUE, Color.RED));
        Region V = new Region("V", createColorsList(Color.GREEN, Color.BLUE, Color.RED));
        Region T = new Region("T", createColorsList(Color.GREEN, Color.BLUE, Color.RED));

        WA.setAdjacentRegions(Arrays.asList(SA, NT));
        SA.setAdjacentRegions(Arrays.asList(WA, NT, Q, NSW, V));
        NT.setAdjacentRegions(Arrays.asList(SA, WA, Q));
        T.setAdjacentRegions(Collections.singletonList(V));
        Q.setAdjacentRegions(Arrays.asList(SA, NT, NSW));
        NSW.setAdjacentRegions(Arrays.asList(SA, V, Q));
        V.setAdjacentRegions(Arrays.asList(SA, NSW, T));

        return Arrays.asList(WA, SA, NT, Q, NSW, V, T);
    }

    public static void main(String[] args) {
        System.out.println("Exemplul 1:");
        Problem problem = new Problem(example1());
        problem.resolve();
        
        System.out.println("\nExemplul 2:");
        Problem problem1 = new Problem(example2());
        problem1.resolve();
    }
}
