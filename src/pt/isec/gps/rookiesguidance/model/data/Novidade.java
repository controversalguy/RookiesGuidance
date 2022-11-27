package pt.isec.gps.rookiesguidance.model.data;

public class Novidade {

    private String titulo;
    private String descricao;
    private int id;

    public Novidade(String titulo, String descricao, int id) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.id = id;
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
    public String toString() {
        return
                "titulo:'" + titulo +
                ", descricao:'" + descricao +
                ", id:" + id ;
    }
}
