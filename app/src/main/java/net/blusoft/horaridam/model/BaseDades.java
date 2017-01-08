package net.blusoft.horaridam.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by IES on 30/11/2016.
 */

public class BaseDades extends SQLiteOpenHelper {
    private static final int idPati = 0;
    private static final int idM07 = 1;
    private static final int idTutoria = 2;
    private static final int idM03 = 3;
    private static final int idM10 = 4;
    private static final int idM020506 = 5;
    private static final int idM09 = 6;
    private static final int idM08 = 7;
    private static final int DILLUNS = 1;
    private static final int DIMARTS = 2;
    private static final int DIMECRES = 3;
    private static final int DIJOUS = 4;
    private static final int DIVENDRES = 5;
    private static final int GRUP_A = 3;
    private static final int GRUP_A1 = 0;
    private static final int GRUP_A2 = 1;
    public static final int VERSIO_DB=16;




    private String sqlCreateHorario = "CREATE TABLE horario (grup TEXT, idHorari INTEGER PRIMARY KEY AUTOINCREMENT, codAsignatura INTEGER, horaInicio TIME, horaFin TIME, diaSetmana INTEGER)";
    private String sqlCreateModulos = "CREATE TABLE modulos (codAsignatura TEXT, nomAssignatura, nomProfe text)";

    private String sqlOmplirModulos= "INSERT INTO modulos (codAsignatura, nomAssignatura, nomProfe) values" +
                                "(0,'Pati',''),(1,'M07 - Desenvolupament dinterficies', 'Leo')," +
                                "(2,'Tutoria','Josefa'),(3,'M03 - Programacio', 'Josefa')," +
                                "(4,'M10 - Sistemes de Gestio Empresarial','Marta'),"+
                                "(6,'Programació de Serveis i processos','Jorge'),(7,'M08 Prog. Multimedia i disp. Movils', 'Lluis')," +
                                "(5,'M05-M02-M06 Entorns de Desenvolupament - Bases de dades - Acces a dades', 'Jorge')" ;


