package ai.tema4;

import ai.tema4.ai.NeuralNetworkUtils;
import ai.tema4.ai.entities.Edge;
import ai.tema4.data.DataReader;
import ai.tema4.entity.Instance;
import ai.tema4.ai.entities.Layer;
import ai.tema4.ai.entities.Neuron;
import ai.tema4.ai.entities.NeuronalNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int epochs = 500;
    public static double learningRate = 0.5; // 0.7

    private static void neuronalNetwork(List<Instance> instances) {
        Layer layer1 = new Layer(1, List.of(
                new Neuron(),
                new Neuron()
        ));
        Layer layer2 = new Layer(2, List.of(
                new Neuron(),
                new Neuron()
        ));
        Layer layer3 = new Layer(3, List.of(
                new Neuron()
        ));

        List<Edge> edges = new ArrayList<>();

        for (Neuron neuron : layer1.getNeurons()) {
            for (Neuron neuron2 : layer2.getNeurons()) {
                edges.add(new Edge(neuron, neuron2, 1 - 2 * new Random().nextDouble()));
            }
        }

        for (Neuron neuron : layer2.getNeurons()) {
            for (Neuron neuron2 : layer3.getNeurons()) {
                edges.add(new Edge(neuron, neuron2, 1 - 2 * new Random().nextDouble()));
            }
        }

        NeuronalNetwork neuronalNetwork = new NeuronalNetwork(
                List.of(
                        layer1,
                        layer2,
                        layer3
                ),
                edges
        );

        for (int i = 0; i < epochs; i++) {
            NeuralNetworkUtils.train(neuronalNetwork, instances, learningRate);
        }

        int numberOfErrors = 0;
        for (Instance instance : instances) {
            Double predicted = NeuralNetworkUtils.predict(neuronalNetwork, instance);
            System.out.printf("(%f, %f) = %f%n", instance.getInput1(), instance.getInput2(), predicted);

            Double predictedValue = predicted < 0.45 ? 0d : 1d;
            if (!predictedValue.equals(instance.getOutput())) {
                numberOfErrors++;
            }
        }

        System.out.printf("%d erori din %d%n", numberOfErrors, instances.size());
    }

    public static void main(String[] args) {
        List<Instance> andInstances = DataReader.readFile("AND.csv");
        List<Instance> orInstances = DataReader.readFile("OR.csv");
        List<Instance> xorInstances = DataReader.readFile("XOR.csv");
        System.out.println("---- AND ----");
        neuronalNetwork(andInstances);
        System.out.println("---- OR ----");
        neuronalNetwork(orInstances);
        System.out.println("---- XOR ----");
        neuronalNetwork(xorInstances);
    }
}
