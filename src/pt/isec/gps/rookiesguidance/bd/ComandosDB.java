package pt.isec.gps.rookiesguidance.bd;

import java.sql.SQLException;

public class ComandosDB {

    public void criaTabela() {
        try {
            ConnDB db = new ConnDB();
            db.createTables();
            db.close();
        } catch (SQLException e) {
            System.out.println("Erro no registo, o nome ou o username poderão estar já em uso, porfavor tente de novo");
            throw new RuntimeException(e);
        }

    }
}
