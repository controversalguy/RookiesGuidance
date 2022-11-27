package pt.isec.gps.rookiesguidance.ui.text;

import pt.isec.gps.rookiesguidance.bd.ComandosDB;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ComandosDB cmd = new ComandosDB();
        cmd.criaTabela();
    }
}