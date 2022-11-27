package pt.isec.gps.rookiesguidance.ui.text;

import pt.isec.gps.rookiesguidance.bd.ComandosDB;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ComandosDB cmd = new ComandosDB();
//        cmd.criaTabela();
//        cmd.registo(2020020798,"Joao Cunha","a2020020798@isec.pt","123456","LEI");
        if( cmd.EfetuaLogin("a2020020798@isec.pt","123456") != -1)
            System.out.println("Login efetuado com sucesso");
        }
}