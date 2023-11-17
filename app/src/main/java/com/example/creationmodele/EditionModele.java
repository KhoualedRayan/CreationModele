package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import Habitation.Modele;
import Habitation.Piece;

public class EditionModele extends AppCompatActivity {
    private ArrayList<Piece> pieces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_modele);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView = findViewById(R.id.textView_nom_Modele);
        textView.setText(textView.getText()+Modele.getInstance().getNom());
        this.pieces = new ArrayList<>();
        initPieces();
    }
    private void initPieces(){
        pieces = Modele.getInstance().getPieceArrayList();
    }

    public void ajouterPiece(View view) {
        Intent intent = new Intent(this,PieceActivity.class);
        startActivity(intent);
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