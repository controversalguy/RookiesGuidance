package pt.isec.gps.rookiesguidance.ui.text.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ViewSwitcher {

    private static Scene scene;
    public static Parent root;
    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }
    public static Scene getScene() {
        return scene.getRoot().getScene();
    }
    public static Parent getRoot() {
        return scene.getRoot();
    }

    public static void switchTo(View view) {
        if (view == null) {
            System.out.println("No scene was set");
            return;
        }

        try {
            root = FXMLLoader.load(ViewSwitcher.class.getResource(view.getFilename()));
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
