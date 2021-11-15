package ai.tema4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Pair<T, V> {
    private T key;
    private V value;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(obj.getClass() != Pair.class) {
            return false;
        }

        return key.equals(((Pair<?, ?>) obj).getKey()) && value.equals(((Pair<?, ?>) obj).getValue());
    }
}
