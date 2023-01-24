package views;

import javax.swing.*;
import java.awt.*;

public class BasicPage {

    JFrame window;
    public JPanel contentPane;
    public static int X_SIZE = 1280;
    public static int Y_SIZE = 720;
    
    //Image de fond de l'accueil
    JLabel backgroundImageHome = new JLabel("", new ImageIcon("assets/fond_deck_main.jpg"), JLabel.CENTER);
    //Image de fond durant la partie
    JLabel backgroundImageGame = new JLabel("", new ImageIcon("assets/fond_deck.png"), JLabel.CENTER);
    //Image de fond basique
    JLabel backgroundImage = new JLabel("", new ImageIcon("assets/fond_deck_main_Home.png"), JLabel.CENTER);

    //Style css
    public String css = "    <style>        * {            font-family: Arial;            color: #212121;        }        h1 {            font-style: italic;       }    </style>    ";

    //Style html
    public String startHtml = "<html>" + css + "<h3>";
    public String endHtml = "</h3></html>";

    public BasicPage() {

    }

    /*Transforme n'importe quel texte en texte HTML (entre deux balises)
      @param text
      @return le texte en mode HTML*/
    public String textHTML(String text) {
        return startHtml + text + endHtml;
    }

    //Ferme la fenêtre automatiquement
    public void dispose() {
        this.window.dispose();
    }

    //Ajoute positionne l'arriere plan du jeux
    public void setBackgroundLaunch(JPanel contentPane) {
        backgroundImage.setBounds(0, 0, 1280, 720);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(backgroundImage);
        contentPane.repaint();
    }

    public void setBackgroundHome(JPanel contentPane) {
        backgroundImageHome.setBounds(0, 0, 1280, 720);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(backgroundImageHome);
        contentPane.repaint();
    }

    public void setBackgroundGame(JPanel contentPane) {
        backgroundImageGame.setBounds(0, 0, 1280, 700);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(backgroundImageGame);
        contentPane.repaint();
    }
}
