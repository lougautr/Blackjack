package cartes;

// Démo des classes Hand, Deck and Card
public class Main {

    public static void main(String[] args) {
        Deck p = DeckFactory.get52DeckCard("classique");
        p.shuffleDeck();

        Hand a = new Hand();
        a.setDeck(p);

        for (int i = 0; i < 4; i++) {
            a.addCard(p.drawCard());
        }

        System.out.println(p.getDeck().size());
        System.out.println(a.playCard(1));

    }
}
