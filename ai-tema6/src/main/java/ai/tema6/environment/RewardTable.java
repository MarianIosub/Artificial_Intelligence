package ai.tema6.environment;

import ai.tema6.state.State;
import ai.tema6.state.StateUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
public class RewardTable {
    private final int n = 4; // Numarul de linii = coloane
    private Map<PairStateAction, Double> table;

    private Double randomNumber() {
        return new Random().nextDouble();
    }

    public RewardTable() {
        table = new HashMap<>();

        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                State state = new State(i, j);
                List<String> actions = List.of("N", "S", "V", "E");
                for (String action : actions) {
                    PairStateAction pairStateAction = new PairStateAction(state, action);

                    if (i == 1 && action.equals("N") || i == 4 && action.equals("S") ||
                            j == 1 && action.equals("V") || j == 4 && action.equals("E")) {
                        table.put(pairStateAction, 0d);
                    } else {
                        table.put(pairStateAction, randomNumber());
                    }
                }
            }
        }
    }

    public String getBestActionFromState(State state) {
        Double valueOfBestAction = Double.MIN_VALUE;
        String bestAction = null;

        List<String> actions = List.of("N", "S", "V", "E");
        for (String action : actions) {
            PairStateAction pairStateAction = new PairStateAction(state, action);
            if (table.get(pairStateAction) > valueOfBestAction) {
                valueOfBestAction = table.get(pairStateAction);
                bestAction = action;
            }
        }

        return bestAction;
    }

    public void updateTable(Double learningRate, Double discountFactor, State actualState, String action, int environmentReward) {
        State nextState = StateUtils.nextState(actualState, action);
        PairStateAction pairNextStateAction = new PairStateAction(nextState, getBestActionFromState(nextState));

        PairStateAction pairStateAction = new PairStateAction(actualState, action);
        Double actualRewardValue = table.get(pairStateAction);

        table.replace(
                pairStateAction,
                actualRewardValue + learningRate * (
                        environmentReward +
                                discountFactor * table.get(pairNextStateAction) -
                                actualRewardValue)
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (PairStateAction pairStateAction : table.keySet()) {
            sb.append(String.format("<%s, %s>%n", pairStateAction, table.get(pairStateAction)));
        }

        return sb.toString();
    }
}
