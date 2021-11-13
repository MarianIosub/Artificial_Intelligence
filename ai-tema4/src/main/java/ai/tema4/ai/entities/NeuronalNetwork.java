package ai.tema4.ai.entities;

import ai.tema4.entity.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class NeuronalNetwork {
    private List<Layer> layers;
    private List<Edge> edges;
}
