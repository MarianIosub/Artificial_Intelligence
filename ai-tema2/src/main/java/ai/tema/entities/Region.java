package ai.tema.entities;

import java.util.ArrayList;
import java.util.List;

public class Region implements Comparable<Region> {
    private String name;
    private List<Colours> domain;
    private Colours chosenColour;
    private List<Region> adjacentRegions;

    public Region(String name, List<Colours> domain) {
        this.name = name;
        this.domain = domain;
        this.chosenColour = null;
        this.adjacentRegions = new ArrayList<>();
    }

    public Region() {

    }

    public String getName() {
        return name;
    }

    public List<Colours> getDomain() {
        return domain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomain(List<Colours> domain) {
        this.domain = domain;
    }

    public Colours getChosenColour() {
        return chosenColour;
    }

    public void setChosenColour(Colours chosenColour) {
        this.chosenColour = chosenColour;
    }

    public List<Region> getAdjacentRegions() {
        return adjacentRegions;
    }

    public void extractColor(Colours chosenColour) {
        if (domain.contains(chosenColour)) {
            this.domain.remove(chosenColour);
        }
    }

    public void addColour(Colours colours) {
        domain.add(colours);
    }

    public void setAdjacentRegions(List<Region> adjacentRegions) {
        this.adjacentRegions = adjacentRegions;
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", chosenColour=" + chosenColour +
                '}';
    }

    @Override
    public int compareTo(Region o) {
        return Integer.compare(this.getDomain().size(), o.getDomain().size());
    }
}
