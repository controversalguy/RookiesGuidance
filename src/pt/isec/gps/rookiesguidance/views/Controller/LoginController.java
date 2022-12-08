package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.time.LocalDate;

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
