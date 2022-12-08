package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventosController implements Initializable {
    @FXML
    private HBox PICKER;
    @FXML
    private ImageView homePageIcon;

    @FXML
    private Label teste;

    @FXML
    DatePicker datePicker =new DatePicker(LocalDate.now()) ;

    @FXML
    void handleButtonClick(MouseEvent event) {
//        final Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(ViewSwitcher.getScene().getWindow());
//        VBox dialogVbox = new VBox(20);
//        dialogVbox.getChildren().add(new Text("This is a Dialog"));
//        Scene dialogScene = new Scene(dialogVbox, 300, 200);
//        dialog.setScene(dialogScene);
//        dialog.show();
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Eventos");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        Text text = new Text("Teste"); //Inserir nome de evento
        ButtonType insertButtonType = new ButtonType("Insert", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);


        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Button detalhes = new Button("Ver detalhes");
        Button atualizar = new Button("Atualizar");
        Button remover = new Button("Remover");;


        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(detalhes, 1, 0);
        grid.add(new Label("hora:"), 0, 1); //coluna 0 | linha 1
        grid.add(atualizar, 1, 1);
        grid.add(new Label("localizacao:"), 0, 2);
        grid.add(remover, 1, 2);



        // Enable/Disable login button depending on whether a username was entered.
        Node insertButton = dialog.getDialogPane().lookupButton(insertButtonType);
        insertButton.setDisable(true);
        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();
    }


    @FXML
    void onIconPressed() {ViewSwitcher.switchTo(View.HOMEPAGE);  }
    @FXML
    void onEventosPressed() {
        ViewSwitcher.switchTo(View.EVENTOS);
    }

    @FXML
    void onInformacoesPressed() {
        ViewSwitcher.switchTo(View.INFORMACOES);
    }

    @FXML
    void onPerfilPressed() {
        ViewSwitcher.switchTo(View.PERFIL);
    }

    @FXML
    void onPerguntasPressed() {
        ViewSwitcher.switchTo(View.PERGUNTAS);
    }

    @FXML
    void onTerminarSessaoPressed() {
        ViewSwitcher.switchTo(View.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        PICKER.getChildren().add(popupContent);
    }

}
