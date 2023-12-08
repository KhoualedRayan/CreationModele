package com.example.creationmodele;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;

import outils.ModeleSingleton;

public class CreationModele extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_modele);
        editText = findViewById(R.id.editTextText_nouveau_modele);
    }
    public void CreerNouveeauModele(View view) {
        if(editText.getText().toString().getBytes().length != 0 || editText.getText().toString().endsWith(".")){
            ModeleSingleton.getInstance().clear();
            ModeleSingleton.getInstance().getModeleInstance().setNom(editText.getText().toString());
            Log.i("CREATION_MODELE",editText.getText().toString());
            File repertoireExterne = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File dossierJson = new File(repertoireExterne, "DossierJSON");
            if (!dossierJson.exists()) {
                dossierJson.mkdirs();
            }
            setResult(2);
            finish();

        }else {
            new AlertDialog.Builder(this)
                    .setTitle("Nom invalide")
                    .setMessage("Ecrivez un nom valide :\nPas de '.' à la fin et mini 1 lettre.")
                    .setNeutralButton("OK",null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}