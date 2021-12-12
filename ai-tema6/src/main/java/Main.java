import ai.tema6.algorithm.Algorithm;
import ai.tema6.environment.RewardTable;

public class Main {
    private static final Double LEARNING_RATE = 0.01d;
    private static final Double DISCOUNT_FACTOR = 0.95d;
    private static final Integer EPISODES = 100000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Episode number 0");
        Algorithm algorithm = new Algorithm();
        RewardTable rewardTable = algorithm.run(Main.LEARNING_RATE, Main.DISCOUNT_FACTOR);

        for (int i = 1; i < EPISODES - 1; i++) {
            System.out.println("Episode number " + i);
            algorithm = new Algorithm(rewardTable);
            rewardTable = algorithm.run(Main.LEARNING_RATE, Main.DISCOUNT_FACTOR);
        }

        System.out.println("Episode number " + EPISODES);
        algorithm = new Algorithm(rewardTable);
        rewardTable = algorithm.run(Main.LEARNING_RATE, Main.DISCOUNT_FACTOR, true);
    }
}
