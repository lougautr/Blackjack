package blackjack;

import java.util.*;
import cartes.*;

//Classe représentant une partie de blackjack
public class Party {
    
    //le jeu de cartes
    private Deck deck;
    
    //liste de toutes les mains de tous les joueurs
    private ArrayList<Hand> listHand;
    
    //liste des sommes des mains de tous les joueurs
    private ArrayList<Integer> listSumHand;

    //main du dealer
    private Hand dealerHand;
    
    //somme de la main du dealer
    private Integer sumDealerHand;

    //nombre initial de carte par main (joueurs)
    private int NB_CARD_INITIAL_DRAW = 2;
    
    //valeur de l'as (1 ou 11)
    private int AS_VALUE;
    
    //dictionnaire contenant les relations entre les valeurs des cartes
    private HashMap<String, Integer> RELATION_NUMBER_VALUE;

    public Party(int AS_VALUE, String deckStyle) {
        this.deck = DeckFactory.get52DeckCard(deckStyle);

        this.listHand = new ArrayList<>();
        this.listSumHand = new ArrayList<>();

        this.dealerHand = new Hand();
        this.dealerHand.changeShowHand();
        this.sumDealerHand = 0;

        this.AS_VALUE = 11;

        this.generateRELATION_NUMBER_VALUE();
    }

    // Getter -------------
    public Deck getDeck() {
        return this.deck;
    }

    public int getAsValue() {
        return this.AS_VALUE;
    }

    public ArrayList<Hand> getListHand() {
        return this.listHand;
    }

    public ArrayList<Integer> getListSumHand() {
        return listSumHand;
    }

    public Hand getDealerHand() {
        return this.dealerHand;
    }

    // Methods -------------

    //Génère dans la HashMap RELATION_NUMBER_VALUE, la relation entre les "values" d'une carte et leurs valeurs numériques au blackjack
    public void generateRELATION_NUMBER_VALUE() {
        this.RELATION_NUMBER_VALUE = new HashMap<>();

        for (int i = 2; i < 11; i++) {
            this.RELATION_NUMBER_VALUE.put(Integer.toString(i), i);
        }

        this.RELATION_NUMBER_VALUE.put("as", this.AS_VALUE);

        ArrayList<String> figure = new ArrayList<>(Arrays.asList("valet", "reine", "roi"));
        for (String f : figure) {
            this.RELATION_NUMBER_VALUE.put(f, 10);
        }
    }

    /*Ajoute une main dans la partie
      @param Hand*/
    public void addHand(Hand h) {
        this.listHand.add(h);
    }

    //Tirage initial de 2 cartes par personne et d'une carte pour le croupier
    public void initialDraw() {
        for (Hand h : this.listHand) {
            for (int i = 0; i < NB_CARD_INITIAL_DRAW; i++) {
                h.addCard(this.deck.drawCard());
            }
        }

        this.dealerHand.addCard(this.deck.drawCard());
    }

    //Insère dans les attributs correspondant, la somme des mains des joueurs et du croupier
    public void getSumHandValue() {
        while (this.listSumHand.size() != 0) {
            this.listSumHand.remove(0);
        }

        for (Hand h : this.listHand) {

            int sum = 0;
            for (Card c : h.getHand()) {
                sum += this.RELATION_NUMBER_VALUE.get(c.getValue());
            }
            this.listSumHand.add(sum);
        }

    }

    /*Retoune la valeur de la main donnée
      @param Hand
      @return somme Hand*/
    public int getSumHandValueOfOneHand(Hand h) {
        for (Hand hand : this.listHand) {
            if (hand.compareTo(h) == 0) {
                int sum = 0;
                int as = 0;
                for (Card c : h.getHand()) {
                    sum += this.RELATION_NUMBER_VALUE.get(c.getValue());
                    if (c.getValue().equals("as")) {
                        as++;
                    }
                }
                if (sum > 21) {
                    sum -= 10 * as;
                }
                return sum;
            }
        }
        return -1;
    }

