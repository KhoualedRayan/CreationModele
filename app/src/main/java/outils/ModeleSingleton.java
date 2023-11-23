package outils;

import java.util.ArrayList;

import habitation.Modele;

public class ModeleSingleton {
    private Modele modeleInstance;

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
}
