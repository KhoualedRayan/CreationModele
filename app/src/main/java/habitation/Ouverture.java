package habitation;

import android.graphics.Rect;

public class Ouverture {
    private Piece pieceDepart;
    private Piece pieceArrivee;
    private Rect rect;

    public Ouverture(Piece pieceDepart, Piece pieceArrivee, Rect rect) {
        this.pieceDepart = pieceDepart;
        this.pieceArrivee = pieceArrivee;
        this.rect = rect;
    }

    public Piece getPieceDepart() {
        return pieceDepart;
    }

    public void setPieceDepart(Piece pieceDepart) {
        this.pieceDepart = pieceDepart;
    }

    public Piece getPieceArrivee() {
        return pieceArrivee;
    }

    public void setPieceArrivee(Piece pieceArrivee) {
        this.pieceArrivee = pieceArrivee;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public String toString() {
        return "Ouverture{" +
                "pieceDepart=" + pieceDepart +
                ", pieceArrivee=" + pieceArrivee +
                ", rect=" + rect +
                '}';
    }
}
