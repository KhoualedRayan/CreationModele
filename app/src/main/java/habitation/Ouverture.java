package habitation;

import android.graphics.Rect;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties()
public class Ouverture {
    //private Piece pieceDepart;
    /*
    OUVERTURE NE CONNAIT PAS 2 PIECES MAIS LES NOMS DES 2 PIECES
    CE QUI RESOUT LE PB DE BOUCLE INFINI LORS DE LA SAVE
    +
    FAIRE EN SORTE QUE LE NOM DES PIECES SOIT UNIQUE
    SOIT PAR UN IDENTIFIANT SECRET AVEC UNE FABRIQUE
    SOIT EN L'IMPOSANT A L'UTILISATEUR
    EN LUI DEMANDANDANT UN AUTRE NOM SI IL YA DEJA CELUI CI DANS LA BDD
    FAIRE UNE FONCTION FINDBYNOM ??

    */
    private String pieceArrivee;
    private String pieceDepart;
    //private Piece pieceArrivee;
    private Rect rect;

    public Ouverture(@JsonProperty("pieceDepart") String pieceDepart, @JsonProperty("pieceArrivee")String pieceArrivee, @JsonProperty("rect") Rect rect) {
        this.pieceDepart = pieceDepart;
        this.pieceArrivee = pieceArrivee;
        this.rect = rect;
    }

    public String getPieceArrivee() {
        return pieceArrivee;
    }

    public void setPieceArrivee(String pieceArrivee) {
        this.pieceArrivee = pieceArrivee;
    }

    public String getPieceDepart() {
        return pieceDepart;
    }

    public void setPieceDepart(String pieceDepart) {
        this.pieceDepart = pieceDepart;
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
