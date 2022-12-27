package pt.isec.gps.rookiesguidance.gui.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static int idEvento;

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
        horaInicio.setPromptText("00:00");
        TextField local = new TextField();

        grid.add(new Label("Tipo do evento:"), 0, 0);
        grid.add(tipo, 1, 0);
        grid.add(new Label("Data:"), 0, 1); //coluna 0 | linha 1
        grid.add(data, 1, 1);
        grid.add(new Label("Hora:"), 0, 2); //coluna 0 | linha 1
        grid.add(horaInicio, 1, 2);
        grid.add(new Label("Local do evento:"), 0, 3); //coluna 0 | linha 1
        grid.add(local, 1, 3);

        local.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 30 ? change : null));

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

                    String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
                    Pattern pattern;
                    Matcher matcher;

                    pattern = Pattern.compile(regex);
                    matcher = pattern.matcher(horaInicio.getText());

                    if(!matcher.matches()) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Preencha os campos corretamente");
                        onAdicionarPressed();
                        return null;
                    }

                    LocalDate localDate = data.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    strDate[0] = dateFormat.format(date);
                    System.out.println(strDate[0]);

                    String data_hora = strDate[0] + " " + horaInicio.getText();

                    if (data.getValue().compareTo(LocalDate.now()) < 0) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Data Inválida");
                        onAdicionarPressed();
                        return null;
                    }

                    if (!connDB.adicionaEvento(LoginController.getNumero(),tipo.getText(), local.getText(), data_hora)) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível adicionar o evento!");
                        onAdicionarPressed();
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
            eventos = connDB.getEventos2(strDate);
            Text t;

            if(eventos.size() == 0) {
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existem eventos neste dia!");
                return;
            }

            for (int i = 0; i < eventos.size(); i+=4) { //i++
                for(int j = 0; j < 4; j ++) {
                    t = new Text();
                    t.setText(eventos.get(i + j)); //0 -> id //1 -> tipo // 2 -> hora // 3 -> local
                    if(j == 1) {
                        t.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
                        //t.setStyle("-fx-font-weight: bold");
                    }
                    eventosText.add(t);
                }
            }
            //PICKER.getChildren().addAll(eventosText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Eventos");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);


        VBox detalhesEventos = new VBox();
        for (int i = 0; i < eventosText.size() ; i+=4) {
            ArrayList<Button> id = new ArrayList<>();

            Button detalhes = new Button("Detalhes");
            detalhes.setStyle("-fx-background-color: #38819c; -fx-font-weight: bold; -fx-text-fill: #ffffff;");
            detalhes.minWidth(100);
            Button atualizar = new Button("Atualizar");
//            if(eventosText.get(1).getText().equals()) TODO
//                atualizar.setDisable(true);
            atualizar.setStyle("-fx-background-color: #38819c; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
            atualizar.minWidth(100);
            Button remover = new Button("Remover");
            remover.setStyle("-fx-background-color: #38819c; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
            remover.minWidth(100);

            id.add(detalhes);
            id.add(atualizar);
            id.add(remover);

            int j = i;

            id.get(0).setOnAction(actionEvent -> {
                System.out.println("ID" + eventosText.get(j).getText());
                idEvento = Integer.parseInt(eventosText.get(j).getText());
                ViewSwitcher.switchTo(View.INSCREVE_EVENTO);
                dialog.close();
            });
            id.get(1).setOnAction(actionEvent -> {

                Dialog<String> dialog2 = new Dialog<>();
                dialog2.setTitle("Eventos");
                dialog2.setHeaderText("Editar Evento");

                ButtonType ok = new ButtonType("Editar", ButtonBar.ButtonData.OK_DONE);
                dialog2.getDialogPane().getButtonTypes().addAll(ok);

                // Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField tipo = new TextField();
                tipo.setText(eventosText.get(j+1).getText());
                DatePicker data = new DatePicker(datePickerSkin.getSkinnable().getValue());
                TextField horaInicio = new TextField();
                horaInicio.setText(eventosText.get(j+2).getText());
                TextField local = new TextField();
                local.setText(eventosText.get(j+3).getText());

                grid.add(new Label("Tipo do evento:"), 0, 0);
                grid.add(tipo, 1, 0);
                grid.add(new Label("Data:"), 0, 1); //coluna 0 | linha 1
                grid.add(data, 1, 1);
                grid.add(new Label("Hora:"), 0, 2); //coluna 0 | linha 1
                grid.add(horaInicio, 1, 2);
                grid.add(new Label("Local do evento:"), 0, 3); //coluna 0 | linha 1
                grid.add(local, 1, 3);

                Node okButton = dialog2.getDialogPane().lookupButton(ok);
                okButton.setDisable(true);
                okButton.setStyle("-fx-background-color: #38819c; -fx-text-fill: #ffffff;");

                final String[] strDate2 = new String[1];

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

                dialog2.getDialogPane().setContent(grid);

                dialog2.setResultConverter(dialogButton -> {
                    if (dialogButton == ok) {

                        try {

                            String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
                            Pattern pattern;
                            Matcher matcher;

                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(horaInicio.getText());

                            if(!matcher.matches()) {
                                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Preencha os campos corretamente");
                                onDiaPressed();
                                return null;
                            }

                            LocalDate localDate2 = data.getValue();
                            Instant instant = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
                            Date date = Date.from(instant);
                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            strDate2[0] = dateFormat.format(date);
                            System.out.println(strDate2[0]);

                            String data_hora = strDate2[0] + " " + horaInicio.getText();

                            if (data.getValue().compareTo(LocalDate.now()) < 0) {
                                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Data Inválida");
                                onDiaPressed();
                                return null;
                            }

                            if (!connDB.editaEvento(Integer.parseInt(eventosText.get(j).getText()),tipo.getText(), local.getText(), data_hora, LoginController.getNumero())) {
                                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível editar o evento!");
                                return null;
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return null;
                });

                dialog2.showAndWait();

                dialog.close();
            });
            id.get(2).setOnAction(actionEvent -> {
                try {
                    if (!connDB.removeEvento(Integer.parseInt(eventosText.get(j).getText()), LoginController.getNumero())) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe evento para remover");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                dialog.close();
                /*try {
                    onDiaPressed();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }*/
            });
            VBox vBoxButoesevento = new VBox(detalhes,atualizar,remover);
            vBoxButoesevento.setSpacing(5);
            eventosText.get(i+1).setFont(javafx.scene.text.Font.font(Font.BOLD));
            HBox[] boxInfo = new HBox[3];
            for (int k = 1; k < 4; k++) {
                boxInfo[k-1] = new HBox(eventosText.get(i+k));
            }
            VBox vBoxdadosEvento = new VBox(boxInfo[0],boxInfo[1],boxInfo[2]);
            vBoxdadosEvento.setSpacing(5);
            //vBoxdadosEvento.setAlignment(Pos.CENTER_LEFT);
            //vBoxButoesevento.setAlignment(Pos.CENTER_RIGHT);

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            HBox hBoxdadosEventoTotais = new HBox(vBoxdadosEvento,region,vBoxButoesevento);

            // 77hBoxdadosEventoTotais.setAlignment(Pos.CENTER_RIGHT);
//            hBoxdadosEventoTotais.setMaxWidth(350);

            hBoxdadosEventoTotais.setSpacing(100);
            detalhesEventos.getChildren().add(hBoxdadosEventoTotais);
            detalhesEventos.setSpacing(15);



            dialog.getDialogPane().setContent(detalhesEventos);


//            if (!connDB.removeNovidade(Integer.parseInt(result.get()), LoginController.getNumero())) {
//                ToastMessage.show(getScene().getWindow(), "Não existe novidades para remover");
//                return;
//            }
        }

        Optional<String> result = dialog.showAndWait();

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

    public static int getIdEvento() {
        return idEvento;
    }

    @FXML
    void onTerminarSessaoPressed() throws SQLException {

        if(connDB.logout(LoginController.getNumero())){
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Erro ao terminar sessão");
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

