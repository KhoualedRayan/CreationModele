package outils;

import java.util.ArrayList;

import habitation.Modele;
import habitation.Piece;

public class ModeleSingleton {
    private Modele modeleInstance;
    private Piece pieceEnCours;

    private static final ModeleSingleton instance = new ModeleSingleton();

    private ModeleSingleton() {
        modeleInstance = new Modele();
    }

    public static ModeleSingleton getInstance() {
        return instance;
    }

    public Modele getModeleInstance() {
        return modeleInstance;
    }

    public void clear() {
        this.modeleInstance.getPieceArrayList().clear();
    }

    public Piece getPieceEnCours() {
        return pieceEnCours;
    }

    public void setPieceEnCours(Piece pieceEnCours) {
        this.pieceEnCours = pieceEnCours;
    }
}
