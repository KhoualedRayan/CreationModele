package recyclerViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.EditionModele;
import com.example.creationmodele.PieceActivity;
import com.example.creationmodele.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import habitation.Modele;
import habitation.Piece;
import outils.ModeleSingleton;

public class ModeleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView nom;
    private Context context;
    public ModeleViewHolder(View itemView,Context context) {
        super(itemView);
        this.context = context;
        nom = itemView.findViewById(R.id.modele_nom);
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
                .setPositiveButton("Charger le modèle", (dialog, which) -> chargerModele())

                .setNegativeButton("Supprimer le modèle", (dialog, which) -> supprimerModele())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void supprimerModele(){
        File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
        String chemin = dossier.getAbsolutePath()+"/"+nom.getText().toString();
        // Créez un objet File pour représenter le dossier
        File fichier = new File(chemin);

        if(fichier.delete()){
            Log.i("CHARGEMENT_MODELE",fichier.getName()+" est supprimé");
        }else {
            Log.i("CHARGEMENT_MODELE","ECHEC SUPPRESSION DU FICHIER");
        }


    }
    private void chargerModele(){
        File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
        String chemin = dossier.getAbsolutePath()+"/"+nom.getText().toString();
        try {
            // Créer un objet ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


            // Lire le fichier JSON et le convertir en objet de la classe MaClasse
            Modele modeleCharger = objectMapper.readValue(new File(chemin), Modele.class);
            ModeleSingleton.getInstance().getModeleInstance().remplacementModele(modeleCharger);
            Intent intent = new Intent(context, EditionModele.class);
            ((Activity) context).finish();
            ((Activity) context).startActivity(intent);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