    private String[] sqlOmplirHorario = {
            //Tots els dies el pati es a la mateixa hora
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A + "," + idPati + ",'18:00:00','18:19:59'," + DILLUNS + ") ," +
                    "(" + GRUP_A + "," + idPati + ",'18:00:00','18:19:59'," + DIMARTS + ") ," +
                    "(" + GRUP_A + "," + idPati + ",'18:00:00','18:19:59'," + DIMECRES + ")," +
                    "(" + GRUP_A + "," + idPati + ",'18:00:00','18:19:59'," + DIJOUS + ")," +
                    "(" + GRUP_A + "," + idPati + ",'18:00:00','18:19:59'," + DIVENDRES + ")",

            //Horaris Segon DAM 2016-2017

            //Dilluns
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A + "," + idM07 + ",'15:00:00','17:59:59'," + DILLUNS + ")," +
                    "(" + GRUP_A + "," + idTutoria + ",'18:00:00','19:19:59'," + DILLUNS + ")," +
                    "(" + GRUP_A2 + "," + idM03 + ",'19:00:00','21:19:59'," + DILLUNS + ")",

            //Dimarts
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A1 + "," + idM03 + ",'15:00:00','16:59:59'," + DIMARTS + ")," +
                    "(" + GRUP_A2 + "," + idM08 + ",'15:00:00','16:59:59'," + DIMARTS + ")," +
                    "(" + GRUP_A + "," + idM10 + ",'17:00:00','17:59:59'," + DIMARTS + ")," +
                    "(" + GRUP_A + "," + idM10 + ",'18:00:00','19:19:59'," + DIMARTS + ")," +
                    "(" + GRUP_A + "," + idM020506 + ",'19:00:00','21:19:59'," + DIMARTS + ")",

            //Dimecres
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A + "," + idM020506 + ",'16:00:00','16:59:59'," + DIMECRES + ")," +
                    "(" + GRUP_A1 + "," + idM09 + ",'17:00:00','17:59:59'," + DIMECRES + ")," +
                    "(" + GRUP_A2 + "," + idM08 + ",'17:00:00','17:59:59'," + DIMECRES + ")," +
                    "(" + GRUP_A1 + "," + idM09 + ",'18:0:00','19:19:59'," + DIMECRES + ")," +
                    "(" + GRUP_A2 + "," + idM08 + ",'18:0:00','19:19:59'," + DIMECRES + ")," +
                    "(" + GRUP_A1 + "," + idM03 + ",'19:0:00','20:19:59'," + DIMECRES + ")," +
                    "(" + GRUP_A2 + "," + idM09 + ",'19:0:00','20:19:59'," + DIMECRES + ")," +
                    "(" + GRUP_A1 + "," + idM03 + ",'20:0:00','21:19:59'," + DIMECRES + ")",

            //Dijous
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A1 + "," + idM09 + ",'15:00:00','15:59:59'," + DIJOUS + ")," +
                    "(" + GRUP_A1 + "," + idM08 + ",'16:00:00','16:59:59'," + DIJOUS + ")," +
                    "(" + GRUP_A2 + "," + idM03 + ",'16:00:00','16:59:59'," + DIJOUS + ")," +
                    "(" + GRUP_A1 + "," + idM08 + ",'17:00:00','17:59:59'," + DIJOUS + ")," +
                    "(" + GRUP_A2 + "," + idM03 + ",'17:00:00','17:59:59'," + DIJOUS + ")," +
                    "(" + GRUP_A1 + "," + idM020506 + ",'18:00:00','21:19:59'," + DIJOUS + ")",


            //Divendres
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) values " +
                    "(" + GRUP_A + "," + idM10 + ",'15:00:00','16:59:59'," + DIVENDRES + ")," +
                    "(" + GRUP_A1 + "," + idM08 + ",'17:00:00','17:59:59'," + DIVENDRES + ")," +
                    "(" + GRUP_A2 + "," + idM09 + ",'17:00:00','17:59:59'," + DIVENDRES + ")," +
                    "(" + GRUP_A1 + "," + idM08 + ",'18:00:00','19:19:59'," + DIVENDRES + ")," +
                    "(" + GRUP_A2 + "," + idM09 + ",'18:00:00','19:19:59'," + DIVENDRES + ")," +
                    "(" + GRUP_A + "," + idM020506 + ",'19:00:00','21:19:59'," + DIVENDRES + ")"
    };

    public BaseDades(Context contexto, String nombre,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Creando DB");
        db.execSQL(sqlCreateHorario);
        db.execSQL(sqlCreateModulos);
        for (String consulta : sqlOmplirHorario) {
            db.execSQL(consulta);
        }
        db.execSQL(sqlOmplirModulos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //NOT IMPLEMENTED
        System.out.println("Actualizando DB");
        String sqlRemoveHorario = "DROP TABLE horario";
        db.execSQL(sqlRemoveHorario);
        String sqlRemoveModulos = "DROP TABLE modulos";
        db.execSQL(sqlRemoveModulos);
        db.execSQL(sqlCreateHorario);
        db.execSQL(sqlCreateModulos);
        for (String consulta : sqlOmplirHorario) {
            db.execSQL(consulta);
        }
        db.execSQL(sqlOmplirModulos);
    }


    public Assignatura getAssignatura(int grup, SQLiteDatabase bbdd){
        Assignatura res=null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Date horaActual=null;
        try {
            horaActual=formatter.parse((c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql= "SELECT codAsignatura, horaInicio,horaFin FROM horario " +
                "WHERE (grup="+grup+" OR grup="+GRUP_A+") AND diaSetmana="+c.get(Calendar.DAY_OF_WEEK)+" AND '"+formatter.format(horaActual)+"' BETWEEN horaInicio AND horaFin";
        Cursor curs = bbdd.rawQuery(sql,null);
        if(curs.getCount()!=0) {
            curs.moveToFirst();
            String sql2 = "SELECT nomAssignatura,nomProfe FROM modulos WHERE codAsignatura=" + curs.getInt(0);
            Cursor d = bbdd.rawQuery(sql2, null);
            d.moveToFirst();
            Date horaInici = null;
            Date horaFi = null;
            try {
                horaInici = formatter.parse(curs.getString(1));
                horaFi = formatter.parse(curs.getString(2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            res = new Assignatura(d.getString(0), d.getString(1), horaInici, horaFi, horaActual);
            d.close();
        }
        curs.close();

        return res;
    }
}
