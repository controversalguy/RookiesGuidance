package pt.isec.gps.rookiesguidance.views;

public enum View {
    LOGIN("login.fxml"),
    REGISTO("registo.fxml"),
    HOMEPAGE("homepage.fxml"),
    MAIN("main.fxml");

    private String filename;

    View(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
