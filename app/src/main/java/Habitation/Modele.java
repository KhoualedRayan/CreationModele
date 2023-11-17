package Habitation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Modele {
    private ArrayList<Piece> pieces;
    private String nom;

    public Modele() {

    }
    private static final Modele instance = new Modele();
    public static final Modele getInstance() {
        return instance;
    }


    public ArrayList<Piece> getPieceArrayList() {
        return pieces;
    }

    public void setPieceArrayList(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }
    public void ajouterPiece(Piece piece){
        this.pieces.add(piece);
    }
    public void remplacementModele(Modele modele){
        this.pieces = modele.pieces;
        this.nom = modele.getNom();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
