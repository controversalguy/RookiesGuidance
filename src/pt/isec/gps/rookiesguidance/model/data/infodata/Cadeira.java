package pt.isec.gps.rookiesguidance.model.data.infodata;

import java.util.ArrayList;

public class Cadeira {

    private ArrayList<String> professores;
    private ArrayList<String> salas;
    private String nome;
    private int ano;
    public int getAno() {
        return ano;
    }
    public ArrayList<String> getProfessores() {
        return professores;
    }

    public ArrayList<String> getSalas() {
        return salas;
    }

    public String getNome() {
        return nome;
    }
}
