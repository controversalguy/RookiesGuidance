package pt.isec.gps.rookiesguidance.model.data;

import pt.isec.gps.rookiesguidance.model.data.infodata.Curso;

import java.util.Objects;

public class Utilizador {

    private boolean isGestor;
    private long id; // id do aluno- 2019133920
    private String nome;    //nome do aluno
    private String email;   // email do aluno
    private Curso curso;   // curso do aluno
    private String password;

    public Utilizador(boolean isGestor, long id, String nome, String email, Curso curso) {
        this.isGestor = isGestor;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    public boolean isGestor() {
        return isGestor;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmai() {
        return email;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Utilizador)) return false;
        Utilizador user = (Utilizador) o;
        return Objects.equals(id,user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return
                "nrAluno:" + id +
                ", nomeAluno:'" + nome +
                ", emailAluno:'" + email +
                ", curso:'" + curso ;
    }
}
