package pt.isec.gps.rookiesguidance.model.data;

import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;

public class GestaoData {
    ConnDB dbconn;


    public GestaoData() throws SQLException {
        dbconn = new ConnDB();
    }

    public boolean adicionaUtilizador(int nrAluno, String nome, String email, String password, String curso) throws SQLException {
        if (dbconn.registaNovoUtilizador(nrAluno, nome, email, password, curso))
            return true;

        return false;
    }

    public boolean adicionalocalA(String local,String tipo,int idGestor)throws SQLException {
        if(dbconn.addlocal(local,tipo,idGestor))
            return true;
        return false;
    }

    public boolean adicionalocalE(String local,String tipo,int idGestor)throws SQLException{
        if(dbconn.addlocal(local,tipo,idGestor))
            return true;
        return false;
    }

    public boolean removeLocalA(int pos,int idGestor)throws SQLException {
        if(dbconn.removelocal(pos,idGestor))
            return true;
        return false;
    }

    public boolean removeLocalE(int pos,int idGestor) throws SQLException{
        if(dbconn.removelocal(pos,idGestor))
            return true;
        return false;
    }

    public boolean adicionaNovidade(Novidade nov)throws SQLException{
        if(dbconn.adicionaNovidade(nov.getTitulo(), nov.getDescricao(),nov.getId()))
            return true;
        return false;
    }

    public boolean removeNovidade(Novidade nov,int idGestor)throws SQLException{
        if(dbconn.removeNovidade(nov.getId(),idGestor))
            return true;
        return false;
    }

    public boolean adicionaEvento(Evento ev,int idGestor)throws SQLException{
        if(dbconn.adicionaEvento(idGestor, ev.getTipo(),ev.getLocalizacao(), ev.getData()))
            return true;
        return false;
    }

    public boolean removeEvento(Evento ev,int idGestor)throws SQLException{
        if(dbconn.removeEvento(ev.getId(),idGestor))
            return true;
        return false;
    }

    public boolean editaEvento(int id,String campo,int tipo,int idGestor)throws SQLException{
        if(dbconn.editaEvento(id,campo,tipo,idGestor))
            return true;
        return false;
    }

    public boolean adicionaPergunta(Pergunta p,int idGestor)throws SQLException{
        if(dbconn.adicionaPergunta(p.getPergunta(),idGestor))
            return true;
        return false;
    }

    public boolean removePergunta(int pos,int idGestor)throws SQLException{
        if(dbconn.removePergunta(pos,idGestor))
            return true;
        return false;
    }

    public boolean adicionaResposta(int id, String resposta,int idGestor)throws SQLException{
        if(dbconn.adicionaResposta(resposta,id,idGestor))
            return true;
        return false;
    }

    public boolean inscreveEmEvento(long idUser, int idEvento) throws SQLException{
        if(dbconn.inscreveEmEvento(idUser,idEvento))
            return true;
        return false;
    }

    public boolean editaPassword(int idUser, String campo) throws SQLException {
        if(dbconn.editaUtilizador(idUser,campo, 0))
            return true;
        return false;
    }

    public boolean editaCurso(int idUser, String campo) throws SQLException {
        if(dbconn.editaUtilizador(idUser,campo, 1))
            return true;
        return false;
    }

    public boolean removePerfil(int idUser)throws SQLException {
        if(dbconn.removeUtilizador(idUser))
            return true;
        return false;
    }

}
