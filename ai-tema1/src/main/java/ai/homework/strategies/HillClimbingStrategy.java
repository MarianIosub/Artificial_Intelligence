package ai.homework.strategies;

import ai.homework.models.State;
import ai.homework.models.Transition;
import ai.homework.states.FinalState;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HillClimbingStrategy extends Strategy {
    private boolean foundSolution = false;

    private List<State> visited = new ArrayList<>();

    private Integer actualStateEuristic;

    public HillClimbingStrategy(int numberOfCouples) {
        super(numberOfCouples);
    }

    private void hillclimbing() {
        visited.add(actualState);

        if (FinalState.isFinalState(actualState)) {
            foundSolution = true;
            System.out.println(this.toString());
            return;
        }

        Map<State, Integer> adjacentStates = new HashMap<>();

        // Calculam starile adiacente starii actuale
        for (int femaleIndex1 = -1; femaleIndex1 < numberOfCouples; femaleIndex1++) {
            for (int maleIndex1 = -1; maleIndex1 < numberOfCouples; maleIndex1++) {
                for (int femaleIndex2 = -1; femaleIndex2 < numberOfCouples; femaleIndex2++) {
                    for (int maleIndex2 = -1; maleIndex2 < numberOfCouples; maleIndex2++) {
                        State adjacentState = Transition.computeNewState(
                                SerializationUtils.clone(actualState),
                                femaleIndex1, femaleIndex2, maleIndex1, maleIndex2
                        );

                        // Stare adiacenta valida; calculez euristica
                        if (adjacentState != null) {
                            Integer stateEuristic = 0;

                            for (int i = 0; i < numberOfCouples; i++) {
                                stateEuristic += adjacentState.getWomen().get(i) ? 0 : 1;
                                stateEuristic += adjacentState.getMen().get(i) ? 0 : 1;
                            }

                            // Daca barca este in dreapta in nodul actual, inseamna ca o persoana se va intoarce in stanga
                            //=> scadem din euristica pentru a putea gasi noduri adiacente valide
                            if (actualState.isBoatOnRight()) {
                                stateEuristic--;
                            }

                            adjacentStates.put(adjacentState, stateEuristic);
                        }
                    }
                }
            }
        }

        // Alegem starea adiacenta cu euristica minima
        State chosenState = null;
        Integer chosenStateEuristic = Integer.MAX_VALUE;

        for (State state : adjacentStates.keySet()) {
            if (adjacentStates.get(state) < chosenStateEuristic && !visited.contains(state)) {
                chosenState = state;
                chosenStateEuristic = adjacentStates.get(state);
            }
        }

        if (chosenState == null) {
            return;
        }

        chosenState.setPreviousState(actualState);
        actualState = chosenState;
        hillclimbing();
    }

    @Override
    public void resolve() {
        hillclimbing();

        if (!foundSolution) {
            System.out.println("Nu am gasit solutie.");
        }
    }
}
