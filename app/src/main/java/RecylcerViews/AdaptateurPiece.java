package RecylcerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creationmodele.R;

import java.util.ArrayList;

import Habitation.Modele;
import Habitation.Piece;

public class AdaptateurPiece extends RecyclerView.Adapter<PieceViewHolder>{
    private ArrayList<Piece> pieces;

    public AdaptateurPiece(ArrayList<Piece> pieces){
        this.pieces =pieces;
    }

    @Override
    public PieceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.piece_item, parent, false);
        return new PieceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PieceViewHolder pieceViewHolder, int position) {
        pieceViewHolder.getNom().setText(this.pieces.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }
}
