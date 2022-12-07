package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

public class LoginController {

    @FXML
    void buttonPressed() {
        ViewSwitcher.switchTo(View.HOMEPAGE);
    }

    @FXML
    void onButtonRegisto() {
        ViewSwitcher.switchTo(View.REGISTO);
    }
}
