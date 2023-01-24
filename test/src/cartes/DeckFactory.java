package cartes;

import java.util.*;

//  Classe permettant de générer des Decks prédéfinis
public class DeckFactory {

    /* Initialise le deck avec 52 Cards de jeu basiques (as -> roi) (coeur, pique, carreau, trefle)
       @return Deck */
    public static Deck get52DeckCard(String deckStyle) {
        Deck d = new Deck("assets/card_img/backcard.png", deckStyle);

        ArrayList<String> color = new ArrayList<>(Arrays.asList("coeur", "pique", "carreau", "trefle"));

        for (int i = 2; i < 11; i++) {
            for (String c : color) {
                d.addCardToEnd(new Card(Integer.toString(i), c, deckStyle));
            }
        }

        ArrayList<String> figure_as = new ArrayList<>(Arrays.asList("as", "valet", "reine", "roi"));
        for (String c : color) {
            for (String f : figure_as) {
                d.addCardToEnd(new Card(f, c, deckStyle));
            }
        }
        d.shuffleDeck();
        return d;

    }

    /* Initialise le deck avec 40 Cards de jeu basiques (as -> 10) (coeur, pique, carreau, trefle)
       @return Deck */
    public static Deck get40DeckCard(String deckStyle) {
        Deck d = new Deck("assets/card_img/backcard.png", deckStyle);

        ArrayList<String> color = new ArrayList<>(Arrays.asList("coeur", "pique", "carreau", "trefle"));

        for (int i = 2; i < 11; i++) {
            for (String c : color) {
                d.addCardToEnd(new Card(Integer.toString(i), c, deckStyle));
            }
        }
        for (String c : color) {
            d.addCardToEnd(new Card("as", c, deckStyle));
        }
        return d;

    }

}
