package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

public class RegistoController {
    @FXML
    void buttonPressed() {
        ViewSwitcher.switchTo(View.LOGIN);
    }

    @FXML
    void onButtonLogin() {
        ViewSwitcher.switchTo(View.LOGIN);
    }
}
