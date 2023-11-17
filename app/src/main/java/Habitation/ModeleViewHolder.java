package Habitation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ModeleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView nom;

    public ModeleViewHolder(View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.contact_nom);
        itemView.setOnClickListener(this);
    }


    public TextView getNom() {
        return nom;
    }

    public void setNom(TextView nom) {
        this.nom = nom;
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(nom.getText().toString())
                .setMessage("Que voulez vous faire avec ce modèle ?")
                .setPositiveButton("Charger le modèle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        chargerModele();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("Supprimer le modèle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        supprimerModele();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void supprimerModele(){
        String chemin = "/data/user/0/com.example.creationmodele/files/"+nom.getText().toString();
        // Créez un objet File pour représenter le dossier
        File fichier = new File(chemin);
        if(fichier.delete()){
            Log.i("CHARGEMENT_MODELE",fichier.getName()+" est supprimé");
        }else {
            Log.i("CHARGEMENT_MODELE","ECHEC SUPPRESSION DU FICHIER");
        }
    }
    private void chargerModele(){
        String chemin = "/data/user/0/com.example.creationmodele/files/"+nom.getText().toString();
        try {
            // Créer un objet ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Lire le fichier JSON et le convertir en objet de la classe MaClasse
            Modele modeleCharger = objectMapper.readValue(new File(chemin), Modele.class);
            Modele.getInstance().remplacementModele(modeleCharger);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
