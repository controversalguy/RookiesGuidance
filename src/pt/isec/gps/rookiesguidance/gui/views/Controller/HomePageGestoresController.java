
package pt.isec.gps.rookiesguidance.gui.views.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import javax.swing.*;
import java.io.IOException;
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

public class HomePageGestoresController implements Initializable {
    @FXML
    private Button removerNovidade;
    @FXML
    private Button adicionarNovidade;
    ConnDB connDB;
    DatePickerSkin datePickerSkin;
    LocalDate localDate;
    Instant instant;
    Date date;
    DateFormat dateFormat;
    String strDate;
    @FXML
    private VBox detalhesCalendario; // TODO meter um pra um
    @FXML
    private VBox novidadesvBox;
    @FXML
    private HBox PICKER;
    @FXML
    private ImageView homePageIcon;
    ArrayList<String> novidades;
    ArrayList<Text> novidadesText;

    @FXML
    void onAdicionarNovidade() throws SQLException {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Novidades");
        dialog.setHeaderText("Inserir Novidades");

        ButtonType ok = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titulo = new TextField();
        titulo.setPromptText("Titulo:");
        TextArea descricao = new TextArea();
        descricao.setPromptText("Descrição:");
        descricao.setWrapText(true);
        grid.add(new Label("Título:"), 0, 0);
        grid.add(titulo, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1); //coluna 0 | linha 1
        grid.add(descricao, 1, 1);

        titulo.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 50 ? change : null));
        descricao.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 10000 ? change : null));

        Node okButton = dialog.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        titulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!titulo.textProperty().getValue().isEmpty() && !descricao.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });

        descricao.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!titulo.textProperty().getValue().isEmpty() && !descricao.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);

        });
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                try {
                    if (!connDB.adicionaNovidade(titulo.getText(), descricao.getText(), LoginController.getNumero())) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível adicionar novidade");
                        return null;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    novidadesText = new ArrayList<>();
                    novidades = connDB.getNovidades();
                    if (novidades.size() == 0) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "NOVIDADES NULL");
                    }
                    Text t;
                    for (int i = 0; i < novidades.size(); i++) {
                        if (i % 2 == 0) {
                            t = new Text();
                            t.setText("\n" + novidades.get(i));
                            t.setStyle("-fx-font-weight: bold;");
                        } else {
                            t = new Text();
                            t.setText(novidades.get(i));
                        }
                        novidadesText.add(t);
                    }
                    novidadesvBox.getChildren().clear();
                    if(novidades.size()!=0)
                        novidadesvBox.getChildren().addAll(novidadesText);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    void onEventosPressed() {
            ViewSwitcher.switchTo(View.EVENTOS);
    }
    @FXML
    void onIconPressed() {
        ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
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
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Erro ao terminar sessão");
    }
    @FXML
    public void onRemoverNovidade() throws SQLException {
        try {
            novidades = connDB.getNovidades();
            if (novidades.isEmpty()) {
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe novidades para remover");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        novidadesText = new ArrayList<>();

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Novidades");
        dialog.setHeaderText("Remover Novidades");

        //dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        ArrayList<Button> id = new ArrayList<>();

        for (int i = 0; i < novidades.size(); i+=2) {
            int index = i/2;
            id.add(new Button(novidades.get(i)));
            System.out.println("novidades.get(i): "+novidades.get(i));
            System.out.println(novidades);
            id.get(index).setOnAction(mouseEvent -> {
                dialog.setResult(String.valueOf(index));
            });
            grid.add(new Label("Novidade:"), index, index);
            grid.add(id.get(index), index + 1, index);
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        System.out.println("NOVIDADES: " + novidades);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        try {
            if (!connDB.removeNovidade(Integer.parseInt(result.get()), LoginController.getNumero())) {
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe novidades para remover");
                return;
            }

            novidades = connDB.getNovidades();

            if (novidades == null) {
                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe novidades para remover");
            }
            System.out.println("novidades:" + novidades);
            Text t;
            for (int i = 0; i < novidades.size(); i++) {
                if (i % 2 == 0) {
                    t = new Text();
                    t.setText("\n" + novidades.get(i));
                    t.setStyle("-fx-font-weight: bold;");
                } else {
                    t = new Text();
                    t.setText(novidades.get(i));
                }
                novidadesText.add(t);
            }
            novidadesvBox.getChildren().clear();
            novidadesvBox.getChildren().addAll(novidadesText);
        }catch (ClassCastException e) {
            dialog.close();
       }

    }
    @FXML
    public void onDiaPressed() {
        detalhesCalendario.getChildren().clear();
        LocalDate localDate = datePickerSkin.getSkinnable().getValue();
        instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        date = Date.from(instant);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        try {
            ArrayList<String> eventos;
            ArrayList<Text> eventosText = new ArrayList<>();
            eventos = connDB.getEventos(strDate);
            for (int i = 0; i < eventos.size(); i++) {
                Text t = new Text();
                //t.setStyle("-fx-font-weight: bold");

                String[] texto = eventos.get(i).split("\n");
                t.setText(texto[0]);
                t.setStyle("-fx-font-weight: bold");

                Text q = new Text();
                q.setText(texto[1] + "\n");

                //t.setText(eventos.get(i));
                eventosText.add(t);
                eventosText.add(q);
            }
            detalhesCalendario.getChildren().addAll(eventosText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        PICKER.getChildren().add(popupContent);
        localDate = datePickerSkin.getSkinnable().getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        strDate = dateFormat.format(date);

        try {
            ArrayList<String> eventos;
            ArrayList<Text> eventosText = new ArrayList<>();
            eventos = connDB.getEventos(strDate);
            for (int i = 0; i < eventos.size(); i++) {
                Text t = new Text();

                String[] texto = eventos.get(i).split("\n");
                t.setText(texto[0]);
                t.setStyle("-fx-font-weight: bold");

                Text q = new Text();
                q.setText(texto[1] + "\n");

                eventosText.add(t);
                eventosText.add(q);
            }
            detalhesCalendario.getChildren().addAll(eventosText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {

            novidadesText = new ArrayList<>();
            novidades = connDB.getNovidades();
            if (novidades == null) {
                return;
            }
            Text t;
            for (int i = 0; i < novidades.size(); i++) {
                if (i % 2 == 0) {
                    t = new Text();
                    t.setText("\n" + novidades.get(i));
                    t.setStyle("-fx-font-weight: bold;");
                } else {
                    t = new Text();
                    t.setText(novidades.get(i));
                }
                novidadesText.add(t);
            }
            novidadesvBox.getChildren().addAll(novidadesText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
