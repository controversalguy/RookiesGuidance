package pt.isec.gps.rookiesguidance.views.Controller;

import com.sun.webkit.ColorChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.w3c.dom.css.RGBColor;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PerguntasController implements Initializable {
    @FXML
    private ImageView homePageIcon;
    @FXML
    private javafx.scene.control.Button adicionaPergunta;
    @FXML
    private VBox vBox;
    @FXML
    void onAdicionarPerguntaPressed() {
        Text t = new Text("Pergunta:");
        javafx.scene.control.Button bResponderPergunta= new javafx.scene.control.Button("Responder\n Pergunta");
        javafx.scene.control.Button bRemoverPergunta= new javafx.scene.control.Button("Remover\n Pergunta");
        javafx.scene.control.TextField tf = new javafx.scene.control.TextField();

        bRemoverPergunta.setMaxSize(90, 40);
        bResponderPergunta.setMaxSize(90, 40);
        tf.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox hBox = new HBox(t,tf);
        hBox.setMinSize(300,40);
        HBox hBox1 = new HBox(hBox,bResponderPergunta,bRemoverPergunta);
        Color c = Color.rgb(56, 129, 156);
        VBox group = new VBox(hBox1,adicionaPergunta);
        hBox.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().add(group);

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


    }
//    @FXML
//    public void respondePergunta1() {
//        javafx.scene.control.TextField t = new javafx.scene.control.TextField();
//        t.setMinSize(300, 20);
//        HBox h = new HBox(new Text("Resposta:"),t);
//        pergunta1.getChildren().add(h);
//    }
//    @FXML
//    public void respondePergunta2() {
//        javafx.scene.control.TextField t = new javafx.scene.control.TextField();
//        t.setMinSize(300, 20);
//        HBox h = new HBox(new Text("Resposta:"),t);
//        pergunta2.getChildren().add(h);
//        if(pergunta2.getHeight() != pergunta2.getMaxHeight()){
//            pergunta2.setPrefHeight(pergunta2.getMaxHeight());
//        }
//    }
//    @FXML
//    public void respondePergunta3() {
//        javafx.scene.control.TextField t = new javafx.scene.control.TextField();
//        t.setMinSize(300, 20);
//        HBox h = new HBox(new Text("Resposta:"),t);
//        pergunta2.getChildren().add(h);
//    }
//    @FXML
//    public void removePergunta1(){
//
//    }
//    @FXML
//    public void removePergunta2(){
//
//    }
//    @FXML
//    public void removePergunta3(){
//
//    }

}
