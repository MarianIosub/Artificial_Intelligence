package ai.homework.strategies;

import ai.homework.models.State;
import ai.homework.models.Transition;
import ai.homework.states.FinalState;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class BackTrackingStrategy extends Strategy {
    /**
     * Retine lista starilor vizitate de algoritmul Backtracking (algoritm finalizat sau nu pentru acestea).
     */
    private List<State> listOfStates;

    /**
     * Indica daca algoritmul Backtracking a gasit o solutie sau nu.
     */
    private boolean foundSolution;

    public BackTrackingStrategy(int numberOfCouples) {
        super(numberOfCouples);
    }

    private void backtracking() {
        for (int femaleIndex1 = -1; femaleIndex1 < numberOfCouples; femaleIndex1++) {
            for (int maleIndex1 = -1; maleIndex1 < numberOfCouples; maleIndex1++) {
                for (int femaleIndex2 = -1; femaleIndex2 < numberOfCouples; femaleIndex2++) {
                    for (int maleIndex2 = -1; maleIndex2 < numberOfCouples; maleIndex2++) {
                        State newState = Transition.computeNewState(
                                SerializationUtils.clone(actualState),
                                femaleIndex1, femaleIndex2, maleIndex1, maleIndex2
                        );

                        if (newState != null && !listOfStates.contains(newState)) {
                            newState.setPreviousState(actualState);
                            listOfStates.add(newState);

                            if (FinalState.isFinalState(newState)) {
                                foundSolution = true;
                                actualState = newState;
                                System.out.println(this.toString());
                                return;
                            }

                            actualState = newState;
                            backtracking();
                            actualState = actualState.getPreviousState();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void resolve() {
        foundSolution = false;
        listOfStates = new ArrayList<>();

        backtracking();

        if(!foundSolution) {
            System.out.println(String.format("Nu am gasit o solutie pentru %d cupluri.", numberOfCouples));
        }
    }
}
