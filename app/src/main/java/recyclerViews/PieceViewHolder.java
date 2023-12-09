package recyclerViews;

import android.app.Activity;
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
        int position =-1;

        while (iterator.hasNext()) {
            Piece p = iterator.next();
            position++;
            if (p.getNom().equals(nom.getText().toString())) {
                iterator.remove(); // Utiliser l'itérateur pour supprimer l'élément de la liste en toute sécurité.
                supprOuvertures(p);
                break;
            }
        }
        ArrayList<Piece> updatedList = new ArrayList<>(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList());
        AdaptateurPiece newAdapter = new AdaptateurPiece(updatedList);

        // Actualiser la RecyclerView avec le nouvel adaptateur
        RecyclerView rc = ((Activity) context).findViewById(R.id.recylcerView_Pieces);
        rc.setAdapter(newAdapter);

    }
    public void supprOuvertures(Piece p){
        for(int i =0;i< ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size();++i){
            //NORD
            if(!ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurNord().getOuvertures().isEmpty()){
                for(int j = 0; j <ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurNord().getOuvertures().size();++j){
                    if(ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurNord().getOuvertures().get(j).getPieceArrivee().equals(p.getNom())){
                        Log.i("Suppresion ouverture Nord: ",p.getNom() );
                        ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().get(i).getMurNord().getOuvertures().remove(j);
                    }
                }
            }

            //EST
            if(!ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurEst().getOuvertures().isEmpty()){
                for(int j = 0; j <ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurEst().getOuvertures().size();++j){
                    if(ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurEst().getOuvertures().get(j).getPieceArrivee().equals(p.getNom())){
                        Log.i("Suppresion ouverture Est: ",p.getNom() );
                        ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().get(i).getMurEst().getOuvertures().remove(j);
                    }
                }
            }
            //OUEST
            if(!ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurOuest().getOuvertures().isEmpty()){
                for(int j = 0; j <ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurOuest().getOuvertures().size();++j){
                    if(ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurOuest().getOuvertures().get(j).getPieceArrivee().equals(p.getNom())){
                        Log.i("Suppresion ouverture Ouest: ",p.getNom() );
                        ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().get(i).getMurOuest().getOuvertures().remove(j);
                    }
                }
            }
            //SUD
            if(!ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurSud().getOuvertures().isEmpty()){
                for(int j = 0; j <ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurSud().getOuvertures().size();++j){
                    if(ModeleSingleton.getInstance().getModeleInstance().getPieces().get(i).getMurSud().getOuvertures().get(j).getPieceArrivee().equals(p.getNom())){
                        Log.i("Suppresion ouverture Sud: ",p.getNom() );
                        ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().get(i).getMurSud().getOuvertures().remove(j);
                    }
                }
            }
        }
    }
    private void modifPiece(){
        Intent intent = new Intent(context, PieceActivity.class);
        nom.setInputType(InputType.TYPE_CLASS_TEXT );
        intent.putExtra("nomPiece",nom.getText().toString());
        ((Activity) context).startActivityForResult(intent, 1);
        Log.i("Chargement Piece",nom.getText().toString());

    }

}
