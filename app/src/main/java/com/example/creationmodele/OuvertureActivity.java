package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import habitation.Mur;
import habitation.Ouverture;
import habitation.Piece;
import outils.ModeleSingleton;

public class OuvertureActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button lastOp;
    private Button allOP;
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
            dessin = true;
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
        lastOp = findViewById(R.id.button_derniereOuverture);
        allOP = findViewById(R.id.button_supprOuvertures);
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if(ouvertures.size()==0){
                lastOp.setVisibility(View.INVISIBLE);
                allOP.setVisibility(View.INVISIBLE);
            }else {
                lastOp.setVisibility(View.VISIBLE);
                allOP.setVisibility(View.VISIBLE);
            }
            affichageRect();
        }
    }
    public void affichageRect(){
        enleverAnciensEtiquettes();
        enleverAnciensEtiquettes();
        canvas = surfaceView.getHolder().lockCanvas();
        if (canvas != null) {
            try {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Efface le contenu précédent
                for (Rect r : rects) {
                    canvas.drawRect(r, paint);
                    RelativeLayout relativeLayout = new RelativeLayout(this);
                    int backgroundColor = Color.argb(127, 0, 255, 0); // 50% d'opacité (255 * 0.5)
                    RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                            r.width(),
                            r.height()
                    );
                    relativeLayoutParams.leftMargin = r.left;
                    relativeLayoutParams.topMargin = r.top;

                    ((FrameLayout) surfaceView.getParent()).addView(relativeLayout, relativeLayoutParams);

                    TextView textView = new TextView(this);
                    textView.setText(trouverPieceParRect(r));
                    textView.setAllCaps(true);
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextColor(Color.BLACK);

                    relativeLayout.setBackgroundColor(backgroundColor);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                    relativeLayout.addView(textView, params);
                }
            } finally {
                surfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
            dessin = false;
        }
    }
    private String trouverPieceParRect(Rect r){
        String s = "";
        for(Ouverture ouverture : ouvertures){
            if(ouverture.getRect().equals(r)){
                s = ouverture.getPieceArrivee();
            }
        }
        return s;
    }
    private void enleverAnciensEtiquettes() {
        FrameLayout layout = (FrameLayout) surfaceView.getParent();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof RelativeLayout) {
                layout.removeView(child);
            }
        }
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
            dessin = false;
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
                al.dismiss();
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
                                rects.add(rect);
                                Ouverture ouverture = new Ouverture(ModeleSingleton.getInstance().getPieceEnCours().getNom(), p.getNom(), rect);
                                ouvertures.add(ouverture);
                                Toast.makeText(this, "Ajout de la nouvelle pièce : " + nomPiece, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Annuler", (dialog2, which2) -> {

                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                // Mettez ici le code pour traiter la pièce existante si nécessaire
                Piece p = trouverPieceParNom(nomPieceSelectionnee);
                Ouverture ouverture = new Ouverture(ModeleSingleton.getInstance().getPieceEnCours().getNom(), p.getNom(),rect);
                pieces.add(p);
                rects.add(rect);
                ouvertures.add(ouverture);
                Toast.makeText(this, "Ajout de l'ouverture avec : " + nomPieceSelectionnee, Toast.LENGTH_SHORT).show();
                al.dismiss();
            }
        });

         al =new AlertDialog.Builder(this)
                .setTitle("Nouvelle pièce")
                .setView(listViewPieces)

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
        imageView.getHitRect(imageRect);
        if (!isEmplacementOccupe(rect) && imageRect.contains(rect)) {
            superposition = false;
            //rects.add(rect);
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
            onWindowFocusChanged(true);
        }
    }

    public void supprOuvertures(View view) {
        if(rects.size() >=1) {
            rects.clear();
            pieces.clear();
            ouvertures.clear();
            superposition = true;
            dessinRectangle();
            onWindowFocusChanged(true);

        }
    }
}