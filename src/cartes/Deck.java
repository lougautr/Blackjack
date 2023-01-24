package cartes;

import java.util.ArrayList;
import java.util.Collections;

// Classe représentant un deck
public class Deck {

    private ArrayList<Card> deck;
    private String backcardPath;
    private String deckStyle;

    public Deck(String backcardPath, String deckStyle) {
        this.deck = new ArrayList<>();
        this.backcardPath = backcardPath;
        this.deckStyle = deckStyle;
    }
    
    // Getter ------------------------

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public String getBackcardString() {
        return this.backcardPath;
    }

    public String getDeckStyle() {
        return this.deckStyle;
    }

    // Mélange aléatoirement les cartes du deck
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    /* Ajoute une carte à la fin du deck
       @param Card */
    public void addCardToEnd(Card c) {
        this.deck.add(c);
    }

    /* Ajoute une carte au début du deck
       @param Card */
    public void addCardToHead(Card c) {
        this.deck.add(0, c);
    }

    /* Pioche une carte
       @return Card */
    public Card drawCard() {
        Card c = this.deck.get(0);
        this.deck.remove(0);
        return c;
    }
}
