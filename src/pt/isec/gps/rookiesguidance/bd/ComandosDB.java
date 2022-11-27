package pt.isec.gps.rookiesguidance.bd;

import java.sql.SQLException;

public class ComandosDB {

    public final String ANO_CALOIRO = "2022";

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

    int isGestor(String email){
        if(email.contains(ANO_CALOIRO)) return 0;
        return 1;
    }

    public void registo(int nrAluno, String nome,String email, String password, String curso) {
        boolean flag;
        try {
            ConnDB db = new ConnDB();

            flag = db.registaNovoUtilizador(nrAluno, nome, email, password, curso,isGestor(email));
            if(flag){
                System.out.println("Registo efetuado com Sucesso!");
            }else{
                System.out.println("Porfavor introduza outro username e nome! ");
            }
            db.close();
        } catch (SQLException e) {
            System.out.println("Erro no registo, o nome ou o username poderão estar já em uso, porfavor tente de novo");
            throw new RuntimeException(e);
        }
    }



    public int getID(String email) {
        int ID = 0;
        try {
            ConnDB db = new ConnDB();
            ID = db.getID(email);
            if (ID == -1) {
                System.out.println("Email não existe!!");
                return -1;
            } else {
                return ID;
            }

        } catch (SQLException e) {
            System.out.println("Erro na ligação com a base de Dados (getID()");
            //throw new RuntimeException(e);
            return -1;
        }
    }


    public int EfetuaLogin(String email,String password) {
        int ID;
        boolean flag;
        try {
            ConnDB db = new ConnDB();


            flag = db.loginUtilizador(email, password);
            if(!flag){
                db.close();
                throw new SQLException();
            }else{
                ID = db.getID(email);
                db.close();
                return ID;
            }

        } catch (SQLException e) {
            System.out.println("Erro na ligação com a base de Dados (Efetua Login)");
            //throw new RuntimeException(e);
            return -1;
        }
    }

}
