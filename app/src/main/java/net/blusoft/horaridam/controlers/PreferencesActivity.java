package net.blusoft.horaridam.controlers;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.blusoft.horaridam.R;
import net.blusoft.horaridam.model.BaseDades;

public class PreferencesActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;
    Spinner curs,grup,cicle,tema;
    EditText nom;
    BaseDades db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        //Define the APP theme.
        switch(prefs.getInt("tema",0)){
            case 0:
                setTheme(android.R.style.Theme_Material_Light_NoActionBar);
                break;
            case 1:
                setTheme(android.R.style.Theme_Material_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Inicializando  DB");
        db = new BaseDades(this, "horariosDAM", null, BaseDades.VERSIO_DB);
        db.getWritableDatabase();
        boolean mostrarPreferencias=false;
        if(getIntent().getExtras()!=null) {
            Bundle extras = getIntent().getExtras();
            System.out.println("Get Extras");
            if(extras.getBoolean("mostrarPreferencies")){
                System.out.println("Mostrar preferencies true");
                mostrarPreferencias=true;
            }
        }
        System.out.println(mostrarPreferencias);
        if (!prefs.getString("nom", "").equals("") && !mostrarPreferencias) {
            System.out.println("Preferencias ya guardadas, redirigimos a calendario");
            goToCalendar();
        } else {
            System.out.println("Preferencias no guardadas, a la espera de introduccion por parte de usuario.");
            refrescarPantalla();
        }
    }

    /**
     * This method creates an intent for the Calendar Activity and starts it with startActivityForResult
     * The destination activity can return a value with the request code 100
     * The results of the destination activity should be checked with onActivityResult method.
     */
    public void goToCalendar() {
        Intent desti = new Intent(PreferencesActivity.this, CalendariActivity.class);
        startActivity(desti);
        finish();
    }

    /**
     * This method fills the preference selectors, and sets the listeners
     */
    private void refrescarPantalla() {
        curs = (Spinner) findViewById(R.id.spinnerCurs);
        grup = (Spinner) findViewById(R.id.spinnerGrup);
        cicle = (Spinner) findViewById(R.id.spinnerCicle);
        nom = (EditText) findViewById(R.id.etNom);
        tema = (Spinner) findViewById(R.id.spinnerTema);
        Button botoGuardar = (Button) findViewById(R.id.buttonGuardar);
        botoGuardar.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterCurs = ArrayAdapter.createFromResource(this, R.array.curs, android.R.layout.simple_spinner_item);
        curs.setAdapter(adapterCurs);

        ArrayAdapter<CharSequence> adapterCicle = ArrayAdapter.createFromResource(this, R.array.cicle, android.R.layout.simple_spinner_item);
        cicle.setAdapter(adapterCicle);

        ArrayAdapter<CharSequence> adapterGrup = ArrayAdapter.createFromResource(this, R.array.grup, android.R.layout.simple_spinner_item);
        grup.setAdapter(adapterGrup);

        ArrayAdapter<CharSequence> adapterTema = ArrayAdapter.createFromResource(this, R.array.tema, android.R.layout.simple_spinner_item);
        tema.setAdapter(adapterTema);

        //Fill the options there is a SharedPreference File, otherwise, put some default values.
        curs.setSelection(prefs.getInt("curs", 0));
        cicle.setSelection(prefs.getInt("cicle", 0));
        grup.setSelection(prefs.getInt("grup", 0));
        nom.setText(prefs.getString("nom", ""));
        tema.setSelection(prefs.getInt("tema",0));
    }

    /**
     * Override of the onClick Method that handles the click of the save button.
     * When clicked, saves the preferences and sends the user to the second activity.
     * @param view The View that we're listenning
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonGuardar) {
            System.out.println("pulsado el boton guardar");
            if (nom.getText().toString().equals("")) {
                System.out.println("No se ha introducido un nombre valido");
                Toast.makeText(this, R.string.name_required, Toast.LENGTH_LONG).show();
            } else {
                System.out.println("Guardamos las preferencias");
                SharedPreferences.Editor editor = prefs.edit();
                ///  getResources().getStringArray(R.array.curs)[0]
                editor.putString("nom", nom.getText().toString());
                editor.putInt("curs", curs.getSelectedItemPosition());
                editor.putInt("cicle", cicle.getSelectedItemPosition());
                editor.putInt("grup", grup.getSelectedItemPosition());
                editor.putInt("tema", tema.getSelectedItemPosition());
                editor.apply();
                goToCalendar();
            }
        }
    }
}
