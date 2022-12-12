package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class TwebController implements Initializable {
    ConnDB connDB;
    @FXML
    private ImageView homePageIcon;
    @FXML
    void onIconPressed() {
        if(LoginController.isGestor())
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
            ViewSwitcher.switchTo(View.INFORMACOES);    }

    @FXML
    void onPerfilPressed() {
        ViewSwitcher.switchTo(View.PERFIL);
    }

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
            ToastMessage.show(getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(getScene().getWindow(), "Erro ao terminar sessão");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connDB = new ConnDB();
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
