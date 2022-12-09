package pt.isec.gps.rookiesguidance.views.Controller;

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
import pt.isec.gps.rookiesguidance.views.View;
import pt.isec.gps.rookiesguidance.views.ViewSwitcher;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static pt.isec.gps.rookiesguidance.views.ViewSwitcher.getScene;

public class InformacoesController implements Initializable {
    ConnDB connDB;
    @FXML
    private Button adicionarInfo;

    @FXML
    private HBox alimentacaoVbox;

    @FXML
    private HBox buttonsHbox;

    @FXML
    private HBox estudoVbox;

    @FXML
    private ImageView homePageIcon;

    @FXML
    private VBox locaisVbox;

    @FXML
    private Button removerInfo;

    ArrayList<String> locais;
    ArrayList<Text> locaisText;

    @FXML
    void onAdicionarInfoPressed() throws SQLException {
            Dialog<String> dialog1 = new Dialog<>();
            dialog1.setTitle("Locais");
            dialog1.setHeaderText("Inserir local");

            ButtonType ok = new ButtonType("Inserir", ButtonBar.ButtonData.OK_DONE);
            dialog1.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

            // Create the username and password labels and fields.
            GridPane grid1 = new GridPane();
            grid1.setHgap(10);
            grid1.setVgap(10);
            grid1.setPadding(new Insets(20, 150, 10, 10));

            ChoiceBox<String> tipo = new ChoiceBox<>();
            tipo.getItems().addAll("Alimentação","Estudo");
            TextArea local = new TextArea();
            local.setPromptText("Escreva texto aqui...");

            grid1.add(new Label("Típo de Informação:"), 0, 0);
            grid1.add(tipo, 1, 0);
            grid1.add(new Label("Local:"), 0, 1); //coluna 0 | linha 1
            grid1.add(local, 1, 1);

            Node okButton = dialog1.getDialogPane().lookupButton(ok);
            okButton.setDisable(true);
            tipo.accessibleTextProperty().addListener((observable, oldValue, newValue) -> {
                if(!tipo.getSelectionModel().getSelectedItem().isEmpty() && !local.textProperty().getValue().isEmpty())
                    okButton.setDisable(false);
                else
                    okButton.setDisable(true);
            });

            local.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!tipo.getSelectionModel().getSelectedItem().isEmpty()  && !local.textProperty().getValue().isEmpty())
                    okButton.setDisable(false);
                else
                    okButton.setDisable(true);

            });
            dialog1.getDialogPane().setContent(grid1);

            dialog1.setResultConverter(dialogButton -> {
                if (dialogButton == ok) {
                    try {
                        if (!connDB.addlocal(local.getText(), tipo.getSelectionModel().getSelectedItem(), LoginController.getNumero())) {
                            ToastMessage.show(getScene().getWindow(), "Não foi possível adicionar local");
                            return null;
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        locais = connDB.getLocais();
                        if (locais.size() == 0) {
                            ToastMessage.show(getScene().getWindow(), "LOCAIS NULL");
                        }
                        Text t;

                        for (int i = 0; i < locais.size(); i += 2) {
                            //int index = i / 2;
                            if(!locais.get(i+1).equalsIgnoreCase("Alimentação"))
                                continue;
                            t = new Text("\n"+ locais.get(i)+ "\n");
                            //t.setText("\n" + locais.get(i) + "\n");
                            t.setStyle("-fx-font-weight: bold;");
                            locaisText.add(t);
                        }
                        System.out.println("locaisText: "+ locaisText);
                        alimentacaoVbox.getChildren().clear();
                        alimentacaoVbox.getChildren().add(new Text("Alimentação"));
                        alimentacaoVbox.getChildren().addAll(locaisText);

                        locaisText.clear();
                        for (int i = 0; i < locais.size(); i += 2) {
                            if(!locais.get(i+1).equalsIgnoreCase("Estudo"))
                                continue;


                            t = new Text("\n"+ locais.get(i)+ "\n");
                            //t.setText("\n" + locais.get(i) + "\n");
                            t.setStyle("-fx-font-weight: bold;");
                            locaisText.add(t);
                        }

                        estudoVbox.getChildren().clear();
                        estudoVbox.getChildren().add(new Text("Estudo"));
                        estudoVbox.getChildren().addAll(locaisText);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            });

            dialog1.showAndWait();

    }
    @FXML
   void onRemoverInfoPressed(){}
//    {
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
//    }
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
        locais = new ArrayList<>();
        locaisText = new ArrayList<>();

        try {
            locais = connDB.getLocais();
            if (locais.size() == 0) {
                ToastMessage.show(getScene().getWindow(), "LOCAIS NULL");
            }
            Text t;

            /*for (int i = 0; i < locais.size(); i += 2) {
                //int index = i / 2;
                if(!locais.get(i+1).equalsIgnoreCase("Alimentação"))
                    continue;
                t = new Text("\n" + locais.get(i) + "\n");
                System.out.println("locais.get(i):"+t.getText());

                t.setStyle("-fx-font-weight: bold;");
                locaisText.add(t);
            }
            System.out.println("locaisText: "+ locaisText);
            alimentacaoVbox.getChildren().clear();
            alimentacaoVbox.getChildren().add(new Text("Alimentação"));
            alimentacaoVbox.getChildren().addAll(locaisText);*/

            locaisText.clear();
            estudoVbox.getChildren().clear();
            //estudoVbox.getChildren().add(new Text("Estudo"));
            for (int i = 0; i < locais.size(); i+=2) {
                /*if(!locais.get(i+1).equalsIgnoreCase("Estudo"))
                    continue;*/
                t = new Text("\n"+locais.get(i));
                //t.setText("\n" + locais.get(i) + "\n");
                System.out.println("t.getText()"+t.getText());
                //t.setStyle("-fx-font-weight: bold;");
                //locaisText.add(t);/**/
                estudoVbox.getChildren().add(t);
            }



            //estudoVbox.getChildren().addAll(locaisText);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}