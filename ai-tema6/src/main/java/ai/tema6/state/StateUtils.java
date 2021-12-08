package ai.tema6.state;

public class StateUtils {
    public static State initialState() {
        return new State(0, 0);
    }

    public static State nextState(State state, String action) {
        switch(action){
            case "N": {
                if(state.getRow() == 0) {
                    return null;
                }

                return new State(state.getRow() - 1, state.getColumn());
            }

            case "S": {
                if(state.getRow() == 3) {
                    return null;
                }

                return new State(state.getRow() + 1, state.getColumn());
            }

            case "E": {
                if(state.getColumn() == 3) {
                    return null;
                }

                return new State(state.getRow(), state.getColumn() + 1);
            }

            case "V": {
                if(state.getColumn() == 0) {
                    return null;
                }

                return new State(state.getRow(), state.getColumn() - 1);
            }

            default: {
                return null;
            }
        }
    }
}
