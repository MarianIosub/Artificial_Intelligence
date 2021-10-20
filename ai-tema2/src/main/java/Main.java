import ai.tema.entities.Colours;
import ai.tema.entities.Region;

import java.util.Arrays;

public class Main {
    public static void exemple1() {
        Region WA = new Region("WA", Arrays.asList(Colours.RED, Colours.BLUE, Colours.BLUE));
        Region SA = new Region("SA", Arrays.asList(Colours.GREEN, Colours.RED));
        Region NT = new Region("NT", Arrays.asList(Colours.GREEN));
        WA.setAdjacentRegions(Arrays.asList(SA, NT));
        SA.setAdjacentRegions(Arrays.asList(WA, NT));
        NT.setAdjacentRegions(Arrays.asList(SA, WA));
    }

    public static void exemple2() {
        Region WA = new Region("WA", Arrays.asList(Colours.RED));
        Region SA = new Region("SA", Arrays.asList(Colours.GREEN, Colours.RED, Colours.BLUE));
        Region NT = new Region("NT", Arrays.asList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region Q =  new Region("Q", Arrays.asList(Colours.GREEN));
        Region NSW =new Region("Q", Arrays.asList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region V =  new Region("V", Arrays.asList(Colours.GREEN, Colours.BLUE, Colours.RED));
        Region T =  new Region("T", Arrays.asList(Colours.GREEN, Colours.BLUE, Colours.RED));
        WA.setAdjacentRegions(Arrays.asList(SA, NT));
        SA.setAdjacentRegions(Arrays.asList(WA, NT, Q, NSW, V));
        NT.setAdjacentRegions(Arrays.asList(SA, WA, Q));
        T.setAdjacentRegions(Arrays.asList(V));
        Q.setAdjacentRegions(Arrays.asList(SA, NT, NSW));
        NSW.setAdjacentRegions(Arrays.asList(SA, V, Q));
        V.setAdjacentRegions(Arrays.asList(SA, NSW, T));

    }

    public static void main(String[] args) {

    }
}
