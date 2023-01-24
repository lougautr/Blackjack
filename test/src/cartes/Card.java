package cartes;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

// Classe représentant une carte
public class Card implements Comparable {
    private String value;
    private String color;

    private String prefixePath;

    public Card(String value, String color, String cardStyle) {
        this.value = value;
        this.color = color;

        if (cardStyle == "classique") {
            this.prefixePath = "assets/card_img2/";
        } else if (cardStyle == "vintage") {
            this.prefixePath = "assets/card_img/";
        } else {
            this.prefixePath = "assets/card_img/";
        }
    }
    
    // Getter ------------------

    public String getValue() {
        return this.value;
    }

    public String getColor() {
        return this.color;
    }

    public String getImgPath() {
        return this.prefixePath + this.color + "_" + this.value + ".png";
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "(" + this.value + ", " + this.color + ")";
    }

    /* Méthode de comparaison d'objet
       @param Object
       @return valeur de comparaison (1, -1, ou 0) */
    @Override
    public int compareTo(Object O) {
        Card c = (Card) O;
        // HashMap comparator init
        HashMap<String, Integer> powerRelation = new HashMap<>();

        for (int i = 2; i < 11; i++) {
            powerRelation.put(Integer.toString(i), i);
        }
        powerRelation.put("as", 1);

        ArrayList<String> figure = new ArrayList<>(Arrays.asList("valet", "reine", "roi"));
        int i = 11;
        for (String f : figure) {
            powerRelation.put(f, i);
            i++;
        }
        
        // Compare
        int val_c1 = powerRelation.get(this.value);
        int val_c2 = powerRelation.get(c.getValue());

        if (val_c1 > val_c2) {
            return 1;
        } else if (val_c1 < val_c2) {
            return -1;
        } else {
            return 0;
        }
    }

    /* Méthode d'égalité d'objets
       @param Object
       @return boolean */
    @Override
    public boolean equals(Object object) {

        if (object != null && object instanceof Card) {
            Card c = (Card) object;
            if (c.getValue().equals(this.getValue()) && c.getColor().equals(this.getColor())) {
                return true;
            }
        }

        return false;
    }
}
