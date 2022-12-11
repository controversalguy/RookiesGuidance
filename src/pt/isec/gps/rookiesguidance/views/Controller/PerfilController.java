package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class PerfilController implements Initializable {

    ConnDB connDB;
    @FXML
    private Text nomeUser;
    @FXML
    private Text cursoUser;

    @FXML
    private Text numeroUser;
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
    void onEditarPerfilPressed() {ViewSwitcher.switchTo(View.EDITAR_PERFIL);}

    @FXML
    void onEliminarPerfilPressed() throws SQLException {
        connDB.removeUtilizador(LoginController.getNumero());
        ViewSwitcher.switchTo(View.LOGIN);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> credenciais;
        nomeUser.setText(LoginController.getNome());
        try {
            credenciais = connDB.getUser(LoginController.getEmail());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        nomeUser.setText(credenciais.get(0));
        cursoUser.setText(credenciais.get(1));
        numeroUser.setText(String.valueOf(LoginController.getNumero()));
    }
}
