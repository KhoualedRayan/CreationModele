package com.example.creationmodele;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import habitation.Mur;
import habitation.Orientation;
import habitation.Piece;
import outils.ModeleSingleton;
import recyclerViews.AdaptateurPiece;

public class EditionModele extends AppCompatActivity {
    private ArrayList<Piece> pieces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_modele);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView = findViewById(R.id.textView_nom_Modele);
        textView.setText(textView.getText()+ModeleSingleton.getInstance().getModeleInstance().getNom());
        this.pieces = new ArrayList<>();
        initPieces();
    }

    public void ajouterPiece(View view) {
        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        Intent intent = new Intent(this,PieceActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Nouvelle piece")
                .setMessage("Ecrire le nom de la pièce à créer.")
                .setView(input)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("nomPiece",input.getText().toString());
                        Log.i("EDITION MODELE",input.getText().toString());
                        startActivityForResult(intent,1);
                        setResult(RESULT_OK,intent);
                    }
                })
                .setNegativeButton("Annuler",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            pieces.clear();
            initPieces();
        }
    }
    private void initPieces(){
        for (Piece p : ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList()){
            pieces.add(p);
        }
        Log.i("EDITION MODELE NB PIECES", String.valueOf(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size()));
        AdaptateurPiece ad = new AdaptateurPiece(pieces);
        RecyclerView rc = findViewById(R.id.recylcerView_Pieces);
        rc.setHasFixedSize(false);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(ad);
    }

    public void save(View view) {
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
}