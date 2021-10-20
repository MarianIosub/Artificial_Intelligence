package ai.tema.problem;

import ai.tema.entities.Region;

import java.util.List;

public class Problem {
    List<Region> regions;

    public Problem(List<Region> regions) {
        this.regions = regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Region> getRegions() {
        return regions;
    }
}
