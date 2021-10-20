package ai.homework.models;

public class Transition {
    /**
     * Calculeaza noua stare si verifica tranzitia, plecand de la starea actuala, impreuna cu indecsii persoanelor
     * care vor fi mutate de pe un mal pe celalalt.
     * <p>
     * Functia primeste ca si parametru patru indecsi, doi pentru sotii si doi pentru soti, deoarece in barca pot fi
     * mai multe combinatii de persoane:
     * <ul>
     *     <li>un barbat</li>
     *     <li>o femeie</li>
     *     <li>doi barbati</li>
     *     <li>doua femei</li>
     *     <li>un barbat si o femeie</li>
     * </ul>
     * <p>
     * In cazul in care pentru oricare dintre indecsi se primeste valoarea <b>-1</b>, indecsul nu va fi luat in
     * considerare la crearea noii stari.
     *
     * @param state        Starea actuala.
     * @param femaleIndex1 Indecsul primei sotii.
     * @param femaleIndex2 Indecsul celei de-a doua sotii.
     * @param maleIndex1   Indecsul primului sot.
     * @param maleIndex2   Indecsul celui de-al doilea sot.
     * @return <b>Noua stare</b>, daca tranzitia este valida; <b>null</b>, daca tranzitia nu este valida (noua stare
     * nu este conforma regulilor problemei).
     */
    public static State computeNewState(State state,
                                        int femaleIndex1, int femaleIndex2,
                                        int maleIndex1, int maleIndex2) {
        state.moveBoat();

        state.moveWoman(femaleIndex1);
        state.moveWoman(femaleIndex2);

        state.moveMan(maleIndex1);
        state.moveMan(maleIndex2);

        if (verifyTransition(state, femaleIndex1, femaleIndex2, maleIndex1, maleIndex2))
            return state;
        else
            return null;
    }

    /**
     * Verifica daca tranzitia efectuata este valida. Aceasta trebuie sa respecte urmatoarele reguli:
     * <ul>
     *     <li>Barca duce oamenii in directia potrivita (de exemplu, o persoana care a fost mutata nu poate fi
     *     pe malul stang, in timp ce barca se afla pe malul drept).</li>
     *     <li>Indecsii barbatilor/femeilor nu pot avea aceeasi valoare simultan
     *     (de exemplu: <b>maleIndex1 = maleIndex2 = 0</b>)</li>
     *     <li>Barca nu se poate deplasa de pe un mal pe celalalt decat cu cel putin o persoana.</li>
     *     <li>In barca incap maxim doua persoane.</li>
     *     <li>Pe niciun mal nu poate fi vreodata o sotie fara sotul sau, daca pe acel mal exista cel putin un sot.</li>
     * </ul>
     *
     * @param newState     Noua stare, cea pe care se vor face verificarile.
     * @param femaleIndex1 Indexul primei sotii.
     * @param femaleIndex2 Indexul celei de-a doua sotii.
     * @param maleIndex1   Indexul primului sot.
     * @param maleIndex2   Indexul celui de-al doilea sot.
     * @return <b>true</b>, daca tranzitia este valida; <b>false</b>, daca aceasta nu este valida.
     */
    public static boolean verifyTransition(State newState,
                                           int femaleIndex1, int femaleIndex2,
                                           int maleIndex1, int maleIndex2) {
        boolean transitionIsValid = true;

        // Indecsii barbatilor/femeilor nu pot avea aceeasi valoare simultan
        if ((maleIndex1 == maleIndex2 && maleIndex1 != -1) || (femaleIndex1 == femaleIndex2 && femaleIndex1 != -1)) {
            return false;
        }

        // Barca duce oamenii in directia potrivita
        boolean isOk = true;
        int moves = 0;

        if (newState.isBoatOnRight()) {
            if (femaleIndex1 != -1) {
                isOk = isOk && newState.getWomen().get(femaleIndex1);
                moves++;
            }
            if (femaleIndex2 != -1) {
                isOk = isOk && newState.getWomen().get(femaleIndex2);
                moves++;
            }

            if (maleIndex1 != -1) {
                isOk = isOk && newState.getMen().get(maleIndex1);
                moves++;
            }
            if (maleIndex2 != -1) {
                isOk = isOk && newState.getMen().get(maleIndex2);
                moves++;
            }
        } else {
            if (femaleIndex1 != -1) {
                isOk = isOk && !newState.getWomen().get(femaleIndex1);
                moves++;
            }
            if (femaleIndex2 != -1) {
                isOk = isOk && !newState.getWomen().get(femaleIndex2);
                moves++;
            }

            if (maleIndex1 != -1) {
                isOk = isOk && !newState.getMen().get(maleIndex1);
                moves++;
            }
            if (maleIndex2 != -1) {
                isOk = isOk && !newState.getMen().get(maleIndex2);
                moves++;
            }
        }

        // Cel putin o persoana si maxim 2 persoane in barca
        transitionIsValid = moves >= 1 && moves <= 2 && isOk;
        if (!transitionIsValid) return false;

        // Nu poate exista o sotie fara sotul ei pe un mal in care se afla cel putin un alt sot
        int index;

        if (newState.getMen().contains(false)) {
            // Mal stang
            index = -1;
            for (Boolean woman : newState.getWomen()) {
                index++;
                if (!woman && newState.getMen().get(index)) {
                    return false;
                }
            }
        }

        if ((newState.getMen().contains(true))) {
            // Mal drept
            index = -1;
            for (Boolean woman : newState.getWomen()) {
                index++;
                if (woman && !newState.getMen().get(index)) {
                    return false;
                }
            }
        }

        return true;
    }
}
