package ai.tema.entities;

import java.util.ArrayList;
import java.util.List;

public class Region {
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

    public void setAdjacentRegions(List<Region> adjacentRegions) {
        this.adjacentRegions = adjacentRegions;
    }
}
