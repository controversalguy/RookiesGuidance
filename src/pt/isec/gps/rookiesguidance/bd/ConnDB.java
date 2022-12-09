package pt.isec.gps.rookiesguidance.bd;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnDB {

    public final String DATABASE = "jdbc:sqlite:Rookies.db";
    public final Connection dbConn;

    public ConnDB() throws SQLException {
        dbConn = DriverManager.getConnection(DATABASE);
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
                    descricao TEXT NOT NULL,
                    titulo TEXT NOT NULL,
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
    public boolean addlocal(String local, int tipo, long idGestor) throws SQLException {
        if (local == null) {
            System.out.println("Erro ao adicionar novo local!");
            return false;
        }

            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaUtilizador);
            if (rs.next()) { // se existir e for gestor

                String verificaExistente = "SELECT * FROM local";
                if (tipo == 0 || tipo == 1) {
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
                System.out.println("Impossivel inserir local");
            }
            System.out.println("Gestor não existe!");
            statement.close();
            return false;

    } // feito
    public boolean removelocal(int id, long idGestor) throws SQLException {
            Statement statement = dbConn.createStatement();
            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet rs = statement.executeQuery(verificaUtilizador);
            if (rs.next()) { // se existir e for gestor
                String verificaExistente = "SELECT * FROM local WHERE id=" + id;
                ResultSet resultSet = statement.executeQuery(verificaExistente);
                if (resultSet.next()) { // se existir o local com o id recebido
                    statement.executeUpdate("DELETE FROM local WHERE id=" + id);
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
    public boolean addNovidade(String titulo, String descricao, long idGestor) throws SQLException {
        if (titulo == null && descricao == null) {
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
                    statement.executeUpdate("DELETE FROM novidade "); //WHERE id=" + id
                    resultSet.close();
                    statement.close();
                    return true;
                }
                System.out.println("Local inexistente");
            }
            System.out.println("Gestor inexistente");
            rs.close();
            statement.close();
            return false;
    } //feito
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
    public boolean editaEvento(int id,String campo,int tipo, long idGestor) throws SQLException {
            Statement statement = dbConn.createStatement();
            String sqlQuery = null;

            String verificaUtilizador = "SELECT * FROM utilizador WHERE numero = '" + idGestor + "' AND isGestor = '" + 1 + "'";
            ResultSet resultSet = statement.executeQuery(verificaUtilizador);
            if (resultSet.next()) { // se esse utilizador for gestor

                String verificaExistente = "SELECT * FROM evento WHERE id = '" + id + "'";
                ResultSet rs = statement.executeQuery(verificaExistente);
                if (rs.next()) {

                    switch (tipo) {
                        case 0 -> {
                            sqlQuery = "UPDATE evento SET tipo='" + campo + "' WHERE id='" + id + "'";
                        }
                        case 1 -> {
                            sqlQuery = "UPDATE evento SET localizacao='" + campo + "' WHERE id='" + id + "'";
                        }
                        case 2 -> {
                            sqlQuery = "UPDATE evento SET data_hora='" + campo + "' WHERE id='" + id + "'";
                        }
                    }

                    statement.executeUpdate(sqlQuery);
                    resultSet.close();
                    rs.close();
                    statement.close();
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
    public boolean inscreveEmEvento(long idUtilizador, int idEvento) throws SQLException {

            Statement statement = dbConn.createStatement();

            String verificaExistente = "SELECT * FROM utilizador WHERE numero='" + idUtilizador + "'";
            ResultSet rs = statement.executeQuery(verificaExistente);
            if(rs.next()) {
                String verificaPergunta = "SELECT * FROM evento WHERE id='" + idUtilizador + "'";
                ResultSet resultSet = statement.executeQuery(verificaPergunta);

                if (resultSet.next()) {
                    String sqlQuery = "INSERT INTO evento_utilizador VALUES ('" + idEvento + "','" + idUtilizador + "')";
                    statement.executeUpdate(sqlQuery);
                    statement.close();
                    return true;
                }

            }
            System.out.println("Nao foi possivel inscrever no evento");
            statement.close();
            return false;

    }
    public boolean editaUtilizador(long idUtilizador, String campo, int tipo) throws SQLException {
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

            statement.executeUpdate(sqlQuery);
            resultSet.close();
            statement.close();
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

}

