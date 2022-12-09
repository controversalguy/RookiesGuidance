package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class LoginController implements Initializable {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    ConnDB connDB;
    static Long nrUtilizador;
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
            }
            String numero = emailText.substring(1, 11);

            nrUtilizador = Long.parseLong(numero);
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

    public static long getNumero() {
        return nrUtilizador;
    }

}
