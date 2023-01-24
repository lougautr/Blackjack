package views.controller;

import blackjack.Party;
import cartes.*;
import views.BasicPage;
import views.GamePage;
import views.Home;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class ControlGamePage {
  private JPanel contentPane;
  private JButton hit;
  private JButton stand;
  private GamePage view;
  private JFrame window;
  private Party party;
  
  //Image de fond
  JLabel backgroundImageGame = new JLabel("", new ImageIcon("assets/fond_deck.png"), JLabel.CENTER);

  //Taille des cartes
  int heightCard = 120;
  int widthCard = 50;

  public ControlGamePage(GamePage view, JButton hit, JButton stand, Party party, JFrame window, JPanel contentPane) {
    this.view = view;
    this.contentPane = contentPane;
    this.hit = hit;
    this.stand = stand;
    this.party = party;
    this.window = window;

  }

  /*Affiche une main à des coordonées données, du bas vers le haut
    @param Hand, int startX, int startY, boolean*/
  public void printOneHand(Hand hand, int startX, int startY, boolean dealer) {
    actionGetHandValue(hand, startX, startY, dealer);
    ArrayList<Card> listCard = hand.getHand();
    if (dealer) {
      Collections.reverse(listCard);
      startY *= listCard.size();
    }
    startX += 110;

    //Ajout image sur boutton
    for (Card c : listCard) {
      JButton bouton = new JButton(new ImageIcon(c.getImgPath()));
      bouton.setBounds(startX, startY, 80, 117);
      this.contentPane.add(bouton);
      startY -= widthCard + 10;
    }

  }

  //Affiche toutes les mains (dont celle du dealer) en cleanant la dernière version du panel
  public void printAllHandAndBackGround() {
    cleanPanel();
    int nbJoueur = this.party.getListHand().size();
    int x_val = (BasicPage.X_SIZE / 2) - (120 * nbJoueur);

    //Pour une partie de 4 joueurs, la formule ne s'applique pas donc:
    if (nbJoueur == 4) {
      x_val = 10;
    }

    int player = 0;

    for (int i = 0; i < this.party.getListHand().size(); i++) {
      Hand h = this.party.getListHand().get(i);
      if (i == player) {
        JLabel labelPlayer = new JLabel(new ImageIcon("assets/go_icon.png"), JLabel.CENTER);
        labelPlayer.setBounds(x_val - 130, BasicPage.Y_SIZE - widthCard * 2 - 45, 400, 60);
        contentPane.add(labelPlayer);
      }
      this.printOneHand(h, x_val, BasicPage.Y_SIZE - widthCard * 2, false);

      x_val += 250;
    }

    this.printOneHand(this.party.getDealerHand(), BasicPage.X_SIZE / 2 - 120, 50, true);

    this.view.setBackgroundGame(this.contentPane);
    this.contentPane.repaint();
    this.contentPane.revalidate();
  }

  /*Action au moment du la pression du bouton hit; le joueur tire une carte
    @param event, Hand*/
  public void actionHit(ActionEvent e, Hand h) {
    if (h.getHandSize() <= 5) {
      h.addCard(this.party.getDeck().drawCard());
      this.printAllHandAndBackGround();
    }

    printDeck();
    this.view.setBackgroundGame(this.contentPane);
    this.contentPane.repaint();
    this.contentPane.revalidate();
  }

  //Affiche la pioche, chaque fois qu'une carte est tirée, une carte est retirée de la pioche
  public void printDeck() {
    int x = 200;
    int y = 100;

    for (Card c : this.party.getDeck().getDeck()) {
      JLabel labelCardPick = new JLabel(new ImageIcon(this.party.getDeck().getBackcardString()), JLabel.CENTER);
      labelCardPick.setBounds(x, y, 80, 117);
      this.contentPane.add(labelCardPick);
      y -= heightCard - 118;
      x += widthCard - 54;
    }

  }

  /*Action au moment de la pression du bouton stand;
    le joueur arrête de piocher et tout le tour se joue ici
    tous les autres joueurs (dealer compris) piochent leurs cartes et nous voyons qui gagne
    @param event*/
  public void actionStand(ActionEvent e) {
  
    //Grise les boutons
    this.hit.setEnabled(false);
    this.stand.setEnabled(false);

    //Fait jouer les autres joueurs
    for (int i = 1; i < this.party.getListHand().size(); i++) {
      Hand h = this.party.getListHand().get(i);
      this.party.AIPlayerTurn(h);
    }

    //Fait jouer le dealer
    this.party.dealerTurn();

    //Réaffiche tout
    this.printAllHandAndBackGround();

    //Pop up pour annoncer les resultats de la partie + retour au menu
    Object[] options = { "Rejouer",
        "Quitter" };

    int n = JOptionPane.showOptionDialog(this.window,
        this.party.checkWhoWin(),
        "Partie terminée",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[1]);

    if (n == 1) {
      new Home(this.window, this.contentPane);
    } else {
      Party p = new Party(this.party.getAsValue(), this.party.getDeck().getDeckStyle());

      for (int i = 0; i < this.party.getListHand().size(); i++) {
        p.addHand(new Hand());
      }

      new GamePage(p, this.window, this.contentPane);
    }

  }

  /*Affiche la valeur d'une main à des coordonnées données
    @param Hand, int startX, int startY, boolean*/
  public void actionGetHandValue(Hand hand, int startX, int startY, boolean dealer) {

    //Affiche l'icone du dealer même apres hit
    this.actionPrintPictureDealer();
    this.printDeck();

    JLabel sum = new JLabel();
    sum.setBounds(startX, startY, 400, 60);
    sum.setFont(new Font("Serif", Font.BOLD, 20));
    sum.setForeground(Color.WHITE);
    if (dealer) {
      sum.setText(("Valeur : " + this.party.getSumDealerHand()));
    } else {
      sum.setText(("Valeur : " + this.party.getSumHandValueOfOneHand(hand)));
    }
    contentPane.add(sum);
  }

  //Affiche l'icone du dealer
  public void actionPrintPictureDealer() {
    String imgUrl = "assets/dealer.png";
    ImageIcon dealer = new ImageIcon(imgUrl);
    JLabel labelDealer = new JLabel(dealer, JLabel.CENTER);
    labelDealer.setBounds(600, 80, 400, 60);
    contentPane.add(labelDealer);
  }

  //Nettoie tous les composants du panel sauf les boutons hit et stand
  public void cleanPanel() {
    this.contentPane.removeAll();
    this.contentPane.add(this.hit);
    this.contentPane.add(this.stand);
    this.contentPane.repaint();
  }
}
