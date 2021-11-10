package ai.tema4.ai.activation;

public class SigmoidActivation {
    public static double activate(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }
}
