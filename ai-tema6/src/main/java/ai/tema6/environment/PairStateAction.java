package ai.tema6.environment;

import ai.tema6.state.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
class PairStateAction {
    private State state;
    private String action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PairStateAction)) return false;
        PairStateAction that = (PairStateAction) o;
        return Objects.equals(state, that.state) && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, action);
    }

    @Override
    public String toString() {
        return "PairStateAction{" +
                "state=" + state +
                ", action='" + action + '\'' +
                '}';
    }
}