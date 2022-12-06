package pt.isec.gps.rookiesguidance.testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;

public class Testes {

    //Fazer registo (Nº
    @Test
    void testeRegisto() throws SQLException {
        System.out.println("Teste Registo");
        ConnDB connDB = new ConnDB();
        Assertions.assertFalse(connDB.registaNovoUtilizador(2019133920, "Francisco Simões", "a2019133920@isec.pt", "IS3C..0", "LEI"));
    }

    //Fazer login
    @Test
    void testeLogin() throws SQLException {
        System.out.println("Teste login");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.loginUtilizador("a2019133920@isec.pt","IS3C..0"));
    }

    //Terminar sessão

    //Editar perfil (password)
    @Test
    void testeEditaPassword() throws SQLException {
        System.out.println("Teste edita password");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "IS3C..1", 0));
    }

    //Editar perfil (curso)
    @Test
    void testeEditaCurso() throws SQLException {
        System.out.println("Teste edita curso");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.editaUtilizador(2019133920, "LEM", 1));
    }

    //Remove perfil
    @Test
    void testeRemovePerfil() throws SQLException {
        System.out.println("Teste remove perfil");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.removeUtilizador(2019133920));
    }

    //Adicionar eventos
    @Test
    void testeAddEventos() throws SQLException {
        System.out.println("Teste adiciona eventos");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.adicionaEvento(2019133920,"Praxe", "Parque Verde", "Quarta-feira 15h"));
    }

    //Adicionar eventos
    @Test
    void testeEditarEventos() throws SQLException {
        System.out.println("Teste Editar novidades novidades");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.editaEvento(1,"Praxe",0 , 2019133920));
        //Assertions.assertTrue(connDB.editaEvento(1,"Parque verde",1 , 2019133920));
    }

    //Remove eventos
    @Test
    void testeRemoveEventos() throws SQLException {
        System.out.println("Teste remove eventos");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.removeEvento(1,2019133920));
    }

    //Adicionar Informacao
    @Test
    void testeAddInfo() throws SQLException {
        System.out.println("Adicionar Informacao");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.addlocal("Cantina Amarela", 0, 2019133920));
    }

    //Remover Informacao
    @Test
    void testeRemoveInfo() throws SQLException {
        System.out.println("Remover Informacao");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.removelocal(1, 0));
    }

    //Adicionar Novidades
    @Test
    void testeAddNov() throws SQLException {
        System.out.println("Adicionar Novidades");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.addNovidade("Nova Merge do ISEC","Novas t-shirts, camisolas e casacos do ISEC" ,2019133920));
    }

    //Remover Novidades
    @Test
    void testeRemoveNov() throws SQLException {
        System.out.println("Remover Novidades");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.removeNovidade(1 ,2019133920));
    }

    //Adicionar uma Pergunta
    @Test
    void testeAddPerg() throws SQLException {
        System.out.println("Adicionar uma Pergunta"); // nos testes não precisam destes prints
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.adicionaPergunta("Como funcionam as praxes?",2019133920));
    }

    //Responde a uma Pergunta
    @Test
    void testeRespondePerg() throws SQLException {
        System.out.println("Responde a uma Pergunta");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.adicionaResposta("Respondi" ,1,2019133920));
    }

    //Remove uma Pergunta
    @Test
    void testeRemovePerg() throws SQLException {
        System.out.println("Remove Pergunta");
        ConnDB connDB = new ConnDB();
        Assertions.assertTrue(connDB.removePergunta(1 ,2019133920));
    }



}
