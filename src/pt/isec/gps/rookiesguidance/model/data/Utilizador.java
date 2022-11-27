package pt.isec.gps.rookiesguidance.model.data;

public class Utilizador {

    private boolean isGestor;
    private long nrAluno; // id do aluno- 2019133920
    private String nomeAluno;    //nome do aluno
    private String emailAluno;   // email do aluno
    private String curso;   // curso do aluno
    private String password;

    public Utilizador(boolean isGestor, long nrAluno, String nomeAluno, String emailAluno, String curso) {
        this.isGestor = isGestor;
        this.nrAluno = nrAluno;
        this.nomeAluno = nomeAluno;
        this.emailAluno = emailAluno;
        this.curso = curso;
    }


    public boolean isGestor() {
        return isGestor;
    }

    public long getNrAluno() {
        return nrAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public String getCurso() {
        return curso;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return
                "nrAluno:" + nrAluno +
                ", nomeAluno:'" + nomeAluno +
                ", emailAluno:'" + emailAluno +
                ", curso:'" + curso ;
    }
}
