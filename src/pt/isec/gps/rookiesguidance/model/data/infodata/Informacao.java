package pt.isec.gps.rookiesguidance.model.data.infodata;

import java.util.ArrayList;

public class Informacao {

    private ArrayList<Curso> cursos;
    private ArrayList<String> localAlimentacao;
    private ArrayList<String> localEstudo;

    public Informacao(ArrayList<Curso> cursos, ArrayList<String> localAlimentacao, ArrayList<String> localEstudo) {
        this.cursos = cursos;
        this.localAlimentacao = new ArrayList<>();
        this.localEstudo = new ArrayList<>();
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public ArrayList<String> getLocalAlimentacao() {
        return localAlimentacao;
    }

    public ArrayList<String> getLocalEstudo() {
        return localEstudo;
    }

}
