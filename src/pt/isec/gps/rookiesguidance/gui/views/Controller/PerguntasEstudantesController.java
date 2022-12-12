package pt.isec.gps.rookiesguidance.gui.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher.getScene;

public class PerguntasEstudantesController implements Initializable {
    @FXML
    private ImageView homePageIcon;
    @FXML
    private Button adicionaPergunta;
    @FXML
    private Button removerPergunta;
    ConnDB connDB;
    @FXML
    private Map<String,ArrayList<String>> mapaRespostasPerguntas;
    @FXML
    private ArrayList <String> respostas;
    @FXML
    private ArrayList <Text> respostasPerguntasText;
    @FXML
    private GridPane gridPerguntaResposta;

    ArrayList<Button> id;

    @FXML
    void onAdicionarPerguntaPressed() {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Perguntas");
        dialog.setHeaderText("Inserir Pergunta");

        ButtonType ok = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea pergunta = new TextArea();
        pergunta.setWrapText(true);
        grid.add(new Label("Pergunta:"), 0, 0);
        grid.add(pergunta, 1, 0);

        Node okButton = dialog.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        pergunta.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!pergunta.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                try {
                    if (!connDB.adicionaPergunta(pergunta.getText(), LoginController.getNumero())) {
                        ToastMessage.show(getScene().getWindow(), "Não foi possível adicionar Pergunta!");
                        return null;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();

        ViewSwitcher.switchTo(View.PERGUNTAS_ESTUDANTE);
    }

    @FXML
    public void onRemoverPerguntaPressed() throws SQLException {
        Dialog<String> dialog1 = new Dialog<>();
        dialog1.setTitle("Perguntas");
        dialog1.setHeaderText("Remover pergunta");

        ButtonType ok = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog1.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid1 = new GridPane();
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(20, 150, 10, 10));

        ArrayList<String> ids = connDB.getIdPerguntas();
        if(ids.size() == 0) {
            ToastMessage.show(getScene().getWindow(), "Não existe perguntas para remover");
            return;
        }

        ChoiceBox<String> tipo = new ChoiceBox<>();

        tipo.getItems().addAll(ids);

        grid1.add(new Label("Número da Pergunta:"), 0, 0);
        grid1.add(tipo, 1, 0);

        dialog1.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                if(tipo.getSelectionModel().isEmpty()) {
                    ToastMessage.show(getScene().getWindow(), "Insira um número!");
                    return null;
                } else {
                    try {
                        if (connDB.removePergunta(Integer.parseInt(tipo.getSelectionModel().getSelectedItem()), LoginController.getNumero()))
                            ToastMessage.show(getScene().getWindow(), "Pergunta eliminado com sucesso!");
                        else
                            ToastMessage.show(getScene().getWindow(), "Impossivel eliminar pergunta!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    ViewSwitcher.switchTo(View.PERGUNTAS);

                }
            }
            return null;
        });

        dialog1.getDialogPane().setContent(grid1);

        dialog1.showAndWait();
    }

    @FXML
    void onIconPressed() {ViewSwitcher.switchTo(View.HOMEPAGE);  }

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

    @FXML
    void onTerminarSessaoPressed() {
        ViewSwitcher.switchTo(View.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        respostas = new ArrayList<>();
        respostasPerguntasText = new ArrayList<>();

        //vBox.getChildren().add(adicionaPergunta);

        try {
            mapaRespostasPerguntas = connDB.getPerguntas();

            if (mapaRespostasPerguntas.size() == 0) {
                // ToastMessage.show(getScene().getWindow(), "mapaRespostasPerguntas NULL");
            }
            System.out.println(mapaRespostasPerguntas);
            Text t;
            id = new ArrayList<>();
            int i = 0,j=0;

            int index = 0;
            i =0;
            for (var p : mapaRespostasPerguntas.keySet()){// adiciona titulo
                if(index == id.size()-1)
                    break;
                VBox perguntaResposta = new VBox(); // uma pergunta varias respostas
                t = new Text();
                String temp = "";
                for (int k = 0; k < p.length() ; k++) {
                    temp += p.charAt(k);
                    if(k % 70 == 0 && k != 0)
                        temp += "\n";
                }

                t.setText("Pergunta"+temp);
                t.setFill(Color.WHITE);
                HBox pergunta = new HBox(t);
                //pergunta.setMinWidth();
                pergunta.setStyle("-fx-background-radius: 10; -fx-background-color:#38819c; -fx-font-weight: bold;" );
                perguntaResposta.getChildren().add(pergunta);
                index++;
                index++;
                //hButtoes.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold;");

                for (var r : mapaRespostasPerguntas.get(p)){ // adiciona respostas
                    t = new Text();
                    t.setText("\nResposta " + r);
                    HBox resposta = new HBox(t);
                    resposta.setStyle("-fx-border-color: #38819c; -fx-border-radius: 2; -fx-background-color: #ffffff; -fx-border-width: 3;" +
                            "-fx-font-weight: bold;");
                    perguntaResposta.getChildren().add(resposta);

                }
                gridPerguntaResposta.add(perguntaResposta,0,i);
                respostasPerguntasText = new ArrayList<>();
                i++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}