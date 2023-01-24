package views.controller;

import views.*;
import java.net.*;
import java.awt.Desktop;
import java.io.IOException;
import views.Formulaire;
import java.awt.event.*;
import javax.swing.*;

public class ControlHome {
    private Home graphics;
    private JPanel contentPane;
    private JFrame window;

    //Image de fond
    JLabel backgroundImage = new JLabel("", new ImageIcon("assets/fond_deck_main_Home.png"), JLabel.CENTER);

    public ControlHome(Home graphics, JFrame window, JPanel contentPane) {
        this.graphics = graphics;
        this.contentPane = contentPane;
        this.window = window;
    }

    /*Redirige vers un formulaire de lancement de jeu
      @param event*/
    public void actionLoadGameButton(ActionEvent event) {
        this.contentPane.removeAll();
        new Formulaire(this.window, this.contentPane);
    }

    /*Redirige vers un lien hyperText au moment du clic (utilisation de java.awt.Desktop)
      @param event*/
    public void actionRulesButton(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.guide-blackjack.com/regles-du-black-jack.html"));
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /*Ferme la fenêtre de jeu
      @param event*/
    public void actionExitButton(ActionEvent event) {

        System.exit(0);
        this.graphics.dispose();
    }

}
