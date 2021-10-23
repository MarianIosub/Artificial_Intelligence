package ai.tema.constraint;

import ai.tema.entities.Region;

public class Constraint {

    public static boolean verifyConstraint(Region region1) {
        if (region1.getChosenColour() == null) {
            return false;
        }
        for (Region region : region1.getAdjacentRegions()) {
            if (region.getChosenColour().equals(region1.getChosenColour())) {
                return false;
            }
        }
        return true;
    }
}
