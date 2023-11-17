package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Habitation.Modele;

public class CreationModele extends AppCompatActivity {
    private EditText editText;
    private Modele modele ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_modele);
        editText = findViewById(R.id.editTextText_nouveau_modele);
        modele = Modele.getInstance();

    }
    public void CreerNouveeauModele(View view) {
        Modele.getInstance().setNom(editText.getText().toString());
        Log.i("CREATION_MODELE",editText.getText().toString());
        Intent intent = new Intent(CreationModele.this,MainActivity.class);
        startActivity(intent);
    }
}