package ai.homework.strategies;

import ai.homework.models.State;
import ai.homework.states.InitialState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public abstract class Strategy {
    /**
     * Retine starea actuala din strategie.
     */
    protected State actualState;

    /**
     * Retine numarul de cupluri din problema.
     */
    protected int numberOfCouples;

    protected Strategy(int numberOfCouples) {
        this.numberOfCouples = numberOfCouples;
        actualState = InitialState.initialState(numberOfCouples);
    }

    private Strategy() {
    }

    /**
     * Rezolva problema folosind algoritmul actual. Solutia gasita va fi afisata pe ecran. In cazul in care nu a
     * fost gasita o solutie, va fi afisat pe ecran un mesaj corespunzator.
     */
    public abstract void resolve();

    @Override
    public String toString() {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        State state = actualState;

        while (state != null) {
            if (state.getPreviousState() == null) break;

            lines.add(state.toString());
            state = state.getPreviousState();
        }
        Collections.reverse(lines);

        AtomicReference<Integer> index = new AtomicReference<>(1);
        lines = lines.stream()
                .map(line -> line = String.format("Tranzitia %d: %s", index.getAndSet(index.get() + 1), line))
                .collect(Collectors.toList());

        sb.append("\n");
        lines.forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }
}
