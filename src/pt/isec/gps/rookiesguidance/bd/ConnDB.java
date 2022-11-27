package pt.isec.gps.rookiesguidance.bd;

import java.sql.*;

public class ConnDB {

    //Project structure w/jar file
    public final String DATABASE = "jdbc:sqlite:TP.db";
    public final Connection dbConn;



    public ConnDB() throws SQLException {
        dbConn = DriverManager.getConnection(DATABASE);
    }


    public void close() throws SQLException {
        if (dbConn != null)
            dbConn.close();
    }

    public void createTables() {
        try {
            Statement statement = dbConn.createStatement();


            String utilizador = "CREATE TABLE IF NOT EXISTS utilizador" +
                    "(numero INTEGER NOT NULL," +
                    "nome TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "curso TEXT NOT NULL," +
                    "isGestor INTEGER NOT NULL," +
                    "autenticado INTEGER NOT NULL)";

            String informacao = "CREATE TABLE IF NOT EXISTS informacao" +
                    "(locaisA TEXT NOT NULL," +
                    "locaisE TEXT NOT NULL)";

            String novidade = "CREATE TABLE IF NOT EXISTS novidade" +
                    "(titulo TEXT NOT NULL," +
                    "descricao TEXT NOT NULL)";

            String pergunta = "CREATE TABLE IF NOT EXISTS pergunta" +
                    "(pergunta TEXT NOT NULL," +
                    "resposta TEXT NOT NULL)";

            String evento = "CREATE TABLE IF NOT EXISTS evento" +
                    "(tipo TEXT NOT NULL," +
                    "localizacao TEXT NOT NULL," +
                    "hora TEXT NOT NULL," +
                    "data TEXT NOT NULL)";

            statement.executeUpdate(utilizador);
            statement.executeUpdate(informacao);
            statement.executeUpdate(novidade);
            statement.executeUpdate(pergunta);
            statement.executeUpdate(evento);

            statement.close();
        } catch (SQLException e) {
            System.out.println("erro");
            }

    }



    public boolean registaNovoUtilizador(int nrAluno, String nome,String email, String password, String curso,int isGestor){
        try{
            Statement statement = dbConn.createStatement();
            int aunt = 0;
            String sqlQuery = "INSERT INTO utilizador VALUES ('"+nrAluno+"','"+nome+"','"+email+"','"+password+"','"+curso+"','"+isGestor+"','" + aunt + "')";

            statement.executeUpdate(sqlQuery);
            statement.close();
            return true;
        }catch (SQLException e){
            System.out.println("Já Existe um utilizador com esse Username e/ou nome");
            return false;
        }
    }

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

    public boolean loginUtilizador(String email, String password) {

        try {
            Statement statement = dbConn.createStatement();
            String sqlQuery = "SELECT numero, nome, email, password, curso, isGestor,autenticado  FROM utilizador";
            if (email != null && password != null) {
                sqlQuery += " WHERE email = '" + email + "' AND password = '" + password + "'";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                if (!resultSet.next()) {
                    System.out.println("Login Incorreto!");
                    throw new SQLException();
                } else {
                    /*int admin = resultSet.getInt("administrador");
                    int id = resultSet.getInt("id");
                    if (admin == 1) {
                        System.out.println("Tentativa de entrada como admin detetada!");
                        statement.close();
                        return false;
                    } else {*/
                    int nrAluno = resultSet.getInt("numero");

                    loginEfetuado(nrAluno);
                    System.out.println("Login Efetuado Com Sucesso!");
                    statement.close();
                    return true;
                    //}
                }

            }
        } catch (SQLException e) {
            System.out.println("Excecao lançada (login)");
            return false;
        }

        return false;
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

