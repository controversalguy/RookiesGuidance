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

    TWEB("tweb.fxml"),
    SD("sd.fxml"),
    IP("ip.fxml"),
    ELETRO("eletro.fxml"),
    ALGEBRA("algebra.fxml"),
    AM1("am1.fxml"),

    SO("so.fxml"),
    BD("bd.fxml"),
    POO("poo.fxml"),
    SO2("so2.fxml"),
    PA("pa.fxml"),
    CR("cr.fxml"),

    PD("pd.fxml"),
    PWEB("pweb.fxml"),
    AMOV("amov.fxml"),
    ETICA("etica.fxml"),


    DT("dt.fxml"),
    QUIMICA("quimica.fxml"),
    FA("fa.fxml"),

    RM1("rm1.fxml"),
    MH("mh.fxml"),
    TM1("tm1.fxml"),
    ME("me.fxml"),
    MF("mf.fxml"),
    TM2("tm2.fxml"),

    GQ("gq.fxml"),
    FM("fm.fxml"),
    OG("og.fxml"),
    IC("ic.fxml"),

    AP("ap.fxml"),
    PC("pc.fxml"),
    MI("mi.fxml"),
    FG("fg.fxml"),

    AI("ai.fxml"),
    SC("sc.fxml"),
    TSC("tsc.fxml"),
    PS("ps.fxml"),
    SM("sm.fxml"),

    EP("ep.fxml"),
    IE("ie.fxml"),
    CE("ce.fxml"),
    GE("ge.fxml")
    ;




    private String filename;

    View(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

}
