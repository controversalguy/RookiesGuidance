package pt.isec.gps.rookiesguidance.ui.gui;

import javafx.scene.layout.*;

public class RootPane extends BorderPane {
    SistemManager sistemaManager;

    public RootPane(SistemManager sistemaManager) {
        this.sistemaManager = sistemaManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        //CSSManager.applyCSS(this,"styles.css");
        StackPane stackPane = new StackPane(
                new AutenticationUI(sistemaManager)
        );
        this.setCenter(stackPane);
    }

    private void registerHandlers() {
        sistemaManager.addPropertyChangeListener(evt -> {
            update();
        });
    }

    private void update() {

    }
}
