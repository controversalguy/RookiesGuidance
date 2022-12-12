package pt.isec.gps.rookiesguidance.gui.views.Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Estudante logado com sucesso");
                ViewSwitcher.switchTo(View.HOMEPAGE);
            }else if((valor == 1)){
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Gestor logado com sucesso");
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
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Credenciais inválidas!");
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
        email.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 20 ? change : null));
        password.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 15 ? change : null));
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
