package recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.creationmodele.R;

import java.util.ArrayList;

import habitation.Modele;

public class AdaptateurModele extends RecyclerView.Adapter<ModeleViewHolder> {

    private ArrayList<Modele> modeles;

    public AdaptateurModele(ArrayList<Modele> modeles){
        this.modeles =modeles;
    }

    @Override
    public ModeleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.modele_item, parent, false);
        return new ModeleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ModeleViewHolder modeleViewHolder, int position) {
        modeleViewHolder.getNom().setText(this.modeles.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return modeles.size();
    }

}
