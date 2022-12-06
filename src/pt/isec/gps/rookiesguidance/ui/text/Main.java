package pt.isec.gps.rookiesguidance.ui.text;

import javafx.application.Application;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.ui.gui.MainJFX;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args){
        Application.launch(MainJFX.class,args);
    }
//        ConnDB cDb = new ConnDB();
//        cDb.criaTabelas();
////        if (cDb.removeRegisto(2019133920))
////            System.out.println("Eliminado aluno com sucesso!");
//        if (cDb.registaNovoUtilizador(2019133920, "Francisco Simões", "a2019133920@isec.pt", "IS3C..0", "LEI"))
//            System.out.println("Registado com Sucesso!");
//        //cDb.addNovidade("luva pedreiro","RECEBAAAAAAAAAAAAAAAAAA",2019133920);
//    }
}