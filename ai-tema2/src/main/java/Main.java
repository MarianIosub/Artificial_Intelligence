import ai.tema.entities.Colours;
import ai.tema.entities.Region;
import ai.tema.problem.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static List<Colours> createColoursList(Colours a, Colours b, Colours c) {
        List<Colours> colours = new ArrayList<>();
        if (a != null) {
            colours.add(a);
        }
        if (b != null) {
            colours.add(b);
        }
        if (c != null) {
            colours.add(c);
        }
        return colours;
    }

    public static List<Region> exemple1() {
        Region WA = new Region("WA", createColoursList(Colours.RED, Colours.BLUE, Colours.BLUE));
        Region SA = new Region("SA", createColoursList(Colours.GREEN, Colours.RED, null));
        Region NT = new Region("NT", createColoursList(Colours.GREEN, null, null));
        WA.setAdjacentRegions(Arrays.asList(SA, NT));
        SA.setAdjacentRegions(Arrays.asList(WA, NT));
        NT.setAdjacentRegions(Arrays.asList(SA, WA));
        return Arrays.asList(WA, SA, NT);
    }

    public static List<Region> exemple2() {
        Region WA = new Region("WA", createColoursList(Colours.RED, null, null));
        Region SA = new Region("SA", createColoursList(Colours.GREEN, Colours.RED, Colours.BLUE));
        Region NT = new Region("NT", createColoursList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region Q = new Region("Q", createColoursList(Colours.GREEN, null, null));
        Region NSW = new Region("Q", createColoursList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region V = new Region("V", createColoursList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region T = new Region("T", createColoursList(Colours.GREEN, Colours.BLUE, Colours.RED));
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
        Problem problem = new Problem(exemple1());
        problem.resolve();
    }
}
