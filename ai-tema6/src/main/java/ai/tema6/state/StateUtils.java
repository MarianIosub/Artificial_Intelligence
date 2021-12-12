package ai.tema6.state;

import ai.tema6.environment.GameTable;

public class StateUtils {
    public static State initialState() {
        return new State(1, 1);
    }

    public static State nextState(State state, String action) {
        switch (action) {
            case "N": {
                return new State(state.getRow() - 1, state.getColumn());
            }

            case "S": {
                return new State(state.getRow() + 1, state.getColumn());
            }

            case "E": {
                return new State(state.getRow(), state.getColumn() + 1);
            }

            case "V": {
                return new State(state.getRow(), state.getColumn() - 1);
            }
        }

        return state;
    }

    public static int isFinalState(State state, GameTable gameTable) {
        if (state == null) {
            return -1;
        }

        if (state.getRow() < 1 || state.getColumn() < 1 || state.getRow() > 4 || state.getColumn() > 4) {
            return -1;
        }

        return gameTable.getTable()[state.getRow()][state.getColumn()];
    }
}
