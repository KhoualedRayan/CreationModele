package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import outils.ModeleSingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button creerModele = findViewById(R.id.button_creation_modele);
        Button chargerModele = findViewById(R.id.button_charger_modele);

        sendIntent(creerModele,CreationModele.class);
        sendIntent(chargerModele,ChargementModele.class);


    }
    public void sendIntent(Button button, Class c){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,c);
                startActivity(intent);
            }
        });
    }

    public void SauvegarderModele(View view) {
        if(ModeleSingleton.getInstance().getModeleInstance().getNom() != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Sauvegarde du modèle")
                    .setMessage("Voulez-vous sauvegarder le modèle en cours ?")
                    .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            save();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Annuler", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Impossible de sauvegarder")
                    .setMessage("Veuillez charger ou créer un modèle avant de sauvegarder.")
                    .setNeutralButton("OK",null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
    /*private void save(){
        try {
            Context context = this;
            // Créer un objet ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convertir l'objet en format JSON
            String json = objectMapper.writeValueAsString(ModeleSingleton.getInstance().getModeleInstance());

            // Spécifier le nom du fichier
            String nomFichier = ModeleSingleton.getInstance().getModeleInstance().getNom() + ".json";

            // Obtenir le répertoire de stockage interne de l'application
            File repertoireInterne = context.getFilesDir();

            // Créer un objet File pour le fichier de destination
            File fichierDestination = new File(repertoireInterne, nomFichier);

            // Écrire le JSON dans le fichier de destination
            try (FileOutputStream fos = new FileOutputStream(fichierDestination)) {
                fos.write(json.getBytes());
            }
            Log.i("MainACTIVITY : ", "Sauvegarde en JSON réussie dans : " + fichierDestination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void save() {
        try {
            // Créer un objet ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convertir l'objet en format JSON
            String json = objectMapper.writeValueAsString(ModeleSingleton.getInstance().getModeleInstance());

            // Spécifier le nom du fichier
            String nomFichier = ModeleSingleton.getInstance().getModeleInstance().getNom() + ".json";

            // Obtenir le répertoire de stockage externe public
            File repertoireExterne = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            // Créer un dossier s'il n'existe pas encore
            File dossierJson = new File(repertoireExterne, "DossierJSON");
            if (!dossierJson.exists()) {
                dossierJson.mkdirs();
            }

            // Créer un objet File pour le fichier de destination dans le dossier créé
            File fichierDestination = new File(dossierJson, nomFichier);

            // Écrire le JSON dans le fichier de destination
            try (FileOutputStream fos = new FileOutputStream(fichierDestination)) {
                fos.write(json.getBytes());
            }

            Log.i("MainACTIVITY : ", "Sauvegarde en JSON réussie dans : " + fichierDestination.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void EditionModele(View view) {
        if(ModeleSingleton.getInstance().getModeleInstance().getNom() != null) {
            Intent i = new Intent(this, EditionModele.class);
            startActivity(i);
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Impossible d'éditer")
                    .setMessage("Veuillez charger ou créer un modèle avant d'éditer un modèle.")
                    .setNeutralButton("OK",null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}