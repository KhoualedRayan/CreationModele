package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import Habitation.Modele;

public class ChargementModele extends AppCompatActivity {
    private ArrayList<Modele> modeleArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement_modele);
    }
}