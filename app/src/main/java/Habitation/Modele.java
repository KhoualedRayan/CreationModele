package Habitation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Modele {
    private ArrayList<Piece> pieceArrayList;
    private String nom;

    public Modele() {

    }
    private static final Modele instance = new Modele();
    public static final Modele getInstance() {
        return instance;
    }


        public ArrayList<Piece> getPieceArrayList() {
        return pieceArrayList;
    }

    public void setPieceArrayList(ArrayList<Piece> pieceArrayList) {
        this.pieceArrayList = pieceArrayList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


}
