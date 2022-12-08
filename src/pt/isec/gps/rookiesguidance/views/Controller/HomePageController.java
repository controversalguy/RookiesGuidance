
package pt.isec.gps.rookiesguidance.views.Controller;

        import javafx.application.Platform;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.geometry.Insets;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.control.skin.DatePickerSkin;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Text;
        import javafx.stage.Modality;
        import javafx.stage.Popup;
        import javafx.stage.PopupWindow;
        import javafx.stage.Stage;
        import javafx.stage.Window;
        import pt.isec.gps.rookiesguidance.views.View;
        import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

        import javax.swing.*;
        import java.net.URL;
        import java.time.LocalDate;
        import java.util.ResourceBundle;
        import java.util.Timer;
        import java.util.TimerTask;

        import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class HomePageController implements Initializable {
    @FXML
    private HBox PICKER;
    @FXML
    private ImageView homePageIcon;
    @FXML
    void onAdicionarNovidade() {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        PICKER.getChildren().add(popupContent);
    }
}
