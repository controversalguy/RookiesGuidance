package pt.isec.gps.rookiesguidance.model.data.infodata;

import java.util.ArrayList;

public class Curso {

    private String nome;
    private ArrayList<Cadeira> cadeiras;

    public String getNome() {
        return nome;
    }



    public ArrayList<Cadeira> getCadeiras() {
        return cadeiras;
    }
}
