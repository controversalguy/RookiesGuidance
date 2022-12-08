package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

public class PerfilController {
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
    void onTerminarSessaoPressed() {

        ViewSwitcher.switchTo(View.LOGIN);
    }

    @FXML
    void onEditarPerfilPressed() {ViewSwitcher.switchTo(View.EDITAR_PERFIL);}
}
