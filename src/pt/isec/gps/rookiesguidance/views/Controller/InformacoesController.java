package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

public class InformacoesController {
    @FXML
    private ImageView homePageIcon;

    @FXML
    private javafx.scene.control.Button adicionaAlimentacao;
    @FXML
    private javafx.scene.control.Button adicionaLocais;
    @FXML
    private VBox vBox;
    @FXML
    void onAdicionarAlimentacaoPressed() {
        Text t = new Text("Alimentacao:");
        javafx.scene.control.Button bResponderPergunta= new javafx.scene.control.Button("Responder\n Pergunta");
        javafx.scene.control.Button bRemoverPergunta= new javafx.scene.control.Button("Remover\n Pergunta");
        javafx.scene.control.TextField tf = new javafx.scene.control.TextField();

        bRemoverPergunta.setMaxSize(80, 40);
        bResponderPergunta.setMaxSize(80, 40);
        tf.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox hBox = new HBox(t,tf);
        hBox.setMinSize(200,40);
        HBox hBox1 = new HBox(hBox,bResponderPergunta,bRemoverPergunta);
        Color c = Color.rgb(56, 129, 156);
        VBox group = new VBox(hBox1,adicionaAlimentacao);
        hBox.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().add(group);
    }

    @FXML
    void onAdicionarLocaisPressed() {
        Text t = new Text("Alimentacao:");
        javafx.scene.control.Button bResponderPergunta= new javafx.scene.control.Button("Responder\n Pergunta");
        javafx.scene.control.Button bRemoverPergunta= new javafx.scene.control.Button("Remover\n Pergunta");
        javafx.scene.control.TextField tf = new javafx.scene.control.TextField();

        bRemoverPergunta.setMaxSize(80, 40);
        bResponderPergunta.setMaxSize(80, 40);
        tf.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        HBox hBox = new HBox(t,tf);
        hBox.setMinSize(200,40);
        HBox hBox1 = new HBox(hBox,bResponderPergunta,bRemoverPergunta);
        Color c = Color.rgb(56, 129, 156);
        VBox group = new VBox(hBox1,adicionaAlimentacao);
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

}