package cartes;

import java.util.ArrayList;

// Classe représentant une main de jeu composée de carte
public class Hand {

    private ArrayList<Card> hand;
    private Deck deck;

    private boolean showHand;

    public Hand() {
        this.hand = new ArrayList<>();
        this.deck = null;
        this.showHand = false;
    }

    // Setters -------------------
    public void setDeck(Deck d) {
        this.deck = d;
    }

    public void setHandList(ArrayList<Card> hand) {
        this.hand = hand;
    }

    // Getters -------------------
    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public Deck getDeck() {
        return this.deck;
    }
    
    public int getHandSize() {
        return this.hand.size();
    }

    /* Tire une carte de la main
       @return Card */
    public Card getCard(int idCard) {
        try {
            return this.hand.get(idCard);
        } catch (Exception e) {
            return null;
        }
    }

    // Change l'attribut showHand afin de voir la main
    public void changeShowHand() {
        this.showHand = !this.showHand;
    }

    /* Ajouter une carte dans la main
       @param Card */
    public void addCard(Card c) {
        this.hand.add(c);
    }

    /* Retire une carte de la main avec son id
       @param int idCard
       @return Card */
    public Card removeCard(int idCard) {
        try {
            Card c = this.hand.get(idCard);
            this.hand.remove(idCard);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    // Réinitialise la main
    public void resetHand() {
        /*
         * Reinitialise la main
         */
        try {
            while (true) {
                this.hand.remove(0);
            }
        } catch (Exception e) {
        }
    }

    /* Joue une carte et la retire de la main
       @param int idCard
       @return Card */
    public Card playCard(int idCard) {
        Card c = this.getCard(idCard);
        this.removeCard(idCard);
        return c;
    }

    /* Fonction de comparaison de main
       @param Hand
       @return 0 ou -1 */
    public int compareTo(Hand h) {
        for (Card c : h.getHand()) {
            boolean flag = false;
            for (Card this_card : this.hand) {
                if (c.compareTo(this_card) == 0) {
                    flag = true;
                }
            }
            if (!flag) {
                return -1;
            }
        }
        return 0;
    }

}
