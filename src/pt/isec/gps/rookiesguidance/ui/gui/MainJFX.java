package pt.isec.gps.rookiesguidance.ui.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class MainJFX extends Application {
    SistemManager sistemManager;
    @Override
   public void init() throws Exception {
        super.init();
        sistemManager = new SistemManager();
    }

    @Override
    public void start(Stage stage) {
//        try {
//            BorderPane root = new BorderPane();
//            Scene scene = new Scene(root, 400, 400);
//
//            DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
//            Node popupContent = datePickerSkin.getPopupContent();
//
//            root.setCenter(popupContent);
//
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Scene scene = new Scene(new Pane());

        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.LOGIN);
        stage.setMinHeight(550);
        stage.setMinWidth(750);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

}
