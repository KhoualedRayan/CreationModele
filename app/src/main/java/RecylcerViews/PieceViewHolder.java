package RecylcerViews;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.R;

public class PieceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView nom;


    public void setNom(TextView nom) {
        this.nom = nom;
    }
    public PieceViewHolder(@NonNull View itemView) {
        super(itemView);
        nom = itemView.findViewById(R.id.piece_nom);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
    public TextView getNom() {
        return nom;
    }
}
