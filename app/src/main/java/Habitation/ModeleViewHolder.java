package Habitation;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.R;

public class ModeleViewHolder extends RecyclerView.ViewHolder {
    private TextView nom;

    public ModeleViewHolder(View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.contact_nom);
    }


    public TextView getNom() {
        return nom;
    }

    public void setNom(TextView nom) {
        this.nom = nom;
    }
}
