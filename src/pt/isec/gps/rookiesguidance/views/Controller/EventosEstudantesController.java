package pt.isec.gps.rookiesguidance.views.Controller;

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
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

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

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class EventosEstudantesController implements Initializable {
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
                ToastMessage.show(getScene().getWindow(), "Não existem eventos neste dia!");
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

//            if(eventosText.get(1).getText().equals()) TODO
//                atualizar.setDisable(true);



            id.add(detalhes);

            int j = i;

            id.get(0).setOnAction(actionEvent -> {
                System.out.println("ID" + eventosText.get(j).getText());
                idEvento = Integer.parseInt(eventosText.get(j).getText());
                ViewSwitcher.switchTo(View.INSCREVE_EVENTO);
                dialog.close();
            });


            VBox vBoxButoesevento = new VBox(detalhes);
            vBoxButoesevento.setSpacing(5);
            eventosText.get(i+1).setFont(javafx.scene.text.Font.font(Font.BOLD));
            HBox[] boxInfo = new HBox[3];
            for (int k = 1; k < 4; k++) {
                boxInfo[k-1] = new HBox(eventosText.get(i+k));
            }
            VBox vBoxdadosEvento = new VBox(boxInfo[0],boxInfo[1],boxInfo[2]);
            vBoxdadosEvento.setSpacing(5);


            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            HBox hBoxdadosEventoTotais = new HBox(vBoxdadosEvento,region,vBoxButoesevento);



            hBoxdadosEventoTotais.setSpacing(100);
            detalhesEventos.getChildren().add(hBoxdadosEventoTotais);
            detalhesEventos.setSpacing(15);


            dialog.getDialogPane().setContent(detalhesEventos);

        }

        Optional<String> result = dialog.showAndWait();

    }

    @FXML
    void onIconPressed()
    {
        ViewSwitcher.switchTo(View.HOMEPAGE);
    }

    @FXML
    void onEventosPressed() {
        ViewSwitcher.switchTo(View.EVENTOS_ESTUDANTE);
    }

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

    public static int getIdEvento() {
        return idEvento;
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
    }

}

