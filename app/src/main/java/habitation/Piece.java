package habitation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Piece {
    @JsonManagedReference(value = "piece-murNord")
    private Mur murNord;

    @JsonManagedReference(value = "piece-murEst")
    private Mur murEst;

    @JsonManagedReference(value = "piece-murSud")
    private Mur murSud;

    @JsonManagedReference(value = "piece-murOuest")
    private Mur murOuest;
    private String nom;
    @JsonCreator
    public Piece(@JsonProperty("nom") String nom) {
        this.murNord = new Mur();
        this.murOuest= new Mur();
        this.murEst = new Mur();
        this.murSud = new Mur();
        this.nom = nom;
    }

    public Mur getMurNord() {
        return murNord;
    }

    public void setMurNord(Mur murNord) {
        this.murNord = murNord;
    }

    public Mur getMurEst() {
        return murEst;
    }

    public void setMurEst(Mur murEst) {
        this.murEst = murEst;
    }

    public Mur getMurSud() {
        return murSud;
    }

    public void setMurSud(Mur murSud) {
        this.murSud = murSud;
    }

    public Mur getMurOuest() {
        return murOuest;
    }

    public void setMurOuest(Mur murOuest) {
        this.murOuest = murOuest;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "murNord=" + murNord +
                ", murEst=" + murEst +
                ", murSud=" + murSud +
                ", murOuest=" + murOuest +
                ", nom='" + nom + '\'' +
                '}';
    }
}
