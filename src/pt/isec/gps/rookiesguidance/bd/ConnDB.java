package pt.isec.gps.rookiesguidance.bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConnDB {

    public final String DATABASE = "jdbc:sqlite:Rookies.db";
    public final Connection dbConn;

    public ConnDB() throws SQLException {
        dbConn = DriverManager.getConnection(DATABASE);
        /*partitioncount=1
        minconnection=1
        maxconnection=1*/
    }

    public void close() throws SQLException {
        if (dbConn != null)
            dbConn.close();
    }

    public void criaTabelas() // feito
    {
        System.out.println("[INFO] A criar tabela nova...");
        try {
            Statement statement = dbConn.createStatement();

            String evento = """
                    CREATE TABLE IF NOT EXISTS evento
                    (id INTEGER NOT NULL,
                    tipo TEXT NOT NULL,
                    data_hora TEXT NOT NULL,
                    local TEXT NOT NULL,
                    id_gestor INTEGER NOT NULL)
                    """;

            String utilizador = """
                    CREATE TABLE IF NOT EXISTS utilizador
                    (numero INTEGER NOT NULL,
                    nome TEXT NOT NULL,
                    curso TEXT NOT NULL,
                    email TEXTO NOT NULL,
                    password TEXT NOT NULL,
                    isGestor INTEGER NOT NULL DEFAULT 0,
                    autenticado INTEGER NOT NULL DEFAULT 0)
                    """;
            String novidade = """
                    CREATE TABLE IF NOT EXISTS novidade
                    (id INTEGER NOT NULL,
                    titulo TEXT NOT NULL,
                    descricao TEXT NOT NULL,
                    id_gestor INTEGER NOT NULL)
                    """;

            String pergunta = """
                    CREATE TABLE IF NOT EXISTS pergunta
                    (id INTEGER NOT NULL,
                    texto TEXT NOT NULL,
                    id_utilizador INTEGER NOT NULL)
                    """;
            String resposta = """
                    CREATE TABLE IF NOT EXISTS resposta
                    (id INTEGER NOT NULL,
                    texto TEXT NOT NULL,
                    id_pergunta TEXT NOT NULL,
                    id_gestor INTEGER NOT NULL)
                     """;
            String evento_utilizador = """
                    CREATE TABLE IF NOT EXISTS evento_utilizador
                    (id_evento INTEGER NOT NULL,
                     id_utilizador INTEGER NOT NULL)
                     """;

            String local = """
                    CREATE TABLE IF NOT EXISTS local
                    (id INTEGER NOT NULL,
                     localizacao TEXT NOT NULL,
                     tipo TEXT NOT NULL,
                     id_gestor INTEGER NOT NULL)
                     """;

            statement.executeUpdate(evento);
            statement.executeUpdate(utilizador);
            statement.executeUpdate(novidade);
            statement.executeUpdate(pergunta);
            statement.executeUpdate(resposta);
            statement.executeUpdate(evento_utilizador);
            statement.executeUpdate(local);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeRegisto(int nrAluno) throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM utilizador WHERE numero="+nrAluno;
        ResultSet rs = statement.executeQuery(verificaExistente);
        if(rs.next()){
            statement.executeUpdate("DELETE FROM utilizador WHERE numero="+nrAluno);
            rs.close();
            return true;
        }

        rs.close();
        return false;
    } // feito

    public ArrayList<String> getUser(String emailUtilizador) throws SQLException {

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM utilizador WHERE email = '" + emailUtilizador + "'";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> user = new ArrayList<>();
        if (rs.next()){
            String nome = rs.getString("nome");
            String curso = rs.getString("curso");
            String passe = rs.getString("password");
            user.add(nome);
            user.add(curso);
            user.add(passe);
        }

        /*if(novidades.size() == 0)
            return null;*/

        rs.close();
        statement.close();
        return user;
    }
    public boolean registaNovoUtilizador(int nrAluno, String nome, String curso, String email, String password) throws SQLException {
        if (nome == null || email == null || password == null || curso == null)
            return false;

            int isGestor = 0;
            Statement statement = dbConn.createStatement();
            String verificaExistente = "SELECT * FROM utilizador";
            String nrAlunoString = String.valueOf(nrAluno);
            String teste = null;
            for (int i = 0; i < 4; i++) {
                if(i==0){
                    teste = String.valueOf(nrAlunoString.charAt(i));
                    continue;
                }
                teste += String.valueOf(nrAlunoString.charAt(i));
            }

            int id = Integer.parseInt(teste);
            if (id < 2022) {
                isGestor = 1;
            }

            verificaExistente += " WHERE numero='" + nrAluno + "'";
            ResultSet resultSet = statement.executeQuery(verificaExistente);
            if (!resultSet.next()) {
                String sqlQuery = "INSERT INTO utilizador VALUES ('" + nrAluno + "','" +  nome + "','" + curso + "','" +  email + "','" + password + "','" + isGestor + "','"
                        + 0 + "')";
                statement.executeUpdate(sqlQuery);
                statement.close();
                return true;
            }

            System.out.println("Já Existe um utilizador com esse Username e/ou nome");
            statement.close();
            return false;
    } // feito

    public boolean addlocal(String local, String tipo, long idGestor) throws SQLException {
        if (local.length() == 0 || tipo.length() == 0) {
            System.out.println("Erro ao adicionar novo local!");
            return false;
        }

        System.out.println("TIPO : " + tipo);

        Statement statement = dbConn.createStatement();
        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
        ResultSet rs = statement.executeQuery(verificaUtilizador);
        if (rs.next()) { // se existir e for gestor
            String verificaExistente = "SELECT * FROM local";
            verificaExistente += " WHERE localizacao= '" + local + "' AND tipo = '" + tipo + "'";
            ResultSet resultSet = statement.executeQuery(verificaExistente);
            if (!resultSet.next()) {
                String sqlQuery = "INSERT INTO local VALUES ((SELECT COUNT(*) FROM local),'" + local + "','" + tipo + "','" + idGestor + "')";
                statement.executeUpdate(sqlQuery);
                resultSet.close();
                statement.close();
                return true;
            }
            System.out.println("Já Existe este local");
        }
        System.out.println("Gestor não existe!");
        statement.close();
        return false;

    } // feito

    public ArrayList<String> getLocais() throws SQLException {

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM local";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> locais = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String localizacao = rs.getString("localizacao");
            String tipo = rs.getString("tipo");
            locais.add(id + " - " + localizacao);
            locais.add(tipo);
        }
        /*if(novidades.size() == 0)
            return null;*/
        return locais;
    }

    public ArrayList<String> getIdLocais() throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM local";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> ids = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            ids.add(String.valueOf(id));
        }
        /*if(novidades.size() == 0)
            return null;*/
        return ids;
    }

    public boolean removelocal(int id, int idGestor) throws SQLException {
            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero='" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaUtilizador);
            if (rs.next()) { // se existir e for gestor
                String verificaExistente = "SELECT * FROM local WHERE id='" + id + "' AND id_gestor = '" + idGestor + "'";
                ResultSet resultSet = statement.executeQuery(verificaExistente);
                if (resultSet.next()) { // se existir o local com o id recebido
                    statement.executeUpdate("DELETE FROM local WHERE id='" + id + "' AND id_gestor = '" + idGestor + "'");
                    resultSet.close();
                    rs.close();
                    statement.close();
                    return true;

                }
                System.out.println("Local inexistente");
            }
            System.out.println("Gestor inexistente");
            rs.close();
            statement.close();
            return false;
    } // feito
    public boolean adicionaNovidade(String titulo, String descricao, long idGestor) throws SQLException {
        if (titulo == null || descricao == null) {
            System.out.println("Não foi possível adicionar novidade!");
            return false;
        }

        Statement statement = dbConn.createStatement();
        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
        ResultSet rs = statement.executeQuery(verificaUtilizador);
        if (rs.next()) { // se existir e for gestor

            String verificaExistente = "SELECT * FROM novidade WHERE titulo='" + titulo + "'";

            ResultSet resultSet = statement.executeQuery(verificaExistente);
            if (!resultSet.next()) {
                String sqlQuery = "INSERT INTO novidade VALUES ((SELECT COUNT(*) FROM novidade),'" + titulo + "','" + descricao + "','" + idGestor + "')";
                statement.executeUpdate(sqlQuery);

                resultSet.close();
                rs.close();
                statement.close();
                return true;
            }
            System.out.println("Novidade inexistente");
        }
        System.out.println("Gestor inexistente");
        rs.close();
        statement.close();
        return false;

    } // feito
    public boolean removeNovidade(int id, long idGestor) throws SQLException {
            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaUtilizador);
            if (rs.next()) { // se existir e for gestor
                String verificaExistente = "SELECT * FROM novidade WHERE id=" + id;
                ResultSet resultSet = statement.executeQuery(verificaExistente);
                if (resultSet.next()) { // se existir a novidade com o id recebido
                    statement.executeUpdate("DELETE FROM novidade WHERE id=" + id);
                    resultSet.close();
                    statement.close();

                    ResultSet rsOrdena = statement.executeQuery("SELECT * FROM novidade");
                    int i = 0;
                    while(rsOrdena.next()){
                        int idOrdena = rsOrdena.getInt("id");
                        statement.executeUpdate("UPDATE novidade SET id='"+ i +"' WHERE id ='" + idOrdena+"'");
                        i++;
                    }
                    return true;
                }
                System.out.println("Local inexistente");
            }
            System.out.println("Gestor inexistente");
            rs.close();
            statement.close();
            return false;
    } //feito
    public ArrayList<String> getEventos(String dataHora) throws SQLException {
        if(dataHora == null)
            return null;

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM evento WHERE data_hora like '" + dataHora+ "%'";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> eventos = new ArrayList<>();
        while (rs.next()){
            String tipo = rs.getString("tipo");
            String data = rs.getString("data_hora");
            String [] datahora = data.split(" ");
            eventos.add(tipo+"\n"+datahora[1]+ "\n");
        }
        return eventos;
    }

    public ArrayList<String> getEventos2(String dataHora) throws SQLException {
        if(dataHora == null)
            return null;

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM evento WHERE data_hora like '" + dataHora+ "%'";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> eventos = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String tipo = rs.getString("tipo");
            String data = rs.getString("data_hora");
            String local = rs.getString("local");
            String [] hora = data.split(" ");

            eventos.add(String.valueOf(id));
            eventos.add(tipo);
            eventos.add(hora[1]);
            eventos.add(local);
        }
        return eventos;
    }

    public ArrayList<String> getEventos2() throws SQLException {

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM evento";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> eventos = new ArrayList<>();
        while (rs.next()){
            String tipo = rs.getString("tipo");
            String data_hora = rs.getString("data_hora");
            String local = rs.getString("local");
            eventos.add(tipo);
            eventos.add(data_hora);
            eventos.add(local);

        }
        return eventos;
    }
    public ArrayList<String> getNovidades() throws SQLException {

        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM novidade";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> novidades = new ArrayList<>();
        while (rs.next()){
            String titulo = rs.getString("titulo");
            String descricao = rs.getString("descricao");
            novidades.add(titulo);
            novidades.add(descricao);
        }
        /*if(novidades.size() == 0)
            return null;*/
        return novidades;
    }
    public boolean adicionaEvento(long idGestor, String tipo, String localizacao, String data_hora) throws SQLException {
            if(tipo == null || localizacao == null || data_hora == null){
                System.out.println("Impossível adicionar evento");
                return false;
            }
            Statement statement = dbConn.createStatement();
            String verificaExistente = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaExistente);
            if (rs.next()) { // se esse utilizador for gestor
                String verificaEvento = "SELECT * FROM evento WHERE local = '" + localizacao + "' AND data_hora = '" + data_hora + "'";
                ResultSet resultSet = statement.executeQuery(verificaEvento);
                if (!resultSet.next()) { // se não houver já um evento a essa hora nesse local
                    String sqlQuery = "INSERT INTO evento VALUES ((SELECT COUNT(*) FROM evento),'" + tipo + "','" + data_hora + "','" + localizacao + "','" + idGestor + "')";
                    statement.executeUpdate(sqlQuery);

                    resultSet.close();
                    rs.close();
                    statement.close();
                    return true;
                }
                System.out.println("Já Existe um evento semelhante");
            }
            System.out.println("Impossivel inserir evento");
            rs.close();
            statement.close();
            return false;

    } //feito

    public boolean removeEvento(int id, long idGestor) throws SQLException {

            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaUtilizador);
            if (rs.next()) { // se existir e for gestor
                String verificaExistente = "SELECT * FROM evento WHERE id=" + id;
                ResultSet resultSet = statement.executeQuery(verificaExistente);
                if (resultSet.next()) { // se existir a novidade com o id recebido
                    statement.executeUpdate("DELETE FROM evento WHERE id=" + id);
                    resultSet.close();
                    statement.close();
                    return true;

                }
                System.out.println("Evento inexistente");
            }
            System.out.println("Gestor inexistente");
            rs.close();
            statement.close();
            return false;

    } //feito
    public boolean editaEvento(int id, String tipo, String localidade, String data, int idGestor) throws SQLException {

        System.out.println(id);
        System.out.println(tipo);
        System.out.println(localidade);
        System.out.println(data);
        System.out.println(idGestor);
            Statement statement = dbConn.createStatement();
            String sqlQuery = null;

            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet resultSet = statement.executeQuery(verificaUtilizador);
            if (resultSet.next()) { // se esse utilizador for gestor

                String verificaExistente = "SELECT * FROM evento WHERE id = '" + id + "' AND id_gestor = '" + idGestor + "'";
                ResultSet rs = statement.executeQuery(verificaExistente);
                if (rs.next()) {

                    sqlQuery = "UPDATE evento SET tipo='" + tipo + "',data_hora='" + data + "',local='" + localidade + "' WHERE id='" + id + "'";

                    /*switch (tipo) {
                        case 0 -> {
                            sqlQuery = "UPDATE evento SET tipo='" + campo + "' WHERE id='" + id + "'";
                        }
                        case 1 -> {
                            sqlQuery = "UPDATE evento SET localizacao='" + campo + "' WHERE id='" + id + "'";
                        }
                        case 2 -> {
                            sqlQuery = "UPDATE evento SET data_hora='" + campo + "' WHERE id='" + id + "'";
                        }
                    }*/

                    statement.executeUpdate(sqlQuery);
                    resultSet.close();
                    rs.close();
                    statement.close();
                    return true;
                }
                System.out.println("Evento inexistente");
            }
            System.out.println("Gestor inexistente");
            resultSet.close();
            statement.close();
            return false;
    } //feito

    public boolean adicionaPergunta(String pergunta, long idUtilizador) throws SQLException {
            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idUtilizador + "' AND isGestor = '" + 1 + "'";
            ResultSet resultSet = statement.executeQuery(verificaUtilizador);
            if (resultSet.next()) { // se esse utilizador for gestor
                String verificaExistente = "SELECT * FROM pergunta WHERE texto = '" + pergunta + "'";
                ResultSet rs = statement.executeQuery(verificaExistente);

                if (!rs.next()) {
                    String sqlQuery = "INSERT INTO pergunta VALUES ((SELECT COUNT(*) FROM pergunta),'" + pergunta + "','" +idUtilizador + "')";
                    statement.executeUpdate(sqlQuery);
                    statement.close();
                    return true;
                }
                System.out.println("Já Existe uma pergunta semelhante");
            }
            System.out.println("Utilizador inexistente");
            statement.close();
            return false;
    }

    public ArrayList<String> getIdPerguntas() throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM pergunta";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> ids = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            ids.add(String.valueOf(id));
        }
        /*if(novidades.size() == 0)
            return null;*/
        return ids;
    }

    public Map<String, ArrayList<String>> getPerguntas() throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM pergunta";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String perguntas;
        ArrayList<String> respostas = new ArrayList<>();
        Map<String, ArrayList<String>> mapaRespostas = new HashMap<>();
        while (rs.next()) { // se tiver uma pergunta
            String idPergunta = rs.getString("id");
            String texto = rs.getString("texto");
            //String idUtilizador = rs.getString("id_utilizador");
            perguntas = idPergunta + ": " + texto;
            // perguntas.add(idUtilizador);
            String respostaExistente = "SELECT * FROM resposta WHERE id_pergunta='" + Integer.parseInt(idPergunta) + "'";
            Statement st = dbConn.createStatement();
            ResultSet resultSet = st.executeQuery(respostaExistente);

            while (true) {
                if (!resultSet.next()) { // se não houver (mais/nenhuma) resposta para esta pergunta
                    mapaRespostas.put(perguntas, respostas);
                    resultSet.close();
                    break;
                }
                int idResposta = resultSet.getInt("id");
                String textoResposta = resultSet.getString("texto");
                String textoId = idResposta + ": " + textoResposta;
                respostas.add(textoId);
                mapaRespostas.put(perguntas, respostas);
            }
            respostas = new ArrayList<>();
            st.close();
        }

        statement.close();
        System.out.println("mapaRespostas" + mapaRespostas);
        return mapaRespostas;
    }
    public boolean removePergunta(int id, long idUtilizador) throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idUtilizador + "'";
        ResultSet resultSet = statement.executeQuery(verificaUtilizador);
        if (resultSet.next()) { // se existe
            String verificaExistente = "SELECT * FROM pergunta WHERE id=" + id;
            ResultSet rs = statement.executeQuery(verificaExistente);
            if (rs.next()) { // se existir a pergunta com o id recebido
                ResultSet rs1 = statement.executeQuery("SELECT * FROM resposta WHERE id_pergunta=" + id);
                while (rs1.next()) {
                    statement.executeUpdate("DELETE FROM resposta WHERE id_pergunta=" + id);
                }
                rs1.close();
                statement.executeUpdate("DELETE FROM pergunta WHERE id=" + id);
                rs.close();
                statement.close();
                return true;
            }
            System.out.println("Pergunta inexistente");
        }
        System.out.println("Utilizador inexistente");
        resultSet.close();
        statement.close();
        return false;
    } // feito
    public boolean adicionaResposta(String resposta, int idPergunta, long idGestor) throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
        ResultSet resultSet = statement.executeQuery(verificaUtilizador);
        if (resultSet.next()) { // se esse utilizador for gestor
            String verificaExistente = "SELECT * FROM pergunta WHERE texto = '" + idPergunta + "'";
            ResultSet rs = statement.executeQuery(verificaExistente);

            if (!rs.next()) {
                String sqlQuery = "INSERT INTO resposta VALUES ((SELECT COUNT(*) FROM resposta),'" + resposta + "','" + idPergunta + "','" + idGestor + "')";
                statement.executeUpdate(sqlQuery);
                statement.close();
                return true;
            }
            System.out.println("Pergunta inexistente");
        }
        System.out.println("Gestor inexistente");

        statement.close();
        return false;

    }

    public ArrayList<String> getIdRespostas(int iD) throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaExistente = "SELECT * FROM resposta WHERE id_pergunta='"+iD+"'";
        ResultSet rs = statement.executeQuery(verificaExistente);
        ArrayList<String> ids = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            ids.add(String.valueOf(id));
        }
        System.out.println("ids"+ids);
        rs.close();
        return ids;
    }
    public boolean removeResposta(int id, long idGestor) throws SQLException {
        Statement statement = dbConn.createStatement();
        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
        ResultSet resultSet = statement.executeQuery(verificaUtilizador);
        if (resultSet.next()) { // se esse utilizador for gestor
            String verificaExistente = "SELECT * FROM resposta WHERE id=" + id;
            ResultSet rs = statement.executeQuery(verificaExistente);
            if (rs.next()) { // se existir a pergunta com o id recebido
                statement.executeUpdate("DELETE FROM resposta WHERE id=" + id);
                rs.close();
                resultSet.close();
                statement.close();
                return true;
            }
            System.out.println("Resposta inexistente");
        }
        System.out.println("Gestor inexistente");
        resultSet.close();
        statement.close();
        return false;

    } //feito

    public boolean inscreveEmEvento(int idUtilizador, int idEvento) throws SQLException {

            Statement statement = dbConn.createStatement();

            String verificaExistente = "SELECT * FROM utilizador WHERE numero='" + idUtilizador + "'";
            ResultSet rs = statement.executeQuery(verificaExistente);
            if(rs.next()) {
                String verificaEvento = "SELECT * FROM evento WHERE id='" + idEvento + "'";
                ResultSet resultSet = statement.executeQuery(verificaEvento);

                if (resultSet.next()) {

                    String verificaTudo = "SELECT * FROM evento_utilizador WHERE id_evento='" + idEvento + "' AND id_utilizador='" + idUtilizador + "'";
                    ResultSet resultSet2 = statement.executeQuery(verificaTudo);
                    if(!resultSet2.next()) {
                        String sqlQuery = "INSERT INTO evento_utilizador VALUES ('" + idEvento + "','" + idUtilizador + "')";
                        statement.executeUpdate(sqlQuery);
                        statement.close();
                        return true;
                    }
                }

            }
            System.out.println("Nao foi possivel inscrever no evento");
            statement.close();
            return false;

    }

    public boolean desinscreveEmEvento(int idUtilizador, int idEvento) throws SQLException {

        Statement statement = dbConn.createStatement();

        String verificaExistente = "SELECT * FROM utilizador WHERE numero='" + idUtilizador + "'";
        ResultSet rs = statement.executeQuery(verificaExistente);
        if(rs.next()) {
            String verificaEvento = "SELECT * FROM evento WHERE id='" + idEvento + "'";
            ResultSet resultSet = statement.executeQuery(verificaEvento);

            if (resultSet.next()) {

                String verificaTudo = "SELECT * FROM evento_utilizador WHERE id_evento='" + idEvento + "' AND id_utilizador='" + idUtilizador + "'";
                ResultSet resultSet2 = statement.executeQuery(verificaTudo);
                if(resultSet2.next()) {
                    String sqlQuery = "DELETE FROM evento_utilizador WHERE id_evento='" + idEvento + "' AND id_utilizador='" + idUtilizador + "'";
                    statement.executeUpdate(sqlQuery);
                    statement.close();
                    return true;
                }
            }

        }
        System.out.println("Nao foi possivel inscrever no evento");
        statement.close();
        return false;

    }

    public ArrayList<String> getUtilizadoresEvento(int idEvento) throws SQLException {
        ArrayList<String> utilizadoresEvento = new ArrayList<>();
        Statement statement = dbConn.createStatement();

        String verificaEvento = "SELECT * FROM evento WHERE id='" + idEvento + "'";
        ResultSet resultSet = statement.executeQuery(verificaEvento);

        if (resultSet.next()) {
            String verificaTudo = "SELECT * FROM evento_utilizador WHERE id_evento='" + idEvento + "'";
            ResultSet resultSet2 = statement.executeQuery(verificaTudo);
            while (resultSet2.next()) {
                Statement st = dbConn.createStatement();
                int idUtilizador = resultSet2.getInt("id_utilizador");

                String verificaUtilizador = "SELECT * FROM utilizador WHERE numero='" + idUtilizador + "'";

                ResultSet resultSet3 = st.executeQuery(verificaUtilizador);
                if (resultSet3.next()) {
                    String nomeUtilizador = resultSet3.getString("nome");
                    utilizadoresEvento.add(nomeUtilizador);
                }
                resultSet3.close();
                st.close();
            }
            resultSet2.close();
        }

        resultSet.close();

        statement.close();
        return utilizadoresEvento;
    }

    public boolean editaUtilizador(int idUtilizador, String campo, int tipo) throws SQLException {
        Statement statement = dbConn.createStatement();
        String sqlQuery = null;

        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idUtilizador + "'";
        ResultSet resultSet = statement.executeQuery(verificaUtilizador);
        if (resultSet.next()) { // se esse utilizador existe

            switch (tipo) {
                case 0 -> {
                    sqlQuery = "UPDATE utilizador SET password='" + campo + "' WHERE numero='" + idUtilizador + "'";
                }
                case 1 -> {
                    sqlQuery = "UPDATE utilizador SET curso='" + campo + "' WHERE numero='" + idUtilizador + "'";
                }
            }

            System.out.println(sqlQuery);
            statement.executeUpdate(sqlQuery);
            //resultSet.close();
            //statement.close();
            return true;
        }

        System.out.println("Utilizador inexistente");
        resultSet.close();
        statement.close();
        return false;
    } //feito

    public boolean removeUtilizador(long idUtilizador) throws SQLException {
        Statement statement = dbConn.createStatement();
        String sqlQuery = null;

        String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idUtilizador + "'";
        ResultSet resultSet = statement.executeQuery(verificaUtilizador);
        if (resultSet.next()) { // se esse utilizador existe

            statement.executeUpdate("DELETE FROM evento_utilizador WHERE id_utilizador= '" + idUtilizador + "'");
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + idUtilizador);

            resultSet.close();
            statement.close();
            return true;
        }

        System.out.println("Utilizador inexistente");
        resultSet.close();
        statement.close();
        return false;
    } //feito

    public int getID(String email) {

        try {
            Statement statement = dbConn.createStatement();
            String sqlQuery = "Select numero, nome, email, password, curso, isGestor, autenticado FROM utilizador";
            if (email != null) {
                sqlQuery += " WHERE email ='" + email + "'";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                if (!resultSet.next()) {
                    System.out.println("Erro no getID (username nao existe)");
                    statement.close();
                    throw new SQLException();
                } else {
                    int nrAluno = resultSet.getInt("numero");
                    System.out.println("[" + nrAluno + "] ");
                    statement.close();
                    return nrAluno;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro no getID!");
            return -1;
        }

        return -1;
    }
    public int loginUtilizador(String email, String password) throws SQLException {
        if (email == null || password == null) {
            return -1;
        }
        Statement statement = dbConn.createStatement();
        String sqlQuery = "SELECT * FROM utilizador WHERE email = '" + email + "' AND password = '" + password + "'";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        if (resultSet.next()) {
            int isGestor = resultSet.getInt("isGestor");
            int nrAluno = resultSet.getInt("numero");
            loginEfetuado(nrAluno);
            System.out.println("Login Efetuado Com Sucesso!");
            statement.close();
            return isGestor;
        }
        System.out.println("Login Incorreto!");
        return -1;
    }
    public void loginEfetuado (int nrAluno) throws SQLException {
        int autent = 1;
        Statement statement = dbConn.createStatement();
        String sqlQuery = "UPDATE utilizador SET autenticado='" + autent + "' WHERE numero=" + nrAluno;
        statement.executeUpdate(sqlQuery);
        System.out.println("Está agora autenticado!");
        statement.close();
    }
    public boolean logout(long nrAluno) throws SQLException {
        Statement st = dbConn.createStatement();
        String user = "SELECT * FROM utilizador WHERE numero=" + nrAluno;
        ResultSet rs = st.executeQuery(user);
        if(rs.next()) {
            String logout = "UPDATE utilizador SET autenticado='" + 0 + "' WHERE numero=" + nrAluno;
            st.executeUpdate(logout);
            return true;
        }

        return false;
    }

}

