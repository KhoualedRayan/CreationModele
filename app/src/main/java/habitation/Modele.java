package habitation;

import java.util.ArrayList;

public class Modele {
    private ArrayList<Piece> pieces;

    private String nom;
    private ArrayList<String> allBitmaps;

    public Modele() {
        this.pieces = new ArrayList<>();
        allBitmaps = new ArrayList<>();
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
        allBitmaps = modele.allBitmaps;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<String> getAllBitmaps() {
        return allBitmaps;
    }

    public void setAllBitmaps(ArrayList<String> allBitmaps) {
        this.allBitmaps = allBitmaps;
    }
}
