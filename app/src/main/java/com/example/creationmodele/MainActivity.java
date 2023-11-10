package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Habitation.Modele;

public class MainActivity extends AppCompatActivity {
    public Modele modeleActuel;
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
}