package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import Habitation.Piece;
import Outils.ModeleSingleton;
import RecylcerViews.AdaptateurPiece;

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
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Annuler",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    private void initPieces(){
        pieces.addAll(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList());
        AdaptateurPiece ad = new AdaptateurPiece(pieces);
        RecyclerView rc = findViewById(R.id.recylcerView_Pieces);
        rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(ad);
    }

    /*
        POUR LA CREATION DES PHOTOS :
        AFFICHER LE MAGNETOMETRE AVANT DE PRENDRE LA PHOTO
        DONC TELEPHONE A PLAT POUR VOIR OU EST LE MUR AU NORD, EST,OUEST,SUD
        ENSUITE QUAND ON A IDENTIFIE LES DIRECTIONS
        FAIRE 4 CASES DE DIRECTION POUR LES PHOTOS
        CLIQUER SUR LA L'UNE DES 4 CASES :ex nord
        AFFICHER L'ACCELEROMETRE PENDANT LA PHOTO POUR VOIR L'HORIZON CAD:
        METTRE LA BARRE VERTE AU MILIEU par exemple
        ET QUAND ELLE EST DROITE PRENDRE LA PHOTO

        UNE FOIS QUE LA PHOTO EST PRISE:
        POUVOIR RAJOUTER DES ACCES
        QUAND UN ACCES EST RAJOUTER DEMANDER DIRECTEMENT LE NOM DE LA NOUVELLE PIECE
        LA PIECE S'AJOUTE A LA LISTE ET POURRA ETRE REMPLIE PLUS TARD

        REFERENCE THOMAS
     */
}