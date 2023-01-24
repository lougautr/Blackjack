package views;

import views.controller.ControlGamePage;
import blackjack.Party;
import javax.swing.*;

public class GamePage extends BasicPage {
   private ControlGamePage controller;
   private Party party;

   //Boutons hit et stand
   JButton hit = new JButton(new ImageIcon("assets/hit.png"));
   JButton stand = new JButton(new ImageIcon("assets/stand.png"));

   public GamePage(Party party, JFrame window, JPanel contentPane) {

      super();
      this.window = window;
      this.contentPane = contentPane;
      //Appel du controller
      this.controller = new ControlGamePage(this, hit, stand, party, this.window, this.contentPane);

      //Lance une partie
      this.party = party;
      this.party.launchGame();

      //Ajoute positionne les boutons du jeu
      hit.setBounds(X_SIZE - 150, Y_SIZE - 175, 120, 128);
      contentPane.add(hit);
      hit.addActionListener((e) -> this.controller.actionHit(e, this.party.getListHand().get(0)));

      // stand.setBounds(X_SIZE - 250, Y_SIZE - 100, 100, 50);
      stand.setBounds(X_SIZE - 290, Y_SIZE - 175, 128, 128);
      contentPane.add(stand);
      stand.addActionListener((e) -> this.controller.actionStand(e));

      //Affiche toutes les mains (première main -> joueur, autres -> IA)
      this.controller.printAllHandAndBackGround();
      this.controller.actionPrintPictureDealer();
      this.setBackgroundGame(this.contentPane);
      this.window.setVisible(true);

   }
}
