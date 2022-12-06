package pt.isec.gps.rookiesguidance.model.data;

import java.util.Objects;

public class Novidade {

    private String titulo;
    private String descricao;
    private int id;
    private static int count = 0;

    public Novidade(String titulo, String descricao) {
        id = count++;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Novidade)) return false;
        Novidade nov = (Novidade) o;
        return Objects.equals(id,nov.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return
                "titulo:'" + titulo +
                ", descricao:'" + descricao +
                ", id:" + id ;
    }
}
