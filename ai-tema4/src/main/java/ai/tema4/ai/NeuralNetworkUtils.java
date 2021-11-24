package ai.tema4.ai;

import ai.tema4.ai.activation.SigmoidActivation;
import ai.tema4.ai.entities.Edge;
import ai.tema4.ai.entities.Neuron;
import ai.tema4.ai.entities.NeuronalNetwork;
import ai.tema4.entity.Instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeuralNetworkUtils {
    public static void train(NeuronalNetwork neuronalNetwork, List<Instance> instances, Double learningRate) {
        Map<Instance, Double> returnedValues = new HashMap<>();

        for (Instance instance : instances) {
            Neuron inputNeuron1 = neuronalNetwork.getLayers().get(0).getNeurons().get(0);
            inputNeuron1.setValue(instance.getInput1());
            Neuron inputNeuron2 = neuronalNetwork.getLayers().get(0).getNeurons().get(1);
            inputNeuron2.setValue(instance.getInput2());

            for (Neuron neuron : neuronalNetwork.getLayers().get(1).getNeurons()) {
                Double edge1Value = Edge.findEdgeByNeurons(inputNeuron1, neuron, neuronalNetwork.getEdges()).getValue();
                Double edge2Value = Edge.findEdgeByNeurons(inputNeuron2, neuron, neuronalNetwork.getEdges()).getValue();

                neuron.setValue(
                        inputNeuron1.getValue() * edge1Value +
                                inputNeuron2.getValue() * edge2Value
                );

                neuron.setValue(SigmoidActivation.activate(neuron.getValue()));
            }

            Neuron outputNeuron = neuronalNetwork.getLayers().get(2).getNeurons().get(0);
            outputNeuron.setValue(0d);
            for (Neuron neuron : neuronalNetwork.getLayers().get(1).getNeurons()) {
                Double edgeValue = Edge.findEdgeByNeurons(neuron, outputNeuron, neuronalNetwork.getEdges()).getValue();

                outputNeuron.setValue(
                        outputNeuron.getValue() +
                                neuron.getValue() * edgeValue
                );
            }
            outputNeuron.setValue(SigmoidActivation.activate(outputNeuron.getValue()));

            returnedValues.put(instance, outputNeuron.getValue());

            // Optimizam valorile muchiilor (backpropagation)
            Map<Neuron, Double> deltas = new HashMap<>();
            deltas.put(outputNeuron, outputNeuron.getValue() - instance.getOutput());
            for(Neuron neuron : neuronalNetwork.getLayers().get(1).getNeurons()) {
                Double delta = neuron.getValue() * (1 - neuron.getValue()) * (deltas.get(outputNeuron) *
                        Edge.findEdgeByNeurons(neuron, outputNeuron, neuronalNetwork.getEdges()).getValue());

                deltas.put(neuron, delta);
            }

            Map<Edge, Double> errors = new HashMap<>();
            for(Edge edge: neuronalNetwork.getEdges()) {
                errors.put(edge, deltas.get(edge.getToNeuron()) * edge.getFromNeuron().getValue());
            }

            for(Edge edge: neuronalNetwork.getEdges()) {
                edge.setValue(edge.getValue() - errors.get(edge) * learningRate);
            }
        }
    }

    public static Double predict(NeuronalNetwork neuronalNetwork, Instance instance) {
        Neuron inputNeuron1 = neuronalNetwork.getLayers().get(0).getNeurons().get(0);
        inputNeuron1.setValue(instance.getInput1());
        Neuron inputNeuron2 = neuronalNetwork.getLayers().get(0).getNeurons().get(1);
        inputNeuron2.setValue(instance.getInput2());

        for (Neuron neuron : neuronalNetwork.getLayers().get(1).getNeurons()) {
            Double edge1Value = Edge.findEdgeByNeurons(inputNeuron1, neuron, neuronalNetwork.getEdges()).getValue();
            Double edge2Value = Edge.findEdgeByNeurons(inputNeuron2, neuron, neuronalNetwork.getEdges()).getValue();

            neuron.setValue(
                    inputNeuron1.getValue() * edge1Value +
                            inputNeuron2.getValue() * edge2Value
            );

            neuron.setValue(SigmoidActivation.activate(neuron.getValue()));
        }

        Neuron outputNeuron = neuronalNetwork.getLayers().get(2).getNeurons().get(0);
        outputNeuron.setValue(0d);
        for (Neuron neuron : neuronalNetwork.getLayers().get(1).getNeurons()) {
            Double edgeValue = Edge.findEdgeByNeurons(neuron, outputNeuron, neuronalNetwork.getEdges()).getValue();

            outputNeuron.setValue(
                    outputNeuron.getValue() +
                            neuron.getValue() * edgeValue
            );
        }
        outputNeuron.setValue(SigmoidActivation.activate(outputNeuron.getValue()));

        return outputNeuron.getValue();
    }
}
