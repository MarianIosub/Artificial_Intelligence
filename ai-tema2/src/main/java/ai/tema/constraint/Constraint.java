package ai.tema.constraint;

import ai.tema.entities.Region;

public class Constraint {

    private static boolean verifyConstraint(Region region1, Region region2) {
        if (!region1.getAdjacentRegions().contains(region2)) {
            return true;
        }
        return !region1.getChosenColour().equals(region2.getChosenColour());
    }
}
