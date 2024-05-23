package com.example.exercicio5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PlanetFragment extends Fragment {

    private FrameLayout planetFrameLayout;
    private TextView planetNameTextView;
    private TextView travelCountTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout para o fragment
        View view = inflater.inflate(R.layout.planet_fragment, container, false);

        // Inicializar views
        planetFrameLayout = view.findViewById(R.id.planetBackground);
        planetNameTextView = view.findViewById(R.id.nomePlaneta);
        travelCountTextView = view.findViewById(R.id.contagemViagens);

        return view;
    }

    // Método para atualizar a UI do fragmento
    public void updatePlanetInfo(String planetName, int backgroundColor, int travelCount) {
        if (planetFrameLayout != null) {
            planetFrameLayout.setBackgroundColor(backgroundColor);
        }
        if (planetNameTextView != null) {
            planetNameTextView.setText(planetName);
        }
        if (travelCountTextView != null) {
            travelCountTextView.setText("Viagens realizadas: " + travelCount);
        }
    }
}