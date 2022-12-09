package pt.isec.gps.rookiesguidance.views.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;
import java.net.URL;
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
        Button bResponderPergunta= new Button("Responder\n Pergunta");
        Button bRemoverPergunta= new Button("Remover\n Pergunta");
        TextField tf = new TextField();

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
}
