package com.example.creationmodele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import Outils.AccelVectorView;

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
    }

    public void ajouterImageOuest(View view) {
    }

    public void ajouterImageEst(View view) {
    }

    public void ajouterImageNord(View view) {
    }

    public void ajouterImageSud(View view) {
    }

    public void Valider(View view) {
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