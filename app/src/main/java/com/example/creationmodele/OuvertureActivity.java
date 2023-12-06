package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import habitation.Modele;
import habitation.Mur;
import habitation.Ouverture;
import habitation.Piece;
import outils.ModeleSingleton;
import recyclerViews.AdaptateurPiece;

public class OuvertureActivity extends AppCompatActivity {
    private ImageView imageView;
    private int x,y,x1,y1;
    private ArrayList<Integer> list;
    private Rect rect;
    private ArrayList<Rect> rects;
    private SurfaceView surfaceView;
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean dessin = false;
    private ArrayList<Piece> pieces;
    private Mur mur;
    private Piece piece;
    private ArrayList<Ouverture> ouvertures;
    private String nomMur;
    private AlertDialog al;
    private boolean superposition = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouverture);
        init();
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                int pointerIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(pointerIndex);

                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        motionDownHandler(pointerId);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        motionPointerDownHandler(motionEvent,pointerId);
                        break;
                    case MotionEvent.ACTION_UP:
                        motionUpHandler(motionEvent);
                        break;
                }
                return true;
            }
        });
    }
    public void motionDownHandler(int pointerId){
        if(pointerId == 0){
            remove0();
            list.add(pointerId);
        }else if(pointerId == 1){
            remove1();
            list.add(pointerId);
        }
    }
    public void motionPointerDownHandler(MotionEvent motionEvent, int pointerId){
        if(pointerId == 0){
            remove0();
            list.add(pointerId);
        } else if(pointerId == 1){
            remove1();
            list.add(pointerId);
        }
        if(motionEvent.getPointerCount() == 2){
            x = (int) motionEvent.getX(0);
            y = (int) motionEvent.getY(0);
            String coord1 = "Coords du pointeur 0, X : " + x + " Y : " + y;
            x1 = (int) motionEvent.getX(1);
            y1 = (int)  motionEvent.getY(1);
            String coord2 = "Coords du pointeur 1, X : " + x1 + " Y : " + y1;
            Log.i("Select Activity", coord1);
            Log.i("Select Activity", coord2);
            rect = new Rect();
            rect.set(x1, y1, x, y);
            rect.sort();
            Log.i("Select Activity", rect.toString());
            dessinRectangle();
        }
        Log.i("Select Activity", list.toString());
    }
    public void motionUpHandler(MotionEvent motionEvent){
        if(motionEvent.getPointerCount() < 2 && dessin){
            verifAjoutPiece();
        }
    }
    public void init(){
        imageView = findViewById(R.id.imageView_MurEdit);
        pieces = new ArrayList<>();
        ouvertures = new ArrayList<>();
        rects = new ArrayList<>();
        mur = new Mur();
        rect = new Rect();
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceView.setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        canvas = new Canvas();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            Bitmap bitmap =(Bitmap) b.get("Bitmap");
            imageView.setImageBitmap(bitmap);
            nomMur = (String) b.get("Mur");
            piece = ModeleSingleton.getInstance().getPieceEnCours();
            mur = getMurByNom(nomMur);
            for (Ouverture ouverture: ouvertures){
                pieces.add(trouverPieceParNom(ouverture.getPieceArrivee()));
                rects.add(ouverture.getRect());
            }
            dessinRectangle();
            Log.i("OUVERTURE ACTIVITY", mur.getNomBitmap());
            Log.i("OUVERTURE ACTIVITY", piece.toString());
        }
    }
    public Mur getMurByNom(String nom) {
        if (nom.equals(piece.getMurEst().getNomBitmap())){
            ouvertures = ModeleSingleton.getInstance().getPieceEnCours().getMurEst().getOuvertures();
            return piece.getMurEst();
        }
        if (nom.equals(piece.getMurOuest().getNomBitmap())){
            ouvertures = ModeleSingleton.getInstance().getPieceEnCours().getMurOuest().getOuvertures();
            return piece.getMurOuest();
        }
        if (nom.equals(piece.getMurSud().getNomBitmap())){
            ouvertures = ModeleSingleton.getInstance().getPieceEnCours().getMurSud().getOuvertures();
            return piece.getMurSud();
        }
        if (nom.equals(piece.getMurNord().getNomBitmap())){
            ouvertures = ModeleSingleton.getInstance().getPieceEnCours().getMurNord().getOuvertures();
            return piece.getMurNord();
        }
        return new Mur();
    }


    public void dessinRectangle() {
        Rect imageRect = new Rect();
        imageView.getHitRect(imageRect);
        canvas = surfaceView.getHolder().lockCanvas();
        if (canvas != null) {
            try {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Efface le contenu précédent
                for (Rect r : rects)
                    canvas.drawRect(r, paint);
                if (!superposition)
                    canvas.drawRect(rect, paint);
                superposition = false;
            } finally {
                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
            dessin = true;
        }

    }
    public void ajoutPiece() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        List<String> listeNomsPieces = new ArrayList<>();
        listeNomsPieces.add("Créer une nouvelle pièce");
        for (Piece piece : ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList()) {
            listeNomsPieces.add(piece.getNom());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeNomsPieces);
        ListView listViewPieces = new ListView(this);
        listViewPieces.setAdapter(adapter);

        listViewPieces.setOnItemClickListener((parent, view, position, id) -> {
            String nomPieceSelectionnee = listeNomsPieces.get(position);
            if ("Créer une nouvelle pièce".equals(nomPieceSelectionnee)) {
                Log.i("Info", "Position dans le code : 2");
                new AlertDialog.Builder(this)
                        .setTitle("Nouvelle pièce")
                        .setMessage("Ecrire le nom de la pièce à créer à relier à cette ouverture.")
                        .setView(input)
                        .setPositiveButton("Valider", (dialog2, which2) -> {
                            String nomPiece = input.getText().toString();
                            if (!nomPiece.isEmpty()) {
                                Piece p = new Piece(nomPiece);
                                pieces.add(p);
                                Ouverture ouverture = new Ouverture(ModeleSingleton.getInstance().getPieceEnCours().getNom(), p.getNom(), rect);
                                ouvertures.add(ouverture);
                                Toast.makeText(this, "Ajout de la nouvelle pièce : " + nomPiece, Toast.LENGTH_SHORT).show();
                                al.dismiss();
                            }
                        })
                        .setNegativeButton("Annuler", (dialog2, which2) -> {
                            rects.remove(rects.size() - 1);
                            superposition = true;
                            dessinRectangle();
                            al.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                // Mettez ici le code pour traiter la pièce existante si nécessaire
                Piece p = trouverPieceParNom(nomPieceSelectionnee);
                Ouverture ouverture = new Ouverture(ModeleSingleton.getInstance().getPieceEnCours().getNom(), p.getNom(),rect);
                pieces.add(p);
                ouvertures.add(ouverture);
                Toast.makeText(this, "Ajout de l'ouverture avec : " + nomPieceSelectionnee, Toast.LENGTH_SHORT).show();
                al.dismiss();

            }
        });

         al =new AlertDialog.Builder(this)
                .setTitle("Nouvelle pièce")
                .setView(listViewPieces)
                .setPositiveButton("Valider", (dialog, which) -> {
                    // Code pour traiter le bouton Valider dans la boîte de dialogue principale
                })
                .setNegativeButton("Annuler", (dialog, which) -> {
                    rects.remove(rects.size() - 1);
                    superposition = true;
                    dessinRectangle();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }



    public Piece trouverPieceParNom(String nom) {
        for (Piece piece : ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList()) {
            if (piece.getNom().equals(nom)) {
                return piece;
            }
        }
        return null; // Pièce non trouvée
    }

    public void remove0() {
        removePointerId(0);
    }

    public void remove1() {
        removePointerId(1);
    }
    public void removePointerId(int pointerId) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer id = iterator.next();
            if (id == pointerId) {
                iterator.remove();
            }
        }
    }

    public void validerOnClick(View view) {
        for (Piece piece : pieces) {
            if (!ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().contains(piece)) {
                ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().add(piece);
            }
        }
        updateOuvertures();
        finish();
    }
    public void updateOuvertures() {
        if(nomMur.equals(piece.getMurEst().getNomBitmap()))
            ModeleSingleton.getInstance().getPieceEnCours().getMurEst().setOuvertures(ouvertures);
        if(nomMur.equals(piece.getMurOuest().getNomBitmap()))
            ModeleSingleton.getInstance().getPieceEnCours().getMurOuest().setOuvertures(ouvertures);
        if(nomMur.equals(piece.getMurSud().getNomBitmap()))
            ModeleSingleton.getInstance().getPieceEnCours().getMurSud().setOuvertures(ouvertures);
        if(nomMur.equals(piece.getMurNord().getNomBitmap()))
            ModeleSingleton.getInstance().getPieceEnCours().getMurNord().setOuvertures(ouvertures);
    }
    public boolean isEmplacementOccupe(Rect nouvelEmplacement) {
        for (Rect rect : rects) {
            if (Rect.intersects(rect, nouvelEmplacement)) {
                // Les rectangles se chevauchent, l'emplacement est occupé
                return true;
            }
        }
        // Aucun chevauchement trouvé, l'emplacement est libre
        return false;
    }
    public void verifAjoutPiece() {
        Rect imageRect = new Rect();
        int nbouv = ouvertures.size();
        int nbrect = rects.size();
        imageView.getHitRect(imageRect);
        if (!isEmplacementOccupe(rect) && imageRect.contains(rect)) {
            superposition = false;
            rects.add(rect);
            ajoutPiece();
        }else if(!imageRect.contains(rect)){
            superposition = true;
            Toast.makeText(this, "EN DEHORS DE L'IMAGE", Toast.LENGTH_SHORT).show();
            dessinRectangle();
        }else if(imageRect.contains(rect) && !!imageRect.contains(rect)){
            // Gérer le cas où il y a une superposition
            superposition = true;
            Toast.makeText(this, "Superposition de rectangles détectée", Toast.LENGTH_SHORT).show();
            // Redessiner avec la liste mise à jour des rectangles
            dessinRectangle();
        }

    }

    public void supprDerniereOuveture(View view) {
        if(rects.size() >=1) {
            rects.remove(rects.size() - 1);
            if(pieces.size()>0)
                pieces.remove(pieces.size() - 1);
            if(ouvertures.size()>0)
                ouvertures.remove(ouvertures.size() - 1);
            superposition = true;
            dessinRectangle();
        }
    }

    public void supprOuvertures(View view) {
        if(rects.size() >=1) {
            rects.clear();
            pieces.clear();
            ouvertures.clear();
            superposition = true;
            dessinRectangle();
        }
    }
}