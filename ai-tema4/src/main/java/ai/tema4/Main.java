package ai.tema4;

import ai.tema4.data.DataReader;
import ai.tema4.entity.Instance;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Instance> andInstances = DataReader.readFile("AND.csv");
        List<Instance> orInstances = DataReader.readFile("OR.csv");
        List<Instance> xorInstances = DataReader.readFile("XOR.csv");
    }
}
