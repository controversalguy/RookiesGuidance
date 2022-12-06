package pt.isec.gps.rookiesguidance.model.data;

import java.util.ArrayList;
import java.util.Objects;

public class Pergunta {
    private static int count = 0;
    private int id;
    ArrayList<String> respostas;
    private String pergunta;

    public Pergunta(ArrayList<String> respostas, String pergunta) {
        this.id = count++;
        this.respostas = respostas;
        this.pergunta = pergunta;
    }

    public ArrayList<String> getRespostas() {
        return respostas;
    }

    public String getPergunta() {
        return pergunta;
    }

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Pergunta)) return false;
        Pergunta p = (Pergunta) o;
        return Objects.equals(id,p.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
