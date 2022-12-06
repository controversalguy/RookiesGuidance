package pt.isec.gps.rookiesguidance.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pt.isec.gps.rookiesguidance.model.fsm.RookiesGuidanceState;

public class AutenticationUI extends BorderPane {
    SistemManager sistemaManager;
    Label lbEstado;
    public AutenticationUI(SistemManager sistemaManager) {
        this.sistemaManager = sistemaManager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        lbEstado = new Label("Autentica State");
        lbEstado.setPadding(new Insets(15));

        setAlignment(lbEstado, Pos.TOP_CENTER);

        lbEstado.setTranslateY(80);
        this.setTop(lbEstado);

    }

    private void registerHandlers() {

    }

    private void update() {
        if (sistemaManager.getState() != RookiesGuidanceState.AUTENTICADOR) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}
