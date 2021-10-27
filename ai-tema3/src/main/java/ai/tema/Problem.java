package ai.tema;

import ai.tema.constraint.Constraint;
import ai.tema.constraint.InputFormatter;
import ai.tema.entities.State;
import ai.tema.state.StateUtils;
import lombok.SneakyThrows;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Problem {
    private int n;
    private int m;
    private int k;

    private State actualState;

    public Problem(int n, int m, int k) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.actualState = StateUtils.getInitialState(n, m, k);
    }

    /**
     * Compara secventa de culori aleasa de jucatorul A cu secventa de culori ghicita de jucatorul B.
     *
     * @return Numarul de pozitii in care cele doua secvente au aceeasi valoare (aceeasi culoare).
     */


    @SneakyThrows
    public void resolve() {
        System.out.println(actualState.getChosenColorSequence());

        Scanner scanner = new Scanner(System.in);

        while (StateUtils.isFinalState(actualState) == null) {
            System.out.println("Introduceti secventa de culori");
            System.out.print(" >> ");
            String colorSequenceLine = scanner.nextLine();
            List<Integer> colorSequence = null;
            try {
                colorSequence = InputFormatter.formatInputLine(colorSequenceLine);
            } catch (NumberFormatException exception) {
                System.out.println("Secventa de culori este invalida!");
                continue;
            }

            while (colorSequence.size() != k || !Constraint.verifyMConstraint(colorSequence, m, null) || !Constraint.verifyNConstraint(colorSequence, n)) {
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
                    continue;
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
                Thread.sleep(1500);
                System.out.println("\n\n\n" + StateUtils.printStatesTillActual(actualState));
            }
        }

        System.out.printf("A castigat jucatorul %s%n", StateUtils.isFinalState(actualState));
    }
}
