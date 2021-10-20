package ai.homework.ui;

import ai.homework.strategies.AStarStrategy;
import ai.homework.strategies.BFSStrategy;
import ai.homework.strategies.BackTrackingStrategy;
import ai.homework.strategies.HillClimbingStrategy;

import java.util.Scanner;

public class Console {
    public static void runConsole() {
        int n = 0;
        int strategy = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Numarul de cupluri: ");
        n = scanner.nextInt();

        while(n <= 0) {
            System.out.println("Input invalid!");
            System.out.print(" >> ");
            n = scanner.nextInt();
        }

        System.out.println("\nAlegeti strategia: ");
        System.out.println("\t 1 - Backtracking");
        System.out.println("\t 2 - BFS");
        System.out.println("\t 3 - Hill Climbing");
        System.out.println("\t 4 - A*");
        System.out.print(" >> ");
        strategy = scanner.nextInt();

        while(strategy < 1 || strategy > 4) {
            System.out.println("Input invalid!");
            System.out.print(" >> ");
            strategy = scanner.nextInt();
        }

        switch(strategy) {
            case 1: {
                new BackTrackingStrategy(n).resolve();
                break;
            }

            case 2: {
                new BFSStrategy(n).resolve();
                break;
            }

            case 3: {
                new HillClimbingStrategy(n).resolve();
                break;
            }

            case 4: {
                new AStarStrategy(n).resolve();
                break;
            }
        }
    }
}
