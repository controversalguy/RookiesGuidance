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
//        Text t = new Text("Pergunta:");
//        Button bResponderPergunta= new Button("Responder\n Pergunta");
//        Button bRemoverPergunta= new Button("Remover\n Pergunta");
//        TextField tf = new TextField();
//
//        bRemoverPergunta.setMaxSize(90, 40);
//        bResponderPergunta.setMaxSize(90, 40);
//        tf.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
//        HBox hBox = new HBox(t,tf);
//        hBox.setMinSize(300,40);
//        HBox hBox1 = new HBox(hBox,bResponderPergunta,bRemoverPergunta);
//        Color c = Color.rgb(56, 129, 156);
//        VBox group = new VBox(hBox1,adicionaPergunta);
//        hBox.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
//        vBox.getChildren().add(group);
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
            if(!pergunta.textProperty().getValue().isEmpty() )
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
                        Button bremoveResposta  = new Button("Remove\nResposta");
                        badicionaResposta.setMaxSize(90, 40);
                        bremoveResposta.setMaxSize(90, 40);
                        id.add(badicionaResposta);
                        id.add(bremoveResposta);
                        id.get(index).setOnAction(mouseEvent -> {
                            dialog.setResult(String.valueOf(finalIndex));
                        });
                        index++;

                        int finalIndex1 = index;
                        id.get(index).setOnAction(mouseEvent -> {
                            dialog.setResult(String.valueOf(finalIndex1));
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
    }

    @FXML
    public void onRemoverPerguntaPressed() throws SQLException {
//        try {
//            mapaRespostasPerguntas = connDB.getNovidades();
//            if (novidades.isEmpty()) {
//                ToastMessage.show(getScene().getWindow(), "Não existe novidades para remover");
//                return;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        novidadesText = new ArrayList<>();
//
//        Dialog<String> dialog = new Dialog<>();
//        dialog.setTitle("Novidades");
//        dialog.setHeaderText("Remover Novidades");
//
//        // Create the username and password labels and fields.
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));
//        ArrayList<Button> id = new ArrayList<>();
//
//        for (int i = 0; i < novidades.size(); i+=2) {
//            int index = i/2;
//            id.add(new Button(novidades.get(i)));
//            System.out.println("novidades.get(i): "+novidades.get(i));
//            System.out.println(novidades);
//            id.get(index).setOnAction(mouseEvent -> {
//                dialog.setResult(String.valueOf(index));
//            });
//            grid.add(new Label("Novidade:"), index, index);
//            grid.add(id.get(index), index + 1, index);
//        }
//
//
//        System.out.println("NOVIDADES: " + novidades);
//
//        dialog.getDialogPane().setContent(grid);
//
//        Optional<String> result = dialog.showAndWait();
//
//        if (!connDB.removeNovidade(Integer.parseInt(result.get()), LoginController.getNumero())) {
//            ToastMessage.show(getScene().getWindow(), "Não existe novidades para remover");
//            return;
//        }
//
//        novidades = connDB.getNovidades();
//
//        if(novidades == null){
//            ToastMessage.show(getScene().getWindow(), "Não existe novidades para remover");
//        }
//        System.out.println("novidades:" + novidades);
//        Text t;
//        for (int i = 0; i < novidades.size(); i++) {
//            if (i % 2 == 0) {
//                t = new Text();
//                t.setText("\n" + novidades.get(i));
//                t.setStyle("-fx-font-weight: bold;");
//            } else {
//                t = new Text();
//                t.setText(novidades.get(i));
//            }
//            novidadesText.add(t);
//        }
//        novidadesvBox.getChildren().clear();
//        novidadesvBox.getChildren().addAll(novidadesText);

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
                ToastMessage.show(getScene().getWindow(), "mapaRespostasPerguntas NULL");
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