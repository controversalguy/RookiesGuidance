package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EventosController implements Initializable {
    @FXML
    private HBox PICKER;
    @FXML
    private VBox eventosVBox;
    @FXML
    private VBox butoesVBox;

    ArrayList<Text> eventosText;
    ArrayList<Button> eventosBts;
    @FXML
    private ImageView homePageIcon;
    ConnDB connDB;
    @FXML
    private Label teste;

    @FXML
    DatePicker datePicker;

    ArrayList<String> eventos;
    ArrayList<String> evTipoLocal;

    @FXML
    void handleButtonClick(MouseEvent event) throws SQLException {
//        final Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.initOwner(ViewSwitcher.getScene().getWindow());
//        VBox dialogVbox = new VBox(20);
//        dialogVbox.getChildren().add(new Text("This is a Dialog"));
//        Scene dialogScene = new Scene(dialogVbox, 300, 200);
//        dialog.setScene(dialogScene);
//        dialog.show();

//        System.out.println(datePicker.getValue());
        String piker = String.valueOf(datePicker.getValue());
        String[] tokensPiker = piker.split("-");
        boolean match = false;


        try {
            eventosBts = new ArrayList<>();
            eventosText = new ArrayList<>();
            evTipoLocal = new ArrayList<>();
            eventos = connDB.getEventos2();
            System.out.println(eventos);
            Text t;
            Button b;
            for (String s : eventos) {
                if (!s.matches(".*[0-9].*"))
                    evTipoLocal.add(s);
            }
            //int i=0;
            for (int k = 0; k < eventos.size(); k++) {
                if (eventos.get(k).matches(".*[0-9].*")) {
                    String[] token = eventos.get(k).split("-");
                    if (token[0].equals(tokensPiker[2])) {
                        match = true;
                        for (int i = 0; i < evTipoLocal.size(); i++) {
                            if (eventos.get(k - 1).equals(evTipoLocal.get(i))) {
                                b = new Button("Ver detalhes");
                                eventosBts.add(b);
                                b = new Button("Atualizar");
                                eventosBts.add(b);
                                b = new Button("Remover");
                                eventosBts.add(b);
                                t = new Text();
                                t.setText("\n" + evTipoLocal.get(i));
                                t.setStyle("-fx-font-weight: bold;");
                                eventosText.add(t);
                                t = new Text();
                                t.setText(eventos.get(k));
                                eventosText.add(t);
                                t = new Text();
                                t.setText(evTipoLocal.get(i + 1));
                                eventosText.add(t);
                                break;
                            }
                        }
                    }
                }
            }
            eventosVBox.getChildren().clear();
            eventosVBox.getChildren().addAll(eventosText);
            butoesVBox.getChildren().clear();
            butoesVBox.getChildren().addAll(eventosBts);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Text t:eventosText) {

        }

        for (Button b : eventosBts) {
            if (b.getText().equals("Ver detalhes")) {
                b.setOnAction(e -> {
                    popup();
                });
            }
        }


        if (!match) {
            insereEventoPopUp();
        }


//        Dialog<String> dialog = new Dialog<>();
//        dialog.setTitle("Eventos");
//
//        // Set the icon (must be included in the project).
//        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
//
//        // Set the button types.
//        Text text = new Text("Teste"); //Inserir nome de evento
//        ButtonType insertButtonType = new ButtonType("Insert", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);
//
//
//        // Create the username and password labels and fields.
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));
//
//        Button detalhes = new Button("Ver detalhes");
//        Button atualizar = new Button("Atualizar");
//        Button remover = new Button("Remover");
//        ;
//
//
//        grid.add(new Label("Tipo:"), 0, 0);
//        grid.add(detalhes, 1, 0);
//        grid.add(new Label("hora:"), 0, 1); //coluna 0 | linha 1
//        grid.add(atualizar, 1, 1);
//        grid.add(new Label("localizacao:"), 0, 2);
//        grid.add(remover, 1, 2);
//
//
//        // Enable/Disable login button depending on whether a username was entered.
//        Node insertButton = dialog.getDialogPane().lookupButton(insertButtonType);
//        insertButton.setDisable(true);
//        dialog.getDialogPane().setContent(grid);
//
//        dialog.showAndWait();
    }

    void insereEventoPopUp() throws SQLException {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Eventos");
        dialog.setHeaderText("Inserir Evento");

        ButtonType insertButtonType = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tipo = new TextField();
        tipo.setPromptText("Tipo:");
        TextField data = new TextField();
        data.setPromptText("Data:");
        TextField horas = new TextField();
        horas.setPromptText("Horas:");
        TextField local = new TextField();
        local.setPromptText("Local:");

        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(tipo, 1, 0);
        grid.add(new Label("Data:"), 0, 1); //coluna 0 | linha 1
        grid.add(data, 1, 1);
        grid.add(new Label("Horas:"), 0, 2); //coluna 0 | linha 1
        grid.add(horas, 1, 2);
        grid.add(new Label("Local:"), 0, 3); //coluna 0 | linha 1
        grid.add(local, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        connDB.adicionaEvento(LoginController.getNumero(), tipo.getText(), local.getText(), data.getText() + horas.getText());
    }
    void popup() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ViewSwitcher.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @FXML
    void onIconPressed() {
        ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
    }

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

        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        PICKER.getChildren().add(popupContent);
    }

}

