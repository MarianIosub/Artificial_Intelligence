package ai.tema.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ColorSequence {
    private List<Integer> sequence;

    public ColorSequence(List<Integer> sequence) {
        this.sequence = sequence;
    }

    public ColorSequence(Integer... values) {
        this.sequence = List.of(values);
    }
}
