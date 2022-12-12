package pt.isec.gps.rookiesguidance.gui.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    void onIconPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.HOMEPAGE);
        else
            ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
    }

    @FXML
    void onEventosPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.EVENTOS_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.EVENTOS);
    }

    @FXML
    void onInformacoesPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.INFORMACOES_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.INFORMACOES);
    }

    @FXML
    void onPerfilPressed() {ViewSwitcher.switchTo(View.PERFIL);}

    @FXML
    void onPerguntasPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.PERGUNTAS_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.PERGUNTAS);
    }

    @FXML
    void onTerminarSessaoPressed() throws SQLException {
        if(connDB.logout(LoginController.getNumero())){
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Erro ao terminar sessão");
    }

    @FXML
    void onConfirmarPressed() throws SQLException {
        boolean update = false;
        boolean atualizou = false;

        //nao inseriu a passe
        String edita = "";

        //inseriu a passe e não é igual à q tinha
        if(!passAntiga.getText().isEmpty()) {
            if (!passAntiga.getText().trim().equals(LoginController.getPasse().trim())) {
                edita +=  "Insira a palavra-passe correta!";
            } else
                update = true;

            if (!passNova.getText().isEmpty()) {
                if (update) {
                    connDB.editaUtilizador(LoginController.getNumero(), passNova.getText(), 0);
                    edita +=  "Password mudada com sucesso!";
                    atualizou = true;

                }
            }
        }

        if(!dropdownCurso.getSelectionModel().isEmpty()) {
            connDB.editaUtilizador(LoginController.getNumero(), (String) dropdownCurso.getSelectionModel().getSelectedItem(), 1);

            edita +=  "\nCurso mudado com sucesso!";
            atualizou = true;
        }

        //if( (passAntiga.getText().isEmpty() || passNova.getText().isEmpty()) && dropdownCurso.getSelectionModel().isEmpty()) {
          if(!atualizou) {
            edita +=  "\nPreencha os campos!";
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), edita);
        }
        else {
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), edita);
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