    /*Calcule la somme des cartes du croupier
      @return somme main dealer*/
    public int getSumDealerHand() {
        int sumDealer = 0;
        for (Card c : this.dealerHand.getHand()) {
            sumDealer += this.RELATION_NUMBER_VALUE.get(c.getValue());
        }

        this.sumDealerHand = sumDealer;
        return this.sumDealerHand;
    }

    /*Choix du joueur:
      1) hit: tirer une carte
         return true -> le joueur peut continuer à faire des choix
      2) stand : ne souhaite pas tirer d'autres cartes
         return false -> indique que le joueur ne fera plus de choix
      @param Hand, int
      @return boolean*/
    public boolean handChoice(Hand h, int choice) {
        if (choice == 1) {
            h.addCard(this.deck.drawCard());
            this.getSumHandValue();
            return true;
        } else {
            return false;
        }
    }

    /*Demande et applique le choix à l'utilisateur (méthode console)
      @param Hand*/
    public void handTurn(Hand h) {
        int choice = this.askChoiceConsole(h);

        while (handChoice(h, choice)) {
            choice = this.askChoiceConsole(h);
        }
    }

    /*Faire le choix depuis un input utilisateur
      @param Hand
      @return -> 1 (hit) ou 2 (stand)
      (méthode console)*/
    public int askChoiceConsole(Hand h) {
        try {
            Scanner myObj = new Scanner(System.in);

            System.out.println(this.dealerHand.getHand());
            System.out.println(h.getHand());
            System.out.println("Enter choice 1 = hit - 2 = stand");

            String choice = myObj.nextLine();

            return Integer.valueOf(choice);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //Faire jouer tous les utilisateurs chacun leurs tour (méthode console)
    public void allHandTurn() {
        for (Hand h : this.listHand) {
            handTurn(h);
        }
    }

    //Joue le tour du croupier
    public void dealerTurn() {
        while (this.sumDealerHand < 16) {
            Card drawedCard = this.deck.drawCard();
            this.dealerHand.addCard(drawedCard);
            this.getSumDealerHand();
        }

    }

    /*Fixe la manière de jouer des IA
      @param Hand*/
    public void AIPlayerTurn(Hand h) {
        while (getSumHandValueOfOneHand(h) < 15) {
            Card drawedCard = this.deck.drawCard();
            h.addCard(drawedCard);
        }
    }

    /*Vérifie les conditions de victoire du croupier et des joueurs
      @return gagnant(s), perdant(s)*/
    public String checkWhoWin() {
        if (this.sumDealerHand > 21) {
            return "Tous les joueurs ont gagnes, le croupier a + de 21 !";
        }

        int maxVal = -1;
        int maxId = -1;

        ArrayList<Integer> otherMaxId = new ArrayList<>();

        getSumHandValue();
        for (int i = 0; i < this.listHand.size(); i++) {
            if (this.listSumHand.get(i) <= 21 && this.listSumHand.get(i) > this.sumDealerHand
                    && this.listSumHand.get(i) >= maxVal) {

                if (this.listSumHand.get(i) == maxVal) {
                    otherMaxId.add(i);
                } else {
                    maxVal = this.listSumHand.get(i);
                    maxId = i;
                }
            }
        }
        if (maxId == -1) {
            return "Le croupier a gagne";

        } else {
            if (otherMaxId.size() == 0) {
                return "Le joueur " + maxId + " a gagne ";
            } else {
                String winners = maxId + ", ";
                for (int i : otherMaxId) {
                    winners += i + ", ";
                }

                return "Les joueurs " + winners + " ont gagnes !";
            }
        }
    }

    //Joue un tour de table complet
    public void completeTurn() {
        this.allHandTurn();
        this.dealerTurn();

        this.checkWhoWin();
    }

    //Joue une partie (méthode console)
    public void launchGame() {
        this.initialDraw();
        this.getSumHandValue();
        this.getSumDealerHand();
    }
}
