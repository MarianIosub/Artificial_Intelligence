package ai.tema4.ai.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Layer {
    private Integer layerNumber;
    private List<Neuron> neurons;

}
