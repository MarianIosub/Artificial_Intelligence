package ai.homework.states;

import ai.homework.models.State;

import java.util.ArrayList;
import java.util.List;

public class InitialState {
    private InitialState() { }

    /**
     * Creeaza o stare initiala, avand un anumit numar de cupluri. Starea initiala are valoarea <b>null</b> in
     * campul corespunzator starii anterioare.
     * @param numberOfCouples Numarul de cupluri pe care il va avea starea initiala.
     * @return Starea initiala creata.
     */
    public static State initialState(int numberOfCouples) {
        List<Boolean> women = new ArrayList<>();
        List<Boolean> men = new ArrayList<>();

        for (int i = 0; i < numberOfCouples; i++) {
            women.add(false);
            men.add(false);
        }

        return new State(null, numberOfCouples, women, men, false);
    }
}
