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

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendariActivity extends AppCompatActivity{
    TextView tv1, tv2, tv3, tv4;
    String usuari;
    int grup;
    SharedPreferences prefs;
    BaseDades db;
    SQLiteDatabase bbdd;

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
        switch(prefs.getInt("tema",0)){
            case 0:
                setTheme(android.R.style.Theme_Material_Light_NoActionBar);
                break;
            case 1:
                setTheme(android.R.style.Theme_Material_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendari);
        final Usuari usuari=new Usuari(prefs.getString("nom", ""),prefs.getInt("grup", 3)) ;
        db = new BaseDades(this, "horariosDAM", null, BaseDades.VERSIO_DB);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText(getString(R.string.hola_nom, usuari.getNom()));

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Calendar c = Calendar.getInstance();
                bbdd = db.getReadableDatabase();
                Assignatura assignatura=db.getAssignatura(usuari.getGrup(),bbdd);
                tv2 = (TextView) findViewById(R.id.tv2);
                tv3 = (TextView) findViewById(R.id.tv3);
                tv4 = (TextView) findViewById(R.id.tv4);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
                if(assignatura==null){
                    tv2.setText(getString(R.string.no_tens_classe));
                    tv3.setVisibility(View.INVISIBLE);
                    tv4.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else{
                    tv2.setText(getString(R.string.ara_toca_assignatura, assignatura.getNomModul()));
                    progressBar.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                    if(assignatura.getNomModul().equals("Pati")){
                        tv3.setVisibility(View.INVISIBLE);
                    }
                    else {
                        tv3.setVisibility(View.VISIBLE);
                        tv3.setText(getString(R.string.amb_el_professor, assignatura.getProfessor()));
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    tv4.setText(getString(R.string.fins_les_xxxxx, formatter.format(assignatura.getHoraFi())));
                    long tempsTotal=assignatura.getHoraFi().getTime()-assignatura.getHoraInici().getTime();
                    long progress=(assignatura.getHoraActual().getTime()-assignatura.getHoraInici().getTime())*100/tempsTotal;
                    progressBar.setProgress((int)progress);
                }
                TextView tv5 = (TextView) findViewById(R.id.tv5);
                tv5.setText(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle item selection
            switch (item.getItemId()) {
                case R.id.menu_preferences:
                    Intent intent = new Intent(CalendariActivity.this,PreferencesActivity.class);
                    intent.putExtra("mostrarPreferencies", true);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
}
