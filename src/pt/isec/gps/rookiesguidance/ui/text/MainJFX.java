package pt.isec.gps.rookiesguidance.ui.text;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.isec.gps.rookiesguidance.ui.text.views.View;
import pt.isec.gps.rookiesguidance.ui.text.views.ViewSwitcher;

public class MainJFX extends Application {
    @Override
   public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {

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
