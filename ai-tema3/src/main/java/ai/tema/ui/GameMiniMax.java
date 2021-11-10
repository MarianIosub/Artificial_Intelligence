package ai.tema.ui;

import ai.tema.entities.ColorSequence;
import ai.tema.entities.State;
import ai.tema.state.StateUtils;
import lombok.SneakyThrows;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ai.tema.utils.Colors.*;
import static ai.tema.utils.Colors.CONSOLE_COLOR_RED;
import static java.lang.Thread.sleep;

@SuppressWarnings("BusyWait")
public class GameMiniMax {
    /**
     * Numarul de culori cu care se va juca jocul
     */
    private final int n;
    /**
     * Numarul maxim de repetitii a unei culori
     */
    private final int m;
    /**
     * Dimensiunea secventei de culori
     */
    private final int k;

    /**
     * Lista combinatiilor de culori care pot fi alese la pasul actual
     */
    private List<List<Integer>> combinations;

    private State actualState;

    public GameMiniMax(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.actualState = StateUtils.getInitialState(n, m, k);

        List<Integer> colorsList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            colorsList.add(i);
        }
        combinations = Generator.permutation(colorsList)
                .simple()
                .stream()
                .map(combination -> combination.subList(0, k))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Alege cea mai buna secventa de culori de la pasul actual.
     *
     * @return Secventa de culori aleasa.
     */
    private ColorSequence getBestColorSequenceMiniMax() {
        pruneCombinations();

        ColorSequence bestSequence = null;
        int bestSequenceScore = Integer.MIN_VALUE;

        // Aleg combinatia cu scorul cel mai mare
        for (List<Integer> combination : combinations) {
            Integer score = computeCombinationScore(combination);
            if (score > bestSequenceScore) {
                bestSequenceScore = score;
                bestSequence = new ColorSequence(combination);
            }
        }

        // Nu am gasit nicio combinatie cu scor maxim => generez aleator una
        if (bestSequenceScore == 0) {
            Random random = new Random();
            return new ColorSequence(combinations.get(random.nextInt(combinations.size())));
        }

        return bestSequence;
    }

