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

public class Preferences extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;
    Spinner curs;
    Spinner grup;
    Spinner cicle;
    EditText nom;
    BaseDades db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Inicializando  DB");
        db = new BaseDades(this, "horariosDAM", null, 4);
        db.getWritableDatabase();
        prefs = getSharedPreferences("MisPreferencias", this.MODE_PRIVATE);
        if (prefs.getString("nom", "") != "") {
            goToCalendar();
        } else {
            refrescarPantalla();
        }
    }

    /**
     * This method creates an intent for the Calendar Activity and starts it with startActivityForResult
     * The destination activity can return a value with the request code 100
     * The results of the destination activity should be checked with onActivityResult method.
     */
    public void goToCalendar() {
        int REQUEST_CODE=100;
        Intent desti = new Intent(Preferences.this, Calendari.class);
        startActivityForResult(desti, REQUEST_CODE);
    }

    /**
     * This method fills the preference selectors, and sets the listeners
     */
    private void refrescarPantalla() {
        curs = (Spinner) findViewById(R.id.spinnerCurs);
        grup = (Spinner) findViewById(R.id.spinnerGrup);
        cicle = (Spinner) findViewById(R.id.spinnerCicle);
        nom = (EditText) findViewById(R.id.etNom);
        Button botoGuardar = (Button) findViewById(R.id.buttonGuardar);
        botoGuardar.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapterCurs = ArrayAdapter.createFromResource(this, R.array.curs, android.R.layout.simple_spinner_item);
        curs.setAdapter(adapterCurs);

        ArrayAdapter<CharSequence> adapterCicle = ArrayAdapter.createFromResource(this, R.array.cicle, android.R.layout.simple_spinner_item);
        cicle.setAdapter(adapterCicle);

        ArrayAdapter<CharSequence> adapterGrup = ArrayAdapter.createFromResource(this, R.array.grup, android.R.layout.simple_spinner_item);
        grup.setAdapter(adapterGrup);

        //Fill the options there is a SharedPreference File, otherwise, put some default values.
        curs.setSelection(prefs.getInt("curs", 0));
        cicle.setSelection(prefs.getInt("cicle", 0));
        grup.setSelection(prefs.getInt("grup", 0));
        nom.setText(prefs.getString("nom", ""));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonGuardar) {
            if (nom.getText().toString().equals("")) {
                Toast.makeText(this, R.string.name_required, Toast.LENGTH_LONG).show();
            } else {
                SharedPreferences.Editor editor = prefs.edit();

                ///  getResources().getStringArray(R.array.curs)[0]
                editor.putString("nom", nom.getText().toString());
                editor.putInt("curs", curs.getSelectedItemPosition());
                editor.putInt("cicle", cicle.getSelectedItemPosition());
                editor.putInt("grup", grup.getSelectedItemPosition());
                editor.commit();
                goToCalendar();
            }
        }
    }

    /**
     *
     * @param requestCode The request code of the intent launched with StartActivityFoResult
     * @param resultCode The result of the destination activity.
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                // Retornem ok quant volem mostrar les preferencies.
                refrescarPantalla();
            } else {
                //si s'ha retornat de l'activity amb qualsevol altre valor, sortim de l'app
                //Aixo significa que la tecla de tornar a la segona activity finalitzara l'aplicació.
                finish();
            }
        }
    }
}
