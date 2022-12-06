package pt.isec.gps.rookiesguidance.ui.gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainJFX extends Application {
    SistemManager sistemManager;
    @Override
   public void init() throws Exception {
        super.init();
        sistemManager = new SistemManager();
    }

    @Override
    public void start(Stage stage) {
        RootPane root = new RootPane(sistemManager);
        Scene scene = new Scene(root,700,750);
        stage.setScene(scene);
        stage.setTitle("Rookie's Guidance");
        stage.setMinWidth(700);
        stage.setMinHeight(750);
        stage.show();
    }

}
