package net.blusoft.horaridam.controlers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.blusoft.horaridam.R;
import net.blusoft.horaridam.model.Assignatura;
import net.blusoft.horaridam.model.BaseDades;
import net.blusoft.horaridam.model.Usuari;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalendariActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;
    SharedPreferences prefs;
    BaseDades db;
    SQLiteDatabase bbdd;
    Assignatura assignatura = null;
    Usuari usuari;
    boolean inicialitzat,pause = false;

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("OnPause");
        pause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("OnResume");
        pause = false;
        /**It's important that we have the call to the runnable (the inicia() method) on the
         * onResume method, because doing it this way we can stop the GUI update when the APP
         * enters the onPause state.
         */
        inicia();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preferences, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("MisPreferencias", this.MODE_PRIVATE);
        //Define the APP theme.
        switch (prefs.getInt("tema", 0)) {
            case 0:
                setTheme(android.R.style.Theme_Material_Light_NoActionBar);
                break;
            case 1:
                setTheme(android.R.style.Theme_Material_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari);
        usuari = new Usuari(prefs.getString("nom", ""), prefs.getInt("grup", 3));
        db = new BaseDades(this, "horariosDAM", null, BaseDades.VERSIO_DB);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText(getString(R.string.hola_nom, usuari.getNom()));
    }

    /**
     * This is the core of the APP. It checks the current class, updates the GUI and creates
     * a new Runnable to update the app every second. If the class ends, we make a query to the DDBB
     * to check for our next class, and update all the gui, if the class have not ended yet, and we
     * updated the GUI before (inicialitzat=true) we only update the progressBar.
     *
     * This way we try to achieve to waste the minimum CPU cycles
     */
    private void inicia() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Calendar c = Calendar.getInstance();
                bbdd = db.getReadableDatabase();
                tv2 = (TextView) findViewById(R.id.tv2);
                tv3 = (TextView) findViewById(R.id.tv3);
                tv4 = (TextView) findViewById(R.id.tv4);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
                if (assignatura == null) {
                    assignatura = db.getAssignatura(usuari.getGrup(), bbdd);
                    //TODO remove this debug sout in deploy APP
                    System.out.println("Encara no tens classe...");
                    tv2.setText(getString(R.string.no_tens_classe));
                    tv3.setVisibility(View.INVISIBLE);
                    tv4.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    Date horaActual = null;
                    try {
                        horaActual = formatter.parse((c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (horaActual.after(assignatura.getHoraFi())) {
                        assignatura = db.getAssignatura(usuari.getGrup(), bbdd);
                    } else {
                        if (!inicialitzat) {
                            tv2.setText(getString(R.string.ara_toca_assignatura, assignatura.getNomModul()));
                            progressBar.setVisibility(View.VISIBLE);
                            tv4.setVisibility(View.VISIBLE);
                            if (assignatura.getNomModul().equals("Pati")) {
                                tv3.setVisibility(View.INVISIBLE);
                            } else {
                                tv3.setVisibility(View.VISIBLE);
                                tv3.setText(getString(R.string.amb_el_professor, assignatura.getProfessor()));
                            }
                            //TODO remove this debug sout in deploy APP
                            System.out.println("actualiza GUI");
                            inicialitzat=true;
                        }
                        tv4.setText(getString(R.string.fins_les_xxxxx, formatter.format(assignatura.getHoraFi())));
                        long progress = getProgress(assignatura, horaActual);
                        progressBar.setProgress((int) progress);
                    }
                }
                //Shown the current time in a TextView
                TextView tv5 = (TextView) findViewById(R.id.tv5);
                tv5.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
                //
                if (!pause) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(r, 1000);
    }

    /**
     * This function returns a long with the current progress of the assignatura passed. Ranges between 0 an 100
     *
     * @param assignatura Current assignatura
     * @param horaActual  The current time
     * @return
     */
    private long getProgress(Assignatura assignatura, Date horaActual) {
        long tempsTotal = assignatura.getHoraFi().getTime() - assignatura.getHoraInici().getTime();
        long progress = (horaActual.getTime() - assignatura.getHoraInici().getTime()) * 100 / tempsTotal;
        return progress;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_preferences:
                Intent intent = new Intent(CalendariActivity.this, PreferencesActivity.class);
                intent.putExtra("mostrarPreferencies", true);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
