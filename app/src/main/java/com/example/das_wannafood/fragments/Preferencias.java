package com.example.das_wannafood.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.PreferenceFragmentCompat;

import com.example.das_wannafood.R;

public class Preferencias extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    ListenerPreferencias listenerPreferencias;

    public interface ListenerPreferencias {
        void cambiarIdioma();
    }

    // Cuando se crean las preferencias, se ejecutará este método
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.conf_preferencias);
        listenerPreferencias = (ListenerPreferencias) getActivity();
    }

    // Se ejecuta al detectar cambios en el fichero de preferencias
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Cuando se cambie el idioma se ejecutará el método en la otra actividad
        if("idioma".equals(s)){
            listenerPreferencias.cambiarIdioma();
        }
    }

    // Se registra el listener
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // Se quita el listener
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}