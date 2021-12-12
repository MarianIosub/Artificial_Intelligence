package ai.tema6.environment;

import lombok.Getter;

@Getter
public class GameTable {
    private final Integer[][] table;

    public GameTable() {
        table = new Integer[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                table[i][j] = 1;
            }
        }

        for (int i = 0; i < 6; i++) {
            table[i][0] = table[i][5] = -10;
        }

        for (int j = 0; j < 6; j++) {
            table[0][j] = table[5][j] = -10;
        }

        table[2][2] = table[2][4] = table[3][4] = table[4][1] = -1;
        table[4][4] = 10;

        table[1][1] = 0;
    }

    public void updateAgentPosition(int row, int column) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                table[i][j] = 1;
            }
        }

        for (int i = 0; i < 6; i++) {
            table[i][0] = table[i][5] = -10;
        }

        for (int j = 0; j < 6; j++) {
            table[0][j] = table[5][j] = -10;
        }

        table[row][column] = 0;

        table[2][2] = table[2][4] = table[3][4] = table[4][1] = -1;
        table[4][4] = 10;
    }
}
