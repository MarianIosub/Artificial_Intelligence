package ai.tema.problem;

import ai.tema.entities.Colours;
import ai.tema.entities.Region;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Problem {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    List<Region> regions;
    Map<Region, Boolean> visited;

    public Problem(List<Region> regions) {
        this.regions = regions;
        visited = new HashMap<>();
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public void resolve() {
        Queue<Region> queue = new PriorityBlockingQueue<>(1, Comparator.naturalOrder());
        int numberOfColors = Integer.MAX_VALUE;
        Region chosenRegion = new Region();
        for (Region region : regions) {
            if (region.getDomain().size() < numberOfColors) {
                chosenRegion = region;
                numberOfColors = region.getDomain().size();
            }
        }
        queue.add(chosenRegion);
        while (!queue.isEmpty()) {
            Region region = queue.poll();
            if (!(visited.get(region) == null || !visited.get(region))) {
                continue;
            }
            visited.put(region, true);
            int index = 0;
            while (index < region.getDomain().size()) {
                if (verifyNeighbours(region, region.getDomain().get(index))) {
                    break;
                }
                index++;
            }
            if (index == region.getDomain().size()) {
                System.out.println(ANSI_RED + "Nu exista solutie!!!" + ANSI_RESET);
                return;
            }
            region.setChosenColour(region.getDomain().get(index));
            for (Region region1 : region.getAdjacentRegions()) {
                region1.extractColor(region.getChosenColour());
                if (visited.get(region1) == null || !visited.get(region1)) {
                    queue.add(region1);
                }
            }
        }
        for (Region region : regions) {
            System.out.print(ANSI_RESET + region.getName() + " ---> ");
            System.out.println((region.getChosenColour().equals(Colours.RED) ? ANSI_RED :
                    region.getChosenColour().equals(Colours.GREEN) ? ANSI_GREEN : ANSI_BLUE) + region.getChosenColour());
        }

    }

    private boolean verifyNeighbours(Region region, Colours colours) {
        for (Region neighbour : region.getAdjacentRegions()) {
            if (visited.get(neighbour) == null || !visited.get(neighbour)) {
                List<Colours> colours1 = new ArrayList<>(neighbour.getDomain());
                colours1.remove(colours);
                if (colours1.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Region> getRegions() {
        return regions;
    }
}
