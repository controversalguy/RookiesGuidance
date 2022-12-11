package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class EditarPerfilController implements Initializable {

    ConnDB connDB;
    @FXML
    private Text nomeUser;

    @FXML
    private ChoiceBox dropdownCurso;

    @FXML
    private PasswordField passAntiga;
    @FXML
    private PasswordField passNova;
    @FXML
    private ImageView homePageIcon;
    @FXML
    void onIconPressed() {ViewSwitcher.switchTo(View.HOMEPAGE);  }
    @FXML
    void onEventosPressed() {
        ViewSwitcher.switchTo(View.EVENTOS);
    }

    @FXML
    void onInformacoesPressed() {
        ViewSwitcher.switchTo(View.INFORMACOES);
    }

    @FXML
    void onPerfilPressed() {
        ViewSwitcher.switchTo(View.PERFIL);
    }

    @FXML
    void onPerguntasPressed() {
        ViewSwitcher.switchTo(View.PERGUNTAS);
    }

    @FXML
    void onTerminarSessaoPressed() throws SQLException {

        if(connDB.logout(LoginController.getNumero())){
            ToastMessage.show(getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(getScene().getWindow(), "Erro ao terminar sessão");
    }

    @FXML
    void onConfirmarPressed() throws SQLException {
        boolean update = false;
        boolean atualizou = false;

        //nao inseriu a passe
        /*if (passAntiga.getText().isEmpty() || passAntiga.getText().isEmpty()) {
            ToastMessage.show(getScene().getWindow(), "Insira a palavra-passe!");
            ViewSwitcher.switchTo(View.PERFIL);
        }*/
        String edita = "";

        //inseriu a passe e não é igual à q tinha
        if(!passAntiga.getText().isEmpty()) {
            if (!passAntiga.getText().trim().equals(LoginController.getPasse().trim())) {
                edita +=  "Insira a palavra-passe correta!";
                //ToastMessage.show(getScene().getWindow(), "Insira a palavra-passe correta!");
                //ViewSwitcher.switchTo(View.PERFIL);
            } else
                update = true;

            if (!passNova.getText().isEmpty()) {
                if (update) {
                    connDB.editaUtilizador(LoginController.getNumero(), passNova.getText(), 0);
                    edita +=  "Password mudada com sucesso!";
                    atualizou = true;
                    //ToastMessage.show(getScene().getWindow(), "Password mudada com sucesso!");
                }
            }
        }

        if(!dropdownCurso.getSelectionModel().isEmpty()) {
            connDB.editaUtilizador(LoginController.getNumero(), (String) dropdownCurso.getSelectionModel().getSelectedItem(), 1);
            //ToastMessage.show(getScene().getWindow(), "Curso mudado com sucesso!");
            edita +=  "\nCurso mudado com sucesso!";
            atualizou = true;
        }

        //if( (passAntiga.getText().isEmpty() || passNova.getText().isEmpty()) && dropdownCurso.getSelectionModel().isEmpty()) {
          if(!atualizou) {
            edita +=  "\nPreencha os campos!";
            ToastMessage.show(getScene().getWindow(), edita);
        }
        else {
            ToastMessage.show(getScene().getWindow(), edita);
            ViewSwitcher.switchTo(View.PERFIL);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nomeUser.setText(LoginController.getNome());
        dropdownCurso.getItems().addAll("Engenharia Informática","Engenharia Mecânica","Engenharia Eletrotécnica");
    }
}
