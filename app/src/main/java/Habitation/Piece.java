package Habitation;

import java.util.ArrayList;

public class Piece {
    private ArrayList<Mur> murArrayList;
    private String nom;

    public Piece(String nom) {
        this.murArrayList = new ArrayList<>(4);
        this.nom = nom;
    }
    public void ajouterMur(){

    }

    public ArrayList<Mur> getMurArrayList() {
        return murArrayList;
    }

    public void setMurArrayList(ArrayList<Mur> murArrayList) {
        this.murArrayList = murArrayList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
