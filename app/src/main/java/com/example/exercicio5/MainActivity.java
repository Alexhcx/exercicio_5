package com.example.exercicio5;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MediaPlayer mediaPlayer;
    private PlanetFragment planetFragment;
    private int currentPlanetIndex = 0;
    private int travelCount = 0;

    // Nome dos planetas
    private final String[] planetNames = {"Terra", "Marte", "Jupiter", "Saturno"};
    private int[] planetColors;
    //Sons dos planetas presentes na pasta res/raw
    private final int[] planetSounds = {
            R.raw.terra_som,
            R.raw.marte_som,
            R.raw.jupiter_som,
            R.raw.saturno_som
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SensorManager and Accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Initialize PlanetFragment
        planetFragment = new PlanetFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.exibicaoFragment, planetFragment);
        fragmentTransaction.commit();

        planetColors = new int[] {
                ContextCompat.getColor(this, R.color.azul_terra),
                ContextCompat.getColor(this, R.color.vermelho_marte),
                ContextCompat.getColor(this, R.color.laranja_jupiter),
                ContextCompat.getColor(this, R.color.marrom_saturno)
        };

        // Set initial planet data
        updatePlanetData();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // condição para checar se o sensor é do tipo accelerometer
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Get accelerometer values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Detecta movimento baseando nos valores do accelerometer (Valores precisam ser ajustados)
            if (Math.abs(x) > 20 || Math.abs(y) > 20 || Math.abs(z) > 20) {
                travelToNewPlanet();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void travelToNewPlanet() {
        travelCount++;
        currentPlanetIndex = selectRandomPlanet();
        updatePlanetData();
        playPlanetSound();
    }

    private int selectRandomPlanet() {
        // Gera o index de forma randomica baseando no lenght da quantidade de planetas
        return (int) (Math.random() * planetNames.length);
    }

    private void updatePlanetData() {
        planetFragment.updatePlanetInfo(planetNames[currentPlanetIndex],
                planetColors[currentPlanetIndex],
                travelCount);
    }

    private void playPlanetSound() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        mediaPlayer = MediaPlayer.create(this, planetSounds[currentPlanetIndex]);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registra o listener do sensor
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancela o listener do sensor
        sensorManager.unregisterListener(this);
    }
}