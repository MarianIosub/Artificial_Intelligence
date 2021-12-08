package ai.tema6.environment;

import ai.tema6.state.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@AllArgsConstructor
@Getter
@Setter
class PairStateAction {
    private State state;
    private String action;
}

@Getter
@Setter
public class RewardTable {
    private final int n = 4; // Numarul de linii = coloane
    private Map<PairStateAction, Double> table;git

    private Double randomNumber() {
        return new Random().nextDouble();
    }

    public RewardTable() {
        table = new HashMap<>();

        for(int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                State state = new State(i, j);
                List<String> actions = List.of("N", "S", "V", "E");
                actions.forEach(action -> {
                    PairStateAction pairStateAction = new PairStateAction(state, action);
                    table.put(pairStateAction, randomNumber());
                });
            }
        }
    }
}
