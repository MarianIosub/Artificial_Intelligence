package ai.tema4.ai.entities;

import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Edge {
    private Neuron fromNeuron;
    private Neuron toNeuron;
    private Double value;

    public static Edge findEdgeByNeurons(Neuron fromNeuron, Neuron toNeuron, List<Edge> edges) {
        for (Edge edge : edges) {
            if (edge.getFromNeuron().equals(fromNeuron) && edge.getToNeuron().equals(toNeuron)) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return fromNeuron.equals(edge.fromNeuron) && toNeuron.equals(edge.toNeuron) && Objects.equals(value, edge.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromNeuron, toNeuron, value);
    }
}
