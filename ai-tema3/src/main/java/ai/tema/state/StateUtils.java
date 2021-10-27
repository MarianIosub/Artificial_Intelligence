package ai.tema.state;

import ai.tema.constraint.Constraint;
import ai.tema.entities.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StateUtils {
    /**
     * Genereaza o secventa de culori aleatoare.
     *
     * @param n Numarul de culori.
     * @param m Numarul maxim de aparitii pe care o culoare il poate avea.
     * @param k Dimensiunea secventei de culori.
     * @return Secventa generata.
     */
    private static List<Integer> chooseColorSequence(int n, int m, int k) {
        List<Integer> colorSequence = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < k; i++) {
            int color = random.nextInt(n) + 1;
            while (!Constraint.verifyMConstraint(colorSequence, m, color)) {
                color = random.nextInt(n) + 1;
            }

            colorSequence.add(color);
        }

        return colorSequence;
    }

    /**
     * Creeaza starea initiala.
     *
     * @param n Numarul de culori.
     * @param m Numarul maxim de aparitii pe care o culoare il poate avea.
     * @param k Dimensiunea secventei de culori.
     * @return Starea creata.
     */
    public static State getInitialState(int n, int m, int k) {
        State state = new State();
        state.setParent(null);
        state.setSteps(0);
        state.setN(n);

        // Secventa de culori aleasa de jucatorul A
        state.setChosenColorSequence(chooseColorSequence(n, m, k));

        // Secventa de culori ghicita de jucatorul B in momentul actual
        List<Integer> guessedColorSequence = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            guessedColorSequence.add(-1);
        }
        state.setGuessedColorSequence(guessedColorSequence);

        return state;
    }

    /**
     * Verifica daca o stare este stare finala.
     *
     * @param state Starea pentru care se face verificarea.
     * @return <b>A</b>, daca starea este finala si a castigat jucatorul A; <b>B</b>, daca starea este finala si
     * a castigat jucatorul B; <b>null</b>, daca starea nu este finala
     */
    public static String isFinalState(State state) {
        if (state.getChosenColorSequence().equals(state.getGuessedColorSequence())) {
            return "B";
        }

        if (state.getSteps() >= 2 * state.getN()) {
            return "A";
        }

        return null;
    }

    public static String printStatesTillActual(State state) {
        StringBuilder stringBuilder = new StringBuilder();
        State actualState = state;
        while (actualState.getSteps() != 0) {
            stringBuilder.append(actualState);
            actualState = actualState.getParent();
        }
        return stringBuilder.toString();
    }

    public static int compareSequencesFromState(State state1) {
        int numberOfGuessedColors = 0;
        List<Integer> guessedColors = state1.getGuessedColorSequence();
        List<Integer> chosenColors = state1.getChosenColorSequence();

        for (int i = 0; i < chosenColors.size(); i++) {
            if (guessedColors.get(i).equals(chosenColors.get(i))) {
                numberOfGuessedColors++;
            }
        }

        return numberOfGuessedColors;
    }
}
