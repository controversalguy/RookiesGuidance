package pt.isec.gps.rookiesguidance.ui.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.io.IOException;
import java.util.Objects;

public class MainJFX extends Application {
    SistemManager sistemManager;
    @Override
   public void init() throws Exception {
        super.init();
        sistemManager = new SistemManager();
    }

    @Override
    public void start(Stage stage) throws IOException {
      /*  RootPane root = new RootPane(sistemManager);
        Scene scene = new Scene(root,700,750);
        stage.setScene(scene);
        stage.setTitle("Rookie's Guidance");
        stage.setMinWidth(700);
        stage.setMinHeight(750);
        stage.show();*/

        Scene scene = new Scene(new Pane());

        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.LOGIN);

        stage.setScene(scene);
        stage.show();

    }

}
