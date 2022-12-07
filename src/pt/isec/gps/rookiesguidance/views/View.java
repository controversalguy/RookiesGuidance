package pt.isec.gps.rookiesguidance.views;

public enum View {
    LOGIN("login.fxml"),
    REGISTO("registo.fxml"),
    HOMEPAGE("homepage.fxml"),
    MAIN("main.fxml"),
    PERFIL("perfil.fxml"),
    EVENTOS("eventos.fxml"),
    INFORMACOES("informacoes.fxml"),
    PERGUNTAS("perguntas.fxml");

    private String filename;

    View(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
