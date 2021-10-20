package ai.homework.models;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable {
    /**
     * Retine starea anterioara. <b>null</b>, in cazul in care starea actuala este cea initiala.
     */
    private State previousState;

    /**
     * Retine numarul de cupluri din problema.
     */
    private final int numberOfCouples;

    /**
     * <b>women[i] = 0</b> inseamna ca sotia <b>i</b> este pe malul stang; <b>women[i] = 1</b> inseamna ca este pe malul drept
     */
    private List<Boolean> women;

    /**
     * <b>men[i] = 0</b> inseamna ca sotul <b>i</b> este pe malul stang; <b>men[i] = 1</b> inseamna ca este pe malul drept
     */
    private List<Boolean> men;

    /**
     * <b>boatPosition = 0</b>, daca barca este la malul stang; <b>boatPosition = 1</b>, daca este la malul drept
     */
    private boolean boatPosition;

    public State(State previousState, int numberOfCouples, List<Boolean> women, List<Boolean> men, boolean boatPosition) {
        this.previousState = previousState;
        this.numberOfCouples = numberOfCouples;
        this.women = women;
        this.men = men;
        this.boatPosition = boatPosition;
    }

    public List<Boolean> getWomen() {
        return women;
    }

    public List<Boolean> getMen() {
        return men;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public boolean isBoatOnRight() {
        return boatPosition;
    }

    /**
     * Muta barca de pe malul stang (<b>boatPosition = 0</b>), pe malul drept(<b>boatPosition = 1</b>) si vice-versa.
     */
    public void moveBoat() {
        this.boatPosition = !this.boatPosition;
    }

    /**
     * Muta sotia de pe indexul dat de pe un mal pe celalalt.
     *
     * @param index Indexul sotiei care va fi mutata.
     */
    public void moveWoman(int index) {
        if (index == -1) return;

        this.women.set(index, !this.women.get(index));
    }

    /**
     * Muta sotul de pe indexul dat de pe un mal pe celalalt.
     *
     * @param index Indexul sotului care va fi mutat.
     */
    public void moveMan(int index) {
        if (index == -1) return;

        this.men.set(index, !this.men.get(index));
    }

    @Override
    public String toString() {
        if(previousState == null) return "";

        StringBuilder sb = new StringBuilder();

        // Femeia x si barbatul y (daca e) s-au dus in dreapta
        for (int i = 0; i < numberOfCouples; i++) {
            if (!previousState.getWomen().get(i).equals(women.get(i))) {
                sb.append("Sotia ").append(i).append(", ");
            }

            if (!previousState.getMen().get(i).equals(men.get(i))) {
                sb.append("Sotul ").append(i).append(", ");
            }
        }

        sb.append("s-a/s-au mutat in ").append(
                boatPosition ? "dreapta" : "stanga"
        );

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != State.class) {
            return false;
        }

        return this.numberOfCouples == ((State) obj).numberOfCouples &&
                this.women.equals(((State) obj).getWomen()) &&
                this.men.equals(((State) obj).getMen()) &&
                this.boatPosition == ((State) obj).boatPosition;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + numberOfCouples;
        result = 31 * result + women.hashCode();
        result = 31 * result + men.hashCode();
        result = 31 * result + (boatPosition ? 1 : 0);
        return result;
    }
}
