package com.example.das_wannafood.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.das_wannafood.fragments.Preferencias;
import com.example.das_wannafood.R;

import java.util.Locale;

public class GestionPreferencias extends AppCompatActivity implements Preferencias.ListenerPreferencias {

    private Preferencias preferencias;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        // Acceder a las preferencias para conseguir el valor de la clave del idioma
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma = prefs.getString("idioma", "es");

        // Cambiar el idioma
        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        setContentView(R.layout.preferencias);
        preferencias = (Preferencias) getSupportFragmentManager().findFragmentById(R.id.prefsFragment);
    }

    // Método de la clase Preferencias para cmabiar el idioma
    @Override
    public void cambiarIdioma() {
        // Cuando se detecte un cambio, se destruirá la actividad y se creará una nu    eva, de esta manera, se cambiará el idioma
        finish();
        startActivity(getIntent());
    }

    @Override
    public void accederURL() {
        Intent intentInfo = new Intent(Intent.ACTION_VIEW, Uri.parse(prefs.getString("urls", null)));
        startActivity(intentInfo);
    }

    // Método para usar un intent implícito para acceder a la página web de un restaurante

}