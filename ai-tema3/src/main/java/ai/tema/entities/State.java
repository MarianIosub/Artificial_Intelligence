package ai.tema.entities;

import ai.tema.state.StateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class State {
    private State parent;

    /**
     * Secventa de culori aleasa de jucatorul A.
     */
    private List<Integer> chosenColorSequence;

    /**
     * Secventa de culori ghicita de B la momentul actual.
     */
    private List<Integer> guessedColorSequence;

    /**
     * Numarul de pasi efectuati.
     */
    private int steps;

    private int n;

    @Override
    public String toString() {
        return guessedColorSequence + "   ->>Step: " + steps + "  ->>Steps remained: " + (n * 2 - steps) + "  ->>Here you guessed: " + StateUtils.compareSequencesFromState(this) +
                '\n';
    }
}
