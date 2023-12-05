package habitation;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mur {
    private Orientation orientation;
    private String nomBitmap;
    private ArrayList<Ouverture> ouvertures;
    private byte[] bytes;

    public Mur() {
        ouvertures = new ArrayList<>();
    }

    public Mur(Orientation orientation, String nomBitmap) {
        this.orientation = orientation;
        this.nomBitmap = nomBitmap;
        ouvertures = new ArrayList<>();
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

    public ArrayList<Ouverture> getOuvertures() {
        return ouvertures;
    }

    public void setOuvertures(ArrayList<Ouverture> ouvertures) {
        this.ouvertures = ouvertures;
    }



    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
