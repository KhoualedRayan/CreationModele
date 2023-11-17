package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import Habitation.Modele;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button creerModele = findViewById(R.id.button_creation_modele);
        Button chargerModele = findViewById(R.id.button_charger_modele);
        Button editerModele = findViewById(R.id.button_edition_modele);

        sendIntent(creerModele,CreationModele.class);
        sendIntent(chargerModele,ChargementModele.class);
        sendIntent(editerModele, EditionModele.class);

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
        if(Modele.getInstance().getNom() != null) {
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
    private void save(){
        try {
            Context context = this;
            // Créer un objet ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convertir l'objet en format JSON
            String json = objectMapper.writeValueAsString(Modele.getInstance());

            // Spécifier le nom du fichier
            String nomFichier = Modele.getInstance().getNom() + ".json";

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
    }

}