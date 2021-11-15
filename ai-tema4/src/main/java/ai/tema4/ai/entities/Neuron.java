package ai.tema4.ai.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Neuron {
    private Double value;

    @Override
    public int hashCode() {
        if(value == null) {
            return super.hashCode();
        }

        return super.hashCode() + value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj.getClass() != Neuron.class) {
            return false;
        }

        if(value == null) {
            return super.equals(obj);
        }

        return super.equals(obj) && value.equals(((Neuron) obj).getValue());
    }
}
