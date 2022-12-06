package pt.isec.gps.rookiesguidance.model.data.infodata;

import java.util.ArrayList;

public class Informacao {

    private ArrayList<Curso> cursos;
    private ArrayList<String> localAlimentacao;
    private ArrayList<String> localEstudo;

    public Informacao() {
        this.cursos = new ArrayList<>();
        this.localAlimentacao = new ArrayList<>();
        this.localEstudo = new ArrayList<>();
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public ArrayList<String> getLocalA() {

        return localAlimentacao;

    }

    public ArrayList<String> getLocalE() {
        return localEstudo;
    }

}
