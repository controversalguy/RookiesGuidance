package pt.isec.gps.rookiesguidance.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class PerguntasController implements Initializable {
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

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea pergunta = new TextArea();

        grid.add(new Label("Pergunta:"), 0, 0);
        grid.add(pergunta, 1, 0);

        Node okButton = dialog.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        pergunta.textProperty().addListener((observable, oldValue, newValue) -> {
//            && !descricao.textProperty().getValue().isEmpty()
            if (!pergunta.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);
        });

//            descricao.textProperty().addListener((observable, oldValue, newValue) -> {
//                if(!pergunta.textProperty().getValue().isEmpty() && !descricao.textProperty().getValue().isEmpty())
//                    okButton.setDisable(false);
//                else
//                    okButton.setDisable(true);
//
//            });
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

                try {
                    mapaRespostasPerguntas = connDB.getPerguntas();

                    if (mapaRespostasPerguntas.size() == 0) {
                        ToastMessage.show(getScene().getWindow(), "mapaRespostasPerguntas NULL");
                    }

                    Text t;
                    System.out.println("mapaRespostasPerguntas" + mapaRespostasPerguntas);
                    int index = 0;
                    //vBox.getChildren().clear();
                    for (var p : mapaRespostasPerguntas.keySet()) {// adiciona titulo
                        HBox h = new HBox();
                        VBox perguntaResposta = new VBox();
                        t = new Text();
                        t.setText("\n" + p);
                        t.setStyle("-fx-font-weight: bold;");
                        respostasPerguntasText.add(t);
                        int finalIndex = index;
                        Button badicionaResposta = new Button("Adiciona\nResposta");
                        Button bremoveResposta = new Button("Remove\nResposta");
                        badicionaResposta.setMaxSize(90, 40);
                        bremoveResposta.setMaxSize(90, 40);
                        id.add(badicionaResposta);
                        id.add(bremoveResposta);


                        id.get(index).setOnAction(mouseEvent -> {

                            //dialog.setResult(String.valueOf(finalIndex));

                            Dialog<String> dialog1 = new Dialog<>();
                            dialog1.setTitle("Perguntas");
                            dialog1.setHeaderText("Inserir Pergunta");

                            ButtonType ok1 = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
                            dialog1.getDialogPane().getButtonTypes().addAll(ok1, ButtonType.CANCEL);

                            // Create the username and password labels and fields.
                            GridPane grid1 = new GridPane();
                            grid1.setHgap(10);
                            grid1.setVgap(10);
                            grid1.setPadding(new Insets(20, 150, 10, 10));

                            TextArea pergunta1 = new TextArea();

                            grid1.add(new Label("Pergunta:"), 0, 0);
                            grid1.add(pergunta1, 1, 0);

                            Node okButton1 = dialog1.getDialogPane().lookupButton(ok1);
                            okButton1.setDisable(true);
                            pergunta1.textProperty().addListener((observable, oldValue, newValue) -> {

                                if (!pergunta1.textProperty().getValue().isEmpty())
                                    okButton1.setDisable(false);
                                else
                                    okButton1.setDisable(true);
                            });

                            dialog1.getDialogPane().setContent(grid1);

                            dialog1.setResultConverter(dialogButton1 -> {
                                if (dialogButton1 == ok1) {
                                    try {
                                        if (!connDB.adicionaPergunta(pergunta1.getText(), LoginController.getNumero())) {
                                            ToastMessage.show(getScene().getWindow(), "Não foi possível adicionar Pergunta!");
                                            return null;
                                        }
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                return null;
                            });

                            dialog1.showAndWait();

                            ViewSwitcher.switchTo(View.PERGUNTAS);
                        });
                        index++;
                        int finalIndex1 = index;
                        id.get(index).setOnAction(mouseEvent -> {
                            // dialog.setResult(String.valueOf(finalIndex1));


                        });
                        index++;
                        h.getChildren().addAll(respostasPerguntasText);
                        h.getChildren().addAll(badicionaResposta);
                        h.getChildren().addAll(bremoveResposta);
                        perguntaResposta.getChildren().add(h);
                        respostasPerguntasText = new ArrayList<>();
                        for (var resposta : mapaRespostasPerguntas.get(p)) { // adiciona respostas
                            t = new Text();
                            t.setText("\n" + resposta);
                            t.setStyle("-fx-font-weight: bold;");
                            respostasPerguntasText.add(t);
                        }
                        perguntaResposta.getChildren().addAll(respostasPerguntasText);
                        //  vBox.getChildren().add(perguntaResposta);
                        respostasPerguntasText = new ArrayList<>();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            return null;
        });

        dialog.showAndWait();

        ViewSwitcher.switchTo(View.PERGUNTAS);
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

            Text t;
            id = new ArrayList<>();
            int i = 0,j=0;

            for (var p : mapaRespostasPerguntas.keySet()){// adiciona titulo
                VBox perguntaResposta = new VBox(); // uma pergunta varias respostas
                t = new Text();
                t.setText("\nPergunta: " + p + "\n");
                t.setFill(Color.WHITE);
                HBox pergunta = new HBox(t);
                //pergunta.setMinWidth();
                pergunta.setStyle("-fx-background-radius: 10; -fx-background-color:#38819c; -fx-font-weight: bold;" );
                perguntaResposta.getChildren().add(pergunta);
                Button adicionaPergunta = new Button("Adiciona\nResposta\n\n");
                Button removePergunta = new Button("Remove\nResposta\n\n");
                id.add(adicionaPergunta);
                id.add(removePergunta);
                HBox hButtoes = new HBox(adicionaPergunta, removePergunta);
                hButtoes.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold;");

//                int row = GridPane.getRowIndex(vBox);
//                int column = GridPane.getColumnIndex(hButtoes);
                gridPerguntaResposta.add(hButtoes, 1, i);

                for (var r : mapaRespostasPerguntas.get(p)){ // adiciona respostas
                    t = new Text();
                    t.setText("\nResposta: " + r);
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