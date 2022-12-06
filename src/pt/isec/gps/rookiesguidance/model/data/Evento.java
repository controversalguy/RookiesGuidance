package pt.isec.gps.rookiesguidance.model.data;

import jdk.jfr.Event;

import java.util.Objects;

public class Evento {

    private String tipo;
    private String localizacao;
    private String hora;
    private String data;
    private int id;

    private static int count = 0;

    public Evento(String tipo, String localizacao, String hora, String data){
        this.id = count++;
        this.tipo = tipo;

    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String local) {
        this.localizacao = local;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Novidade)) return false;
        Evento ev = (Evento) o;
        return Objects.equals(id,ev.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
