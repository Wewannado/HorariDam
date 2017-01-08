package net.blusoft.horaridam.model;

import java.util.Date;

/**
 * Created by IES on 08/01/2017.
 */

public class Assignatura {
    private String nomModul;
    private String professor;
    private Date horaInici;
    private Date horaFi;
    private Date horaActual;


    public Assignatura(String nomModul, String professor, Date horaInici, Date horaFi, Date horaActual){
        this.nomModul=nomModul;
        this.professor=professor;
        this.horaInici=horaInici;
        this.horaFi=horaFi;
        this.horaActual=horaActual;
    }

    public String getNomModul() {
        return nomModul;
    }

    public Date getHoraActual() {
        return horaActual;
    }

    public String getProfessor() {
        return professor;
    }

    public Date getHoraInici() {
        return horaInici;
    }

    public Date getHoraFi() {
        return horaFi;
    }
}
