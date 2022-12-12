
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

public class HomePageController implements Initializable {
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
    void onEventosPressed() {
        ViewSwitcher.switchTo(View.EVENTOS_ESTUDANTE);
    }
    @FXML
    void onIconPressed() {ViewSwitcher.switchTo(View.HOMEPAGE);  }
    @FXML
    void onInformacoesPressed() {
        ViewSwitcher.switchTo(View.INFORMACOES_ESTUDANTE);
    }

    @FXML
    void onPerfilPressed() {
        ViewSwitcher.switchTo(View.PERFIL);
    }

    @FXML
    void onPerguntasPressed() {
        ViewSwitcher.switchTo(View.PERGUNTAS_ESTUDANTE);
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
