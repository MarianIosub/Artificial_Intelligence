package ai.homework.states;

import ai.homework.models.State;

public class FinalState {
    private FinalState() { }

    /**
     * Verifica daca o stare este stare finala.
     * @param state Starea care va fi verificata.
     * @return <b>true</b>, daca aceasta este finala; <b>false</b>, daca aceasta nu este, sau daca
     * starea este <b>null</b>.
     */
    public static boolean isFinalState(State state) {
        if(state == null) return false;

        return !state.getWomen().contains(false) && !state.getMen().contains(false) && state.isBoatOnRight();
    }
}
