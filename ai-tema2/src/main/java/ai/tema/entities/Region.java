package ai.tema.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Region implements Comparable<Region> {
    @NonNull
    private String name;
    @NonNull
    private List<Color> domain;
    private Color chosenColor = null;
    private List<Region> adjacentRegions = new ArrayList<>();

    /**
     * Sterge o culoare din domeniul regiunii, daca aceasta exista in domeniu.
     *
     * @param chosenColor Culoarea care va fi stearsa.
     */
    public void extractColor(Color chosenColor) {
        if (domain.contains(chosenColor)) {
            this.domain.remove(chosenColor);
        }
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", chosenColour=" + chosenColor +
                '}';
    }

    @Override
    public int compareTo(Region o) {
        return Integer.compare(this.getDomain().size(), o.getDomain().size());
    }
}
