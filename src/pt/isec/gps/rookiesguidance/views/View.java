package pt.isec.gps.rookiesguidance.views;

public enum View {
    LOGIN("login.fxml"),
    REGISTO("registo.fxml"),
    HOMEPAGE("homepage.fxml"),
    HOMEPAGE_GESTORES("homepagegestores.fxml"),
    PERFIL("perfil.fxml"),
    EDITAR_PERFIL("editarPerfil.fxml"),
    EVENTOS("eventos.fxml"),
    INSCREVE_EVENTO("inscreveEvento.fxml"),
    INFORMACOES("informacoes.fxml"),
    PERGUNTAS("perguntas.fxml"),

    TWEB("cursosInfo/tweb.fxml"),
    SD("cursosInfo/sd.fxml"),
    IP("cursosInfo/ip.fxml"),
    ELETRO("cursosInfo/eletro.fxml"),
    ALGEBRA("cursosInfo/algebra.fxml"),
    AM1("cursosInfo/am1.fxml"),
    SO("cursosInfo/so.fxml"),
    BD("cursosInfo/bd.fxml"),
    POO("cursosInfo/poo.fxml"),
    SO2("cursosInfo/so2.fxml"),
    PA("cursosInfo/pa.fxml"),
    CR("cursosInfo/cr.fxml"),

    PD("cursosInfo/pd.fxml"),
    PWEB("cursosInfo/pweb.fxml"),
    AMOV("cursosInfo/amov.fxml"),
    ETICA("cursosInfo/etica.fxml"),


    DT("cursosInfo/dt.fxml"),
    QUIMICA("cursosInfo/quimica.fxml"),
    FA("cursosInfo/fa.fxml"),

    RM1("cursosInfo/rm1.fxml"),
    MH("cursosInfo/mh.fxml"),
    TM1("cursosInfo/tm1.fxml"),
    ME("cursosInfo/me.fxml"),
    MF("cursosInfo/mf.fxml"),
    TM2("cursosInfo/tm2.fxml"),

    GQ("cursosInfo/gq.fxml"),
    FM("cursosInfo/fm.fxml"),
    OG("cursosInfo/og.fxml"),
    IC("cursosInfo/ic.fxml"),

    AP("cursosInfo/ap.fxml"),
    PC("cursosInfo/pc.fxml"),
    MI("cursosInfo/mi.fxml"),
    FG("cursosInfo/fg.fxml"),

    AI("cursosInfo/ai.fxml"),
    SC("cursosInfo/sc.fxml"),
    TSC("cursosInfo/tsc.fxml"),
    PS("cursosInfo/ps.fxml"),
    SM("cursosInfo/sm.fxml"),

    EP("cursosInfo/ep.fxml"),
    IE("cursosInfo/ie.fxml"),
    CE("cursosInfo/ce.fxml"),
    GE("cursosInfo/ge.fxml");



    private String filename;

    View(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
