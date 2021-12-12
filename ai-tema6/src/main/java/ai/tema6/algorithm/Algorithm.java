package ai.tema6.algorithm;

import ai.tema6.environment.GameTable;
import ai.tema6.environment.RewardTable;
import ai.tema6.state.State;
import ai.tema6.state.StateUtils;

public class Algorithm {
    private State actualState;
    private RewardTable rewardTable;
    private GameTable gameTable;

    public Algorithm(RewardTable rewardTable) {
        actualState = StateUtils.initialState();
        this.rewardTable = rewardTable;
        gameTable = new GameTable();
    }

    public Algorithm() {
        actualState = StateUtils.initialState();
        rewardTable = new RewardTable();
        gameTable = new GameTable();
    }

    public RewardTable run(Double learningRate, Double discountFactor) throws InterruptedException {
        return run(learningRate, discountFactor, false);
    }

    public RewardTable run(Double learningRate, Double discountFactor, boolean logOutput) throws InterruptedException {
        if (logOutput) {
            System.out.println(actualState);
        }

        int environmentReward = StateUtils.isFinalState(actualState, gameTable);
        while (environmentReward != 10 && environmentReward != -1 && environmentReward != -10) {
            String bestAction = rewardTable.getBestActionFromState(actualState);
            rewardTable.updateTable(learningRate, discountFactor, actualState, bestAction, environmentReward);
            actualState = StateUtils.nextState(actualState, bestAction);
            environmentReward = StateUtils.isFinalState(actualState, gameTable);
            gameTable.updateAgentPosition(actualState.getRow(), actualState.getColumn());

            if (logOutput) {
                System.out.println(actualState);
            }
        }

        return rewardTable;
    }
}
