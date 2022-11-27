package pt.isec.gps.rookiesguidance.model.data;

import java.util.ArrayList;

public class Pergunta {
    private int id;
    ArrayList<String> respostas;
    private String pergunta;

    public ArrayList<String> getRespostas() {
        return respostas;
    }

    public String getPergunta() {
        return pergunta;
    }
}
