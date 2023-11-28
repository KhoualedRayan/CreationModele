package recyclerViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.EditionModele;
import com.example.creationmodele.PieceActivity;
import com.example.creationmodele.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import habitation.Modele;
import habitation.Mur;
import habitation.Ouverture;
import habitation.Piece;
import outils.ModeleSingleton;

public class PieceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView nom;
    private Context context;

    public void setNom(TextView nom) {
        this.nom = nom;
    }
    public PieceViewHolder(@NonNull View itemView,Context context) {
        super(itemView);
        this.context = context;
        nom = itemView.findViewById(R.id.piece_nom);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(itemView.getContext())
                .setTitle(nom.getText().toString())
                .setMessage("Que voulez vous faire avec cette pièce ?")
                .setPositiveButton("Modifier la pièce", (dialog, which) -> modifPiece())

                .setNegativeButton("Supprimer la pièce", (dialog, which) -> supprimerPiece())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public TextView getNom() {
        return nom;
    }
    private void supprimerPiece(){
        nom.setInputType(InputType.TYPE_CLASS_TEXT );
        Iterator<Piece> iterator = ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().iterator();

        while (iterator.hasNext()) {
            Piece p = iterator.next();
            if (p.getNom().equals(nom.getText().toString())) {
                iterator.remove(); // Utiliser l'itérateur pour supprimer l'élément de la liste en toute sécurité.
            }
        }

    }
    private void modifPiece(){
        Intent intent = new Intent(context, PieceActivity.class);
        nom.setInputType(InputType.TYPE_CLASS_TEXT );
        intent.putExtra("nomPiece",nom.getText().toString());
        context.startActivity(intent);
        Log.i("Chargement Piece",nom.getText().toString());

    }
}
