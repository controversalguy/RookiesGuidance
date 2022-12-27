package pt.isec.gps.rookiesguidance.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;
import java.sql.Statement;

public class Testes {

    //Fazer registo
    @Test
    void testeRegisto() throws SQLException {
        System.out.println("Teste Registo");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3C..0"));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo sem nome
    @Test
    void testeRegistoSemNome() throws SQLException {
        System.out.println("Teste Registo sem nome");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "", "LEI", "a2019133920@isec.pt", "IS3C..0"));
    }

    //Fazer registo sem curso
    @Test
    void testeRegistoSemCurso() throws SQLException {
        System.out.println("Teste Registo sem curso");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "", "a2019133920@isec.pt", "IS3C..0"));
    }

    //Fazer registo sem email
    @Test
    void testeRegistoSemEmail() throws SQLException {
        System.out.println("Teste Registo sem email");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "", "IS3C..0"));
    }

    //Fazer registo sem password
    @Test
    void testeRegistoSemPassword() throws SQLException {
        System.out.println("Teste Registo sem password");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2020144095@isec.pt", ""));
    }

    //Fazer registo de utilizador já registado
    @Test
    void testeRegistoUtilizadorRegistado() throws SQLException {
        System.out.println("Teste Registo utilizador já registado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 0 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3C..0"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);

    }

    //Fazer registo de password menos 5 carateres
    @Test
    void testeRegistoPasswordMenos5() throws SQLException {
        System.out.println("Teste Registo password menos de 5 carateres");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3C"));
    }

    //Fazer registo de password mais 15 carateres
    @Test
    void testeRegistoPasswordMais15() throws SQLException {
        System.out.println("Teste Registo password mais de 15 carateres");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3CisecISECisec"));
    }

    //Fazer login
    @Test
    void testeLogin() throws SQLException {
        System.out.println("Teste login");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 0 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..0"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login com login já iniciado
    @Test
    void testeLoginUtilizadorLogado() throws SQLException {
        System.out.println("Teste login utilizador já logado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..0"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login com a password errada
    @Test
    void testeLoginPasswordErrada() throws SQLException {
        System.out.println("Teste login password errada");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..1"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login com o email errado
    @Test
    void testeLoginEmailErrado() throws SQLException {
        System.out.println("Teste login email errado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 0 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133921@isec.pt", "IS3C..0"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login sem email
    @Test
    void testeLoginSemEmail() throws SQLException {
        System.out.println("Teste login sem inserir email");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 0 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("", "IS3C..0"));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login sem password
    @Test
    void testeLoginSemPassword() throws SQLException {
        System.out.println("Teste login sem inserir password");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 +"')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133921@isec.pt", ""));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Editar perfil em que mudamos a password
    @Test
    void testeEditaPassword() throws SQLException {
        System.out.println("Teste edita password");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 +"')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "IS3C..1", 0));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);

    }

    //Editar perfil em que mudamos o curso
    @Test
    void testeEditaCurso() throws SQLException {
        System.out.println("Teste edita curso");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 +"')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "LEM", 1));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remove perfil
    @Test
    void testeRemovePerfil() throws SQLException {
        System.out.println("Teste remove perfil");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 +"')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.removeUtilizador(2019133920));
    }

    //Adicionar eventos
    @Test
    void testeAddEventos() throws SQLException {
        System.out.println("Teste adiciona eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.adicionaEvento(2019133920, "Praxe", "ISEC", "Quinta-feira 15h"));
        Statement st = connDB.dbConn.createStatement();
        st.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
        Statement st1 = connDB.dbConn.createStatement();
        st1.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }


    //Inscreve eventos
    @Test
    void testeInscreverEventos() throws SQLException {
        System.out.println("Teste inscreve eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaEvento = "INSERT INTO evento VALUES ('" + 0 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "','" + 2019133920 + "')";
        Statement st = connDB.dbConn.createStatement();
        st.executeUpdate(verificaEvento);
        Assertions.assertTrue(connDB.inscreveEmEvento(2019133920, 0));
        statement.executeUpdate("DELETE FROM evento_utilizador WHERE id_utilizador=" + 2019133920);
        statement.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Inscreve 2x mesmo evento
    @Test
    void testeInscreve2XMesmoEventos() throws SQLException {
        System.out.println("Teste inscreve 2x mesmo evento");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 +"','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaEvento = "INSERT INTO evento VALUES ('" + 0 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaEvento);
        String verificaInscricao = "INSERT INTO evento_utilizador VALUES ('" + 0 + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaInscricao);
        Assertions.assertFalse(connDB.inscreveEmEvento(2019133920, 0));
        statement.executeUpdate("DELETE FROM evento_utilizador WHERE id_utilizador=" + 2019133920);
        statement.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }


    //Editar eventos
    @Test
    void testeEditarEventos() throws SQLException {
        System.out.println("Teste Editar eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaEvento = "INSERT INTO evento VALUES ('" + 0 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaEvento);

        Assertions.assertTrue(connDB.editaEvento(0, "Praxe", "praca", "21/12/2022 13:00", 2019133920));
        statement.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remove eventos
    @Test
    void testeRemoveEventos() throws SQLException {
        System.out.println("Teste remove eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaEvento = "INSERT INTO evento VALUES ('" + 0 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaEvento);
        Assertions.assertTrue(connDB.removeEvento(0, 2019133920));
        statement.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remove eventos que não existe
    @Test
    void testeRemoveEventosNaoExiste() throws SQLException {
        System.out.println("Teste remove evento que não existe!");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaEvento = "INSERT INTO evento VALUES ('" + 0 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaEvento);
        Assertions.assertFalse(connDB.removeEvento(1,2019133920));
            statement.executeUpdate("DELETE FROM evento WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }


    //Adicionar Informacao
    @Test
    void testeAddInfo() throws SQLException {
        System.out.println("Adicionar Informacao");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.addlocal("Cantina Amarela", "Alimentação", 2019133920));
        statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remover Informacao
    @Test
    void testeRemoveInfo() throws SQLException {
        System.out.println("Remover Informacao");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String sqlQuery = "INSERT INTO local VALUES (0,'" + "Cantina Amarela" + "','" + "Alimentação"+ "','" + 2019133920 + "')";
        statement.executeUpdate(sqlQuery);

        Assertions.assertTrue(connDB.removelocal(0, 2019133920));
        statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remover Informacao inexistente
    @Test
    void testeRemoveInfoInexistente() throws SQLException {
        System.out.println("Remover Informacao inexistente");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);

        Assertions.assertFalse(connDB.removelocal(1, 2019133920));
        statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);

    }

    //Adicionar Novidades
    @Test
    void testeAddNov() throws SQLException {
        System.out.println("Adicionar Novidades");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.adicionaNovidade("Nova Merge do ISEC", "Novas t-shirts, camisolas e casacos do ISEC", 2019133920));
        statement.executeUpdate("DELETE FROM novidade WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remover Novidades
    @Test
    void testeRemoveNov() throws SQLException {
        System.out.println("Remover Novidades");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String sqlQuery = "INSERT INTO novidade VALUES (0,'" + "Nova Merch do Isec" + "','" + "Novas t-shirts, camisolas e casacos do ISEC" + "','" + 2019133920 + "')";
        statement.executeUpdate(sqlQuery);
        Assertions.assertTrue(connDB.removeNovidade(0 ,2019133920));
        statement.executeUpdate("DELETE FROM novidade WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Remover Novidades inexistente
    @Test
    void testeRemoveNovInexistente() throws SQLException {
        System.out.println("Remover Novidades inexistente");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertFalse(connDB.removeNovidade(0 ,2019133920));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);

    }

    //Adicionar uma Pergunta
    @Test
    void testeAddPerg() throws SQLException {
        System.out.println("Adicionar uma Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.adicionaPergunta("Como funcionam as praxes?",2019133920));
        statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Responde a uma Pergunta
    @Test
    void testeRespondePerg() throws SQLException {
        System.out.println("Responde a uma Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('"+ 0 + "','" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaPergunta);
        Assertions.assertTrue(connDB.adicionaResposta("Respondi" ,0,2019133920));
        statement.executeUpdate("DELETE FROM resposta WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Responde a uma Pergunta que já tem resposta
    @Test
    void testeRespondePergComMais1Resposta() throws SQLException {
        System.out.println("Responde a uma Pergunta que já tem resposta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('"+ 0 + "','" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaPergunta);
        String verificaResposta = "INSERT INTO resposta VALUES ('"+ 0 + "','"  + "Quartas 15h no ISEC" + "','" + 0 + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaResposta);
        Assertions.assertTrue(connDB.adicionaResposta("Segunda Resposta" ,0,2019133920));
        statement.executeUpdate("DELETE FROM resposta WHERE id_gestor=" + 2019133920);
        statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);

    }


    //Remove uma Pergunta
    @Test
    void testeRemovePerg() throws SQLException {
        System.out.println("Remove Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('"+ 0 + "','" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        statement.executeUpdate(verificaPergunta);
        Assertions.assertTrue(connDB.removePergunta(0 ,2019133920));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Faz logout
    @Test
    void testeLogout() throws SQLException {
        System.out.println("Teste faz logout");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        statement.executeUpdate(verificaExistente);
        Assertions.assertTrue(connDB.logout(2019133920));
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }
}
