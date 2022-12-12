package pt.isec.gps.rookiesguidance.views.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class LoginController implements Initializable {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    ConnDB connDB;
    static int nrUtilizador;
    static String emailUtilizador;
    static String nomeUtilizador;
    static String cursoUtilizador;
    static String passeUtilizador;
    static boolean isGestor = false;
    @FXML
    void buttonPressed() throws SQLException {
        String emailText = email.getText();
        String passwordText  = password.getText();
        int valor = connDB.loginUtilizador(emailText, passwordText);
        if (valor>-1) {
            if(valor == 0){
                ToastMessage.show(getScene().getWindow(), "Estudante logado com sucesso");
                ViewSwitcher.switchTo(View.HOMEPAGE);
            }else if((valor == 1)){
                ToastMessage.show(getScene().getWindow(), "Gestor logado com sucesso");
                ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
                isGestor = true;
            }
            String numero = emailText.substring(1, 11);

            nrUtilizador = Integer.parseInt(numero);
            emailUtilizador = emailText;
            ArrayList<String> user = connDB.getUser(emailUtilizador);
            nomeUtilizador = user.get(0);
            cursoUtilizador = user.get(1);
            passeUtilizador = user.get(2);

        } else {
            ToastMessage.show(getScene().getWindow(), "Credenciais inválidas!");
        }
    }
    @FXML
    void onButtonRegisto() {
        ViewSwitcher.switchTo(View.REGISTO);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getNumero() {
        return nrUtilizador;
    }
    public static String getEmail() {
        return emailUtilizador;
    }
    public static String getNome() {
        return nomeUtilizador;
    }
    public static String getCurso() {
        return cursoUtilizador;
    }
    public static String getPasse() {
        return passeUtilizador;
    }
    public static boolean isGestor() {
        return isGestor;
    }

}
