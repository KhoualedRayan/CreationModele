package com.example.creationmodele;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Habitation.Modele;
import Habitation.Mur;
import Habitation.Orientation;
import Habitation.Piece;
import Outils.AccelVectorView;
import Outils.ModeleSingleton;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometre = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        capteur = findViewById(R.id.capteur_switch);
        accelVectorView = findViewById(R.id.accelVectorView);
        matriceR = new float[9];
        accel = new float[3];
        magneto = new float[3];
        orientation = new float[3];
        String nomPiece = "null";
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            nomPiece =(String) b.get("nomPiece");
        }
        piece = new Piece(nomPiece);
        Log.i("PIECE ACTIVITY",nomPiece);
        imageView_Nord = findViewById(R.id.imageView_Nord);
        imageView_Sud = findViewById(R.id.imageView_Sud);
        imageView_Est = findViewById(R.id.imageView_Est);
        imageView_Ouest = findViewById(R.id.imageView_Ouest);

        //showImageOnCreate();
    }

    public void ajouterImageOuest(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (intent.resolveActivity(getPackageManager())!=null) {
            Toast.makeText(PieceActivity.this, "Photo du mur OUEST", Toast.LENGTH_SHORT).show() ;
            startActivityForResult(intent,PHOTO_OUEST);
            setResult(RESULT_OK,intent);
        }
    }

    public void ajouterImageEst(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (intent.resolveActivity(getPackageManager())!=null) {
            Toast.makeText(PieceActivity.this, "Photo du mur EST", Toast.LENGTH_SHORT).show() ;
            startActivityForResult(intent,PHOTO_EST);
            setResult(RESULT_OK,intent);
        }
    }

    public void ajouterImageNord(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (intent.resolveActivity(getPackageManager())!=null) {
            Toast.makeText(PieceActivity.this, "Photo du mur NORD", Toast.LENGTH_SHORT).show() ;
            startActivityForResult(intent,PHOTO_NORD);
            setResult(RESULT_OK,intent);
        }
    }

    public void ajouterImageSud(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        if (intent.resolveActivity(getPackageManager())!=null) {
            Toast.makeText(PieceActivity.this, "Photo du mur SUD", Toast.LENGTH_SHORT).show() ;
            startActivityForResult(intent,PHOTO_SUD);
            setResult(RESULT_OK,intent);
        }
    }

    public void Valider(View view) {
        Log.i("PIECE ACTIVITY", ModeleSingleton.getInstance().getModeleInstance().getNom());
        Log.i("PIECE ACTIVITY", String.valueOf(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size()));
        ModeleSingleton.getInstance().getModeleInstance().ajouterPiece(piece);
        Log.i("PIECE ACTIVITY", ModeleSingleton.getInstance().getModeleInstance().getNom());
        Log.i("PIECE ACTIVITY", String.valueOf(ModeleSingleton.getInstance().getModeleInstance().getPieceArrayList().size()));
        finish();
    }
    public void showImageOnCreate(){
    }    
    public void showImage(Mur m){
        FileInputStream fis = null;
        try {
            fis = openFileInput(m.getNomBitmap());
            Bitmap bm = BitmapFactory.decodeStream(fis);
            switch (m.getOrientation()){
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
            String name;
            Mur mur = new Mur();
            try {
                switch (requestCode){
                    case PHOTO_NORD:
                        name = piece.getNom()+"_Nord";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.NORD);
                        piece.setMurNord(mur);
                        fos = openFileOutput(name, MODE_PRIVATE);
                        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        break;
                    case PHOTO_EST:
                        name = piece.getNom()+"_Est";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.EST);
                        piece.setMurEst(mur);
                        fos = openFileOutput(name, MODE_PRIVATE);
                        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        break;
                    case PHOTO_OUEST:
                        name = piece.getNom()+"_Ouest";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.OUEST);
                        piece.setMurOuest(mur);
                        fos = openFileOutput(name, MODE_PRIVATE);
                        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        break;
                    case PHOTO_SUD:
                        name = piece.getNom()+"_Sud";
                        mur.setNomBitmap(name);
                        mur.setOrientation(Orientation.SUD);
                        piece.setMurSud(mur);
                        fos = openFileOutput(name, MODE_PRIVATE);
                        imageBitMap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        break;

                }
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