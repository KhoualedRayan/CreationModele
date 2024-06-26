package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import recyclerViews.AdaptateurModele;
import habitation.Modele;

public class ChargementModele extends AppCompatActivity {
    private ArrayList<Modele> modeles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement_modele);
        modeles = new ArrayList<>();
        initModeles();
    }

    private void initModeles(){
        File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
        Log.i("Chargement modele",dossier.getAbsolutePath());
        if (dossier.isDirectory()) {
            // Obtenez la liste des fichiers dans le dossier
            File[] fichiers = dossier.listFiles();
            Log.i("Chargement modele", String.valueOf(fichiers.length));

            // Parcourez les fichiers et affichez ceux avec l'extension .json
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    if (fichier.isFile() && fichier.getName().toLowerCase().endsWith(".json")) {
                        Log.i("CHARGEMENTMODELE : ","Fichier JSON trouvé : " + fichier.getAbsolutePath());
                        Modele m = new Modele();
                        m.setNom(fichier.getName());
                        modeles.add(m);
                    }
                }
            } else {
                Log.i("ChargementModele","Le dossier est vide.");
            }
        } else {
            Log.i("ChargementModele","Le chemin ne correspond pas à un dossier existant.");
        }
        AdaptateurModele ad = new AdaptateurModele(modeles);
        RecyclerView rc = findViewById(R.id.recylcerView);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(ad);
    }
}