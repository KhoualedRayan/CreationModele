package com.example.creationmodele;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import habitation.Mur;
import habitation.Orientation;
import habitation.Ouverture;
import habitation.Piece;
import outils.AccelVectorView;
import outils.ModeleSingleton;

public class PieceActivity extends AppCompatActivity implements SensorEventListener {
    private Switch capteur;
    private SensorManager sensorManager;
    private Sensor accelerometre;
    private Sensor magnetometre;
    private boolean isSensorEnabled = false;
    private AccelVectorView accelVectorView;
    private static final int FPS = 1000 / 30; // 30 fps
    private final Handler handler = new Handler();
    private float[] matriceR;
    private float[] accel;
    private float[] magneto;
    private float[] orientation;
    private Piece piece;
    private static final int PHOTO_NORD = 1;
    private static final int PHOTO_SUD = 2;
    private static final int PHOTO_EST = 3;
    private static final int PHOTO_OUEST = 4;
    private ImageView imageView_Nord;
    private ImageView imageView_Sud;
    private ImageView imageView_Est;
    private ImageView imageView_Ouest;
    private boolean pieceExistant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initCapteurs();
        initPiece();
        showImageOnCreate();
    }
    public void initCapteurs(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometre = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        capteur = findViewById(R.id.capteur_switch);
        accelVectorView = findViewById(R.id.accelVectorView);
        matriceR = new float[9];
        accel = new float[3];
        magneto = new float[3];
        orientation = new float[3];
    }
    public void initPiece(){
        String nomPiece = "null";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            nomPiece =(String) b.get("nomPiece");
        }
        for (Piece p : ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList()){
            if(p.getNom().equals(nomPiece)){
                ModeleSingleton.getInstance().setPieceEnCours(p);
                piece = p;
                Log.i("PIECE ACTIVTY","PIECE EXISTE DEJA");
                pieceExistant = true;
            }
        }
        if (!pieceExistant) {
            piece = new Piece(nomPiece);
            ModeleSingleton.getInstance().setPieceEnCours(piece);
        }
        Log.i("PIECE ACTIVITY",nomPiece);
        imageView_Nord = findViewById(R.id.imageView_Nord);
        imageView_Sud = findViewById(R.id.imageView_Sud);
        imageView_Est = findViewById(R.id.imageView_Est);
        imageView_Ouest = findViewById(R.id.imageView_Ouest);
        Log.i("PIECE ACTIVITY",piece.getMurEst().getOuvertures().toString());
        Log.i("PIECE ACTIVITY",piece.getMurNord().getOuvertures().toString());
        Log.i("PIECE ACTIVITY",piece.getMurOuest().getOuvertures().toString());
        Log.i("PIECE ACTIVITY",piece.getMurSud().getOuvertures().toString());
    }

    public void ajouterImageOuest(View view) {
        prendrePhoto(PHOTO_OUEST);
    }

    public void ajouterImageEst(View view) {
        prendrePhoto(PHOTO_EST);
    }

    public void ajouterImageNord(View view) {
        prendrePhoto(PHOTO_NORD);
    }

    public void ajouterImageSud(View view) {
        prendrePhoto(PHOTO_SUD);
    }
    private void prendrePhoto(int requestCode) {
        new AlertDialog.Builder(this)
                .setTitle("Nouveau Mur")
                .setMessage("Voulez-vous créer un nouveau mur ou l'éditer ?")
                .setPositiveButton("Nouveau Mur", (dialog, which) -> {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        Toast.makeText(PieceActivity.this, "Prendre une photo", Toast.LENGTH_SHORT).show();
                        startActivityForResult(intent, requestCode);
                        setResult(RESULT_OK, intent);
                    }
                })
                .setNegativeButton("Editer", (dialog, which) -> {
                    Intent intent = new Intent(this,OuvertureActivity.class);
                    Bitmap bitmap;
                    switch (requestCode){
                        case PHOTO_NORD:
                            imageView_Nord.buildDrawingCache();
                            bitmap = Bitmap.createBitmap(imageView_Nord.getDrawingCache());
                            imageView_Nord.destroyDrawingCache();
                            intent.putExtra("Mur",ModeleSingleton.getInstance().getPieceEnCours().getMurNord().getNomBitmap());
                            intent.putExtra("Bitmap",bitmap);
                            break;
                        case PHOTO_EST:
                            imageView_Est.buildDrawingCache();
                            bitmap = Bitmap.createBitmap(imageView_Est.getDrawingCache());
                            imageView_Est.destroyDrawingCache();
                            intent.putExtra("Mur",ModeleSingleton.getInstance().getPieceEnCours().getMurEst().getNomBitmap());
                            intent.putExtra("Bitmap",bitmap);
                            break;
                        case PHOTO_OUEST:
                            imageView_Ouest.buildDrawingCache();
                            bitmap = Bitmap.createBitmap(imageView_Ouest.getDrawingCache());
                            imageView_Ouest.destroyDrawingCache();
                            intent.putExtra("Mur",ModeleSingleton.getInstance().getPieceEnCours().getMurOuest().getNomBitmap());
                            intent.putExtra("Bitmap",bitmap);
                            break;
                        case PHOTO_SUD:
                            imageView_Sud.buildDrawingCache();
                            bitmap = Bitmap.createBitmap(imageView_Sud.getDrawingCache());
                            imageView_Sud.destroyDrawingCache();
                            intent.putExtra("Mur",ModeleSingleton.getInstance().getPieceEnCours().getMurSud().getNomBitmap());
                            intent.putExtra("Bitmap",bitmap);
                            break;
                    }
                    startActivity(intent);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void Valider(View view) {
        Log.i("PIECE ACTIVITY", ModeleSingleton.getInstance().getModeleInstance().getNom());
        Log.i("PIECE ACTIVITY", String.valueOf(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size()));
        if(!pieceExistant)
            ModeleSingleton.getInstance().getModeleInstance().ajouterPiece(ModeleSingleton.getInstance().getPieceEnCours());
        Log.i("PIECE ACTIVITY", ModeleSingleton.getInstance().getModeleInstance().getNom());
        Log.i("PIECE ACTIVITY", String.valueOf(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size()));
        finish();
    }
    public void showImageOnCreate(){
        File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
        FileInputStream fis = null;
        try {
            File filesDir = getFilesDir();
            Log.i("PIECE ACTIVITY", "Chemin du dossier : " + filesDir.getAbsolutePath());
            if(piece.getMurNord().getNomBitmap() != null){
                Log.i("PIECE ACTIVITY",piece.getMurNord().getNomBitmap() );
                String chemin = dossier.getAbsolutePath()+"/"+piece.getMurNord().getNomBitmap();
                fis = new FileInputStream(new File(chemin));
                Bitmap bm = BitmapFactory.decodeStream(fis);
                imageView_Nord.setImageBitmap(bm);
            }
            if(piece.getMurEst().getNomBitmap() != null){
                Log.i("PIECE ACTIVITY",piece.getMurEst().getNomBitmap() );
                String chemin = dossier.getAbsolutePath()+"/"+piece.getMurEst().getNomBitmap();
                fis = new FileInputStream(new File(chemin));
                Bitmap bm = BitmapFactory.decodeStream(fis);
                imageView_Est.setImageBitmap(bm);
            }
            if(piece.getMurOuest().getNomBitmap() != null){
                Log.i("PIECE ACTIVITY",piece.getMurOuest().getNomBitmap() );
                String chemin = dossier.getAbsolutePath()+"/"+piece.getMurOuest().getNomBitmap();
                fis = new FileInputStream(new File(chemin));
                Bitmap bm = BitmapFactory.decodeStream(fis);
                imageView_Ouest.setImageBitmap(bm);
            }
            if(piece.getMurSud().getNomBitmap() != null){
                Log.i("PIECE ACTIVITY",piece.getMurSud().getNomBitmap() );
                String chemin = dossier.getAbsolutePath()+"/"+piece.getMurSud().getNomBitmap();
                fis = new FileInputStream(new File(chemin));
                Bitmap bm = BitmapFactory.decodeStream(fis);
                imageView_Sud.setImageBitmap(bm);
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }    
    public void showImage(Mur m){
        FileInputStream fis = null;
        Intent intent = new Intent(this, OuvertureActivity.class);

        try {
            File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
            String chemin = dossier.getAbsolutePath() + "/" + m.getNomBitmap();

            fis = new FileInputStream(new File(chemin));

            Bitmap bm = BitmapFactory.decodeStream(fis);

            switch (m.getOrientation()) {
                case EST:
                    imageView_Est.setImageBitmap(bm);
                    break;
                case SUD:
                    imageView_Sud.setImageBitmap(bm);
                    break;
                case NORD:
                    imageView_Nord.setImageBitmap(bm);
                    break;
                case OUEST:
                    imageView_Ouest.setImageBitmap(bm);
                    break;
            }

            intent.putExtra("Bitmap", bm);
            intent.putExtra("Mur", m.getNomBitmap());
            startActivity(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitMap = (Bitmap) extras.get("data");
            FileOutputStream fos;
            String name =ModeleSingleton.getInstance().getModeleInstance().getNom();
            Mur mur = new Mur();
            try {
                switch (requestCode){
                    case PHOTO_NORD:
                        name += piece.getNom()+"_Nord";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.NORD);
                        piece.setMurNord(mur);
                        ModeleSingleton.getInstance().getPieceEnCours().setMurNord(mur);
                        break;
                    case PHOTO_EST:
                        name += piece.getNom()+"_Est";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.EST);
                        ModeleSingleton.getInstance().getPieceEnCours().setMurEst(mur);
                        piece.setMurEst(mur);
                        break;
                    case PHOTO_OUEST:
                        name += piece.getNom()+"_Ouest";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.OUEST);
                        ModeleSingleton.getInstance().getPieceEnCours().setMurOuest(mur);
                        piece.setMurOuest(mur);
                        break;
                    case PHOTO_SUD:
                        name += piece.getNom()+"_Sud";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.SUD);
                        ModeleSingleton.getInstance().getPieceEnCours().setMurSud(mur);
                        piece.setMurSud(mur);
                        break;
                }
                File dossier = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "DossierJSON");
                fos = new FileOutputStream(new File(dossier, name));
                imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                Log.i("PIECE ACTIVITY",mur.toString());
                showImage(mur);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void isTelMoving(float accelx, float accely, float accelz){
        double omegaMagnitude = Math.sqrt(accelx * accelx + accely * accely +accelz * accelz );
        double gravityMagnitude = SensorManager.STANDARD_GRAVITY;
        double tolerance = 0.2;
        if(Math.abs(omegaMagnitude - gravityMagnitude) < tolerance){
            Log.i("Capteur : ","IMMOBILE");
        }else {
            Log.i("Capteur : ","MOBILE");
        }
    }
    public void switchOnClick(View view){
        if(isSensorEnabled){
            sensorManager.unregisterListener(this);
            capteur.setText("Capteur Désactivé");
            isSensorEnabled = false;
        }else{
            sensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, magnetometre, SensorManager.SENSOR_DELAY_NORMAL);
            capteur.setText("Capteur Activé");
            isSensorEnabled = true;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorEnabled) {
            sensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, magnetometre, SensorManager.SENSOR_DELAY_NORMAL);
            handler.postDelayed(updateViewRunnable, FPS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorEnabled) {
            sensorManager.unregisterListener(this);
            handler.removeCallbacks(updateViewRunnable);

        }
    }

    private final Runnable updateViewRunnable = new Runnable() {
        @Override
        public void run() {
            // Rafraichissement de la vue selon les fps
            accelVectorView.invalidate();
            handler.postDelayed(this, FPS);
        }
    };

    public void capteurOnClick(View view) {
        switchOnClick(view);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == accelerometre) {
            Log.i("Capteur", event.sensor.toString());
            float accelx = event.values[0];
            float accely = event.values[1];
            float accelz = event.values[2];
            accel = event.values;
            accelVectorView.setAccelXY(accel);
            isTelMoving(accelx, accely, accelz);
        }
        if (event.sensor == magnetometre) {
            magneto = event.values;
            SensorManager.getRotationMatrix(matriceR,null,accel,magneto);
            SensorManager.getOrientation(matriceR,orientation);
            accelVectorView.setOrientation(orientation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}