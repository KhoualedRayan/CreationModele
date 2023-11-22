package Habitation;

public class Mur {
    private Orientation orientation;
    private String nomBitmap;

    public Mur() {
    }

    public Mur(Orientation orientation, String nomBitmap) {
        this.orientation = orientation;
        this.nomBitmap = nomBitmap;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public String getNomBitmap() {
        return nomBitmap;
    }

    public void setNomBitmap(String nomBitmap) {
        this.nomBitmap = nomBitmap;
    }

    @Override
    public String toString() {
        return "Mur{" +
                "orientation=" + orientation +
                ", nomBitmap='" + nomBitmap + '\'' +
                '}';
    }
}
