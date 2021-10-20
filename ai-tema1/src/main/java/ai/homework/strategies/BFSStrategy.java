package ai.homework.strategies;

import ai.homework.models.State;
import ai.homework.models.Transition;
import ai.homework.states.FinalState;
import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class BFSStrategy extends Strategy {
    public BFSStrategy(int numberOfCouples) {
        super(numberOfCouples);
    }

    /**
     * Viziteaza starea actuala: parcurge fiecare nod adiacent nodului actual si adauga in stiva starile (nodurile)
     * valide, obtinute prin tranzitii. In stiva nu se adauga duplicate.
     *
     * @param stateQueue Stiva starilor.
     * @param visited    Un map care retine fiecare stare care a fost vizitata.
     */
    private void visitActualNode(Queue stateQueue, Map<State, Boolean> visited) {
        for (int femaleIndex1 = -1; femaleIndex1 < numberOfCouples; femaleIndex1++) {
            for (int femaleIndex2 = -1; femaleIndex2 < numberOfCouples; femaleIndex2++) {
                for (int maleIndex1 = -1; maleIndex1 < numberOfCouples; maleIndex1++) {
                    for (int maleIndex2 = -1; maleIndex2 < numberOfCouples; maleIndex2++) {
                        State newState = Transition.computeNewState(
                                SerializationUtils.clone(actualState),
                                femaleIndex1, femaleIndex2, maleIndex1, maleIndex2
                        );

                        if (newState != null && !stateQueue.contains(newState) && visited.get(newState) == null) {
                            newState.setPreviousState(actualState);
                            stateQueue.add(newState);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void resolve() {
        Queue<State> stateQueue = new LinkedBlockingQueue<>();
        Map<State, Boolean> visited = new HashMap<>();

        while (!FinalState.isFinalState(actualState)) {
            visited.put(actualState, true);
            visitActualNode(stateQueue, visited);
            actualState = stateQueue.poll();
            if (actualState == null) break;
        }

        if (actualState != null) {
            System.out.println(this);
            return;
        }

        System.out.println(String.format("Nu am gasit o solutie pentru %d cupluri.", numberOfCouples));
    }
}
