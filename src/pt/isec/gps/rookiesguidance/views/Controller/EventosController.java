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
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class EventosController implements Initializable {
    @FXML
    private HBox PICKER;
    @FXML
    private Button adicionarEventos;

    @FXML
    private ImageView homePageIcon;
    ConnDB connDB;
    DatePickerSkin datePickerSkin;
    LocalDate localDate;
    Instant instant;
    Date date;
    DateFormat dateFormat;
    String strDate;
    @FXML
    void onAdicionarPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Eventos");
        dialog.setHeaderText("Inserir Eventos");

        ButtonType ok = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tipo = new TextField();
        DatePicker data = new DatePicker(LocalDate.now());
        TextField horaInicio = new TextField();
        horaInicio.setPromptText("00h00");
        TextField local = new TextField();

        grid.add(new Label("Tipo do evento:"), 0, 0);
        grid.add(tipo, 1, 0);
        grid.add(new Label("Data:"), 0, 1); //coluna 0 | linha 1
        grid.add(data, 1, 1);
        grid.add(new Label("Hora:"), 0, 2); //coluna 0 | linha 1
        grid.add(horaInicio, 1, 2);
        grid.add(new Label("Local do evento:"), 0, 3); //coluna 0 | linha 1
        grid.add(local, 1, 3);

        Node okButton = dialog.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        okButton.setStyle("-fx-background-color: #38819c; -fx-text-fill: #ffffff;");

        final String[] strDate = new String[1];

        tipo.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!tipo.textProperty().getValue().isEmpty() && (data.getValue() != null) && !horaInicio.textProperty().getValue().isEmpty() && !local.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });
        data.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!tipo.textProperty().getValue().isEmpty() && (data.getValue() != null) && !horaInicio.textProperty().getValue().isEmpty() && !local.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });
        horaInicio.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!tipo.textProperty().getValue().isEmpty() && (data.getValue() != null) && !horaInicio.textProperty().getValue().isEmpty() && !local.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });
        local.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!tipo.textProperty().getValue().isEmpty() && (data.getValue() != null) && !horaInicio.textProperty().getValue().isEmpty() && !local.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                try {

                    LocalDate localDate = data.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    strDate[0] = dateFormat.format(date);
                    System.out.println(strDate[0]);

                    String data_hora = strDate[0] + " " + horaInicio.getText();
                    if (!connDB.adicionaEvento(LoginController.getNumero(),tipo.getText(), local.getText(), data_hora)) {
                        ToastMessage.show(getScene().getWindow(), "Não foi possível adicionar o evento!");
                        return null;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    void onDiaPressed() throws SQLException {
        LocalDate localDate = datePickerSkin.getSkinnable().getValue();
        instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        date = Date.from(instant);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        ArrayList<String> eventos;
        ArrayList<Text> eventosText = new ArrayList<>();
        try {
            eventos = connDB.getEventos(strDate);
            for (int i = 0; i < eventos.size(); i++) {
                Text t = new Text();
                t.setText(eventos.get(i));
                eventosText.add(t);
            }
            //PICKER.getChildren().addAll(eventosText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //popup
        ArrayList<Button> id = new ArrayList<>();
        for (int i = 0; i < eventos.size() ; i++) {
            Button detalhes = new Button("Detalhes");
            Button atualizar = new Button("Atualizar");
            Button remover = new Button("Remover");
            id.add(detalhes);
            id.add(atualizar);
            id.add(remover);
            HBox hbevento = new HBox(detalhes,atualizar,remover);
        }
        VBox detalhesEventos = new VBox();

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
    void onTerminarSessaoPressed() throws SQLException {

        if(connDB.logout(LoginController.getNumero())){
            ToastMessage.show(getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(getScene().getWindow(), "Erro ao terminar sessão");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();

        popupContent.setScaleX(1.7);
        popupContent.setScaleY(1.7);
        PICKER.getChildren().add(popupContent);
        PICKER.setLayoutX(190);
        PICKER.setLayoutY(175);
        adicionarEventos.setLayoutY(480);
        adicionarEventos.setLayoutX(63);
    }

}

