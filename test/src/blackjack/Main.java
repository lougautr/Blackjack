package blackjack;

import cartes.*;

public class Main {
    public static void main(String[] args) {
        //Démo de Party, avec une partie dans le terminal
        Party a = new Party(11, "vintage");
        a.addHand(new Hand());
        a.addHand(new Hand());
        a.launchGame();
    }
}
