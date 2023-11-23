package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;

import habitation.Modele;
import habitation.Mur;
import habitation.Ouverture;
import habitation.Piece;
import outils.ModeleSingleton;

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
    private Piece p;
    private ArrayList<Ouverture> ouvertures;
    private String nomPiece;
    private String nomMur;
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
                        if(pointerId == 0){
                            remove0();
                            list.add(pointerId);
                        }else if(pointerId == 1){
                            remove1();
                            list.add(pointerId);
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if(pointerId == 0){
                            remove0();
                            list.add(pointerId);
                        }else if(pointerId == 1){
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
                            Rect rect1 = new Rect();
                            rect1.set(x1,y1,x,y);
                            rect1.sort();
                            rects.add(rect1);
                            Log.i("Select Activity",rect1.toString());
                            dessinRectangle();
                        }
                        Log.i("Select Activity",list.toString());
                    case MotionEvent.ACTION_UP:
                        if(motionEvent.getPointerCount() <2 && dessin){
                            ajoutPiece();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void init(){
        imageView = findViewById(R.id.imageView_MurEdit);
        pieces = new ArrayList<>();
        ouvertures = new ArrayList<>();
        rects = new ArrayList<>();
        mur = new Mur();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            Bitmap bitmap =(Bitmap) b.get("Bitmap");
            imageView.setImageBitmap(bitmap);
            nomMur = (String) b.get("Mur");
            nomPiece = (String) b.get("Piece");
            Iterator<Piece> iterator = ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().iterator();

            while (iterator.hasNext()) {
                p = iterator.next();
                if (p.getNom().equals(nomPiece)) {
                    piece = p;
                    if(nomMur.equals(p.getMurEst().getNomBitmap()))
                        mur = p.getMurEst();
                    else if(nomMur.equals(p.getMurOuest().getNomBitmap()))
                        mur = p.getMurOuest();
                    else if(nomMur.equals(p.getMurSud().getNomBitmap()))
                        mur = p.getMurSud();
                    else if(nomMur.equals(p.getMurNord().getNomBitmap()))
                        mur = p.getMurNord();
                }
            }
            Log.i("OUVERTURE ACTIVITY", mur.getNomBitmap());
            Log.i("OUVERTURE ACTIVITY", piece.toString());
        }
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
    }

    public void dessinRectangle() {
        this.canvas = surfaceView.getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // Efface le contenu précédent
            canvas.drawRect(rect, this.paint);
            surfaceView.getHolder().unlockCanvasAndPost(this.canvas);
            dessin = true;
        }
    }
    public void ajoutPiece(){
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        Intent intent = new Intent(this,PieceActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("Nouvelle piece")
                .setMessage("Ecrire le nom de la pièce à créer à relier à cette ouverture.")
                .setView(input)
                .setPositiveButton("Valider", (dialog, which) -> {
                    Piece p = new Piece(input.getText().toString());
                    pieces.add(p);
                    Ouverture ouverture = new Ouverture(piece,p,rect);
                    ouvertures.add(ouverture);
                })
                .setNegativeButton("Annuler",null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void remove0(){
        for (int i = 0; i<list.size(); i++){
            if (list.get(i) == 0){
                list.remove(i);
            }
        }
    }public void remove1(){
        for (int i = 0; i<list.size(); i++){
            if (list.get(i) == 1){
                list.remove(i);
            }
        }
    }

    public void validerOnClick(View view) {
        ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().addAll(pieces);
        if (p.getNom().equals(nomPiece)) {
            piece = p;
            if(nomMur.equals(p.getMurEst().getNomBitmap()))
                p.getMurEst().setOuvertures(ouvertures);
            else if(nomMur.equals(p.getMurOuest().getNomBitmap()))
                p.getMurOuest().setOuvertures(ouvertures);
            else if(nomMur.equals(p.getMurSud().getNomBitmap()))
                p.getMurSud().setOuvertures(ouvertures);
            else if(nomMur.equals(p.getMurNord().getNomBitmap()))
                p.getMurNord().setOuvertures(ouvertures);
        }
        finish();
    }
    private boolean isEmplacementOccupe(Rect nouvelEmplacement) {
        for (Rect rect : rects) {
            if (Rect.intersects(rect, nouvelEmplacement)) {
                // Les rectangles se chevauchent, l'emplacement est occupé
                return true;
            }
        }
        // Aucun chevauchement trouvé, l'emplacement est libre
        return false;
    }
}