    /**
     * Face pruning combinatiilor posibile de culori.
     */
    private void pruneCombinations() {
        if (actualState.getSteps() >= 1) {
            State grandpa = actualState.getParent();
            int grandpaGuessedColors = StateUtils.compareSequencesFromState(grandpa);
            State parent = actualState;
            int parentGuessedColors = StateUtils.compareSequencesFromState(parent);

            // Numarul de culori modificate intre parinte si bunic
            int numberOfModifiedColors = 0;
            for (int i = 0; i < k; i++) {
                if (!grandpa.getGuessedColorSequence().get(i).equals(parent.getGuessedColorSequence().get(i))) {
                    numberOfModifiedColors++;
                }
            }

            // Parintele nu ghiceste nicio culoare
            if (parentGuessedColors == 0) {
                for (int i = 0; i < k; i++) {
                    int finalI = i;
                    combinations = combinations.stream()
                            .filter(combination -> !combination.get(finalI).equals(parent.getGuessedColorSequence().get(finalI)))
                            .collect(Collectors.toList());
                }
            }

            // Parintele ghiceste acelasi numar de culori ca si bunicul
            if (grandpaGuessedColors == parentGuessedColors) {
                // O singura culoare a fost modificata intre secventele parintelui si a bunicului => niciuna din cele doua
                //culori diferite nu este corecta
                if (numberOfModifiedColors == 1) {
                    for (int i = 0; i < k; i++) {
                        if (!grandpa.getGuessedColorSequence().get(i).equals(parent.getGuessedColorSequence().get(i))) {
                            int finalI = i;
                            combinations = combinations.stream()
                                    .filter(combination -> !combination.get(finalI).equals(parent.getGuessedColorSequence().get(finalI)) &&
                                            !combination.get(finalI).equals(grandpa.getGuessedColorSequence().get(finalI)))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }

            // Bunicul a ghicit mai multe culori decat parintele
            if (grandpaGuessedColors > parentGuessedColors) {
                // O singura culoare a fost modificata intre secventele parintelui si a bunicului => niciuna din cele doua
                //culori diferite nu este corecta
                if (numberOfModifiedColors == 1) {
                    for (int i = 0; i < k; i++) {
                        if (!grandpa.getGuessedColorSequence().get(i).equals(parent.getGuessedColorSequence().get(i))) {
                            int finalI = i;
                            combinations = combinations.stream()
                                    .filter(combination -> combination.get(finalI).equals(grandpa.getGuessedColorSequence().get(finalI)))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }

            // Parintele a ghicit mai multe culori decat bunicul
            if (grandpaGuessedColors < parentGuessedColors) {
                // O singura culoare a fost modificata intre secventele parintelui si a bunicului => niciuna din cele doua
                //culori diferite nu este corecta
                if (numberOfModifiedColors == 1) {
                    for (int i = 0; i < k; i++) {
                        if (!grandpa.getGuessedColorSequence().get(i).equals(parent.getGuessedColorSequence().get(i))) {
                            int finalI = i;
                            combinations = combinations.stream()
                                    .filter(combination -> combination.get(finalI).equals(parent.getGuessedColorSequence().get(finalI)))
                                    .collect(Collectors.toList());
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculeaza scorul produs de o secventa de culori. Scorul unei combinatii de culori este suma "aparitiilor" fiecarei
     * culori din combinatie. Aparitia unei culori este suma numarului culorilor ghicite din starile anterioare in care
     * apare acea culoare.
     *
     * @param combination Combinatia pentru care se face calculul.
     * @return Scorul calculat.
     */
    private Integer computeCombinationScore(List<Integer> combination) {
        List<Integer> apparitions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            apparitions.add(0);
        }

        State state = actualState;
        while (state != null) {
            for (int i = 0; i < state.getGuessedColorSequence().size(); i++) {
                if (state.getGuessedColorSequence().get(i).equals(combination.get(i))) {
                    apparitions.set(i, apparitions.get(i) + StateUtils.compareSequencesFromState(state));
                }
            }
            state = state.getParent();
        }

        Integer score = 0;
        for (Integer value : combination) {
            score += apparitions.get(value - 1);
        }
        return score;
    }

    @SneakyThrows
    public void play() {
        // Generez prima secventa de culori --> [1, 2, ..., k]
        ColorSequence colorSequence = new ColorSequence(
                IntStream.rangeClosed(1, k)
                        .boxed().collect(Collectors.toList())
        );

        while (StateUtils.isFinalState(actualState) == null) {
            if (colorSequence == null) {
                colorSequence = getBestColorSequenceMiniMax();
            }
            combinations.remove(colorSequence.getSequence());

            actualState = new State(
                    actualState,
                    actualState.getChosenColorSequence(),
                    colorSequence.getSequence(),
                    actualState.getSteps() + 1,
                    actualState.getN()
            );

            System.out.printf(CONSOLE_COLOR_GREEN + "Ai ghicit %d culori.%n" + CONSOLE_COLOR_RESET,
                    StateUtils.compareSequencesFromState(actualState));
            sleep(500);
            System.out.println("\n".repeat(2 * n - actualState.getSteps()) + StateUtils.printStatesTillActual(actualState));
            colorSequence = null;
        }

        System.out.printf(CONSOLE_COLOR_RED + "\n\n\n>>>>>>>>>   A castigat jucatorul %s    <<<<<<<<<<<%n" + CONSOLE_COLOR_RESET,
                StateUtils.isFinalState(actualState));
        System.out.println(CONSOLE_COLOR_RED + ">>>>>>>>>   Secventa era: " + actualState.getChosenColorSequence() +
                "    <<<<<<<<<<<" + CONSOLE_COLOR_RESET);
    }
}
