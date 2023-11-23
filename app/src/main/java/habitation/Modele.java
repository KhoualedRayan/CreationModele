package habitation;

import java.util.ArrayList;

public class Modele {
    private ArrayList<Piece> pieces;

    private String nom;

    public Modele() {
        this.pieces = new ArrayList<>();
    }

    public ArrayList<Piece> getPieceArrayList() {
        return pieces;
    }

    public void setPieceArrayList(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public void ajouterPiece(Piece piece) {
        this.pieces.add(piece);
    }

    public void remplacementModele(Modele modele) {
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
