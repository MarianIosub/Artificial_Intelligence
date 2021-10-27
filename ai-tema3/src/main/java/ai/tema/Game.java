package ai.tema;

import ai.tema.constraint.Constraint;
import ai.tema.constraint.InputFormatter;
import ai.tema.entities.State;
import ai.tema.state.StateUtils;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Scanner;

import static java.lang.Thread.sleep;

@SuppressWarnings("BusyWait")
public class Game {
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

    private State actualState;

    public Game(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.actualState = StateUtils.getInitialState(n, m, k);
    }


    @SneakyThrows
    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (StateUtils.isFinalState(actualState) == null) {
            System.out.println("Introduceti secventa de culori");
            System.out.print(" >> ");
            String colorSequenceLine = scanner.nextLine();
            List<Integer> colorSequence;
            try {
                colorSequence = InputFormatter.formatInputLine(colorSequenceLine);
            } catch (NumberFormatException exception) {
                System.out.println("Secventa de culori este invalida!");
                continue;
            }

            while (colorSequence.size() != k || !Constraint.verifyMConstraint(colorSequence, m, null) ||
                    !Constraint.verifyNConstraint(colorSequence, n)) {
                System.out.printf(
                        "Secventa de culori trebuie sa aiba dimensiunea %d si fiecare culoare trebuie sa " +
                                "apara de cel mult %d ori.Culorile pot fi pana la %d.%n",
                        k, m, n
                );

                System.out.println("Introduceti secventa de culori");
                System.out.print(" >> ");
                colorSequenceLine = scanner.nextLine();
                try {
                    colorSequence = InputFormatter.formatInputLine(colorSequenceLine);
                } catch (NumberFormatException exception) {
                    System.out.println("Secventa de culori este invalida!");
                }
            }

            actualState = new State(
                    actualState,
                    actualState.getChosenColorSequence(),
                    colorSequence,
                    actualState.getSteps() + 1,
                    actualState.getN()
            );

            if (StateUtils.isFinalState(actualState) == null) {
                System.out.printf("Ai ghicit %d culori.%n", StateUtils.compareSequencesFromState(actualState));
                sleep(1500);
                System.out.println("\n".repeat(2 * n - actualState.getSteps()) + StateUtils.printStatesTillActual(actualState));
            }
        }

        System.out.printf("A castigat jucatorul %s%n", StateUtils.isFinalState(actualState));
    }
}
