
package pt.isec.gps.rookiesguidance.views.Controller;
import javafx.event.ActionEvent;
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

        ButtonType insertButtonType = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField titulo = new TextField();
        titulo.setPromptText("Titulo:");
        TextArea descricao = new TextArea();
        descricao.setPromptText("Descrição:");

        grid.add(new Label("Título:"), 0, 0);
        grid.add(titulo, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1); //coluna 0 | linha 1
        grid.add(descricao, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        connDB.addNovidade(titulo.getText(), descricao.getText(), LoginController.getNumero());

        try {

            novidadesText = new ArrayList<>();
            novidades = connDB.getNovidades();
            Text t;
            for (int i = 0; i < novidades.size(); i++) {
                if(i % 2 ==0){
                    t = new Text();
                    t.setText("\n" + novidades.get(i));
                    t.setStyle("-fx-font-weight: bold;");
                }else{
                    t = new Text();
                    t.setText(novidades.get(i));
                }
                novidadesText.add(t);
            }
            novidadesvBox.getChildren().clear();
            novidadesvBox.getChildren().addAll(novidadesText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onEventosPressed() {
        ViewSwitcher.switchTo(View.EVENTOS);
    }
    @FXML
    void onIconPressed() {ViewSwitcher.switchTo(View.HOMEPAGE);  }
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
    @FXML
    public void onRemoverNovidade() throws SQLException {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Novidades");
        dialog.setHeaderText("Remover Novidades");

        ButtonType insertButtonType = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(insertButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField id = new TextField();
        id.setPromptText("Número da Novidade:");

        grid.add(new Label("Novidade:"), 0, 0);
        grid.add(id, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        connDB.removeNovidade(Integer.parseInt(id.getText()),LoginController.getNumero());
        try {

            novidadesText = new ArrayList<>();
            novidades = connDB.getNovidades();
            Text t;
            for (int i = 0; i < novidades.size(); i++) {
                if(i % 2 ==0){
                    t = new Text();
                    t.setText("\n" + novidades.get(i));
                    t.setStyle("-fx-font-weight: bold;");
                }else{
                    t = new Text();
                    t.setText(novidades.get(i));
                }
                novidadesText.add(t);
            }
            novidadesvBox.getChildren().clear();
            novidadesvBox.getChildren().addAll(novidadesText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                t.setText(eventos.get(i));
                eventosText.add(t);
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
        try{
            ArrayList<String> eventos;
            ArrayList<Text> eventosText = new ArrayList<>();
            eventos = connDB.getEventos(strDate);
            for (int i = 0; i < eventos.size(); i++) {
                Text t = new Text();
                t.setText(eventos.get(i));
                eventosText.add(t);
            }
            detalhesCalendario.getChildren().addAll(eventosText);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {

            novidadesText = new ArrayList<>();
            novidades = connDB.getNovidades();
            Text t;
            for (int i = 0; i < novidades.size(); i++) {
                if(i % 2 ==0){
                    t = new Text();
                    t.setText("\n" + novidades.get(i));
                    t.setStyle("-fx-font-weight: bold;");
                }else{
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
