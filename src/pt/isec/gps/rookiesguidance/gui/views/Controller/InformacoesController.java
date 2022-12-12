package pt.isec.gps.rookiesguidance.gui.views.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import pt.isec.gps.rookiesguidance.bd.ConnDB;
import pt.isec.gps.rookiesguidance.gui.views.View;
import pt.isec.gps.rookiesguidance.gui.views.ViewSwitcher;
import pt.isec.gps.rookiesguidance.utils.ToastMessage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class InformacoesController implements Initializable {
    ConnDB connDB;

    @FXML
    private VBox alimentacaoVbox;
    @FXML
    private VBox estudoVbox;
    ArrayList<String> locais;
    ArrayList<Text> locaisText;

    @FXML
    void onAdicionarInfoPressed() {
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
        tipo.getItems().addAll("Alimentação", "Estudo");
        TextArea local = new TextArea();
        local.setPromptText("Escreva texto aqui...");

        grid1.add(new Label("Típo de Informação:"), 0, 0);
        grid1.add(tipo, 1, 0);
        grid1.add(new Label("Local:"), 0, 1); //coluna 0 | linha 1
        grid1.add(local, 1, 1);

        Node okButton = dialog1.getDialogPane().lookupButton(ok);
        okButton.setDisable(true);
        tipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (!tipo.getSelectionModel().getSelectedItem().isEmpty() && !local.textProperty().getValue().isEmpty())
                okButton.setDisable(false);
            else
                okButton.setDisable(true);

        });

        local.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("localll");
            if (tipo.getSelectionModel().getSelectedItem() != null) {
                if (!tipo.getSelectionModel().getSelectedItem().isEmpty() && !local.textProperty().getValue().isEmpty())
                    okButton.setDisable(false);
                else
                    okButton.setDisable(true);
            } else
                okButton.setDisable(true);

        });
        dialog1.getDialogPane().setContent(grid1);

        dialog1.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                try {
                    if (!connDB.addlocal(local.getText(), tipo.getSelectionModel().getSelectedItem(), LoginController.getNumero())) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Não foi possível adicionar local");
                        return null;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    locais = connDB.getLocais();
                    if (locais.size() == 0) {
                        ToastMessage.show(ViewSwitcher.getScene().getWindow(), "LOCAIS NULL");
                    }
                    Text t;

                    for (int i = 0; i < locais.size(); i += 2) {
                        //int index = i / 2;
                        if (!locais.get(i + 1).equalsIgnoreCase("Alimentação"))
                            continue;
                        t = new Text(locais.get(i));
                        t.setStyle("-fx-font-weight: bold;");
                        locaisText.add(t);
                    }

                    Text tAlimentacao = new Text("Alimentação");
                    tAlimentacao.setStyle("-fx-font-weight: bold;");
                    tAlimentacao.setStyle(" -fx-font-size: 15;");
                    alimentacaoVbox.getChildren().clear();
                    alimentacaoVbox.getChildren().add(tAlimentacao);
                    alimentacaoVbox.getChildren().addAll(locaisText);

                    locaisText.clear();
                    Text tEstudo = new Text("Estudo");
                    tEstudo.setStyle("-fx-font-weight: bold;");
                    tEstudo.setStyle(" -fx-font-size: 15;");


                    for (int i = 0; i < locais.size(); i += 2) {
                        if (!locais.get(i + 1).equalsIgnoreCase("Estudo")) {
                            continue;
                        }
                        t = new Text(locais.get(i));
                        t.setStyle("-fx-font-weight: bold;");
                        locaisText.add(t);
                    }
                    estudoVbox.getChildren().clear();
                    estudoVbox.getChildren().add(tEstudo);
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
   void onRemoverInfoPressed() throws SQLException {
        Dialog<String> dialog1 = new Dialog<>();
        dialog1.setTitle("Locais");
        dialog1.setHeaderText("Remover local");

        ButtonType ok = new ButtonType("Remover", ButtonBar.ButtonData.OK_DONE);
        dialog1.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid1 = new GridPane();
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(20, 150, 10, 10));

        ArrayList<String> ids = connDB.getIdLocais();
        ChoiceBox<String> tipo = new ChoiceBox<>();

        tipo.getItems().addAll(ids);

        grid1.add(new Label("Número da Informação:"), 0, 0);
        grid1.add(tipo, 1, 0);

        dialog1.setResultConverter(dialogButton -> {
            if (dialogButton == ok) {
                if(tipo.getSelectionModel().isEmpty()) {
                    ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Insira um número!");
                    return null;
                } else {
                    try {
                        if(connDB.removelocal(Integer.parseInt(tipo.getSelectionModel().getSelectedItem()), LoginController.getNumero()))
                            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Local eliminado com sucesso!");
                        else
                            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Impossivel eliminar local!");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    locaisText = new ArrayList<>();

                    try {
                        locais = connDB.getLocais();
                        if (locais.size() == 0) {
                            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "LOCAIS NULL");
                        }
                        Text t;

                        for (int i = 0; i < locais.size(); i += 2) {
                            //int index = i / 2;
                            if(!locais.get(i+1).equalsIgnoreCase("Alimentação"))
                                continue;
                            t = new Text(locais.get(i));
                            System.out.println("locais.get(i):"+t.getText());

                            t.setStyle("-fx-font-weight: bold;");
                            locaisText.add(t);
                        }

                        Text tAlimentacao = new Text("Alimentação");
                        tAlimentacao.setStyle("-fx-font-weight: bold;");
                        tAlimentacao.setStyle(" -fx-font-size: 15;");
                        alimentacaoVbox.getChildren().clear();
                        alimentacaoVbox.getChildren().add(tAlimentacao);
                        alimentacaoVbox.getChildren().addAll(locaisText);

                        locaisText.clear();
                        Text tEstudo = new Text("Estudo");
                        tEstudo.setStyle("-fx-font-weight: bold;");
                        tEstudo.setStyle(" -fx-font-size: 15;");


                        for (int i = 0; i < locais.size(); i+=2) {
                            if(!locais.get(i+1).equalsIgnoreCase("Estudo")) {
                                continue;
                            }
                            t = new Text(locais.get(i));
                            t.setStyle("-fx-font-weight: bold;");
                            locaisText.add(t);
                        }
                        estudoVbox.getChildren().clear();
                        estudoVbox.getChildren().add(tEstudo);
                        estudoVbox.getChildren().addAll(locaisText);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            return null;
        });

        dialog1.getDialogPane().setContent(grid1);

        dialog1.showAndWait();
    }
    @FXML
    void onIconPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.HOMEPAGE);
        else
            ViewSwitcher.switchTo(View.HOMEPAGE_GESTORES);
    }
    @FXML
    void onEventosPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.EVENTOS_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.EVENTOS);
    }

    @FXML
    void onInformacoesPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.INFORMACOES_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.INFORMACOES);
    }

    @FXML
    void onPerfilPressed() {
        ViewSwitcher.switchTo(View.PERFIL);
    }

    @FXML
    void onPerguntasPressed() {
        if(!LoginController.isGestor)
            ViewSwitcher.switchTo(View.PERGUNTAS_ESTUDANTE);
        else
            ViewSwitcher.switchTo(View.PERGUNTAS);
    }

    @FXML
    void onTerminarSessaoPressed() throws SQLException {

        if(connDB.logout(LoginController.getNumero())){
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Sessão terminada com sucesso");
            ViewSwitcher.switchTo(View.LOGIN);
        }else
            ToastMessage.show(ViewSwitcher.getScene().getWindow(), "Erro ao terminar sessão");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            connDB = new ConnDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        locaisText = new ArrayList<>();

        try {
            locais = connDB.getLocais();
            if (locais.size() == 0) {
                //ToastMessage.show(getScene().getWindow(), "LOCAIS NULL");
            }
            Text t;

            for (int i = 0; i < locais.size(); i += 2) {
                //int index = i / 2;
                if(!locais.get(i+1).equalsIgnoreCase("Alimentação"))
                    continue;
                t = new Text(locais.get(i));
                System.out.println("locais.get(i):"+t.getText());

                t.setStyle("-fx-font-weight: bold;");
                locaisText.add(t);
            }

            Text tAlimentacao = new Text("Alimentação");
            tAlimentacao.setStyle("-fx-font-weight: bold;");
            tAlimentacao.setStyle(" -fx-font-size: 15;");
            alimentacaoVbox.getChildren().clear();
            alimentacaoVbox.getChildren().add(tAlimentacao);
            alimentacaoVbox.getChildren().addAll(locaisText);

            locaisText.clear();
            Text tEstudo = new Text("Estudo");
            tEstudo.setStyle("-fx-font-weight: bold;");
            tEstudo.setStyle(" -fx-font-size: 15;");


            for (int i = 0; i < locais.size(); i+=2) {
                if(!locais.get(i+1).equalsIgnoreCase("Estudo")) {
                    continue;
                }
                t = new Text(locais.get(i));
                t.setStyle("-fx-font-weight: bold;");
                locaisText.add(t);
            }
            estudoVbox.getChildren().clear();
            estudoVbox.getChildren().add(tEstudo);
            estudoVbox.getChildren().addAll(locaisText);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Informatica1anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("1ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button TWEB = new Button();
        TWEB.setText("Tecnologias Web");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        TWEB.setFont(font);
        TWEB.setPrefWidth(200);
        TWEB.setOnAction(mouseEvent -> {
            dialog.setResult("TWEB");
        });

        Button SD = new Button();
        SD.setText("Sistemas Digitais");
        SD.setFont(font);
        SD.setPrefWidth(200);
        SD.setOnAction(mouseEvent -> {
            dialog.setResult("SD");
        });

        Button IP = new Button();
        IP.setText("Introdução à Programação");
        IP.setFont(font);
        IP.setPrefWidth(200);
        IP.setOnAction(mouseEvent -> {
            dialog.setResult("IP");
        });

        Button ELETRO = new Button();
        ELETRO.setText("Eletrónica");
        ELETRO.setFont(font);
        ELETRO.setPrefWidth(200);
        ELETRO.setOnAction(mouseEvent -> {
            dialog.setResult("ELETRO");
        });

        Button ALGEBRA = new Button();
        ALGEBRA.setText("Álgebra Linear");
        ALGEBRA.setFont(font);
        ALGEBRA.setPrefWidth(200);
        ALGEBRA.setOnAction(mouseEvent -> {
            dialog.setResult("ALGEBRA");
        });

        Button AM1 = new Button();
        AM1.setText("Análise Matemática I");
        AM1.setFont(font);
        AM1.setPrefWidth(200);
        AM1.setOnAction(mouseEvent -> {
            dialog.setResult("AM1");
        });

        grid.add(TWEB, 0, 0);
        grid.add(SD, 0, 1);
        grid.add(IP, 0, 2);
        grid.add(ELETRO, 1, 0);
        grid.add(ALGEBRA, 1, 1);
        grid.add(AM1, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "TWEB" -> ViewSwitcher.switchTo(View.TWEB);
                case "SD" -> ViewSwitcher.switchTo(View.SD);
                case "IP" -> ViewSwitcher.switchTo(View.IP);
                case "ELETRO" -> ViewSwitcher.switchTo(View.ELETRO);
                case "ALGEBRA" -> ViewSwitcher.switchTo(View.ALGEBRA);
                case "AM1" -> ViewSwitcher.switchTo(View.AM1);
            }
        }
    }

    @FXML
    void Informatica2anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("2ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button SO = new Button();
        SO.setText("Sistemas Operativos");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        SO.setFont(font);
        SO.setPrefWidth(230);
        SO.setOnAction(mouseEvent -> {
            dialog.setResult("SO");
        });

        Button BD = new Button();
        BD.setText("Base de Dados");
        BD.setFont(font);
        BD.setPrefWidth(230);
        BD.setOnAction(mouseEvent -> {
            dialog.setResult("BD");
        });

        Button POO = new Button();
        POO.setText("Programação Orientada a Objetos");
        POO.setFont(font);
        POO.setPrefWidth(230);
        POO.setOnAction(mouseEvent -> {
            dialog.setResult("POO");
        });

        Button SO2 = new Button();
        SO2.setText("Sistemas Operativos 2");
        SO2.setFont(font);
        SO2.setPrefWidth(230);
        SO2.setOnAction(mouseEvent -> {
            dialog.setResult("SO2");
        });

        Button PA = new Button();
        PA.setText("Programação Avançada");
        PA.setFont(font);
        PA.setPrefWidth(230);
        PA.setOnAction(mouseEvent -> {
            dialog.setResult("PA");
        });

        Button CR = new Button();
        CR.setText("Conhecimento e Raciocionio");
        CR.setFont(font);
        CR.setPrefWidth(230);
        CR.setOnAction(mouseEvent -> {
            dialog.setResult("CR");
        });

        grid.add(SO, 0, 0);
        grid.add(BD, 0, 1);
        grid.add(POO, 0, 2);
        grid.add(SO2, 1, 0);
        grid.add(PA, 1, 1);
        grid.add(CR, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "SO" -> ViewSwitcher.switchTo(View.SO);
                case "BD" -> ViewSwitcher.switchTo(View.BD);
                case "POO" -> ViewSwitcher.switchTo(View.POO);
                case "SO2" -> ViewSwitcher.switchTo(View.SO2);
                case "PA" -> ViewSwitcher.switchTo(View.PA);
                case "CR" -> ViewSwitcher.switchTo(View.CR);
            }
        }
    }

    @FXML
    void Informatica3anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("3ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button PD = new Button();
        PD.setText("Programação Distribuida");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        PD.setFont(font);
        PD.setPrefWidth(200);
        PD.setOnAction(mouseEvent -> {
            dialog.setResult("PD");
        });

        Button PWEB = new Button();
        PWEB.setText("Programação WEB");
        PWEB.setFont(font);
        PWEB.setPrefWidth(200);
        PWEB.setOnAction(mouseEvent -> {
            dialog.setResult("PWEB");
        });

        Button AMOV = new Button();
        AMOV.setText("Arquiteturas Móveis");
        AMOV.setFont(font);
        AMOV.setPrefWidth(200);
        AMOV.setOnAction(mouseEvent -> {
            dialog.setResult("AMOV");
        });

        Button ETICA = new Button();
        ETICA.setText("Ética");
        ETICA.setFont(font);
        ETICA.setPrefWidth(200);
        ETICA.setOnAction(mouseEvent -> {
            dialog.setResult("ETICA");
        });


        grid.add(PD, 0, 0);
        grid.add(PWEB, 0, 1);
        grid.add(AMOV, 0, 2);
        grid.add(ETICA, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "PD" -> ViewSwitcher.switchTo(View.PD);
                case "PWEB" -> ViewSwitcher.switchTo(View.PWEB);
                case "AMOV" -> ViewSwitcher.switchTo(View.AMOV);
                case "ETICA" -> ViewSwitcher.switchTo(View.ETICA);
            }
        }
    }

    @FXML
    void Mecanica1anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("1ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button DT = new Button();
        DT.setText("Desenho Técnico");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        DT.setFont(font);
        DT.setPrefWidth(200);
        DT.setOnAction(mouseEvent -> {
            dialog.setResult("DT");
        });

        Button QUIMICA = new Button();
        QUIMICA.setText("Química");
        QUIMICA.setFont(font);
        QUIMICA.setPrefWidth(200);
        QUIMICA.setOnAction(mouseEvent -> {
            dialog.setResult("QUIMICA");
        });

        Button FA = new Button();
        FA.setText("Física Aplicada");
        FA.setFont(font);
        FA.setPrefWidth(200);
        FA.setOnAction(mouseEvent -> {
            dialog.setResult("FA");
        });

        Button IP = new Button();
        IP.setText("Introdução à Programação");
        IP.setFont(font);
        IP.setPrefWidth(200);
        IP.setOnAction(mouseEvent -> {
            dialog.setResult("IP");
        });

        Button ALGEBRA = new Button();
        ALGEBRA.setText("Álgebra Linear");
        ALGEBRA.setFont(font);
        ALGEBRA.setPrefWidth(200);
        ALGEBRA.setOnAction(mouseEvent -> {
            dialog.setResult("ALGEBRA");
        });

        Button AM1 = new Button();
        AM1.setText("Análise Matemática I");
        AM1.setFont(font);
        AM1.setPrefWidth(200);
        AM1.setOnAction(mouseEvent -> {
            dialog.setResult("AM1");
        });

        grid.add(DT, 0, 0);
        grid.add(QUIMICA, 0, 1);
        grid.add(FA, 0, 2);
        grid.add(IP, 1, 0);
        grid.add(ALGEBRA, 1, 1);
        grid.add(AM1, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "DT" -> ViewSwitcher.switchTo(View.DT);
                case "QUIMICA" -> ViewSwitcher.switchTo(View.QUIMICA);
                case "FA" -> ViewSwitcher.switchTo(View.FA);
                case "IP" -> ViewSwitcher.switchTo(View.IP);
                case "ALGEBRA" -> ViewSwitcher.switchTo(View.ALGEBRA);
                case "AM1" -> ViewSwitcher.switchTo(View.AM1);
            }
        }
    }

    @FXML
    void Mecanica2anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("2ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button RM1 = new Button();
        RM1.setText("Resistência dos Materiais I");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        RM1.setFont(font);
        RM1.setPrefWidth(200);
        RM1.setOnAction(mouseEvent -> {
            dialog.setResult("RM1");
        });

        Button MH = new Button();
        MH.setText("Máquinas Hidráulicas");
        MH.setFont(font);
        MH.setPrefWidth(200);
        MH.setOnAction(mouseEvent -> {
            dialog.setResult("MH");
        });

        Button TM1 = new Button();
        TM1.setText("Tecnologia Mecânica I");
        TM1.setFont(font);
        TM1.setPrefWidth(200);
        TM1.setOnAction(mouseEvent -> {
            dialog.setResult("TM1");
        });

        Button ME = new Button();
        ME.setText("Métodos Estatísticos");
        ME.setFont(font);
        ME.setPrefWidth(200);
        ME.setOnAction(mouseEvent -> {
            dialog.setResult("ME");
        });

        Button MF = new Button();
        MF.setText("Mecâmica dos Fluidos");
        MF.setFont(font);
        MF.setPrefWidth(200);
        MF.setOnAction(mouseEvent -> {
            dialog.setResult("MF");
        });

        Button TM2 = new Button();
        TM2.setText("Tecnologia Mecânica II");
        TM2.setFont(font);
        TM2.setPrefWidth(200);
        TM2.setOnAction(mouseEvent -> {
            dialog.setResult("TM2");
        });

        grid.add(RM1, 0, 0);
        grid.add(MH, 0, 1);
        grid.add(TM1, 0, 2);
        grid.add(ME, 1, 0);
        grid.add(MF, 1, 1);
        grid.add(TM2, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "RM1" -> ViewSwitcher.switchTo(View.RM1);
                case "MH" -> ViewSwitcher.switchTo(View.MH);
                case "TM1" -> ViewSwitcher.switchTo(View.TM1);
                case "ME" -> ViewSwitcher.switchTo(View.ME);
                case "MF" -> ViewSwitcher.switchTo(View.MF);
                case "TM2" -> ViewSwitcher.switchTo(View.TM2);
            }
        }
    }

    @FXML
    void Mecanica3anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("3ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button GQ = new Button();
        GQ.setText("Gestão da Qualidade");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        GQ.setFont(font);
        GQ.setPrefWidth(200);
        GQ.setOnAction(mouseEvent -> {
            dialog.setResult("GQ");
        });

        Button FM = new Button();
        FM.setText("Fabrico de Moldes");
        FM.setFont(font);
        FM.setPrefWidth(200);
        FM.setOnAction(mouseEvent -> {
            dialog.setResult("FM");
        });

        Button OG = new Button();
        OG.setText("Organização e Gestão");
        OG.setFont(font);
        OG.setPrefWidth(200);
        OG.setOnAction(mouseEvent -> {
            dialog.setResult("OG");
        });

        Button IC = new Button();
        IC.setText("Instalações de Climatização");
        IC.setFont(font);
        IC.setPrefWidth(200);
        IC.setOnAction(mouseEvent -> {
            dialog.setResult("IC");
        });

        grid.add(GQ, 0, 0);
        grid.add(FM, 0, 1);
        grid.add(OG, 0, 2);
        grid.add(IC, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "GQ" -> ViewSwitcher.switchTo(View.GQ);
                case "FM" -> ViewSwitcher.switchTo(View.FM);
                case "OG" -> ViewSwitcher.switchTo(View.OG);
                case "IC" -> ViewSwitcher.switchTo(View.IC);
            }
        }
    }

    @FXML
    void Eletrotecnica1anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("1ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button SD = new Button();
        SD.setText("Sistemas Digitais");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        SD.setFont(font);
        SD.setPrefWidth(200);
        SD.setOnAction(mouseEvent -> {
            dialog.setResult("SD");
        });

        Button AL = new Button();
        AL.setText("Álgebra");
        AL.setFont(font);
        AL.setPrefWidth(200);
        AL.setOnAction(mouseEvent -> {
            dialog.setResult("AL");
        });

        Button AP = new Button();
        AP.setText("Algoritmos e Programação");
        AP.setFont(font);
        AP.setPrefWidth(200);
        AP.setOnAction(mouseEvent -> {
            dialog.setResult("AP");
        });

        Button PC = new Button();
        PC.setText("Programação de Computadores");
        PC.setFont(font);
        PC.setPrefWidth(200);
        PC.setOnAction(mouseEvent -> {
            dialog.setResult("PC");
        });

        Button MI = new Button();
        MI.setText("Medidas e Instrumentação");
        MI.setFont(font);
        MI.setPrefWidth(200);
        MI.setOnAction(mouseEvent -> {
            dialog.setResult("MI");
        });

        Button FG = new Button();
        FG.setText("Física Geral");
        FG.setFont(font);
        FG.setPrefWidth(200);
        FG.setOnAction(mouseEvent -> {
            dialog.setResult("FG");
        });

        grid.add(SD, 0, 0);
        grid.add(AL, 0, 1);
        grid.add(AP, 0, 2);
        grid.add(PC, 1, 0);
        grid.add(MI, 1, 1);
        grid.add(FG, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "SD" -> ViewSwitcher.switchTo(View.SD);
                case "AL" -> ViewSwitcher.switchTo(View.ALGEBRA);
                case "AP" -> ViewSwitcher.switchTo(View.AP);
                case "PC" -> ViewSwitcher.switchTo(View.PC);
                case "MI" -> ViewSwitcher.switchTo(View.MI);
                case "FG" -> ViewSwitcher.switchTo(View.FG);
            }
        }
    }

    @FXML
    void Eletrotecnica2anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("2ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button ELETRONICA = new Button();
        ELETRONICA.setText("Eletrónica");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        ELETRONICA.setFont(font);
        ELETRONICA.setPrefWidth(200);
        ELETRONICA.setOnAction(mouseEvent -> {
            dialog.setResult("ELETRONICA");
        });

        Button AI = new Button();
        AI.setText("Automação Industrial");
        AI.setFont(font);
        AI.setPrefWidth(200);
        AI.setOnAction(mouseEvent -> {
            dialog.setResult("AI");
        });

        Button SC = new Button();
        SC.setText("Sistemas de Comunicação");
        SC.setFont(font);
        SC.setPrefWidth(200);
        SC.setOnAction(mouseEvent -> {
            dialog.setResult("SC");
        });

        Button TSC = new Button();
        TSC.setText("Teoria dos Sistemas e Controlo");
        TSC.setFont(font);
        TSC.setPrefWidth(200);
        TSC.setOnAction(mouseEvent -> {
            dialog.setResult("TSC");
        });

        Button PS = new Button();
        PS.setText("Processamento de Sinal");
        PS.setFont(font);
        PS.setPrefWidth(200);
        PS.setOnAction(mouseEvent -> {
            dialog.setResult("PS");
        });

        Button SM = new Button();
        SM.setText("Sistemas de Microprocessadores");
        SM.setFont(font);
        SM.setPrefWidth(200);
        SM.setOnAction(mouseEvent -> {
            dialog.setResult("SM");
        });

        grid.add(ELETRONICA, 0, 0);
        grid.add(AI, 0, 1);
        grid.add(SC, 0, 2);
        grid.add(TSC, 1, 0);
        grid.add(PS, 1, 1);
        grid.add(SM, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "ELETRONICA" -> ViewSwitcher.switchTo(View.ELETRO);
                case "AI" -> ViewSwitcher.switchTo(View.AI);
                case "SC" -> ViewSwitcher.switchTo(View.SC);
                case "TSC" -> ViewSwitcher.switchTo(View.TSC);
                case "PS" -> ViewSwitcher.switchTo(View.PS);
                case "SM" -> ViewSwitcher.switchTo(View.SM);
            }
        }
    }

    @FXML
    void Eletrotecnica3anoPressed() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("3ºAno");
        dialog.setHeaderText("1ºSemestre                                      2ºSemestre");

        //ButtonType insertButtonType = new ButtonType("Tecnologias Web", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Font font = Font.font("System", FontWeight.BOLD, 12);

        Button EP = new Button();
        EP.setText("Eletrónica de Potência");
        //TWEB.setStyle("-fx-background-color: #ffffff");
        EP.setFont(font);
        EP.setPrefWidth(200);
        EP.setOnAction(mouseEvent -> {
            dialog.setResult("EP");
        });

        Button IE = new Button();
        IE.setText("Instalações Elétricas");
        IE.setFont(font);
        IE.setPrefWidth(200);
        IE.setOnAction(mouseEvent -> {
            dialog.setResult("IE");
        });

        Button CE = new Button();
        CE.setText("Complementos de Eletrónica");
        CE.setFont(font);
        CE.setPrefWidth(200);
        CE.setOnAction(mouseEvent -> {
            dialog.setResult("CE");
        });

        Button GE = new Button();
        GE.setText("Gestão de Energia");
        GE.setFont(font);
        GE.setPrefWidth(200);
        GE.setOnAction(mouseEvent -> {
            dialog.setResult("GE");
        });

        grid.add(EP, 0, 0);
        grid.add(IE, 0, 1);
        grid.add(CE, 0, 2);
        grid.add(GE, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()) {
            switch (result.get()) {
                case "EP" -> ViewSwitcher.switchTo(View.EP);
                case "IE" -> ViewSwitcher.switchTo(View.IE);
                case "CE" -> ViewSwitcher.switchTo(View.CE);
                case "GE" -> ViewSwitcher.switchTo(View.GE);
            }
        }
    }

}