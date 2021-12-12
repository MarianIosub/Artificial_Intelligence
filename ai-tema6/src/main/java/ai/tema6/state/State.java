package ai.tema6.state;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class State {
    private int row;
    private int column;

    @Override
    public String toString() {
        return "State{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return row == state.row && column == state.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
