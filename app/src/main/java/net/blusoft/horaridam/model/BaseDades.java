package net.blusoft.horaridam.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by IES on 30/11/2016.
 */

public class BaseDades extends SQLiteOpenHelper {
    private static final int idPati=0;
    private static final int idM07=1;
    private static final int idTutoria=2;
    private static final int idM03=3;
    private static final int idM10=4;
    private static final int idM020506=5;
    private static final int idM09=6;
    private static final int idM08=7;
    private static final int DILLUNS=1;
    private static final int DIMARTS=2;
    private static final int DIMECRES=3;
    private static final int DIJOUS=4;
    private static final int DIVENDRES=5;




    String sqlCreateHorario = "CREATE TABLE horario (grup TEXT, idHorari INTEGER PRIMARY KEY AUTOINCREMENT, codAsignatura INTEGER, horaInicio TEXT, horaFin TEXT, diaSetmana INTEGER)";
    String sqlCreateModulos = "CREATE TABLE modulos (codAsignatura TEXT, nomAssignatura, nomProfe text)";

    String[] sqlOmplirHorario = {
            //Tots els dies el pati es a la mateixa hora
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idPati+",'18:00','18:19',"+DILLUNS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idPati+",'18:00','18:19',"+DIMARTS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idPati+",'18:00','18:19',"+DIMECRES+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idPati+",'18:00','18:19',"+DIJOUS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idPati+",'18:00','18:19',"+DIVENDRES+")",

            //Horaris Segon DAM 2016-2017

            //Dilluns
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
            "values ('A',"+idM07+",'15:00','17:59',"+DILLUNS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idTutoria+",'18:20','19:19',"+DILLUNS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana)" +
                    " values ('A2',"+idM03+",'19:20','21:19',"+DILLUNS+")",

            //Dimarts
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A1',"+idM03+",'15:00','16:59',"+DIMARTS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A2',"+idM08+",'15:00','16:59',"+DIMARTS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idM10+",'17:00','17:59',"+DIMARTS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idM10+",'18:20','19:19',"+DIMARTS+")",
            "INSERT INTO horario (grup, codAsignatura, horaInicio, horaFin, diaSetmana) " +
                    "values ('A',"+idM020506+",'19:20','21:19',"+DIMARTS+")",

            //Dimecres
            //"INSERT INTO horario values ('A',"+idM020506+",'16:00','16:59',"+DIMECRES+")",
            //"INSERT INTO horario values ('A2',"+idM08+",'17:00','17:59',"+DIMECRES+")",
            //"INSERT INTO horario values ('A2',"+idM08+",'18:20','19:19',"+DIMECRES+")",
            //"INSERT INTO horario values ('A2',"+idM09+",'19:20','20:19',"+DIMECRES+")"

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
        for (int i = 0; i <sqlOmplirHorario.length ; i++) {
            db.execSQL(sqlOmplirHorario[i]);
        }
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
        for (int j = 0; j <sqlOmplirHorario.length ; j++) {
            db.execSQL(sqlOmplirHorario[j]);
        }
    }
}
