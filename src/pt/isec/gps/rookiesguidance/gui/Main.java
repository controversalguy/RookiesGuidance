package pt.isec.gps.rookiesguidance.gui;

import javafx.application.Application;
import pt.isec.gps.rookiesguidance.bd.ConnDB;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        Application.launch(MainJFX.class,args);
        ConnDB c = new ConnDB();
        //c.criaTabelas();
        c.disconnectAll();
    }

}