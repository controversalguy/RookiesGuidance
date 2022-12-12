package pt.isec.gps.rookiesguidance.gui.views.Controller;

import javafx.event.ActionEvent;
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
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

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
        int max = 70;
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
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível adicionar Pergunta!");
                        return null;
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
        if(ids.size() == 0) {
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe perguntas para remover");
            return;
        }

        ChoiceBox<String> tipo = new ChoiceBox<>();

        tipo.getItems().addAll(ids);

        grid1.add(new Label("Número da Pergunta:"), 0, 0);
        grid1.add(tipo, 1, 0);

        dialog1.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                if(tipo.getSelectionModel().isEmpty()) {
                    ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Insira um número!");
                    return null;
                } else {
                    try {
                        if (connDB.removePergunta(Integer.parseInt(tipo.getSelectionModel().getSelectedItem()), LoginController.getNumero()))
                            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Pergunta eliminado com sucesso!");
                        else
                            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Impossivel eliminar pergunta!");
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
    void onIconPressed() {
            ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
    }

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
            System.out.println(mapaRespostasPerguntas);
            Text t;
            id = new ArrayList<>();
            int i = 0,j=0;
            id = new ArrayList<>();

            for (var p : mapaRespostasPerguntas.keySet()) {
                j++;
                Button badicionaResposta = new Button("Adiciona\nResposta");
                Button bremoveResposta = new Button("Remove\nResposta");
                badicionaResposta.setMaxSize(90, 50);
                badicionaResposta.setMinSize(90, 50);
                badicionaResposta.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-radius: 5");
                bremoveResposta.setMinSize(90, 50);
                bremoveResposta.setMaxSize(90, 50);
                bremoveResposta.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold; -fx-border-color: #000000; -fx-border-radius: 5");
                int k = j;


                badicionaResposta.addEventHandler(ActionEvent.ACTION,e -> {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Respostas");
                    dialog.setHeaderText("Adicionar Respostas");

                    ButtonType ok = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setPadding(new Insets(20, 150, 10, 10));

                    TextArea resposta = new TextArea();
                    resposta.setWrapText(true);
                    grid.add(new Label("Resposta:"), 0, 0);
                    grid.add(resposta, 1, 0);

                    Node okButton = dialog.getDialogPane().lookupButton(ok);
                    okButton.setDisable(true);
                    resposta.textProperty().addListener((observable, oldValue, newValue) -> {

                        if (!resposta.textProperty().getValue().isEmpty())
                            okButton.setDisable(false);
                        else
                            okButton.setDisable(true);
                    });

                    dialog.getDialogPane().setContent(grid);

                    dialog.setResultConverter(dialogButton -> {
                        if (dialogButton == ok) {
                            try {
                                if (!connDB.adicionaResposta(resposta.getText(),Integer.parseInt(String.valueOf(p.charAt(0))), LoginController.getNumero())) {
                                    ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível adicionar Pergunta!");
                                    return null;
                                }
                            } catch (SQLException f) {
                                throw new RuntimeException(f);
                            }
                        }
                        return null;
                    });

                    dialog.showAndWait();
                    ViewSwitcher.switchTo(View.PERGUNTAS);
                });
                bremoveResposta.addEventHandler(ActionEvent.ACTION,e->{
                    Dialog<String> dialog1 = new Dialog<>();
                    dialog1.setTitle("Respostas");
                    dialog1.setHeaderText("Remover respostas");

                    ButtonType ok = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
                    dialog1.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

                    // Create the username and password labels and fields.
                    GridPane grid1 = new GridPane();
                    grid1.setHgap(10);
                    grid1.setVgap(10);
                    grid1.setPadding(new Insets(20, 150, 10, 10));

                    ArrayList<String> ids = null;
                    try {
                        ids = connDB.getIdRespostas(Integer.parseInt(String.valueOf(p.charAt(0))));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    if(ids.size() == 0) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não existe respostas para remover");
                        return;
                    }

                    ChoiceBox<String> tipo = new ChoiceBox<>();

                    tipo.getItems().addAll(ids);

                    grid1.add(new Label("Número da Resposta:"), 0, 0);
                    grid1.add(tipo, 1, 0);

                    dialog1.setResultConverter(dialogButton -> {
                        if (dialogButton == ok) {
                            if(tipo.getSelectionModel().isEmpty()) {
                                ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Insira um número!");
                                return null;
                            } else {
                                System.out.println("Integer.parseInt(tipo.getSelectionModel().getSelectedItem())" + Integer.parseInt(tipo.getSelectionModel().getSelectedItem()));
                                try {
                                    if (connDB.removeResposta(Integer.parseInt(tipo.getSelectionModel().getSelectedItem()), LoginController.getNumero()))
                                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Resposta eliminada com sucesso!");
                                    else
                                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Impossivel eliminar resposta!");
                                } catch (SQLException z) {
                                    throw new RuntimeException(z);
                                }

                                ViewSwitcher.switchTo(View.PERGUNTAS);

                            }
                        }
                        return null;
                    });

                    dialog1.getDialogPane().setContent(grid1);

                    dialog1.showAndWait();
                });
                id.add(badicionaResposta);
                id.add(bremoveResposta);
            }
            int index = 0;
            i =0;
            for (var p : mapaRespostasPerguntas.keySet()){// adiciona titulo
                if(index == id.size()-1)
                    break;
                VBox perguntaResposta = new VBox(); // uma pergunta varias respostas
                t = new Text();
                t.setText("\nPergunta " + p + "\n");
                t.setFill(Color.WHITE);
                HBox pergunta = new HBox(t);
                //pergunta.setMinWidth();
                pergunta.setStyle("-fx-background-radius: 10; -fx-background-color:#38819c; -fx-font-weight: bold;" );
                perguntaResposta.getChildren().add(pergunta);
                HBox hButtoes = new HBox(id.get(index),id.get(index+1));
                index++;
                index++;
                //hButtoes.setStyle("-fx-background-color: #ffffff; -fx-font-weight: bold;");

                gridPerguntaResposta.add(hButtoes, 1, i);
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