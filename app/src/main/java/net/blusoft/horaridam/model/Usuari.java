package net.blusoft.horaridam.model;

/**
 * Created by IES on 08/01/2017.
 */

public class Usuari {
    private String nom;
    private int grup;

    public Usuari(String nom, int grup){
        this.nom=nom;
        this.grup=grup;
    }

    public String getNom() {
        return nom;
    }

    public int getGrup() {
        return grup;
    }
}
