package pt.isec.gps.rookiesguidance.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.ResultSet;
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
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo sem curso
    @Test
    void testeRegistoSemCurso() throws SQLException {
        System.out.println("Teste Registo sem curso");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "", "a2019133920@isec.pt", "IS3C..0"));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo sem email
    @Test
    void testeRegistoSemEmail() throws SQLException {
        System.out.println("Teste Registo sem email");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "", "IS3C..0"));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo sem password
    @Test
    void testeRegistoSemPassword() throws SQLException {
        System.out.println("Teste Registo sem password");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2020144095@isec.pt", ""));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo de utilizador já registado
    @Test
    void testeRegistoUtilizadorRegistado() throws SQLException {
        System.out.println("Teste Registo utilizador já registado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3C..0"));
        //if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        //}
    }

    //Fazer registo de password menos 5 carateres
    @Test
    void testeRegistoPasswordMenos5() throws SQLException {
        System.out.println("Teste Registo password menos de 5 carateres");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3C"));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer registo de password mais 15 carateres
    @Test
    void testeRegistoPasswordMais15() throws SQLException {
        System.out.println("Teste Registo password mais de 15 carateres");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "LEI", "a2019133920@isec.pt", "IS3CisecISECisecISEC"));
        Statement statement = connDB.dbConn.createStatement();
        statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
    }

    //Fazer login
    @Test
    void testeLogin() throws SQLException {
        System.out.println("Teste login");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..0"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Fazer login com login já iniciado
    @Test
    void testeLoginUtilizadorLogado() throws SQLException {
        System.out.println("Teste login utilizador já logado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..0"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Fazer login com a password errada
    @Test
    void testeLoginPasswordErrada() throws SQLException {
        System.out.println("Teste login password errada");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..1"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Fazer login com o email errado
    @Test
    void testeLoginEmailErrado() throws SQLException {
        System.out.println("Teste login email errado");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133921@isec.pt", "IS3C..0"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Fazer login sem email
    @Test
    void testeLoginSemEmail() throws SQLException {
        System.out.println("Teste login sem inserir email");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 0 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("", "IS3C..0"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Fazer login sem password
    @Test
    void testeLoginSemPassword() throws SQLException {
        System.out.println("Teste login sem inserir password");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133921@isec.pt", ""));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Editar perfil em que mudamos a password
    @Test
    void testeEditaPassword() throws SQLException {
        System.out.println("Teste edita password");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "IS3C..1", 0));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Editar perfil em que metemos mesma password
    @Test
    void testeEditaPasswordMesma() throws SQLException {
        System.out.println("Teste edita password mesma");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertEquals(-1,connDB.loginUtilizador("a2019133920@isec.pt", "IS3C..0"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Editar perfil em que mudamos o curso
    @Test
    void testeEditaCurso() throws SQLException {
        System.out.println("Teste edita curso");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "LEM", 1));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Editar perfil em que mete o mesmo curso
    @Test
    void testeEditaCursoMesmo() throws SQLException {
        System.out.println("Teste edita curso mesmo");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertFalse(connDB.editaUtilizador(2019133920, "LEI", 1));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remove perfil
    @Test
    void testeRemovePerfil() throws SQLException {
        System.out.println("Teste remove perfil");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.removeUtilizador(2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Adicionar eventos
    @Test
    void testeAddEventos() throws SQLException {
        System.out.println("Teste adiciona eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.adicionaEvento(2019133920, "Praxe", "ISEC", "Quinta-feira 15h"));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }


    //Inscreve eventos
    @Test
    void testeInscreverEventos() throws SQLException {
        System.out.println("Teste inscreve eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaEvento = "INSERT INTO eventos VALUES ('" + 2019133920 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "')";
        ResultSet rs2 = statement.executeQuery(verificaEvento);
        Assertions.assertTrue(connDB.inscreveEmEvento(2019133920, 0));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM evento_utilizador WHERE id_utilizador=" + 2019133920);
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Inscreve 2x mesmo evento
    @Test
    void testeInscreve2XMesmoEventos() throws SQLException {
        System.out.println("Teste inscreve 2x mesmo evento");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaEvento = "INSERT INTO eventos VALUES ('" + 2019133920 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "')";
        ResultSet rs2 = statement.executeQuery(verificaEvento);
        String verificaInscricao = "INSERT INTO evento_utilizador VALUES ('" + 2019133920 + "','" + 0 + "')";
        ResultSet rs3 = statement.executeQuery(verificaInscricao);
        Assertions.assertFalse(connDB.inscreveEmEvento(2019133920, 0));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM evento_utilizador WHERE id_utilizador=" + 2019133920);
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }


    //Editar eventos
    @Test
    void testeEditarEventos() throws SQLException {
        System.out.println("Teste Editar eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        //mesmo local
        Assertions.assertFalse(connDB.editaEvento(0, "Praxe", "praca", "21/12/2022 13:00", 2019133920));
        //local diferente
        Assertions.assertTrue(connDB.editaEvento(0, "Praxe", "praca","21/12/2022 13:00", 2019133920));
        if (rs.next()) {
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remove eventos
    @Test
    void testeRemoveEventos() throws SQLException {
        System.out.println("Teste remove eventos");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" + "Francisco Simões" + "','" + "LEI" + "','" + "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs1 = statement.executeQuery(verificaExistente);
        String verificaEvento = "INSERT INTO eventos VALUES ('" + 2019133920 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "')";
        ResultSet rs2 = statement.executeQuery(verificaEvento);
        Assertions.assertTrue(connDB.removeEvento(0, 2019133920));
        if (rs1.next()) {
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs1.close();
        }
    }

    //Remove eventos que não existe
    @Test
    void testeRemoveEventosNaoExiste() throws SQLException {
        System.out.println("Teste remove evento que não existe!");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs1 = statement.executeQuery(verificaExistente);
        String verificaEvento = "INSERT INTO eventos VALUES ('" +  2019133920 + "','" + "Praxe" + "','" + "ISEC" + "','" + "Quinta-feira 15h" + "')";
        ResultSet rs2 = statement.executeQuery(verificaEvento);
        Assertions.assertFalse(connDB.removeEvento(1,2019133920));
        if(rs1.next()) {
            statement.executeUpdate("DELETE FROM eventos WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs1.close();
        }
    }


    //Adicionar Informacao
    @Test
    void testeAddInfo() throws SQLException {
        System.out.println("Adicionar Informacao");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.addlocal("Cantina Amarela", "Alimentação", 2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remover Informacao
    @Test
    void testeRemoveInfo() throws SQLException {
        System.out.println("Remover Informacao");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.removelocal(0, 2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remover Informacao inexistente
    @Test
    void testeRemoveInfoInexistente() throws SQLException {
        System.out.println("Remover Informacao inexistente");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.removelocal(1, 2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM local WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Adicionar Novidades
    @Test
    void testeAddNov() throws SQLException {
        System.out.println("Adicionar Novidades");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.adicionaNovidade("Nova Merge do ISEC","Novas t-shirts, camisolas e casacos do ISEC" ,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM novidades WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remover Novidades
    @Test
    void testeRemoveNov() throws SQLException {
        System.out.println("Remover Novidades");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaNov = "INSERT INTO novidade VALUES ('" + "Nova Merge do ISEC" + "','" + "Novas t-shirts, camisolas e casacos do ISEC" + "','" +  2019133920 + "')";
        ResultSet rs2 = statement.executeQuery(verificaNov);
        Assertions.assertTrue(connDB.removeNovidade(0 ,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Remover Novidades inexistente
    @Test
    void testeRemoveNovInexistente() throws SQLException {
        System.out.println("Remover Novidades inexistente");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaNov = "INSERT INTO novidade VALUES ('" + "Nova Merge do ISEC" + "','" + "Novas t-shirts, camisolas e casacos do ISEC" + "','" +  2019133920 + "')";
        ResultSet rs2 = statement.executeQuery(verificaNov);
        Assertions.assertFalse(connDB.removeNovidade(1 ,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM novidades WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Adicionar uma Pergunta
    @Test
    void testeAddPerg() throws SQLException {
        System.out.println("Adicionar uma Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.adicionaPergunta("Como funcionam as praxes?",2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Responde a uma Pergunta
    @Test
    void testeRespondePerg() throws SQLException {
        System.out.println("Responde a uma Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        ResultSet rs2 = statement.executeQuery(verificaPergunta);
        Assertions.assertTrue(connDB.adicionaResposta("Respondi" ,0,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM resposta WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Responde a uma Pergunta que já tem resposta
    @Test
    void testeRespondePergComMais1Resposta() throws SQLException {
        System.out.println("Responde a uma Pergunta que já tem resposta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        ResultSet rs2 = statement.executeQuery(verificaPergunta);
        String verificaResposta = "INSERT INTO resposta VALUES ('" + "Quartas 15h no ISEC" + "','" + 0 + "','" + 2019133920 + "')";
        ResultSet rs3 = statement.executeQuery(verificaResposta);
        Assertions.assertTrue(connDB.adicionaResposta("Segunda Resposta" ,0,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM resposta WHERE id_gestor=" + 2019133920);
            statement.executeUpdate("DELETE FROM pergunta WHERE id_utilizador=" + 2019133920);
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }


    //Remove uma Pergunta
    @Test
    void testeRemovePerg() throws SQLException {
        System.out.println("Remove Pergunta");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        String verificaPergunta = "INSERT INTO pergunta VALUES ('" + "Como funcionam as praxes?" + "','" + 2019133920 + "')";
        ResultSet rs2 = statement.executeQuery(verificaPergunta);
        Assertions.assertTrue(connDB.removePergunta(0 ,2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }

    //Faz logout
    @Test
    void testeLogout() throws SQLException {
        System.out.println("Teste faz logout");
        ConnDB connDB = new ConnDB();
        Statement statement = connDB.dbConn.createStatement();
        String verificaExistente = "INSERT INTO utilizador VALUES ('" + 2019133920 + "','" +  "Francisco Simões" + "','" + "LEI" + "','" +  "a2019133920@isec.pt" + "','" + "IS3C..0" + "','" + 1 + "','" + 1 + "')";
        ResultSet rs = statement.executeQuery(verificaExistente);
        Assertions.assertTrue(connDB.logout(2019133920));
        if(rs.next()) {
            statement.executeUpdate("DELETE FROM utilizador WHERE numero=" + 2019133920);
            rs.close();
        }
    }
}
